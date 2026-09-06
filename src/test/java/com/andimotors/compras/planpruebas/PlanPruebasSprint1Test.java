package com.andimotors.compras.planpruebas;

import com.andimotors.compras.contrato.EstadoPedido;
import com.andimotors.compras.contrato.HistoricoVentasContrato;
import com.andimotors.compras.contrato.HistoricoVentasPort;
import com.andimotors.compras.contrato.LineaPedidoContrato;
import com.andimotors.compras.contrato.PedidoContrato;
import com.andimotors.compras.contrato.PedidoContratoPort;
import com.andimotors.compras.contrato.PlaneacionPedidoPort;
import com.andimotors.compras.contrato.mock.PedidoContratoMockAdapter;
import com.andimotors.compras.recepcion.dominio.LineaRecibida;
import com.andimotors.compras.recepcion.dominio.RegistroRecepcion;
import com.andimotors.compras.recepcion.dominio.ResultadoRecepcion;
import com.andimotors.compras.recepcion.dominio.TipoDiferencia;
import com.andimotors.compras.recepcion.dominio.ValidarRecepcionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Ejecucion del plan de pruebas del Sprint 1 (docs/sprint/sprint-1-planning.md, seccion 3).
 *
 * <p>Estado de cada caso:</p>
 * <ul>
 *   <li><b>PT-15.1, PT-15.2</b> &mdash; definitivos: son de HU-15, corren contra el codigo real de esta HU.</li>
 *   <li><b>PT-12.1, PT-12.2, PT-13.1, PT-13.2, PT-14.1</b> &mdash; PENDIENTES de re-ejecutar con el
 *       codigo real de HU-12/13/14. Aqui se corren contra el <i>contrato mock</i>
 *       ({@code com.andimotors.compras.contrato}) para dejar la traza verde y fijar el comportamiento
 *       esperado; cuando HU-12/13/14 se integren, estos casos deben re-escribirse contra sus
 *       endpoints/servicios reales (ver checklist de integracion en arc42 5.7).</li>
 * </ul>
 */
@SpringBootTest
@ActiveProfiles("mock")
@DisplayName("Plan de pruebas Sprint 1 - epica Compras")
class PlanPruebasSprint1Test {

    @Autowired
    private ValidarRecepcionService recepcion;
    @Autowired
    private PedidoContratoPort pedidoPort;
    @Autowired
    private PlaneacionPedidoPort planeacionPort;
    @Autowired
    private HistoricoVentasPort historicoVentasPort;
    @Autowired
    private PedidoContratoMockAdapter mockAdapter;

    @BeforeEach
    void reiniciarDatosSemilla() {
        mockAdapter.reiniciarDatosSemilla();
    }

    @Nested
    @DisplayName("HU-12 - Generar pedido (contra el contrato mock; re-ejecutar con HU-12 real)")
    class Hu12 {

        @Test
        @DisplayName("PT-12.1 - pedido con >=1 modelo y cantidad > 0 queda en estado Pendiente")
        void pt12_1() {
            // PENDIENTE re-ejecutar con HU-12 real: crear el pedido via POST /api/pedidos y verificar la respuesta.
            PedidoContrato pedido = pedidoPort.obtenerPedido(1L).orElseThrow();

            assertThat(pedido.estado()).isEqualTo(EstadoPedido.PENDIENTE);
            assertThat(pedido.lineas()).isNotEmpty();
            assertThat(pedido.lineas()).allSatisfy(l ->
                    assertThat(l.cantidadSolicitada()).isPositive());
        }

        @Test
        @DisplayName("PT-12.2 - generar un pedido sin modelos es rechazado")
        void pt12_2() {
            // PENDIENTE re-ejecutar con HU-12 real: POST /api/pedidos con detalles=[] debe responder 400.
            // Aqui se verifica la invariante del contrato que HU-12 debe hacer cumplir.
            assertThatThrownBy(() ->
                    new PedidoContrato(9L, 100L, EstadoPedido.PENDIENTE, null, List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("al menos una linea");
        }
    }

    @Nested
    @DisplayName("HU-13 - Consultar historico de ventas (contra el mock de EIS; re-ejecutar con HU-13 real)")
    class Hu13 {

        @Test
        @DisplayName("PT-13.1 - modelo con ventas registradas muestra las unidades vendidas")
        void pt13_1() {
            // PENDIENTE re-ejecutar con EIS/HU-13 real.
            HistoricoVentasContrato h = historicoVentasPort.consultarHistoricoVentas(
                    500L, YearMonth.of(2026, 1), YearMonth.of(2026, 6));

            assertThat(h.productoId()).isEqualTo(500L);
            assertThat(h.unidadesVendidas()).isPositive();
        }

        @Test
        @DisplayName("PT-13.2 - modelo sin ventas devuelve historico en cero, sin error")
        void pt13_2() {
            // PENDIENTE re-ejecutar con EIS/HU-13 real.
            assertThatCode(() -> {
                HistoricoVentasContrato h = historicoVentasPort.consultarHistoricoVentas(
                        777L, YearMonth.of(2026, 1), YearMonth.of(2026, 6));
                assertThat(h.unidadesVendidas()).isZero();
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("HU-14 - Registrar fecha estimada de entrega (contra el contrato mock; re-ejecutar con HU-14 real)")
    class Hu14 {

        @Test
        @DisplayName("PT-14.1 - la fecha estimada queda asociada y visible en el detalle del pedido")
        void pt14_1() {
            // PENDIENTE re-ejecutar con HU-14 real: PUT/PATCH sobre el pedido y verificar el detalle.
            LocalDate fecha = LocalDate.of(2026, 10, 1);

            planeacionPort.registrarFechaEstimadaEntrega(1L, fecha);

            assertThat(pedidoPort.obtenerPedido(1L)).get()
                    .extracting(PedidoContrato::fechaEstimadaEntrega)
                    .isEqualTo(fecha);
        }
    }

    @Nested
    @DisplayName("HU-15 - Validar recepcion (definitivo)")
    class Hu15 {

        @Test
        @DisplayName("PT-15.1 - recepcion con cantidades que coinciden -> pedido RECIBIDO, sin discrepancias")
        void pt15_1() {
            PedidoContrato pedido = pedidoPort.obtenerPedido(1L).orElseThrow();
            List<LineaRecibida> exactas = pedido.lineas().stream()
                    .map(l -> new LineaRecibida(l.itemId(), l.cantidadSolicitada()))
                    .toList();

            ResultadoRecepcion r = recepcion.registrarRecepcion(new RegistroRecepcion(1L, exactas));

            assertThat(r.estadoNuevo()).isEqualTo(EstadoPedido.RECIBIDO);
            assertThat(r.esCompleta()).isTrue();
            assertThat(r.lineasConDiferencia()).isEmpty();
            assertThat(pedidoPort.obtenerPedido(1L)).get()
                    .extracting(PedidoContrato::estado).isEqualTo(EstadoPedido.RECIBIDO);
        }

        @Test
        @DisplayName("PT-15.2 - recepcion con faltantes -> senala el faltante y el modelo/cantidad afectado")
        void pt15_2() {
            PedidoContrato pedido = pedidoPort.obtenerPedido(2L).orElseThrow();
            LineaPedidoContrato primera = pedido.lineas().get(0);   // item 21, solicitado 8
            LineaPedidoContrato segunda = pedido.lineas().get(1);   // item 22, solicitado 5

            ResultadoRecepcion r = recepcion.registrarRecepcion(new RegistroRecepcion(2L, List.of(
                    new LineaRecibida(primera.itemId(), primera.cantidadSolicitada() - 2),
                    new LineaRecibida(segunda.itemId(), segunda.cantidadSolicitada()))));

            assertThat(r.estadoNuevo()).isEqualTo(EstadoPedido.RECIBIDO_CON_DIFERENCIAS);
            assertThat(r.lineasConDiferencia()).singleElement().satisfies(l -> {
                assertThat(l.itemId()).isEqualTo(primera.itemId());
                assertThat(l.productoId()).isEqualTo(primera.productoId());
                assertThat(l.productoNombre()).isEqualTo(primera.productoNombre());
                assertThat(l.tipo()).isEqualTo(TipoDiferencia.FALTANTE);
                assertThat(l.unidadesFaltantes()).isEqualTo(2);
            });
        }
    }
}
