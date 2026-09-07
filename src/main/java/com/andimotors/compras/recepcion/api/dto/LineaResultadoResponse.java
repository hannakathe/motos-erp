package com.andimotors.compras.recepcion.api.dto;

/**
 * Detalle por linea del resultado de una recepcion.
 *
 * @param itemId             linea del pedido
 * @param productoId         producto / modelo
 * @param producto           nombre del producto
 * @param cantidadSolicitada unidades pedidas
 * @param cantidadRecibida   unidades recibidas
 * @param diferencia         recibida - solicitada (negativo = faltante)
 * @param tipo               COMPLETO | FALTANTE | SOBRANTE
 */
public record LineaResultadoResponse(
        Long itemId,
        Long productoId,
        String producto,
        int cantidadSolicitada,
        int cantidadRecibida,
        int diferencia,
        String tipo
) {
}
