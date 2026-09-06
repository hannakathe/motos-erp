package com.andimotors.compras.contrato;

import java.util.List;
import java.util.Optional;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Puerto de <b>consulta</b> de pedidos (DIP): HU-15 depende de esta abstraccion,
 * no de una implementacion concreta. Hoy la implementa
 * {@code com.andimotors.compras.contrato.mock.PedidoContratoMockAdapter}; cuando
 * HU-12 exista, la implementara un adaptador JPA sobre la entidad real {@code Pedido}.</p>
 *
 * <p>Contrato que el modulo de Compras (HU-12/13/14) debe cumplir para exponer sus
 * pedidos a Recepcion.</p>
 */
public interface PedidoContratoPort {

    /**
     * Obtiene un pedido con todos sus items.
     *
     * @param pedidoId identificador del pedido
     * @return el pedido, o {@link Optional#empty()} si no existe
     */
    Optional<PedidoContrato> obtenerPedido(Long pedidoId);

    /**
     * Lista los pedidos que estan en alguno de los estados indicados. Lo usa la
     * pantalla de seleccion "pedidos por recibir" (estados PENDIENTE / APROBADO).
     *
     * @param estados estados a incluir; si no se pasa ninguno, devuelve lista vacia
     * @return pedidos coincidentes (puede ser vacia)
     */
    List<PedidoContrato> listarPedidosPorEstado(EstadoPedido... estados);
}
