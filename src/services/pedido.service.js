// src/services/pedido.service.js
// Lógica de negocio de HU-12: Generar pedido.

const Pedido = require('../models/Pedido');
const Producto = require('../models/Producto');
const Proveedor = require('../models/Proveedor');
const { DomainError, NotFoundError } = require('./errors');

/**
 * Valida y arma los detalles a partir del payload recibido, cruzando cada
 * producto contra la base de datos (existencia, estado activo y que
 * pertenezca al proveedor del pedido).
 */
async function construirDetalles(detallesInput, proveedorId) {
  const productoIds = detallesInput.map((d) => d.producto);
  const productos = await Producto.find({ _id: { $in: productoIds } });

  const productosPorId = new Map(productos.map((p) => [p._id.toString(), p]));

  const detalles = detallesInput.map((item) => {
    const producto = productosPorId.get(item.producto.toString());

    if (!producto) {
      throw new NotFoundError(`Producto ${item.producto} no existe`);
    }
    if (!producto.activo) {
      throw new DomainError(`El producto "${producto.nombre}" no está activo`);
    }
    if (producto.proveedor.toString() !== proveedorId.toString()) {
      throw new DomainError(
        `El producto "${producto.nombre}" no pertenece al proveedor indicado`
      );
    }
    if (item.cantidad <= 0) {
      throw new DomainError('La cantidad debe ser mayor a 0');
    }

    const precioUnitario =
      item.precioUnitario !== undefined ? item.precioUnitario : producto.precio;

    return {
      producto: producto._id,
      cantidad: item.cantidad,
      precioUnitario,
      subtotal: Number((item.cantidad * precioUnitario).toFixed(2)),
    };
  });

  return detalles;
}

/**
 * Genera (crea) un pedido nuevo.
 * @param {Object} data - { proveedor, detalles: [{producto, cantidad, precioUnitario?}], observaciones? }
 */
async function generarPedido(data) {
  const proveedor = await Proveedor.findById(data.proveedor);
  if (!proveedor) {
    throw new NotFoundError('El proveedor indicado no existe');
  }
  if (!proveedor.activo) {
    throw new DomainError('El proveedor indicado no está activo');
  }

  if (!Array.isArray(data.detalles) || data.detalles.length === 0) {
    throw new DomainError('El pedido debe incluir al menos un detalle');
  }

  const detalles = await construirDetalles(data.detalles, data.proveedor);
  const total = Number(detalles.reduce((acc, d) => acc + d.subtotal, 0).toFixed(2));

  const pedido = new Pedido({
    proveedor: data.proveedor,
    detalles,
    observaciones: data.observaciones,
    total,
    creadoPor: data.creadoPor,
  });

  await pedido.save();
  return pedido.populate(['proveedor', 'detalles.producto']);
}

async function listarPedidos(filtros = {}) {
  const query = {};
  if (filtros.estado) query.estado = filtros.estado;
  if (filtros.proveedor) query.proveedor = filtros.proveedor;

  return Pedido.find(query)
    .populate('proveedor')
    .populate('detalles.producto')
    .sort({ createdAt: -1 });
}

async function obtenerPedidoPorId(id) {
  const pedido = await Pedido.findById(id)
    .populate('proveedor')
    .populate('detalles.producto');

  if (!pedido) {
    throw new NotFoundError('Pedido no encontrado');
  }
  return pedido;
}

module.exports = {
  generarPedido,
  listarPedidos,
  obtenerPedidoPorId,
};
