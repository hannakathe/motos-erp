# Parte C — Pull Request de feature/HU-12 → develop

## 1. Commits en feature/HU-12

Trabaja con commits pequeños y descriptivos (convención sugerida: Conventional Commits):

```bash
git checkout feature/HU-12

git add src/models/Pedido.js src/models/DetallePedido.js
git commit -m "feat(HU-12): modelo Pedido y esquema DetallePedido"

git add src/services/pedido.service.js src/services/errors.js
git commit -m "feat(HU-12): logica de negocio para generar pedido"

git add src/validators/pedido.validator.js
git commit -m "feat(HU-12): validaciones de entrada para crear pedido"

git add src/controllers/pedido.controller.js src/routes/pedido.routes.js
git commit -m "feat(HU-12): endpoint REST POST/GET /api/pedidos"

git add tests/unit/pedido.service.test.js
git commit -m "test(HU-12): pruebas unitarias del servicio de pedidos"

git add tests/integration/pedido.api.test.js
git commit -m "test(HU-12): prueba de integracion del endpoint de pedidos"

git add docs/B-ARC42-HU12.md
git commit -m "docs(HU-12): documentacion arc42 del modulo de pedidos"

git push origin feature/HU-12
```

📸 **Evidencia — código funcionando**: captura ejecutando `npm run dev` (o el comando del proyecto) y del endpoint respondiendo en Postman/Insomnia/Thunder Client.

📸 **Evidencia — pruebas**: captura de `npx jest` corriendo en verde (unitarias e integración).

## 2. Crear el Pull Request

En GitHub: **Pull requests → New pull request**

- Base: `develop`  ←  Compare: `feature/HU-12`
- Título: `HU-12: Generar pedido`
- Descripción sugerida:

```markdown
## HU-12 — Generar pedido

### Cambios
- Modelo `Pedido` y esquema `DetallePedido` (relación con Producto y Proveedor).
- Servicio con la lógica de negocio y validaciones de dominio.
- Endpoint REST: POST/GET /api/pedidos.
- Pruebas unitarias (jest) y de integración (supertest + mongodb-memory-server).
- Documentación arc42 del módulo.

### Cómo probar
1. `npm install`
2. `npm run dev`
3. POST http://localhost:<puerto>/api/pedidos con body de ejemplo (ver docs/B-ARC42-HU12.md)

### Jira
Relacionado con SCRUM-<numero de la tarjeta de HU-12>
```

📸 **Evidencia — PR creado**: captura de la pantalla del Pull Request abierto.

## 3. Code Review

- Asignar como reviewer a otro integrante del equipo (Settings del PR → Reviewers).
- El revisor deja comentarios línea por línea (Files changed → +).
- Persona 1 corrige lo señalado con nuevos commits en la misma rama (`git push` vuelve a actualizar el PR automáticamente).
- El revisor aprueba con **Approve** una vez conforme.

📸 **Evidencia — Code Review**: captura de los comentarios del revisor y de la aprobación ("Approved").

## 4. Merge

- Una vez aprobado y con los checks en verde: **Merge pull request** (recomendado: "Squash and merge" para mantener el historial de `develop` limpio).
- Eliminar la rama `feature/HU-12` remota tras el merge (botón "Delete branch").

📸 **Evidencia — Merge**: captura del PR marcado como "Merged".

## 5. Actualización en Jira

- Mover la tarjeta **HU-12** en el tablero SCRUM de "In Progress" a "In Review" al abrir el PR, y a "Done" tras el merge.
- Adjuntar el link del PR mergeado en la tarjeta de Jira (o vincularlo si tienen el smart-commit / integración GitHub-Jira activada, usando `SCRUM-<numero>` en el mensaje del commit o del PR).

📸 **Evidencia — Jira**: captura de la tarjeta HU-12 en estado "Done" con el link del PR.
