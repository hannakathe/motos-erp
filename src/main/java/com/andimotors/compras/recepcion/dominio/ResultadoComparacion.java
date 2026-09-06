package com.andimotors.compras.recepcion.dominio;

import java.util.List;

/**
 * Salida pura de {@link ComparadorRecepcion}: el detalle por linea y el resultado
 * global, sin todavia conocer el pedido ni haber persistido nada.
 *
 * @param lineas resultado de cada linea del pedido
 * @param global resultado agregado
 */
public record ResultadoComparacion(
        List<LineaRecepcionResultado> lineas,
        ResultadoGlobalRecepcion global
) {

    public ResultadoComparacion {
        lineas = List.copyOf(lineas);
    }

    /** @return solo las lineas con faltante o sobrante. */
    public List<LineaRecepcionResultado> lineasConDiferencia() {
        return lineas.stream().filter(LineaRecepcionResultado::tieneDiferencia).toList();
    }
}
