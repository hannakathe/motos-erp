// src/validators/venta.validator.js
const { query, validationResult } = require('express-validator');

const historicoRules = [
  query('fechaInicio')
    .optional({ checkFalsy: true })
    .isISO8601().withMessage('fechaInicio debe tener formato de fecha válido (YYYY-MM-DD)'),
  query('fechaFin')
    .optional({ checkFalsy: true })
    .isISO8601().withMessage('fechaFin debe tener formato de fecha válido (YYYY-MM-DD)'),
  query('vendedorNombre')
    .optional({ checkFalsy: true })
    .isLength({ max: 100 }).withMessage('vendedorNombre no puede superar 100 caracteres'),
];

function handleValidation(req, res, next) {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({
      mensaje: 'Datos de entrada inválidos',
      errores: errors.array().map((e) => ({ campo: e.path, mensaje: e.msg })),
    });
  }
  next();
}

module.exports = { historicoRules, handleValidation };