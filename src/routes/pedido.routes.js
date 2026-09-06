// src/routes/pedido.routes.js
const express = require('express');
const router = express.Router();

const {
  crearPedido,
  listarPedidos,
  obtenerPedido,
  pedidoErrorHandler,
} = require('../controllers/pedido.controller');

const {
  crearPedidoRules,
  idParamRule,
  handleValidation,
} = require('../validators/pedido.validator');

// POST /api/pedidos          -> generar un pedido nuevo (HU-12)
router.post('/', crearPedidoRules, handleValidation, crearPedido);

// GET /api/pedidos           -> listar pedidos (?estado=&proveedor=)
router.get('/', listarPedidos);

// GET /api/pedidos/:id       -> detalle de un pedido
router.get('/:id', idParamRule, handleValidation, obtenerPedido);

router.use(pedidoErrorHandler);

module.exports = router;

/*
 * Registro en el app principal (src/app.js o index.js del repo):
 *
 *   const pedidoRoutes = require('./routes/pedido.routes');
 *   app.use('/api/pedidos', pedidoRoutes);
 */
