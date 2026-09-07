package com.andimotors.compras.recepcion.dominio.excepcion;

/** Se registro una cantidad recibida negativa. Se traduce a HTTP 400. */
public class CantidadRecibidaInvalidaException extends RecepcionException {

    public CantidadRecibidaInvalidaException(Long itemId, int cantidad) {
        super("La cantidad recibida de la linea " + itemId + " es " + cantidad
                + "; no puede ser negativa");
    }
}
