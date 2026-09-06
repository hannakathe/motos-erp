// server.js
// Archivo principal: levanta el servidor Express y conecta a MongoDB.

require('dotenv').config();
const dns = require('dns');
dns.setServers(['8.8.8.8', '8.8.4.4']);

const express = require('express');
const mongoose = require('mongoose');

const pedidoRoutes = require('./src/routes/pedido.routes');

const app = express();
app.use(express.json());

// Rutas
app.use('/api/pedidos', pedidoRoutes);

app.get('/', (req, res) => {
  res.json({ mensaje: 'API motos-erp funcionando' });
});

const PORT = process.env.PORT || 3000;
const MONGODB_URI = process.env.MONGODB_URI;

mongoose
  .connect(MONGODB_URI)
  .then(() => {
    console.log('Conectado a MongoDB');
    app.listen(PORT, () => {
      console.log(`Servidor corriendo en http://localhost:${PORT}`);
    });
  })
  .catch((err) => {
    console.error('Error al conectar a MongoDB:', err.message);
  });
