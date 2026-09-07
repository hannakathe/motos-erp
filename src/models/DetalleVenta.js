// src/models/DetalleVenta.js
// Subdocumento embebido en Venta: un ítem vendido dentro de una factura.
const mongoose = require('mongoose');
const { Schema } = mongoose;

const DetalleVentaSchema = new Schema(
  {
    producto: {
      type: Schema.Types.ObjectId,
      ref: 'Producto',
      required: [true, 'Cada detalle de venta debe referenciar un producto'],
    },
    cantidad: {
      type: Number,
      required: true,
      min: [1, 'La cantidad debe ser mayor a 0'],
    },
    precioUnitario: {
      type: Number,
      required: true,
      min: 0,
    },
    subtotal: {
      type: Number,
      required: true,
      min: 0,
    },
  },
  { _id: false }
);

module.exports = DetalleVentaSchema;