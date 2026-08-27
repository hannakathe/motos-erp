[← Volver al índice](arc42-template-ES.md)

# 4. Estrategia de Solución

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). Se completa aquí como parte del esqueleto arc42, documentando únicamente lo que puede justificarse con el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml), el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) y [requisitos/No funcionales.md](<../requisitos/No funcionales.md>). Las decisiones marcadas como "Inferencia" no están explicadas como tales en ningún documento fuente; se deducen de la estructura de los diagramas.

## 4.1 Decisiones tecnológicas

De [requisitos/No funcionales.md](<../requisitos/No funcionales.md>), confirmadas por el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml). La columna "Estado" distingue explícitamente lo **implementado** (existe y se ejecuta en el entorno local del proyecto) de lo **propuesto** (alternativa arquitectónica documentada, sin evidencia de implementación):

| Capa | Tecnología | Evidencia | Estado |
|---|---|---|---|
| Backend | Java + Spring Boot | Tabla de tecnologías de No funcionales.md; nodo "API Backend - Spring Boot" en el diagrama de despliegue. | Tecnología seleccionada, para ejecución en el entorno de desarrollo/demostración local. |
| Frontend | React | Ídem; nodo "Frontend React" en el diagrama de despliegue. | Tecnología seleccionada, para ejecución en el entorno de desarrollo/demostración local. |
| Base de datos | PostgreSQL | Ídem; nodo "PostgreSQL - AndinaMotorsDB" en el diagrama de despliegue. | Tecnología seleccionada, para ejecución en el entorno de desarrollo/demostración local. |
| Infraestructura cloud | AWS (EC2 + RDS) | Ídem; nodos "Servidor Cloud (AWS EC2)" y "Servidor BD (AWS RDS)". | **Propuesto — no implementado.** No existe instancia EC2, RDS ni ninguna infraestructura AWS activa. Ver [Arquitectura de despliegue propuesta](#arquitectura-de-despliegue-propuesta-no-implementada) más abajo y [07. Vista de Despliegue](07_deployment_view.md). |
| Contenerización | Docker | Listada en la tabla de tecnologías de No funcionales.md. | Propuesto. [NO EVIDENCIADO EN EL REPOSITORIO: no existe Dockerfile ni configuración de contenedores en el repositorio; es una tecnología seleccionada, no una implementación verificable.] |

### Arquitectura de desarrollo/demostración (implementada)

El entorno utilizado para desarrollar y demostrar el sistema es **local**: backend (Java/Spring Boot), frontend (React) y base de datos (PostgreSQL) se ejecutan localmente en el equipo del desarrollador/expositor, sin depender de infraestructura cloud. Este es el único entorno del que puede predicarse "implementado" en este proyecto (ver [07.1](07_deployment_view.md#71-estado-actual)).

### Arquitectura de despliegue propuesta (no implementada)

AWS (EC2 + RDS) se documenta como una **estrategia de despliegue propuesta**, planteada como posible escenario futuro si el proyecto evolucionara más allá del alcance académico del taller — no como infraestructura utilizada actualmente. El detalle completo de esta propuesta está en [07.2](07_deployment_view.md#72-arquitectura-de-despliegue-propuesta) y en la decisión [AD-07](09_architecture_decisions.md#ad-07--propuesta-de-despliegue-cloud-aws).

## 4.2 Estilo arquitectónico

**Resuelto** en esta revisión con la Vista de Desarrollo agregada por el equipo ([`3. vista de desarrollo.jpg`](<../../Vista de Procesos (4+1)/3. vista de desarrollo.jpg>)): el backend sigue una **arquitectura en capas (layered architecture)**, dentro de un backend único (monolito modular), con 4 capas explícitas:

1. **Frontend (SPA)**: `UI Ventas`, `UI Compras`, `UI Inventario`, `UI Servicio Técnico` (React).
2. **Backend — Capa API (REST)**: `API Ventas`, `API Compras`, `API Inventario`, `API Facturación`, `API Servicio Técnico`, `API Seguridad` — expone los endpoints REST y aplica seguridad transversal.
3. **Backend — Capa de Dominio**: `Servicio Ventas`, `Servicio Compras`, `Servicio Inventario`, `Servicio Facturación` — lógica de negocio.
4. **Backend — Capa de Persistencia**: `Repositorio Ventas`, `Repositorio Compras`, `Repositorio Inventario` — acceso a datos (patrón Repository), sobre un `ORM` en la capa de Infraestructura.

La capa de Infraestructura agrega además un `Cliente DIAN (SOAP/REST)`, usado por la capa de Dominio (Servicio Facturación) para la integración con la entidad tributaria.

Esto confirma y reemplaza la inferencia previa de "monolito modular": es un **monolito modular en capas**, no solo componentes de negocio sin arquitectura interna definida. Sigue siendo un backend único desplegable (no microservicios) — consistente con el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) y el [diagrama de contenedores C4](../diagramas/plantuml/diagrama_c4_contenedores.plantuml), que modelan un único contenedor "API Backend".

⚠️ La Vista de Desarrollo solo detalla capas para 4 de los 6 módulos (Ventas/Facturación, Compras, Inventario, Servicio Técnico); no incluye Empleados, EIS ni ActivosFijos. [POR DEFINIR si estos módulos seguirían la misma arquitectura en capas — es la extensión razonable, pero no está dibujada explícitamente.]

**Estilo formal (C4)**: **Resuelto**. El equipo adoptó C4 para el Nivel 1 (Contexto, [diagrama_c4_contexto.plantuml](../diagramas/plantuml/diagrama_c4_contexto.plantuml)) y Nivel 2 (Contenedores, [diagrama_c4_contenedores.plantuml](../diagramas/plantuml/diagrama_c4_contenedores.plantuml)). El Nivel 3 (Componentes C4) no está modelado con notación C4 formal, pero el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml) UML original y la Vista de Desarrollo cumplen ese rol informalmente.

## 4.3 Descomposición y modularización

El sistema se divide en 6 módulos de negocio, cada uno con paquetes internos (casos de uso) documentados en `docs/diagramas/plantuml/diagrama_paquetes_*.plantuml` — ver detalle completo en [05. Vista de Bloques](05_building_block_view.md). La comunicación entre módulos se modela como dependencias directas de operación (p. ej. `Facturación ..> Inventario : verificarDisponibilidad()`), es decir, **llamadas síncronas** entre componentes, no mensajería asíncrona ni eventos. No hay evidencia en el repositorio de un bus de eventos, cola de mensajes o mecanismo de comunicación asíncrona.

## 4.4 Persistencia

**Inferencia**: tanto en el entorno local de desarrollo/demostración como en la arquitectura de despliegue propuesta, el diseño contempla una única base de datos relacional (PostgreSQL, nodo "AndinaMotorsDB" en el diagrama de despliegue propuesto) compartida por el backend único, sin bases de datos separadas por módulo. Esto es consistente con la justificación en `No funcionales.md`: "Relacional, ideal para inventario con trazabilidad por VIN". No hay evidencia de un esquema de base de datos, tablas o migraciones en el repositorio — el modelo de datos solo existe a nivel de diagrama de clases (dominio de Facturación, ver [05.4](05_building_block_view.md#54-vista-lógica-diagrama-de-clases-y-objetos)). [NO EVIDENCIADO EN EL REPOSITORIO: no hay evidencia de una instancia de PostgreSQL en ejecución, local o en AWS RDS.]

## 4.5 Seguridad

RNF-2 (`requisitos/No funcionales.md`) exige control de acceso por rol: solo "Vendedor" factura ventas; solo "Mecánico" o "Jefe de taller" cierran órdenes de servicio.

**Resuelto parcialmente** en esta revisión con la Vista Lógica actualizada ([`1. vista logica.jpg`](<../../Vista de Procesos (4+1)/1. vista logica.jpg>)), que agrega una clase `Usuario` (`id`, `rol`, `usuario`, `passwordHash`) — no presente en el `diagrama_clases.plantuml` original — y con la Vista de Desarrollo, que agrega una capa `API Seguridad` dentro de la capa API REST. La Vista de Escenarios agrega además el caso de uso "Autenticar usuario".

Con esta evidencia, se completa la arquitectura de seguridad propuesta para este proyecto (ver [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario)):

- **Autenticación**: usuario + contraseña, verificada contra `Usuario.passwordHash` (hash, no texto plano — el atributo ya se llama `passwordHash` en la Vista Lógica).
- **Autorización**: basada en `Usuario.rol`, con los valores Vendedor, Mecánico/Técnico, Jefe de taller, Administrador/Gerente y Jefe de Compras (ver [01.4](01_introduction_and_goals.md#14-stakeholders)); aplicada en la capa `API Seguridad`.
- **Estado**: **diseño propuesto**, no una implementación verificable — no hay código, no hay evidencia de JWT/OAuth/sesiones ni de un mecanismo de hashing concreto (bcrypt, etc.); `passwordHash` solo indica la *intención* de no almacenar contraseñas en texto plano.

[NO EVIDENCIADO EN EL REPOSITORIO: protocolo de autenticación concreto (JWT, sesiones de servidor, OAuth2), algoritmo de hashing, expiración de sesión, manejo de "olvidé mi contraseña".]

## 4.6 Escalabilidad y despliegue propuesto

RNF-3 exige que el sistema soporte múltiples sucursales consultando el mismo inventario en tiempo real. El diagrama de despliegue **propuesto** y `alcance.md` documentan explícitamente un escenario de **una sola sucursal cliente conectada a un único servidor** — el soporte multi-sucursal con sincronización queda fuera del alcance ya modelado, incluso en la propuesta. Ver nota de esta tensión entre requisito y modelo en [11. Riesgos y Deuda Técnica](11_risks_and_technical_debt.md).

Es importante distinguir: esta discusión de escalabilidad se refiere a la **arquitectura de despliegue propuesta** (AWS), no a un despliegue real. Actualmente, el sistema se ejecuta localmente para fines de desarrollo y demostración, sin escenario de múltiples sucursales activo (ver [07.1](07_deployment_view.md#71-estado-actual)).

## 4.7 Integración con sistemas externos (DIAN y Proveedores)

**Nuevo en esta revisión**, a partir de la Vista Física, la Vista de Procesos, la Vista Lógica y la Vista de Escenarios (ver [`Vista de Procesos (4+1)/`](<../../Vista de Procesos (4+1)>)):

- **DIAN (facturación electrónica)**: el `Servicio Facturación` (capa de Dominio) invoca un `Cliente DIAN (SOAP/REST)` (capa de Infraestructura) para enviar la factura y recibir el CUFE de confirmación. La Vista de Procesos modela esta llamada como **asíncrona** ("enviar factura electrónica (async)"), mientras que el resto del flujo de facturación (verificar disponibilidad, guardar venta, descontar stock) es síncrono. En la Vista Física, el canal propuesto es HTTPS (Web Service) hacia un "Sistema Externo DIAN".
- **Proveedores (compras B2B)**: el `Servicio Compras` se integraría con un "Sistema Proveedores" externo vía HTTPS (API) según la Vista Física; el caso de uso "Registrar compra a proveedor" en la Vista de Escenarios confirma esta relación a nivel de negocio.

Ambas integraciones son **propuestas de diseño**, no implementadas — contradicen lo que `alcance.md` declaraba originalmente como fuera de alcance del taller (ver nota de evolución en [alcance.md](../alcance.md) y el riesgo [R-11](11_risks_and_technical_debt.md#r-11--integración-con-dian-y-proveedores-modelada-en-diagramas-nuevos-pero-ausente-en-los-diagramas-originales)). Ninguna de las dos aparece en el diagrama de componentes ni en el diagrama de clases originales, que siguen siendo la base de la Vista de Bloques (sección 5) salvo donde se indique lo contrario.

## 4.8 Logros de las metas de calidad clave

| Meta de calidad (RNF) | Elemento de la estrategia de solución que la atiende |
|---|---|
| RNF-1 (Rendimiento, <1s en verificar disponibilidad) | [POR DEFINIR — no hay estrategia de caché, índices o medición de tiempos de respuesta documentada; solo se declara el requisito. La llamada síncrona a DIAN (4.7) no afecta esta meta porque `verificarDisponibilidad()` no depende de DIAN.] |
| RNF-2 (Seguridad, control de acceso por rol) | Ver [4.5](#45-seguridad) — resuelto a nivel de diseño propuesto: `Usuario.rol` + `API Seguridad`. |
| RNF-3 (Escalabilidad, multi-sucursal) | En la arquitectura **propuesta**: base de datos centralizada en AWS RDS (4.4). En el estado **actual** (local): despliegue de sucursal única, sin infraestructura cloud (4.6). |
| RNF-4 (Usabilidad, factura en máx. 3 pasos) | [POR DEFINIR — no hay wireframes, mockups ni especificación de flujo de UI en el repositorio.] |
| RNF-5 (Disponibilidad, 99% uptime) | Referida a la infraestructura administrada de la arquitectura **propuesta**; la Vista Física agrega un Load Balancer ([07.4](07_deployment_view.md#74-vista-física-versión-extendida-agregada-posteriormente)), pero sin evidencia de redundancia ni zonas de disponibilidad ([POR DEFINIR], ver [07.5](07_deployment_view.md#75-nivel-3-dimensionamiento-y-configuración-de-infraestructura)). No aplica al entorno local actual, que no tiene meta de uptime declarada. |

---
[← Anterior: Alcance y Contexto del Sistema](03_system_scope_and_context.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Vista de Bloques →](05_building_block_view.md)
