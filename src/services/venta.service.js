// src/services/venta.service.js
// Lógica de negocio de HU-13: Consultar histórico de ventas.
const Venta = require('../models/Venta');
const { DomainError } = require('./errors');

/**
 * Consulta el histórico de ventas aplicando los filtros opcionales de
 * rango de fechas y vendedor, y da forma a cada resultado como lo espera
 * la pantalla (una fila por factura, con los ítems resumidos en texto).
 */
async function listarHistorico(filtros = {}) {
  const { fechaInicio, fechaFin, vendedorNombre } = filtros;

  if (fechaInicio && fechaFin && new Date(fechaInicio) > new Date(fechaFin)) {
    throw new DomainError('La fecha de inicio no puede ser posterior a la fecha fin');
  }

  const query = {};
  if (fechaInicio || fechaFin) {
    query.fecha = {};
    if (fechaInicio) query.fecha.$gte = new Date(fechaInicio);
    if (fechaFin) query.fecha.$lte = new Date(fechaFin);
  }
  if (vendedorNombre) {
    query.vendedorNombre = new RegExp(vendedorNombre, 'i');
  }

  const ventas = await Venta.find(query)
    .populate('items.producto')
    .sort({ fecha: -1 });

  return ventas.map((venta) => ({
    facturaId: venta._id,
    numeroFactura: venta.numeroFactura,
    fecha: venta.fecha,
    clienteNombre: venta.clienteNombre,
    vendedorNombre: venta.vendedorNombre,
    itemDescripcion: venta.items
      .map((item) => (item.producto && item.producto.nombre) || 'Producto eliminado')
      .join(', '),
    total: venta.total,
  }));
}

module.exports = { listarHistorico };