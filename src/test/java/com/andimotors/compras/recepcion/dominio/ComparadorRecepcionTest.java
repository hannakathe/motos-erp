package com.andimotors.compras.recepcion.dominio;

import com.andimotors.compras.contrato.LineaPedidoContrato;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas unitarias de la logica pura de comparacion de HU-15.
 * Sin Spring, sin mocks: {@code new ComparadorRecepcion()}.
 */
@DisplayName("ComparadorRecepcion - comparacion solicitado vs recibido (HU-15)")
class ComparadorRecepcionTest {

    private final ComparadorRecepcion comparador = new ComparadorRecepcion();

    private static LineaPedidoContrato linea(long itemId, int cantidadSolicitada) {
        return new LineaPedidoContrato(itemId, 500L + itemId, "Producto " + itemId, cantidadSolicitada);
    }

    @Test
    @DisplayName("todas las lineas completas -> resultado COMPLETA y estado RECIBIDO")
    void todasCompletas() {
        List<LineaPedidoContrato> pedido = List.of(linea(1, 10), linea(2, 5));
        Map<Long, Integer> recibidas = Map.of(1L, 10, 2L, 5);

        ResultadoComparacion r = comparador.comparar(pedido, recibidas);

        assertThat(r.global()).isEqualTo(ResultadoGlobalRecepcion.COMPLETA);
        assertThat(r.global().estadoResultante().name()).isEqualTo("RECIBIDO");
        assertThat(r.lineas()).allMatch(l -> l.tipo() == TipoDiferencia.COMPLETO);
        assertThat(r.lineasConDiferencia()).isEmpty();
    }

    @Test
    @DisplayName("una linea con menos unidades -> FALTANTE, diferencia negativa, global CON_DIFERENCIAS")
    void faltanteEnUnaLinea() {
        List<LineaPedidoContrato> pedido = List.of(linea(1, 10), linea(2, 5));
        Map<Long, Integer> recibidas = Map.of(1L, 8, 2L, 5);

        ResultadoComparacion r = comparador.comparar(pedido, recibidas);

        assertThat(r.global()).isEqualTo(ResultadoGlobalRecepcion.CON_DIFERENCIAS);
        assertThat(r.global().estadoResultante().name()).isEqualTo("RECIBIDO_CON_DIFERENCIAS");

        LineaRecepcionResultado l1 = r.lineas().get(0);
        assertThat(l1.tipo()).isEqualTo(TipoDiferencia.FALTANTE);
        assertThat(l1.diferencia()).isEqualTo(-2);
        assertThat(l1.unidadesFaltantes()).isEqualTo(2);
        assertThat(l1.unidadesSobrantes()).isZero();
        assertThat(r.lineasConDiferencia()).extracting(LineaRecepcionResultado::itemId).containsExactly(1L);
    }

    @Test
    @DisplayName("una linea con mas unidades -> SOBRANTE, diferencia positiva")
    void sobranteEnUnaLinea() {
        List<LineaPedidoContrato> pedido = List.of(linea(1, 10));
        Map<Long, Integer> recibidas = Map.of(1L, 13);

        ResultadoComparacion r = comparador.comparar(pedido, recibidas);

        LineaRecepcionResultado l1 = r.lineas().get(0);
        assertThat(l1.tipo()).isEqualTo(TipoDiferencia.SOBRANTE);
        assertThat(l1.diferencia()).isEqualTo(3);
        assertThat(l1.unidadesSobrantes()).isEqualTo(3);
        assertThat(r.global()).isEqualTo(ResultadoGlobalRecepcion.CON_DIFERENCIAS);
    }

    @Test
    @DisplayName("mezcla completo + faltante + sobrante -> global CON_DIFERENCIAS y clasificacion por linea correcta")
    void mezcla() {
        List<LineaPedidoContrato> pedido = List.of(linea(1, 10), linea(2, 5), linea(3, 7));
        Map<Long, Integer> recibidas = Map.of(1L, 10, 2L, 2, 3L, 9);

        ResultadoComparacion r = comparador.comparar(pedido, recibidas);

        assertThat(r.lineas()).extracting(LineaRecepcionResultado::tipo)
                .containsExactly(TipoDiferencia.COMPLETO, TipoDiferencia.FALTANTE, TipoDiferencia.SOBRANTE);
        assertThat(r.global()).isEqualTo(ResultadoGlobalRecepcion.CON_DIFERENCIAS);
        assertThat(r.lineasConDiferencia()).extracting(LineaRecepcionResultado::itemId)
                .containsExactlyInAnyOrder(2L, 3L);
    }

    @Test
    @DisplayName("cero unidades recibidas en una linea -> faltante total")
    void ceroRecibido() {
        List<LineaPedidoContrato> pedido = List.of(linea(1, 6));
        Map<Long, Integer> recibidas = Map.of(1L, 0);

        ResultadoComparacion r = comparador.comparar(pedido, recibidas);

        LineaRecepcionResultado l1 = r.lineas().get(0);
        assertThat(l1.tipo()).isEqualTo(TipoDiferencia.FALTANTE);
        assertThat(l1.unidadesFaltantes()).isEqualTo(6);
    }
}
