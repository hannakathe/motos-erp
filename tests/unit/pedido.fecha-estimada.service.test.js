// tests/unit/pedido.fecha-estimada.service.test.js
const mongoose = require('mongoose');
const { MongoMemoryServer } = require('mongodb-memory-server');
const Pedido = require('../../src/models/Pedido');
const Proveedor = require('../../src/models/Proveedor');
const Producto = require('../../src/models/Producto');
const pedidoService = require('../../src/services/pedido.service');
const { NotFoundError } = require('../../src/services/errors');

let mongoServer;

beforeAll(async () => {
  mongoServer = await MongoMemoryServer.create();
  await mongoose.connect(mongoServer.getUri());
});

afterAll(async () => {
  await mongoose.disconnect();
  await mongoServer.stop();
});

afterEach(async () => {
  await Pedido.deleteMany({});
  await Proveedor.deleteMany({});
  await Producto.deleteMany({});
});

async function crearPedidoDePrueba() {
  const proveedor = await Proveedor.create({
    nombre: 'Proveedor Test',
    nit: '900123456-7',
    activo: true,
  });
  const producto = await Producto.create({
    nombre: 'Producto Test',
    sku: 'SKU-TEST-001',
    proveedor: proveedor._id,
    activo: true,
    precio: 100,
  });
  return Pedido.create({
    proveedor: proveedor._id,
    detalles: [{ producto: producto._id, cantidad: 2, precioUnitario: 100, subtotal: 200 }],
    total: 200,
  });
}

test('registra la fecha estimada en un pedido existente', async () => {
  const pedido = await crearPedidoDePrueba();
  const fechaFutura = new Date(Date.now() + 10 * 24 * 60 * 60 * 1000);

  const actualizado = await pedidoService.actualizarFechaEstimada(pedido._id, fechaFutura);

  expect(actualizado.fechaEstimada.toISOString()).toBe(fechaFutura.toISOString());
});

test('lanza NotFoundError si el pedido no existe', async () => {
  const idInexistente = new mongoose.Types.ObjectId();
  await expect(
    pedidoService.actualizarFechaEstimada(idInexistente, new Date())
  ).rejects.toBeInstanceOf(NotFoundError);
});