package com.andimotors.compras.contrato;

import java.util.Objects;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Linea (item) de un {@link PedidoContrato}: un producto y la cantidad solicitada
 * en el pedido. Es la informacion minima que HU-15 necesita de cada item para poder
 * comparar contra lo recibido.</p>
 *
 * <p>Equivalencia con el codigo transitorio de HU-12 ({@code src/models/DetallePedido.js}):</p>
 * <ul>
 *   <li>{@code itemId}            &larr; {@code _id} del subdocumento DetallePedido</li>
 *   <li>{@code productoId}        &larr; {@code producto} (ObjectId ref a Producto)</li>
 *   <li>{@code productoNombre}    &larr; {@code producto.nombre} (populado)</li>
 *   <li>{@code cantidadSolicitada} &larr; {@code cantidad}</li>
 * </ul>
 *
 * @param itemId             identificador de la linea dentro del pedido (no del producto)
 * @param productoId          identificador del producto / modelo solicitado
 * @param productoNombre      nombre legible del producto (para la pantalla de recepcion)
 * @param cantidadSolicitada  unidades pedidas en la orden de compra; siempre &gt; 0
 */
public record LineaPedidoContrato(
        Long itemId,
        Long productoId,
        String productoNombre,
        int cantidadSolicitada
) {

    public LineaPedidoContrato {
        Objects.requireNonNull(itemId, "itemId es obligatorio");
        Objects.requireNonNull(productoId, "productoId es obligatorio");
        if (cantidadSolicitada <= 0) {
            throw new IllegalArgumentException(
                    "cantidadSolicitada debe ser mayor a 0 (linea " + itemId + ")");
        }
    }
}
