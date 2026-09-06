// src/models/Proveedor.js
// NOTA: Igual que Producto.js, es de REFERENCIA. Si ya existe en el repo,
// usa el existente.

const mongoose = require('mongoose');
const { Schema } = mongoose;

const ProveedorSchema = new Schema(
  {
    nombre: { type: String, required: true, trim: true },
    nit: { type: String, required: true, unique: true, trim: true },
    telefono: { type: String, trim: true },
    email: { type: String, trim: true },
    activo: { type: Boolean, default: true },
  },
  { timestamps: true }
);

module.exports = mongoose.models.Proveedor || mongoose.model('Proveedor', ProveedorSchema);
