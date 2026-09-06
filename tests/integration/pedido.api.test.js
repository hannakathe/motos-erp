// tests/integration/pedido.api.test.js
// Requiere: npm install -D jest supertest mongodb-memory-server
// Ejecutar: npx jest tests/integration --runInBand

const request = require('supertest');
const mongoose = require('mongoose');
const { MongoMemoryServer } = require('mongodb-memory-server');
const express = require('express');

const Producto = require('../../src/models/Producto');
const Proveedor = require('../../src/models/Proveedor');
const pedidoRoutes = require('../../src/routes/pedido.routes');

let mongoServer;
let app;

beforeAll(async () => {
  mongoServer = await MongoMemoryServer.create();
  await mongoose.connect(mongoServer.getUri());

  app = express();
  app.use(express.json());
  app.use('/api/pedidos', pedidoRoutes);
});

afterAll(async () => {
  await mongoose.disconnect();
  await mongoServer.stop();
});

afterEach(async () => {
  await Producto.deleteMany({});
  await Proveedor.deleteMany({});
  await mongoose.connection.collection('pedidos').deleteMany({}).catch(() => {});
});

describe('POST /api/pedidos', () => {
  test('crea un pedido válido y responde 201 con el total calculado', async () => {
    const proveedor = await Proveedor.create({
      nombre: 'Repuestos Andina',
      nit: '900123456-1',
      activo: true,
    });
    const producto = await Producto.create({
      nombre: 'Kit de frenos',
      sku: 'KF-001',
      precio: 80000,
      stock: 10,
      proveedor: proveedor._id,
      activo: true,
    });

    const res = await request(app)
      .post('/api/pedidos')
      .send({
        proveedor: proveedor._id,
        detalles: [{ producto: producto._id, cantidad: 3, precioUnitario: 80000 }],
        observaciones: 'Pedido de prueba de integración',
      });

    expect(res.status).toBe(201);
    expect(res.body.total).toBe(240000);
    expect(res.body.estado).toBe('pendiente');
    expect(res.body.detalles).toHaveLength(1);
  });

  test('responde 400 si falta el proveedor', async () => {
    const res = await request(app)
      .post('/api/pedidos')
      .send({ detalles: [{ producto: new mongoose.Types.ObjectId(), cantidad: 1, precioUnitario: 100 }] });

    expect(res.status).toBe(400);
    expect(res.body.errores).toBeDefined();
  });

  test('responde 404 si el proveedor no existe en la base de datos', async () => {
    const res = await request(app)
      .post('/api/pedidos')
      .send({
        proveedor: new mongoose.Types.ObjectId(),
        detalles: [{ producto: new mongoose.Types.ObjectId(), cantidad: 1, precioUnitario: 100 }],
      });

    expect(res.status).toBe(404);
  });
});

describe('GET /api/pedidos/:id', () => {
  test('responde 400 si el id no es un ObjectId válido', async () => {
    const res = await request(app).get('/api/pedidos/no-valido');
    expect(res.status).toBe(400);
  });
});
