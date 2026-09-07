package com.andimotors.compras.contrato;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * MOCK / CONTRATO TEMPORAL &mdash; reemplazar cuando HU-12/13/14 esten integradas.
 *
 * <p>Vista de solo lectura de una Orden de Compra / Pedido con sus items, tal como
 * HU-15 ("Validar recepcion") la necesita. NO es la entidad de persistencia: es el
 * DTO de frontera entre el modulo de Compras (HU-12/13/14, aun sin construir) y el
 * modulo de Recepcion (HU-15).</p>
 *
 * <p>Equivalencia con el codigo transitorio de HU-12 ({@code src/models/Pedido.js}):</p>
 * <ul>
 *   <li>{@code id}                   &larr; {@code _id}</li>
 *   <li>{@code proveedorId}          &larr; {@code proveedor} (ObjectId ref a Proveedor)</li>
 *   <li>{@code estado}               &larr; {@code estado} (string enum)</li>
 *   <li>{@code fechaEstimadaEntrega} &larr; campo que agrega HU-14 (no existia en HU-12)</li>
 *   <li>{@code lineas}               &larr; {@code detalles[]}</li>
 * </ul>
 *
 * @param id                   identificador del pedido
 * @param proveedorId          identificador del proveedor / fabrica-distribuidor
 * @param estado               estado actual del pedido
 * @param fechaEstimadaEntrega fecha estimada de entrega (HU-14); {@code null} si aun no se registro
 * @param lineas               items del pedido; nunca vacio
 */
public record PedidoContrato(
        Long id,
        Long proveedorId,
        EstadoPedido estado,
        LocalDate fechaEstimadaEntrega,
        List<LineaPedidoContrato> lineas
) {

    public PedidoContrato {
        Objects.requireNonNull(id, "id es obligatorio");
        Objects.requireNonNull(estado, "estado es obligatorio");
        if (lineas == null || lineas.isEmpty()) {
            // Invariante que HU-12 debe hacer cumplir al generar el pedido (caso PT-12.2).
            throw new IllegalArgumentException("El pedido debe tener al menos una linea");
        }
        lineas = List.copyOf(lineas);
    }

    /** Busca una linea del pedido por su {@code itemId}. */
    public Optional<LineaPedidoContrato> buscarLinea(Long itemId) {
        return lineas.stream().filter(l -> l.itemId().equals(itemId)).findFirst();
    }
}
