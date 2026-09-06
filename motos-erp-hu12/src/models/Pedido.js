// src/models/Pedido.js
// Entidad principal de HU-12 (Generar pedido).

const mongoose = require('mongoose');
const { Schema } = mongoose;
const DetallePedidoSchema = require('./DetallePedido');

const ESTADOS_PEDIDO = ['pendiente', 'aprobado', 'recibido', 'cancelado'];

const PedidoSchema = new Schema(
  {
    proveedor: {
      type: Schema.Types.ObjectId,
      ref: 'Proveedor',
      required: [true, 'El pedido debe estar asociado a un proveedor'],
    },
    detalles: {
      type: [DetallePedidoSchema],
      validate: {
        validator: (detalles) => Array.isArray(detalles) && detalles.length > 0,
        message: 'El pedido debe tener al menos un detalle (producto)',
      },
    },
    fechaPedido: {
      type: Date,
      default: Date.now,
    },
    estado: {
      type: String,
      enum: ESTADOS_PEDIDO,
      default: 'pendiente',
    },
    observaciones: {
      type: String,
      trim: true,
      maxlength: 500,
    },
    total: {
      type: Number,
      required: true,
      min: 0,
    },
    creadoPor: {
      type: Schema.Types.ObjectId,
      ref: 'Usuario', // ajustar al nombre real del modelo de usuario del repo
    },
  },
  { timestamps: true }
);

// Recalcula el total como suma de los subtotales de cada detalle.
PedidoSchema.pre('validate', function (next) {
  if (Array.isArray(this.detalles)) {
    this.total = Number(
      this.detalles.reduce((acc, d) => acc + (d.subtotal || d.cantidad * d.precioUnitario), 0).toFixed(2)
    );
  }
  next();
});

PedidoSchema.statics.ESTADOS_PEDIDO = ESTADOS_PEDIDO;

module.exports = mongoose.models.Pedido || mongoose.model('Pedido', PedidoSchema);
