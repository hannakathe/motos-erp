// src/controllers/venta.controller.js
const ventaService = require('../services/venta.service');
const { DomainError } = require('../services/errors');

async function historico(req, res, next) {
  try {
    const resultado = await ventaService.listarHistorico({
      fechaInicio: req.query.fechaInicio,
      fechaFin: req.query.fechaFin,
      vendedorNombre: req.query.vendedorNombre,
    });
    return res.status(200).json(resultado);
  } catch (err) {
    return next(err);
  }
}

// Middleware de manejo de errores específico de este dominio.
function ventaErrorHandler(err, req, res, next) {
  if (err instanceof DomainError) {
    return res.status(err.statusCode).json({ mensaje: err.message });
  }
  return next(err);
}

module.exports = { historico, ventaErrorHandler };