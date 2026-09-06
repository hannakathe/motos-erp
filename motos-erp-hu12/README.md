# Entregables Persona 1 — HU-12 (Generar pedido)

Este paquete contiene todo lo pedido para **Persona 1 — Backend + GitHub + HU-12** del taller sobre `motos-erp` (Express + Mongoose).

## Contenido

```
docs/
 ├─ A-GITHUB-SETUP.md     → Parte A: ramas, protección, accesos, capturas
 ├─ B-ARC42-HU12.md       → Documentación arc42 de HU-12
 └─ C-PR-WORKFLOW.md      → Parte C: commits, PR, review, merge, Jira

src/
 ├─ models/
 │   ├─ Pedido.js         → Entidad Pedido (relación con Proveedor)
 │   ├─ DetallePedido.js  → Sub-entidad DetallePedido (relación con Producto)
 │   ├─ Producto.js       → Referencia (usar el existente si ya está en el repo)
 │   └─ Proveedor.js      → Referencia (usar el existente si ya está en el repo)
 ├─ services/
 │   ├─ pedido.service.js → Lógica de negocio + validaciones de dominio
 │   └─ errors.js         → DomainError / NotFoundError
 ├─ validators/
 │   └─ pedido.validator.js → Validación de payload (express-validator)
 ├─ controllers/
 │   └─ pedido.controller.js
 └─ routes/
     └─ pedido.routes.js  → POST/GET /api/pedidos

tests/
 ├─ unit/pedido.service.test.js
 └─ integration/pedido.api.test.js
```

## Pasos para integrar en tu repo real (`motos-erp`)

```bash
git checkout develop
git pull origin develop
git checkout -b feature/HU-12
```

1. Copia las carpetas `src/` y `tests/` dentro de tu proyecto (ajustando rutas
   si tu estructura ya tiene `models/`, `routes/`, etc. en otro nivel).
2. **Importante**: si `Producto.js` y `Proveedor.js` **ya existen** en el repo
   (creados por tus compañeros), NO los reemplaces — solo revisa que tengan
   los campos `activo`, `precio` (Producto) y `activo` (Proveedor), o ajusta
   `pedido.service.js` a los nombres reales de esos campos.
3. Registra las rutas en tu `app.js`/`index.js`:
   ```js
   const pedidoRoutes = require('./routes/pedido.routes');
   app.use('/api/pedidos', pedidoRoutes);
   ```
4. Instala dependencias que falten:
   ```bash
   npm install express-validator
   npm install -D jest supertest mongodb-memory-server
   ```
5. Agrega en `package.json`:
   ```json
   "scripts": {
     "test": "jest"
   }
   ```
6. Corre las pruebas:
   ```bash
   npx jest
   ```
7. Sigue `docs/C-PR-WORKFLOW.md` para commits, PR, revisión, merge y Jira.

## Ejemplo de request para probar el endpoint

```bash
curl -X POST http://localhost:3000/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "proveedor": "<id_proveedor>",
    "detalles": [
      { "producto": "<id_producto>", "cantidad": 3, "precioUnitario": 80000 }
    ],
    "observaciones": "Reposicion de stock semanal"
  }'
```

Respuesta esperada (`201 Created`): el pedido con `total` calculado, `estado: "pendiente"`, y `detalles` con su `subtotal` cada uno.
