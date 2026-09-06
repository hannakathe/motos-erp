package com.andimotors.compras.contrato.mock;

import com.andimotors.compras.contrato.EstadoPedido;
import com.andimotors.compras.contrato.LineaPedidoContrato;
import com.andimotors.compras.contrato.PedidoContrato;

import java.time.LocalDate;
import java.util.List;

/**
 * MOCK &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Datos semilla en memoria que simulan pedidos ya generados por HU-12. Sirven
 * para la demo y para los casos de prueba del Sprint.</p>
 *
 * <pre>
 * Pedido 1  PENDIENTE   prov 100   items: 11(prod 500 x10), 12(prod 501 x24)   -> escenario "recepcion completa"
 * Pedido 2  APROBADO    prov 100   items: 21(prod 500 x8),  22(prod 502 x5)    -> escenario "faltante / sobrante"
 * Pedido 3  RECIBIDO    prov 101   items: 31(prod 503 x6)                       -> escenario "estado no recepcionable"
 * Pedido 4  CANCELADO   prov 101   items: 41(prod 504 x3)                       -> escenario "estado no recepcionable"
 * </pre>
 */
final class DatosSemillaMock {

    private DatosSemillaMock() {
    }

    static List<PedidoContrato> pedidos() {
        return List.of(
                new PedidoContrato(1L, 100L, EstadoPedido.PENDIENTE, null, List.of(
                        new LineaPedidoContrato(11L, 500L, "Casco MX Pro", 10),
                        new LineaPedidoContrato(12L, 501L, "Aceite 20W50 (litro)", 24)
                )),
                new PedidoContrato(2L, 100L, EstadoPedido.APROBADO, LocalDate.parse("2026-09-20"), List.of(
                        new LineaPedidoContrato(21L, 500L, "Casco MX Pro", 8),
                        new LineaPedidoContrato(22L, 502L, "Kit de arrastre 428H", 5)
                )),
                new PedidoContrato(3L, 101L, EstadoPedido.RECIBIDO, LocalDate.parse("2026-08-15"), List.of(
                        new LineaPedidoContrato(31L, 503L, "Llanta 90/90-19", 6)
                )),
                new PedidoContrato(4L, 101L, EstadoPedido.CANCELADO, null, List.of(
                        new LineaPedidoContrato(41L, 504L, "Bateria 12V 7Ah", 3)
                ))
        );
    }
}
