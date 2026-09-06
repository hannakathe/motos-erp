// src/routes/pedido.routes.js
const express = require('express');
const router = express.Router();
const {
  crearPedido,
  listarPedidos,
  obtenerPedido,
  actualizarFechaEstimada,
  pedidoErrorHandler,
} = require('../controllers/pedido.controller');
const {
  crearPedidoRules,
  idParamRule,
  actualizarFechaEstimadaRules,
  handleValidation,
} = require('../validators/pedido.validator');

// POST /api/pedidos          -> generar un pedido nuevo (HU-12)
router.post('/', crearPedidoRules, handleValidation, crearPedido);

// GET /api/pedidos           -> listar pedidos (?estado=&proveedor=)
router.get('/', listarPedidos);

// GET /api/pedidos/:id       -> detalle de un pedido
router.get('/:id', idParamRule, handleValidation, obtenerPedido);

// PATCH /api/pedidos/:id/fecha-estimada -> registrar fecha estimada (HU-14)
router.patch(
  '/:id/fecha-estimada',
  idParamRule,
  actualizarFechaEstimadaRules,
  handleValidation,
  actualizarFechaEstimada
);

router.use(pedidoErrorHandler);

module.exports = router;

/*
 * Registro en el app principal (server.js del repo):
 *
 *   const pedidoRoutes = require('./src/routes/pedido.routes');
 *   app.use('/api/pedidos', pedidoRoutes);
 */
