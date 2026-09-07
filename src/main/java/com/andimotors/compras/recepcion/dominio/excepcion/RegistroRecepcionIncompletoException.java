package com.andimotors.compras.recepcion.dominio.excepcion;

/**
 * El registro de recepcion no cubre exactamente una vez cada linea del pedido
 * (lista vacia, linea del pedido sin registrar, o linea repetida).
 * Se traduce a HTTP 400.
 */
public class RegistroRecepcionIncompletoException extends RecepcionException {

    public RegistroRecepcionIncompletoException(String detalle) {
        super("El registro de recepcion es invalido: " + detalle);
    }
}
