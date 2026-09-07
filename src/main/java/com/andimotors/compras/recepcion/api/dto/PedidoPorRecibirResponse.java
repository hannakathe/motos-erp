package com.andimotors.compras.recepcion.api.dto;

/**
 * Fila de la pantalla de seleccion "pedidos por recibir".
 *
 * @param pedidoId             identificador del pedido
 * @param proveedorId          proveedor / fabrica-distribuidor
 * @param estado               estado actual (PENDIENTE | APROBADO)
 * @param fechaEstimadaEntrega fecha estimada (ISO-8601) o {@code null}
 * @param numeroLineas         cantidad de items del pedido
 */
public record PedidoPorRecibirResponse(
        Long pedidoId,
        Long proveedorId,
        String estado,
        String fechaEstimadaEntrega,
        int numeroLineas
) {
}
