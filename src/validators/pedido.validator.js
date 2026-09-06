// src/validators/pedido.validator.js
// Requiere: npm install express-validator
const { body, param, validationResult } = require('express-validator');
const mongoose = require('mongoose');

const isValidObjectId = (value) => mongoose.Types.ObjectId.isValid(value);

const crearPedidoRules = [
  body('proveedor')
    .exists({ checkFalsy: true }).withMessage('El proveedor es obligatorio')
    .custom(isValidObjectId).withMessage('El id de proveedor no es válido'),

  body('detalles')
    .isArray({ min: 1 }).withMessage('Debe incluir al menos un detalle (producto)'),

  body('detalles.*.producto')
    .exists({ checkFalsy: true }).withMessage('Cada detalle debe incluir un producto')
    .custom(isValidObjectId).withMessage('El id de producto no es válido'),

  body('detalles.*.cantidad')
    .isInt({ min: 1 }).withMessage('La cantidad de cada detalle debe ser un entero mayor a 0'),

  body('detalles.*.precioUnitario')
    .isFloat({ min: 0 }).withMessage('El precio unitario no puede ser negativo'),

  body('observaciones')
    .optional({ checkFalsy: true })
    .isLength({ max: 500 }).withMessage('Las observaciones no pueden superar 500 caracteres'),
];

const idParamRule = [
  param('id').custom(isValidObjectId).withMessage('El id del pedido no es válido'),
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

module.exports = { crearPedidoRules, idParamRule, handleValidation };
