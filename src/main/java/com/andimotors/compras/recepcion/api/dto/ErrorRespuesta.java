package com.andimotors.compras.recepcion.api.dto;

/**
 * Cuerpo estandar de error de la API de recepcion.
 *
 * @param error   codigo corto y estable del error (p. ej. {@code PEDIDO_NO_ENCONTRADO})
 * @param mensaje descripcion legible
 */
public record ErrorRespuesta(String error, String mensaje) {
}
