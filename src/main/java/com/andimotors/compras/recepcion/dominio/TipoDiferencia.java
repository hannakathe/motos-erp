package com.andimotors.compras.recepcion.dominio;

/**
 * Clasificacion de una linea al comparar lo solicitado en la orden de compra
 * contra lo efectivamente recibido en bodega (HU-15).
 */
public enum TipoDiferencia {

    /** Se recibio exactamente la cantidad solicitada. */
    COMPLETO,

    /** Se recibio menos de lo solicitado. */
    FALTANTE,

    /** Se recibio mas de lo solicitado. */
    SOBRANTE;

    /**
     * Deriva el tipo a partir de la diferencia {@code recibida - solicitada}.
     *
     * @param diferencia recibida menos solicitada
     * @return {@link #COMPLETO} si es 0, {@link #FALTANTE} si es negativa,
     *         {@link #SOBRANTE} si es positiva
     */
    public static TipoDiferencia desde(int diferencia) {
        if (diferencia == 0) {
            return COMPLETO;
        }
        return diferencia < 0 ? FALTANTE : SOBRANTE;
    }
}
