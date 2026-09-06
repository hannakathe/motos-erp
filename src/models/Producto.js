// src/models/Producto.js
// NOTA: Este archivo es de REFERENCIA. Si el modelo Producto ya existe en el
// repo (creado por otro integrante), NO lo dupliques: usa el existente y
// ajusta las rutas de "require" en Pedido.js y pedido.service.js.
// Se incluye aquí solo para que el modelo Pedido tenga con qué relacionarse
// y puedas probar HU-12 de forma aislada si hace falta.

const mongoose = require('mongoose');
const { Schema } = mongoose;

const ProductoSchema = new Schema(
  {
    nombre: { type: String, required: true, trim: true },
    sku: { type: String, required: true, unique: true, trim: true },
    precio: { type: Number, required: true, min: 0 },
    stock: { type: Number, required: true, min: 0, default: 0 },
    proveedor: {
      type: Schema.Types.ObjectId,
      ref: 'Proveedor',
      required: true,
    },
    activo: { type: Boolean, default: true },
  },
  { timestamps: true }
);

module.exports = mongoose.models.Producto || mongoose.model('Producto', ProductoSchema);
