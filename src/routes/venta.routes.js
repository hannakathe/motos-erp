// src/routes/venta.routes.js
const express = require('express');
const router = express.Router();
const { historico, ventaErrorHandler } = require('../controllers/venta.controller');
const { historicoRules, handleValidation } = require('../validators/venta.validator');

// GET /api/ventas/historico?fechaInicio=&fechaFin=&vendedorNombre=  (HU-13)
router.get('/historico', historicoRules, handleValidation, historico);

router.use(ventaErrorHandler);

module.exports = router;

/*
 * Registro en el app principal (server.js del repo):
 *
 *   const ventaRoutes = require('./src/routes/venta.routes');
 *   app.use('/api/ventas', ventaRoutes);
 */