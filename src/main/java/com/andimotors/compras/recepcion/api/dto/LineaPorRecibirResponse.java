package com.andimotors.compras.recepcion.api.dto;

/**
 * Linea de un pedido mostrada en el formulario de recepcion (para precargar
 * "cantidad solicitada" y capturar "cantidad recibida").
 *
 * @param itemId             linea del pedido
 * @param productoId         producto / modelo
 * @param producto           nombre del producto
 * @param cantidadSolicitada unidades pedidas
 */
public record LineaPorRecibirResponse(
        Long itemId,
        Long productoId,
        String producto,
        int cantidadSolicitada
) {
}
