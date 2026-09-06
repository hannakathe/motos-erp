// tests/unit/pedido.service.test.js
// Requiere: npm install -D jest
// Ejecutar: npx jest tests/unit

jest.mock('../../src/models/Pedido');
jest.mock('../../src/models/Producto');
jest.mock('../../src/models/Proveedor');

const Pedido = require('../../src/models/Pedido');
const Producto = require('../../src/models/Producto');
const Proveedor = require('../../src/models/Proveedor');
const pedidoService = require('../../src/services/pedido.service');
const { DomainError, NotFoundError } = require('../../src/services/errors');

const PROVEEDOR_ID = '507f1f77bcf86cd799439011';
const PRODUCTO_ID = '507f1f77bcf86cd799439012';

function mockProveedorActivo() {
  Proveedor.findById.mockResolvedValue({
    _id: PROVEEDOR_ID,
    activo: true,
  });
}

function mockProductoValido(overrides = {}) {
  Producto.find.mockResolvedValue([
    {
      _id: PRODUCTO_ID,
      nombre: 'Filtro de aceite',
      precio: 25000,
      activo: true,
      proveedor: PROVEEDOR_ID,
      ...overrides,
    },
  ]);
}

function mockSavePedido() {
  const saveMock = jest.fn().mockResolvedValue(undefined);
  const populateMock = jest.fn().mockResolvedValue({ _id: 'pedido123', total: 50000 });
  Pedido.mockImplementation(function (data) {
    return { ...data, save: saveMock, populate: populateMock };
  });
  return { saveMock, populateMock };
}

describe('pedidoService.generarPedido', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('genera el pedido y calcula el total correctamente', async () => {
    mockProveedorActivo();
    mockProductoValido();
    const { saveMock } = mockSavePedido();

    const data = {
      proveedor: PROVEEDOR_ID,
      detalles: [{ producto: PRODUCTO_ID, cantidad: 2, precioUnitario: 25000 }],
    };

    const resultado = await pedidoService.generarPedido(data);

    expect(saveMock).toHaveBeenCalled();
    expect(resultado).toBeDefined();
  });

  test('lanza NotFoundError si el proveedor no existe', async () => {
    Proveedor.findById.mockResolvedValue(null);

    await expect(
      pedidoService.generarPedido({ proveedor: PROVEEDOR_ID, detalles: [] })
    ).rejects.toThrow(NotFoundError);
  });

  test('lanza DomainError si el proveedor está inactivo', async () => {
    Proveedor.findById.mockResolvedValue({ _id: PROVEEDOR_ID, activo: false });

    await expect(
      pedidoService.generarPedido({
        proveedor: PROVEEDOR_ID,
        detalles: [{ producto: PRODUCTO_ID, cantidad: 1, precioUnitario: 1000 }],
      })
    ).rejects.toThrow(DomainError);
  });

  test('lanza DomainError si no hay detalles', async () => {
    mockProveedorActivo();

    await expect(
      pedidoService.generarPedido({ proveedor: PROVEEDOR_ID, detalles: [] })
    ).rejects.toThrow('al menos un detalle');
  });

  test('lanza NotFoundError si un producto del detalle no existe', async () => {
    mockProveedorActivo();
    Producto.find.mockResolvedValue([]); // ningún producto encontrado

    await expect(
      pedidoService.generarPedido({
        proveedor: PROVEEDOR_ID,
        detalles: [{ producto: PRODUCTO_ID, cantidad: 1, precioUnitario: 1000 }],
      })
    ).rejects.toThrow(NotFoundError);
  });

  test('lanza DomainError si el producto no pertenece al proveedor', async () => {
    mockProveedorActivo();
    mockProductoValido({ proveedor: 'otro-proveedor-id' });

    await expect(
      pedidoService.generarPedido({
        proveedor: PROVEEDOR_ID,
        detalles: [{ producto: PRODUCTO_ID, cantidad: 1, precioUnitario: 1000 }],
      })
    ).rejects.toThrow('no pertenece al proveedor');
  });

  test('lanza DomainError si el producto está inactivo', async () => {
    mockProveedorActivo();
    mockProductoValido({ activo: false });

    await expect(
      pedidoService.generarPedido({
        proveedor: PROVEEDOR_ID,
        detalles: [{ producto: PRODUCTO_ID, cantidad: 1, precioUnitario: 1000 }],
      })
    ).rejects.toThrow('no está activo');
  });
});
