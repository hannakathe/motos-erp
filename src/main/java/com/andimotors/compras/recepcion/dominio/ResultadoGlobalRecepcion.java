package com.andimotors.compras.recepcion.dominio;

import com.andimotors.compras.contrato.EstadoPedido;

/**
 * Resultado global de validar una recepcion completa (HU-15).
 */
public enum ResultadoGlobalRecepcion {

    /** Todas las lineas se recibieron completas. */
    COMPLETA(EstadoPedido.RECIBIDO),

    /** Al menos una linea quedo con faltante o sobrante. */
    CON_DIFERENCIAS(EstadoPedido.RECIBIDO_CON_DIFERENCIAS);

    private final EstadoPedido estadoResultante;

    ResultadoGlobalRecepcion(EstadoPedido estadoResultante) {
        this.estadoResultante = estadoResultante;
    }

    /** Estado al que pasa el pedido cuando la recepcion arroja este resultado. */
    public EstadoPedido estadoResultante() {
        return estadoResultante;
    }
}
