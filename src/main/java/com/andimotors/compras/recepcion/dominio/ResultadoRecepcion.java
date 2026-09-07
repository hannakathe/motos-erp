package com.andimotors.compras.recepcion.dominio;

import com.andimotors.compras.contrato.EstadoPedido;

import java.util.List;

/**
 * Resultado completo de validar una recepcion (HU-15): que pedido, como cambio su
 * estado, el resultado global y el detalle linea por linea.
 *
 * @param pedidoId       pedido validado
 * @param estadoAnterior estado del pedido antes de la recepcion
 * @param estadoNuevo    estado del pedido despues de la recepcion
 * @param resultadoGlobal COMPLETA o CON_DIFERENCIAS
 * @param lineas          detalle por linea
 */
public record ResultadoRecepcion(
        Long pedidoId,
        EstadoPedido estadoAnterior,
        EstadoPedido estadoNuevo,
        ResultadoGlobalRecepcion resultadoGlobal,
        List<LineaRecepcionResultado> lineas
) {

    public ResultadoRecepcion {
        lineas = List.copyOf(lineas);
    }

    /** @return solo las lineas con faltante o sobrante (vacia si la recepcion fue completa). */
    public List<LineaRecepcionResultado> lineasConDiferencia() {
        return lineas.stream().filter(LineaRecepcionResultado::tieneDiferencia).toList();
    }

    /** @return {@code true} si la recepcion no tuvo diferencias. */
    public boolean esCompleta() {
        return resultadoGlobal == ResultadoGlobalRecepcion.COMPLETA;
    }
}
