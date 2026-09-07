// tests/unit/venta.service.test.js
const mongoose = require('mongoose');
const { MongoMemoryServer } = require('mongodb-memory-server');
const Venta = require('../../src/models/Venta');
const Producto = require('../../src/models/Producto');
const Proveedor = require('../../src/models/Proveedor');
const ventaService = require('../../src/services/venta.service');
const { DomainError } = require('../../src/services/errors');

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

async function crearVentaDePrueba(overrides = {}) {
  const nitUnico = `900${Math.floor(Math.random() * 1000000)}-7`;
  const skuUnico = `SKU-MOTO-${Math.floor(Math.random() * 1000000)}`;
  const proveedor = await Proveedor.create({
    nombre: 'Proveedor Test',
    nit: nitUnico,
    activo: true,
  });
  const producto = await Producto.create({
    nombre: 'Moto AKT 125',
    sku: skuUnico,
    proveedor: proveedor._id,
    activo: true,
    precio: 8500000,
  });
  return Venta.create({
    numeroFactura: overrides.numeroFactura || 'F-0001',
    fecha: overrides.fecha || new Date('2026-08-01'),
    clienteNombre: 'Juan Pérez',
    vendedorNombre: overrides.vendedorNombre || 'Ana Gómez',
    items: [{ producto: producto._id, cantidad: 1, precioUnitario: 8500000, subtotal: 8500000 }],
    total: 8500000,
  });
}

test('devuelve el histórico completo cuando no hay filtros', async () => {
  await crearVentaDePrueba();
  const resultado = await ventaService.listarHistorico({});
  expect(resultado).toHaveLength(1);
  expect(resultado[0].numeroFactura).toBe('F-0001');
  expect(resultado[0].itemDescripcion).toContain('Moto AKT 125');
});

test('filtra por rango de fechas', async () => {
  await crearVentaDePrueba({ numeroFactura: 'F-0001', fecha: new Date('2026-01-10') });
  await crearVentaDePrueba({ numeroFactura: 'F-0002', fecha: new Date('2026-06-10') });

  const resultado = await ventaService.listarHistorico({
    fechaInicio: '2026-05-01',
    fechaFin: '2026-07-01',
  });

  expect(resultado).toHaveLength(1);
  expect(resultado[0].numeroFactura).toBe('F-0002');
});

test('filtra por nombre de vendedor (sin distinguir mayúsculas)', async () => {
  await crearVentaDePrueba({ numeroFactura: 'F-0001', vendedorNombre: 'Ana Gómez' });
  await crearVentaDePrueba({ numeroFactura: 'F-0002', vendedorNombre: 'Carlos Ruiz' });

  const resultado = await ventaService.listarHistorico({ vendedorNombre: 'ana' });

  expect(resultado).toHaveLength(1);
  expect(resultado[0].numeroFactura).toBe('F-0001');
});

test('lanza DomainError si fechaInicio es posterior a fechaFin', async () => {
  await expect(
    ventaService.listarHistorico({ fechaInicio: '2026-08-01', fechaFin: '2026-01-01' })
  ).rejects.toBeInstanceOf(DomainError);
});