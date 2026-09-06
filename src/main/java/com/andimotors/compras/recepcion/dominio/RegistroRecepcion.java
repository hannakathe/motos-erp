package com.andimotors.compras.recepcion.dominio;

import java.util.List;

/**
 * Comando de entrada de HU-15: el pedido que llega a bodega y las cantidades
 * recibidas por cada una de sus lineas.
 *
 * @param pedidoId identificador del pedido a validar
 * @param lineas   una entrada por cada linea del pedido
 */
public record RegistroRecepcion(Long pedidoId, List<LineaRecibida> lineas) {

    public RegistroRecepcion {
        lineas = lineas == null ? List.of() : List.copyOf(lineas);
    }
}
