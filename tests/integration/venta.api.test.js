// tests/integration/venta.api.test.js
const request = require('supertest');
const express = require('express');
const mongoose = require('mongoose');
const { MongoMemoryServer } = require('mongodb-memory-server');
const ventaRoutes = require('../../src/routes/venta.routes');
const Venta = require('../../src/models/Venta');
const Producto = require('../../src/models/Producto');
const Proveedor = require('../../src/models/Proveedor');

const app = express();
app.use(express.json());
app.use('/api/ventas', ventaRoutes);

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
  await Venta.deleteMany({});
  await Producto.deleteMany({});
  await Proveedor.deleteMany({});
});

async function crearVentaDePrueba() {
  const proveedor = await Proveedor.create({
    nombre: 'Proveedor Test',
    nit: '900123456-7',
    activo: true,
  });
  const producto = await Producto.create({
    nombre: 'Moto AKT 125',
    sku: 'SKU-MOTO-001',
    proveedor: proveedor._id,
    activo: true,
    precio: 8500000,
  });
  return Venta.create({
    numeroFactura: 'F-0001',
    fecha: new Date('2026-08-01'),
    clienteNombre: 'Juan Pérez',
    vendedorNombre: 'Ana Gómez',
    items: [{ producto: producto._id, cantidad: 1, precioUnitario: 8500000, subtotal: 8500000 }],
    total: 8500000,
  });
}

test('GET /api/ventas/historico devuelve 200 con la lista', async () => {
  await crearVentaDePrueba();

  const res = await request(app).get('/api/ventas/historico');

  expect(res.status).toBe(200);
  expect(res.body).toHaveLength(1);
  expect(res.body[0].numeroFactura).toBe('F-0001');
});

test('GET /api/ventas/historico devuelve 200 y lista vacía sin resultados', async () => {
  const res = await request(app).get('/api/ventas/historico').query({
    fechaInicio: '2027-01-01',
  });

  expect(res.status).toBe(200);
  expect(res.body).toEqual([]);
});

test('GET /api/ventas/historico devuelve 400 con fecha mal formada', async () => {
  const res = await request(app)
    .get('/api/ventas/historico')
    .query({ fechaInicio: 'no-es-fecha' });

  expect(res.status).toBe(400);
});

test('GET /api/ventas/historico devuelve 400 si fechaInicio > fechaFin', async () => {
  const res = await request(app).get('/api/ventas/historico').query({
    fechaInicio: '2026-08-01',
    fechaFin: '2026-01-01',
  });

  expect(res.status).toBe(400);
});