package com.andimotors.compras.recepcion.dominio.excepcion;

/** El pedido indicado para la recepcion no existe. Se traduce a HTTP 404. */
public class PedidoNoEncontradoException extends RecepcionException {

    public PedidoNoEncontradoException(Long pedidoId) {
        super("El pedido " + pedidoId + " no existe");
    }
}
