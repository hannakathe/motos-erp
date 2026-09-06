// src/controllers/pedido.controller.js
const pedidoService = require('../services/pedido.service');
const { DomainError } = require('../services/errors');

async function crearPedido(req, res, next) {
  try {
    const pedido = await pedidoService.generarPedido({
      proveedor: req.body.proveedor,
      detalles: req.body.detalles,
      observaciones: req.body.observaciones,
      creadoPor: req.user ? req.user.id : undefined, // ajustar según auth del proyecto
    });
    return res.status(201).json(pedido);
  } catch (err) {
    return next(err);
  }
}

async function listarPedidos(req, res, next) {
  try {
    const pedidos = await pedidoService.listarPedidos({
      estado: req.query.estado,
      proveedor: req.query.proveedor,
    });
    return res.status(200).json(pedidos);
  } catch (err) {
    return next(err);
  }
}

async function obtenerPedido(req, res, next) {
  try {
    const pedido = await pedidoService.obtenerPedidoPorId(req.params.id);
    return res.status(200).json(pedido);
  } catch (err) {
    return next(err);
  }
}

// --- HU-14: Registrar fecha estimada ---
async function actualizarFechaEstimada(req, res, next) {
  try {
    const pedido = await pedidoService.actualizarFechaEstimada(
      req.params.id,
      req.body.fechaEstimada
    );
    return res.status(200).json(pedido);
  } catch (err) {
    return next(err);
  }
}

// Middleware de manejo de errores específico de este dominio; se puede
// registrar en el error handler global del proyecto en su lugar.
function pedidoErrorHandler(err, req, res, next) {
  if (err instanceof DomainError) {
    return res.status(err.statusCode).json({ mensaje: err.message });
  }
  return next(err);
}

module.exports = {
  crearPedido,
  listarPedidos,
  obtenerPedido,
  actualizarFechaEstimada,
  pedidoErrorHandler,
};
