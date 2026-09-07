package com.andimotors.compras.recepcion.dominio;

import com.andimotors.compras.contrato.EstadoPedido;
import com.andimotors.compras.contrato.LineaPedidoContrato;
import com.andimotors.compras.contrato.PedidoContrato;
import com.andimotors.compras.contrato.PedidoContratoPort;
import com.andimotors.compras.contrato.RecepcionPedidoPort;
import com.andimotors.compras.recepcion.dominio.excepcion.CantidadRecibidaInvalidaException;
import com.andimotors.compras.recepcion.dominio.excepcion.EstadoPedidoNoRecepcionableException;
import com.andimotors.compras.recepcion.dominio.excepcion.LineaDesconocidaException;
import com.andimotors.compras.recepcion.dominio.excepcion.PedidoNoEncontradoException;
import com.andimotors.compras.recepcion.dominio.excepcion.RegistroRecepcionIncompletoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias del caso de uso HU-15 "Validar recepcion".
 * Los puertos del contrato estan mockeados; la comparacion usa la clase real.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ValidarRecepcionService (HU-15)")
class ValidarRecepcionServiceTest {

    @Mock
    private PedidoContratoPort pedidoPort;

    @Mock
    private RecepcionPedidoPort recepcionPort;

    private ValidarRecepcionService servicio;

    private static final long PEDIDO = 1L;

    @BeforeEach
    void setUp() {
        servicio = new ValidarRecepcionService(pedidoPort, recepcionPort, new ComparadorRecepcion());
    }

    private ValidarRecepcionService servicio() {
        return servicio;
    }

    private static LineaPedidoContrato linea(long itemId, int cantidad) {
        return new LineaPedidoContrato(itemId, 500L + itemId, "Producto " + itemId, cantidad);
    }

    private static PedidoContrato pedido(EstadoPedido estado, LineaPedidoContrato... lineas) {
        return new PedidoContrato(PEDIDO, 100L, estado, null, List.of(lineas));
    }

    // --- Camino feliz -----------------------------------------------------------

    @Test
    @DisplayName("recepcion sin diferencias -> estado RECIBIDO y persiste cantidades")
    void recepcionCompleta() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.PENDIENTE, linea(11, 10), linea(12, 5))));

        ResultadoRecepcion resultado = servicio().registrarRecepcion(new RegistroRecepcion(PEDIDO,
                List.of(new LineaRecibida(11L, 10), new LineaRecibida(12L, 5))));

        assertThat(resultado.estadoAnterior()).isEqualTo(EstadoPedido.PENDIENTE);
        assertThat(resultado.estadoNuevo()).isEqualTo(EstadoPedido.RECIBIDO);
        assertThat(resultado.resultadoGlobal()).isEqualTo(ResultadoGlobalRecepcion.COMPLETA);
        assertThat(resultado.lineasConDiferencia()).isEmpty();

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Map<Long, Integer>> cantidades = ArgumentCaptor.forClass(Map.class);
        verify(recepcionPort).aplicarResultadoRecepcion(
                eq(PEDIDO), eq(EstadoPedido.RECIBIDO), cantidades.capture());
        assertThat(cantidades.getValue()).containsEntry(11L, 10).containsEntry(12L, 5);
    }

    @Test
    @DisplayName("recepcion con faltante -> estado RECIBIDO_CON_DIFERENCIAS y se reporta la linea afectada")
    void recepcionConFaltante() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.APROBADO, linea(11, 10), linea(12, 5))));

        ResultadoRecepcion resultado = servicio().registrarRecepcion(new RegistroRecepcion(PEDIDO,
                List.of(new LineaRecibida(11L, 8), new LineaRecibida(12L, 5))));

        assertThat(resultado.estadoNuevo()).isEqualTo(EstadoPedido.RECIBIDO_CON_DIFERENCIAS);
        assertThat(resultado.resultadoGlobal()).isEqualTo(ResultadoGlobalRecepcion.CON_DIFERENCIAS);
        assertThat(resultado.lineasConDiferencia()).singleElement()
                .satisfies(l -> {
                    assertThat(l.itemId()).isEqualTo(11L);
                    assertThat(l.tipo()).isEqualTo(TipoDiferencia.FALTANTE);
                    assertThat(l.unidadesFaltantes()).isEqualTo(2);
                });
        verify(recepcionPort).aplicarResultadoRecepcion(
                eq(PEDIDO), eq(EstadoPedido.RECIBIDO_CON_DIFERENCIAS), anyMap());
    }

    // --- Validaciones ---------------------------------------------------------

    @Test
    @DisplayName("pedido inexistente -> PedidoNoEncontradoException y no persiste")
    void pedidoNoExiste() {
        when(pedidoPort.obtenerPedido(PEDIDO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio().registrarRecepcion(
                new RegistroRecepcion(PEDIDO, List.of(new LineaRecibida(11L, 1)))))
                .isInstanceOf(PedidoNoEncontradoException.class);

        verifyNoInteractions(recepcionPort);
    }

    @Test
    @DisplayName("pedido ya RECIBIDO -> EstadoPedidoNoRecepcionableException")
    void pedidoYaRecibido() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.RECIBIDO, linea(11, 10))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(
                new RegistroRecepcion(PEDIDO, List.of(new LineaRecibida(11L, 10)))))
                .isInstanceOf(EstadoPedidoNoRecepcionableException.class);

        verifyNoInteractions(recepcionPort);
    }

    @Test
    @DisplayName("pedido CANCELADO -> EstadoPedidoNoRecepcionableException")
    void pedidoCancelado() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.CANCELADO, linea(11, 10))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(
                new RegistroRecepcion(PEDIDO, List.of(new LineaRecibida(11L, 10)))))
                .isInstanceOf(EstadoPedidoNoRecepcionableException.class);
    }

    @Test
    @DisplayName("cantidad recibida negativa -> CantidadRecibidaInvalidaException y no persiste")
    void cantidadNegativa() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.PENDIENTE, linea(11, 10))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(
                new RegistroRecepcion(PEDIDO, List.of(new LineaRecibida(11L, -1)))))
                .isInstanceOf(CantidadRecibidaInvalidaException.class);

        verify(recepcionPort, never()).aplicarResultadoRecepcion(any(), any(), anyMap());
    }

    @Test
    @DisplayName("linea que no pertenece al pedido -> LineaDesconocidaException")
    void lineaAjena() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.PENDIENTE, linea(11, 10))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(new RegistroRecepcion(PEDIDO,
                List.of(new LineaRecibida(11L, 10), new LineaRecibida(99L, 1)))))
                .isInstanceOf(LineaDesconocidaException.class);
    }

    @Test
    @DisplayName("falta registrar una linea del pedido -> RegistroRecepcionIncompletoException")
    void registroIncompleto() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.PENDIENTE, linea(11, 10), linea(12, 5))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(
                new RegistroRecepcion(PEDIDO, List.of(new LineaRecibida(11L, 10)))))
                .isInstanceOf(RegistroRecepcionIncompletoException.class);
    }

    @Test
    @DisplayName("linea repetida en el registro -> RegistroRecepcionIncompletoException")
    void lineaRepetida() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.PENDIENTE, linea(11, 10))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(new RegistroRecepcion(PEDIDO,
                List.of(new LineaRecibida(11L, 4), new LineaRecibida(11L, 6)))))
                .isInstanceOf(RegistroRecepcionIncompletoException.class);
    }

    @Test
    @DisplayName("registro sin lineas -> RegistroRecepcionIncompletoException")
    void registroVacio() {
        when(pedidoPort.obtenerPedido(PEDIDO))
                .thenReturn(Optional.of(pedido(EstadoPedido.PENDIENTE, linea(11, 10))));

        assertThatThrownBy(() -> servicio().registrarRecepcion(new RegistroRecepcion(PEDIDO, List.of())))
                .isInstanceOf(RegistroRecepcionIncompletoException.class);
    }
}
