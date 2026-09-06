// src/models/DetallePedido.js
// Entidad "Detalle de pedido". Se modela como sub-documento embebido dentro
// de Pedido (patrón recomendado en MongoDB para relaciones 1-a-muchos que
// siempre se leen junto con su padre), pero se define como Schema propio
// para que sea una entidad clara y reutilizable, y guarda referencia directa
// al Producto (relación con producto).

const mongoose = require('mongoose');
const { Schema } = mongoose;

const DetallePedidoSchema = new Schema(
  {
    producto: {
      type: Schema.Types.ObjectId,
      ref: 'Producto',
      required: [true, 'El detalle debe referenciar un producto'],
    },
    cantidad: {
      type: Number,
      required: [true, 'La cantidad es obligatoria'],
      min: [1, 'La cantidad debe ser mayor a 0'],
    },
    precioUnitario: {
      type: Number,
      required: [true, 'El precio unitario es obligatorio'],
      min: [0, 'El precio unitario no puede ser negativo'],
    },
    subtotal: {
      type: Number,
      required: true,
      min: 0,
    },
  },
  { _id: true }
);

// Calcula el subtotal automáticamente antes de validar el subdocumento.
DetallePedidoSchema.pre('validate', function () {
  this.subtotal = Number((this.cantidad * this.precioUnitario).toFixed(2));
});

module.exports = DetallePedidoSchema;
