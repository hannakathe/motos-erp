package com.andimotors.compras.recepcion.dominio.excepcion;

import com.andimotors.compras.contrato.EstadoPedido;

/**
 * El pedido esta en un estado que no admite recepcion (p. ej. ya RECIBIDO o
 * CANCELADO). Se traduce a HTTP 409 (conflicto).
 */
public class EstadoPedidoNoRecepcionableException extends RecepcionException {

    public EstadoPedidoNoRecepcionableException(Long pedidoId, EstadoPedido estadoActual) {
        super("El pedido " + pedidoId + " esta en estado " + estadoActual
                + " y no admite recepcion (solo PENDIENTE o APROBADO)");
    }
}
