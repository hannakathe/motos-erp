// tests/integration/pedido.fecha-estimada.api.test.js
const request = require('supertest');
const express = require('express');
const mongoose = require('mongoose');
const { MongoMemoryServer } = require('mongodb-memory-server');
const pedidoRoutes = require('../../src/routes/pedido.routes');
const Pedido = require('../../src/models/Pedido');
const Proveedor = require('../../src/models/Proveedor');
const Producto = require('../../src/models/Producto');

const app = express();
app.use(express.json());
app.use('/api/pedidos', pedidoRoutes);

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

test('PATCH /api/pedidos/:id/fecha-estimada registra la fecha con 200', async () => {
  const pedido = await crearPedidoDePrueba();
  const fechaFutura = new Date(Date.now() + 10 * 24 * 60 * 60 * 1000)
    .toISOString()
    .split('T')[0];

  const res = await request(app)
    .patch(`/api/pedidos/${pedido._id}/fecha-estimada`)
    .send({ fechaEstimada: fechaFutura });

  expect(res.status).toBe(200);
  expect(res.body.fechaEstimada.startsWith(fechaFutura)).toBe(true);
});

test('PATCH rechaza una fecha pasada con 400', async () => {
  const pedido = await crearPedidoDePrueba();

  const res = await request(app)
    .patch(`/api/pedidos/${pedido._id}/fecha-estimada`)
    .send({ fechaEstimada: '2020-01-01' });

  expect(res.status).toBe(400);
});

test('PATCH devuelve 400 si falta la fecha', async () => {
  const pedido = await crearPedidoDePrueba();

  const res = await request(app)
    .patch(`/api/pedidos/${pedido._id}/fecha-estimada`)
    .send({});

  expect(res.status).toBe(400);
});

test('PATCH devuelve 404 si el pedido no existe', async () => {
  const idInexistente = new mongoose.Types.ObjectId();
  const fechaFutura = new Date(Date.now() + 5 * 24 * 60 * 60 * 1000)
    .toISOString()
    .split('T')[0];

  const res = await request(app)
    .patch(`/api/pedidos/${idInexistente}/fecha-estimada`)
    .send({ fechaEstimada: fechaFutura });

  expect(res.status).toBe(404);
});