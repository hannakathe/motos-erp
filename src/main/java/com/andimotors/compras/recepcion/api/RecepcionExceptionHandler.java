package com.andimotors.compras.recepcion.api;

import com.andimotors.compras.recepcion.api.dto.ErrorRespuesta;
import com.andimotors.compras.recepcion.dominio.excepcion.CantidadRecibidaInvalidaException;
import com.andimotors.compras.recepcion.dominio.excepcion.EstadoPedidoNoRecepcionableException;
import com.andimotors.compras.recepcion.dominio.excepcion.LineaDesconocidaException;
import com.andimotors.compras.recepcion.dominio.excepcion.PedidoNoEncontradoException;
import com.andimotors.compras.recepcion.dominio.excepcion.RegistroRecepcionIncompletoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Traduce las excepciones de dominio de HU-15 a codigos HTTP:
 * <ul>
 *   <li>404 &mdash; pedido inexistente</li>
 *   <li>409 &mdash; pedido en estado no recepcionable</li>
 *   <li>400 &mdash; datos de recepcion invalidos (cantidad negativa, linea ajena,
 *       registro incompleto, payload mal formado)</li>
 * </ul>
 */
@RestControllerAdvice(assignableTypes = RecepcionController.class)
public class RecepcionExceptionHandler {

    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> pedidoNoEncontrado(PedidoNoEncontradoException ex) {
        return build(HttpStatus.NOT_FOUND, "PEDIDO_NO_ENCONTRADO", ex.getMessage());
    }

    @ExceptionHandler(EstadoPedidoNoRecepcionableException.class)
    public ResponseEntity<ErrorRespuesta> estadoNoRecepcionable(EstadoPedidoNoRecepcionableException ex) {
        return build(HttpStatus.CONFLICT, "ESTADO_NO_RECEPCIONABLE", ex.getMessage());
    }

    @ExceptionHandler({
            CantidadRecibidaInvalidaException.class,
            LineaDesconocidaException.class,
            RegistroRecepcionIncompletoException.class
    })
    public ResponseEntity<ErrorRespuesta> datosInvalidos(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, "RECEPCION_INVALIDA", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespuesta> payloadInvalido(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, "PAYLOAD_INVALIDO", detalle);
    }

    private ResponseEntity<ErrorRespuesta> build(HttpStatus status, String codigo, String mensaje) {
        return ResponseEntity.status(status).body(new ErrorRespuesta(codigo, mensaje));
    }
}
