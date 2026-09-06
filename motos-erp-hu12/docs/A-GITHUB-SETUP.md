# Parte A — GitHub / Configuración del repositorio (motos-erp)

Responsable: Persona 1. Repo: `hannakathe/motos-erp`.

## 1. Verificar/crear rama `develop`

```bash
git clone https://github.com/hannakathe/motos-erp.git
cd motos-erp
git fetch origin

# ¿ya existe develop remota?
git branch -r | grep develop
```

- Si existe: `git checkout develop && git pull origin develop`
- Si NO existe, créala a partir de `main`:

```bash
git checkout main
git pull origin main
git checkout -b develop
git push -u origin develop
```

📸 **Evidencia 1**: captura del listado de ramas en GitHub (pestaña "branches") mostrando `main` y `develop`.

## 2. Configurar protección de `develop`

En GitHub: **Settings → Branches → Add branch ruleset / Add rule**

Configura para `develop` (y opcionalmente `main` con reglas más estrictas):

- ✅ Require a pull request before merging
- ✅ Require approvals → mínimo **1 aprobación**
- ✅ Dismiss stale pull request approvals when new commits are pushed
- ✅ Require status checks to pass before merging (si tienen CI/tests automatizados)
- ✅ Require conversation resolution before merging
- ❌ No permitir "Allow force pushes"
- ❌ No permitir "Allow deletions"

📸 **Evidencia 2**: captura de la pantalla de reglas de protección guardada para `develop`.

## 3. Estrategia de ramas

Estructura acordada para el equipo:

```
main                → versión estable / entregable
develop             → integración de todas las HU antes de pasar a main
feature/HU-12       → Generar pedido (Persona 1)
feature/HU-13       → (Persona 2)
feature/HU-XX       → (Persona 3)
```

Reglas del equipo:

1. Toda HU se desarrolla en su propia rama `feature/HU-<numero>` creada **desde `develop` actualizado**.
2. Nunca se hace push directo a `develop` ni a `main`.
3. Toda rama `feature/*` se integra a `develop` mediante Pull Request con al menos 1 revisor.
4. `develop` se fusiona a `main` solo en entregas/release, también mediante PR.

Crear la rama de esta HU:

```bash
git checkout develop
git pull origin develop
git checkout -b feature/HU-12
git push -u origin feature/HU-12
```

📸 **Evidencia 3**: captura de `git branch -a` o de la lista de ramas en GitHub mostrando `feature/HU-12`, `feature/HU-13`, etc.

## 4. Confirmar acceso de los 3 integrantes

En GitHub: **Settings → Collaborators and teams → Add people**

- Agregar a los 3 integrantes con rol **Write** (o **Maintain** si van a administrar settings).
- Pedir a cada integrante que confirme que puede clonar y hacer push.

📸 **Evidencia 4**: captura de la lista de colaboradores con sus roles.

## 5. Documentar con capturas

Guardar todas las capturas anteriores en `docs/evidencias/` dentro del repo (o en el documento de arquitectura/arc42), con un pie de foto explicando qué muestra cada una. Sugerido:

```
docs/evidencias/
 ├─ 01-ramas-main-develop.png
 ├─ 02-proteccion-develop.png
 ├─ 03-rama-feature-HU12.png
 ├─ 04-colaboradores.png
 ├─ 05-pull-request-HU12.png
 ├─ 06-code-review-HU12.png
 └─ 07-merge-HU12.png
```

Esto cumple el requisito del taller: `main`, `develop`, ramas `feature/<HU>` y revisión antes de merge.
