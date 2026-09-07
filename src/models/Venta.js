// src/models/Venta.js
// Entidad principal de HU-13 (Consultar histórico de ventas).
const mongoose = require('mongoose');
const { Schema } = mongoose;
const DetalleVentaSchema = require('./DetalleVenta');

const VentaSchema = new Schema(
  {
    numeroFactura: {
      type: String,
      required: [true, 'El número de factura es obligatorio'],
      unique: true,
      trim: true,
    },
    fecha: {
      type: Date,
      default: Date.now,
    },
    clienteNombre: {
      type: String,
      required: [true, 'El nombre del cliente es obligatorio'],
      trim: true,
    },
    vendedorNombre: {
      type: String,
      required: [true, 'El nombre del vendedor es obligatorio'],
      trim: true,
    },
    items: {
      type: [DetalleVentaSchema],
      validate: {
        validator: (items) => Array.isArray(items) && items.length > 0,
        message: 'La venta debe tener al menos un ítem',
      },
    },
    total: {
      type: Number,
      required: true,
      min: 0,
    },
  },
  { timestamps: true }
);

// Recalcula el total como suma de los subtotales de cada ítem.
VentaSchema.pre('validate', function () {
  if (Array.isArray(this.items)) {
    this.total = Number(
      this.items.reduce((acc, i) => acc + (i.subtotal || i.cantidad * i.precioUnitario), 0).toFixed(2)
    );
  }
});

module.exports = mongoose.models.Venta || mongoose.model('Venta', VentaSchema);