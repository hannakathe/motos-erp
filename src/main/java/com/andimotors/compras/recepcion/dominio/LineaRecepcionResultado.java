package com.andimotors.compras.recepcion.dominio;

/**
 * Resultado de comparar una linea del pedido contra lo recibido (HU-15).
 *
 * @param itemId             identificador de la linea del pedido
 * @param productoId         producto / modelo de la linea
 * @param productoNombre     nombre legible del producto
 * @param cantidadSolicitada unidades pedidas en la orden de compra
 * @param cantidadRecibida   unidades efectivamente recibidas en bodega
 * @param diferencia         {@code cantidadRecibida - cantidadSolicitada}
 *                           (negativa = faltante, positiva = sobrante)
 * @param tipo               clasificacion de la linea
 */
public record LineaRecepcionResultado(
        Long itemId,
        Long productoId,
        String productoNombre,
        int cantidadSolicitada,
        int cantidadRecibida,
        int diferencia,
        TipoDiferencia tipo
) {

    /** @return {@code true} si la linea tiene faltante o sobrante. */
    public boolean tieneDiferencia() {
        return tipo != TipoDiferencia.COMPLETO;
    }

    /** @return magnitud del faltante (0 si no hay faltante). */
    public int unidadesFaltantes() {
        return tipo == TipoDiferencia.FALTANTE ? -diferencia : 0;
    }

    /** @return magnitud del sobrante (0 si no hay sobrante). */
    public int unidadesSobrantes() {
        return tipo == TipoDiferencia.SOBRANTE ? diferencia : 0;
    }
}
