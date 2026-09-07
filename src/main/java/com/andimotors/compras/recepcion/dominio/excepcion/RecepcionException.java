package com.andimotors.compras.recepcion.dominio.excepcion;

/**
 * Base de los errores de dominio de HU-15 (validar recepcion). La capa de API la
 * traduce a codigos HTTP en {@code RecepcionExceptionHandler}.
 */
public abstract class RecepcionException extends RuntimeException {

    protected RecepcionException(String mensaje) {
        super(mensaje);
    }
}
