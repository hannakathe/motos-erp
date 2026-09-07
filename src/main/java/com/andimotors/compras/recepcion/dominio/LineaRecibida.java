package com.andimotors.compras.recepcion.dominio;

/**
 * Dato de entrada de HU-15: unidades recibidas para una linea del pedido.
 *
 * @param itemId           identificador de la linea del pedido
 * @param cantidadRecibida unidades contadas en bodega (no puede ser negativa)
 */
public record LineaRecibida(Long itemId, int cantidadRecibida) {
}
