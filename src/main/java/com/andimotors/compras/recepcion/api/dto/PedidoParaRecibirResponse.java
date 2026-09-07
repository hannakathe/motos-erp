package com.andimotors.compras.recepcion.api.dto;

import java.util.List;

/**
 * Respuesta de {@code GET /api/compras/recepciones/pedidos/{pedidoId}}: el pedido
 * y sus lineas para armar el formulario de recepcion.
 *
 * @param pedidoId identificador del pedido
 * @param estado   estado actual del pedido
 * @param lineas   items del pedido
 */
public record PedidoParaRecibirResponse(
        Long pedidoId,
        String estado,
        List<LineaPorRecibirResponse> lineas
) {
}
