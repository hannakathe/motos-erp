package com.andimotors.compras.recepcion.api.dto;

import java.util.List;

/**
 * Respuesta de {@code POST /api/compras/recepciones}.
 *
 * @param pedidoId        pedido validado
 * @param estadoAnterior  estado antes de la recepcion
 * @param estadoNuevo     estado despues de la recepcion (RECIBIDO | RECIBIDO_CON_DIFERENCIAS)
 * @param resultadoGlobal COMPLETA | CON_DIFERENCIAS
 * @param lineas          detalle por linea
 */
public record RecepcionResultadoResponse(
        Long pedidoId,
        String estadoAnterior,
        String estadoNuevo,
        String resultadoGlobal,
        List<LineaResultadoResponse> lineas
) {
}
