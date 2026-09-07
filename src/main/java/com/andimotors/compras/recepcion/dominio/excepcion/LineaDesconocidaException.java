package com.andimotors.compras.recepcion.dominio.excepcion;

/**
 * El registro de recepcion incluye un {@code itemId} que no pertenece al pedido.
 * Se traduce a HTTP 400.
 */
public class LineaDesconocidaException extends RecepcionException {

    public LineaDesconocidaException(Long pedidoId, Long itemId) {
        super("La linea " + itemId + " no pertenece al pedido " + pedidoId);
    }
}
