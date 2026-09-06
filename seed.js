// seed.js
// Script de prueba: crea un proveedor y un producto para poder probar HU-12.
// Ejecutar: node seed.js

require('dotenv').config();
const mongoose = require('mongoose');
const dns = require('dns');
dns.setServers(['8.8.8.8', '8.8.4.4']);

const Proveedor = require('./src/models/Proveedor');
const Producto = require('./src/models/Producto');

async function seed() {
  await mongoose.connect(process.env.MONGODB_URI);
  console.log('Conectado a MongoDB');

  const proveedor = await Proveedor.create({
    nombre: 'Repuestos Andina',
    nit: '900123456-1',
    telefono: '3001234567',
    email: '[email protected]',
    activo: true,
  });
  console.log('Proveedor creado, id:', proveedor._id.toString());

  const producto = await Producto.create({
    nombre: 'Kit de frenos',
    sku: 'KF-001',
    precio: 80000,
    stock: 10,
    proveedor: proveedor._id,
    activo: true,
  });
  console.log('Producto creado, id:', producto._id.toString());

  console.log('\nCopia estos dos ids, los necesitas para probar el endpoint:');
  console.log('proveedor:', proveedor._id.toString());
  console.log('producto :', producto._id.toString());

  await mongoose.disconnect();
}

seed().catch((err) => {
  console.error('Error en el seed:', err.message);
  process.exit(1);
});
