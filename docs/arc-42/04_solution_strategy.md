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

El repositorio no declara explícitamente un estilo arquitectónico (por ejemplo, "arquitectura en capas", "microservicios" o "monolito modular").

**Inferencia**: el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) — que documenta la arquitectura de despliegue **propuesta**, no una infraestructura activa (ver [07. Vista de Despliegue](07_deployment_view.md)) — modela un único nodo de servidor ("Servidor Cloud (AWS EC2)") que contendría un único componente desplegable ("API Backend - Spring Boot"). Los 6 módulos de negocio (Inventario, Facturación, Compras, Empleados, EIS, ActivosFijos) se modelan como componentes internos con interfaces explícitas en el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml), no como servicios desplegados independientemente. Esto sugiere un **monolito modular** (backend único, dividido internamente en componentes de negocio con interfaces definidas) en lugar de una arquitectura de microservicios. Esta es una inferencia a partir de la estructura de los diagramas, no una decisión declarada como tal en ningún documento del repositorio.

**Estilo formal (C4, notación de componentes)**: [POR DEFINIR — el repositorio no declara si la intención es UML de componentes tradicional o el modelo C4 explícitamente, aunque `alcance.md` menciona el "Nivel 2 y Nivel 3 del modelo C4" como pendiente, lo que sugiere que el diagrama de componentes actual se pretende equivalente a un nivel C4 "Contenedores/Componentes".]

## 4.3 Descomposición y modularización

El sistema se divide en 6 módulos de negocio, cada uno con paquetes internos (casos de uso) documentados en `docs/diagramas/plantuml/diagrama_paquetes_*.plantuml` — ver detalle completo en [05. Vista de Bloques](05_building_block_view.md). La comunicación entre módulos se modela como dependencias directas de operación (p. ej. `Facturación ..> Inventario : verificarDisponibilidad()`), es decir, **llamadas síncronas** entre componentes, no mensajería asíncrona ni eventos. No hay evidencia en el repositorio de un bus de eventos, cola de mensajes o mecanismo de comunicación asíncrona.

## 4.4 Persistencia

**Inferencia**: tanto en el entorno local de desarrollo/demostración como en la arquitectura de despliegue propuesta, el diseño contempla una única base de datos relacional (PostgreSQL, nodo "AndinaMotorsDB" en el diagrama de despliegue propuesto) compartida por el backend único, sin bases de datos separadas por módulo. Esto es consistente con la justificación en `No funcionales.md`: "Relacional, ideal para inventario con trazabilidad por VIN". No hay evidencia de un esquema de base de datos, tablas o migraciones en el repositorio — el modelo de datos solo existe a nivel de diagrama de clases (dominio de Facturación, ver [05.4](05_building_block_view.md#54-vista-lógica-diagrama-de-clases-y-objetos)). [NO EVIDENCIADO EN EL REPOSITORIO: no hay evidencia de una instancia de PostgreSQL en ejecución, local o en AWS RDS.]

## 4.5 Seguridad

RNF-2 (`requisitos/No funcionales.md`) exige control de acceso por rol: solo "Vendedor" factura ventas; solo "Mecánico" o "Jefe de taller" cierran órdenes de servicio. No hay evidencia de un mecanismo técnico de autenticación o autorización (no se documenta JWT, OAuth, sesiones, ni un módulo de seguridad). [POR DEFINIR — el repositorio no especifica cómo se implementaría el control de acceso por rol descrito en RNF-2.]

## 4.6 Escalabilidad y despliegue propuesto

RNF-3 exige que el sistema soporte múltiples sucursales consultando el mismo inventario en tiempo real. El diagrama de despliegue **propuesto** y `alcance.md` documentan explícitamente un escenario de **una sola sucursal cliente conectada a un único servidor** — el soporte multi-sucursal con sincronización queda fuera del alcance ya modelado, incluso en la propuesta. Ver nota de esta tensión entre requisito y modelo en [11. Riesgos y Deuda Técnica](11_risks_and_technical_debt.md).

Es importante distinguir: esta discusión de escalabilidad se refiere a la **arquitectura de despliegue propuesta** (AWS), no a un despliegue real. Actualmente, el sistema se ejecuta localmente para fines de desarrollo y demostración, sin escenario de múltiples sucursales activo (ver [07.1](07_deployment_view.md#71-estado-actual)).

## 4.7 Logros de las metas de calidad clave

| Meta de calidad (RNF) | Elemento de la estrategia de solución que la atiende |
|---|---|
| RNF-1 (Rendimiento, <1s en verificar disponibilidad) | [POR DEFINIR — no hay estrategia de caché, índices o medición de tiempos de respuesta documentada; solo se declara el requisito.] |
| RNF-2 (Seguridad, control de acceso por rol) | Ver [4.5](#45-seguridad) — solo el requisito está documentado, el mecanismo está [POR DEFINIR]. |
| RNF-3 (Escalabilidad, multi-sucursal) | En la arquitectura **propuesta**: base de datos centralizada en AWS RDS (4.4). En el estado **actual** (local): despliegue de sucursal única, sin infraestructura cloud (4.6). |
| RNF-4 (Usabilidad, factura en máx. 3 pasos) | [POR DEFINIR — no hay wireframes, mockups ni especificación de flujo de UI en el repositorio.] |
| RNF-5 (Disponibilidad, 99% uptime) | Referida a la infraestructura administrada AWS (EC2 + RDS) de la arquitectura **propuesta**; sin evidencia de redundancia, balanceo de carga o zonas de disponibilidad ([POR DEFINIR], ver [07.4](07_deployment_view.md#74-nivel-2)). No aplica al entorno local actual, que no tiene meta de uptime declarada. |

---
[← Anterior: Alcance y Contexto del Sistema](03_system_scope_and_context.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Vista de Bloques →](05_building_block_view.md)
