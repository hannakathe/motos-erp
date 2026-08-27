[← Volver al índice](arc42-template-ES.md)

# 7. Vista de Despliegue

> **Aviso importante**: el sistema **no está desplegado en ninguna infraestructura cloud**. No existe una instancia EC2, una base de datos RDS, un Load Balancer, ni ninguna otra infraestructura AWS o Azure activa asociada a este proyecto. AWS (y, según la Vista Física agregada posteriormente, alternativamente Azure — ver [7.4](#74-vista-física-versión-extendida-agregada-posteriormente)) aparece en este capítulo únicamente como **arquitectura de despliegue propuesta**, documentada con fines académicos (ver [09. Decisiones de Arquitectura — AD-07](09_architecture_decisions.md#ad-07--propuesta-de-despliegue-cloud-aws)). No se afirma en ningún punto de este documento que exista una URL de producción, pruebas realizadas sobre infraestructura cloud, ni costos de nube incurridos.

## 7.1 Estado actual

El sistema **no se encuentra desplegado en infraestructura cloud**. Es un proyecto académico universitario (taller de Arquitectura de Software, ver [alcance.md](../alcance.md)) cuyo objetivo es demostrar funcionalidad, arquitectura, requisitos y calidad del diseño, no operar un servicio en producción.

Para el desarrollo y la demostración académica, el sistema se ejecuta **localmente**: backend, frontend y base de datos se ejecutan en el entorno de desarrollo configurado por el equipo, sobre la misma máquina o red local, sin depender de un proveedor cloud.

La ejecución local permite demostrar:

- funcionamiento del frontend;
- funcionamiento del backend;
- comunicación entre componentes;
- acceso a la base de datos;
- cumplimiento de los requisitos funcionales (RF-1 a RF-4);
- los escenarios de uso definidos en [06. Vista de Ejecución](06_runtime_view.md).

| Entorno | Estado |
|---|---|
| Desarrollo local | Implementado |
| Demostración local | Implementado |
| AWS (EC2, RDS, infraestructura cloud en general) | Propuesto — no implementado |
| Azure (alternativa mencionada en la Vista Física) | Propuesto — no implementado; ver ambigüedad en [R-12](11_risks_and_technical_debt.md#r-12--ambigüedad-de-proveedor-cloud-aws-vs-awsazure-entre-diagramas) |
| Load Balancer | Propuesto — no implementado |
| App móvil | Propuesta — no implementada |
| Integración DIAN | Propuesta — no implementada |
| Integración con Proveedores (Compras B2B) | Propuesta — no implementada |
| Producción | No implementado |

[NO EVIDENCIADO EN EL REPOSITORIO: no hay archivos de configuración, scripts de arranque, Dockerfile ni documentación operativa que describan paso a paso el entorno de desarrollo local (puertos, variables de entorno, comandos de ejecución). Esta tabla describe el estado declarado del proyecto, no un procedimiento verificado en el repositorio.]

## 7.2 Arquitectura de despliegue propuesta

Como ejercicio de arquitectura del taller, el equipo planteó una posible implementación **futura** utilizando Amazon Web Services (AWS), documentada en el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) y en [requisitos/No funcionales.md](<../requisitos/No%20funcionales.md>).

**Esta arquitectura es una PROPUESTA y no corresponde a una infraestructura actualmente desplegada.** Ningún componente descrito a continuación está activo ni operativo.

La arquitectura propuesta contempla conceptualmente:

```
Frontend (React)
     ↓  HTTPS
AWS EC2 — API Backend (Spring Boot)
     ↓  TCP/5432
AWS RDS — PostgreSQL (AndinaMotorsDB)
```

![Diagrama de Despliegue — Arquitectura propuesta, no operativa](../diagramas/img/diagrama_despliegue.png)

> ⚠️ Esta imagen representa la **arquitectura de despliegue propuesta** para una eventual evolución del proyecto. No representa infraestructura actualmente operativa. El archivo fuente ([diagrama_despliegue.plantuml](../diagramas/plantuml/diagrama_despliegue.plantuml)) fue actualizado con un título que indica esta condición; la imagen `.png` embebida corresponde a una versión anterior del título y se conserva sin regenerar (no se elimina ni se reemplaza el diagrama existente).

Del [diagrama_despliegue.plantuml](../diagramas/plantuml/diagrama_despliegue.plantuml) (elementos propuestos, no desplegados):

| Nodo propuesto | Contendría | Descripción |
|---|---|---|
| Cliente - Sucursal | Frontend React | Se ejecutaría en el navegador del usuario, en la sucursal del concesionario. |
| Servidor Cloud (AWS EC2) — propuesto | API Backend - Spring Boot | Concentraría la lógica de negocio del ERP, en un escenario de despliegue futuro. |
| Servidor BD (AWS RDS) — propuesto | PostgreSQL - AndinaMotorsDB | Almacenamiento persistente propuesto para todos los módulos del ERP. |

**Conexiones propuestas**:

| Origen | Destino | Protocolo propuesto |
|---|---|---|
| Cliente - Sucursal | Servidor Cloud (AWS EC2) | HTTPS |
| Servidor Cloud (AWS EC2) | Servidor BD (AWS RDS) | TCP/5432 |

**Motivación de la propuesta**: en un escenario de despliegue futuro, alojar el backend y la base de datos en AWS (EC2 + RDS) permitiría que la sucursal cliente accediera al mismo inventario en tiempo real (RNF-3 Escalabilidad), sin necesidad de infraestructura propia en el local. Esta motivación justifica la elección tecnológica de la propuesta; no implica que el beneficio esté actualmente disponible.

Según [alcance.md](../alcance.md), incluso en el escenario propuesto se contemplaría una sola sucursal cliente conectada a un único servidor; el soporte multi-sucursal con sincronización en la nube entre varias sedes quedaría fuera del alcance ya modelado. Ver también [11. Riesgos y Deuda Técnica](11_risks_and_technical_debt.md).

## 7.3 Infraestructura propuesta (detalle)

Se documenta únicamente como propuesta conceptual. Ningún elemento de esta sección está implementado.

### Frontend

Posible alojamiento en un escenario de despliegue futuro:

- Se propone AWS S3 + CloudFront como alternativa de alojamiento estático, o
- podría utilizarse un servidor web apropiado equivalente.

[NO EVIDENCIADO EN EL REPOSITORIO: el diagrama de despliegue solo modela el frontend como parte del nodo "Cliente - Sucursal", sin especificar S3/CloudFront; esta alternativa se documenta como opción conceptual adicional a lo ya modelado, no como algo ya decidido.]

### Backend

Posible alojamiento: AWS EC2 (tal como está modelado en el diagrama de despliegue propuesto).

### Base de datos

Posible alojamiento: AWS RDS PostgreSQL (tal como está modelado en el diagrama de despliegue propuesto).

### Red y seguridad

Como propuesta conceptual, en un escenario de despliegue futuro podrían contemplarse:

- VPC;
- Security Groups;
- HTTPS (ya modelado como protocolo entre cliente y backend en el diagrama propuesto);
- control de acceso (relacionado con RNF-2, ver [08.1](08_crosscutting_concepts.md#81-seguridad-y-control-de-acceso));
- separación entre aplicación y base de datos (ya modelada como dos nodos distintos en el diagrama propuesto).

[NO EVIDENCIADO EN EL REPOSITORIO: VPC y Security Groups no aparecen en el diagrama de despliegue ni en ningún documento fuente; se listan aquí como alternativas conceptuales de una arquitectura AWS típica, no como elementos ya decididos por el equipo.]

## 7.4 Vista Física (versión extendida, agregada posteriormente)

El equipo agregó, después del diagrama de despliegue original, una **Vista Física** más detallada dentro de [`Vista de Procesos (4+1)/4. vista fisica.jpg`](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>). Esta vista **resuelve gran parte del "Nivel 2" que antes estaba `[POR DEFINIR]`**, aunque sigue siendo una **propuesta de diseño, no una infraestructura desplegada** — se aplica el mismo aviso de la parte superior de este documento.

![Vista Física](<../../Vista de Procesos (4+1)/4. vista fisica.jpg>)

| Elemento propuesto | Descripción | Resuelve |
|---|---|---|
| Load Balancer | Recibe tráfico HTTPS del navegador y de la app móvil, y lo distribuye hacia el Servidor de Aplicaciones por HTTP interno. | [R-04](11_risks_and_technical_debt.md#r-04--despliegue-de-nodo-único-propuesto-frente-a-metas-de-escalabilidad-y-disponibilidad) (sin balanceo de carga documentado). |
| App Móvil (Vendedores) | Cliente adicional al navegador web, mismo canal HTTPS hacia el Load Balancer. | Contradice `alcance.md` original (app móvil declarada fuera de alcance) — ver nota de evolución en [alcance.md](../alcance.md). |
| Servidor de Reportes | Nodo separado del Servidor de Aplicaciones, conectado por HTTP interno; sin mapeo explícito a un módulo, pero es el candidato natural para EIS. | Vacío de infraestructura para EIS que no existía en el diagrama de despliegue original. |
| Sistema Externo DIAN (Facturación Electrónica) | Conectado al Servidor de Aplicaciones por HTTPS (Web Service). | Ver [4.7](04_solution_strategy.md#47-integración-con-sistemas-externos-dian-y-proveedores). |
| Sistema Proveedores (Compras B2B) | Conectado al Servidor de Aplicaciones por HTTPS (API). | Ídem; contradice `alcance.md` original (integración con proveedores/fábrica declarada fuera de alcance). |
| "Proveedor Cloud (AWS/Azure)" | Envoltorio que agrupa Load Balancer, Servidor de Aplicaciones y Servidor de BD. | Ver ambigüedad AWS vs. AWS/Azure en [11. Riesgos — R-12](11_risks_and_technical_debt.md#r-12--ambigüedad-de-proveedor-cloud-aws-vs-awsazure-entre-diagramas). |

Sigue **sin evidencia** en cualquiera de las dos versiones de diagrama de despliegue: contenedores Docker específicos, zonas de disponibilidad, estrategia de backup/recuperación, y el detalle de dimensionamiento (CPU/memoria/instancias) del Servidor de Aplicaciones o del Load Balancer.

## 7.5 Nivel 3 (dimensionamiento y configuración de infraestructura)

[POR DEFINIR — ni el diagrama de despliegue original ni la Vista Física especifican tipo de instancia, número de réplicas, política de autoescalado, zonas de disponibilidad, ni configuración concreta de contenedores. Docker sigue listado como herramienta en [requisitos/No funcionales.md](<../requisitos/No%20funcionales.md>) sin evidencia de implementación — ver [AD-05](09_architecture_decisions.md#ad-05--contenerización-del-backend-con-docker).]

---
[← Anterior: Vista de Ejecución](06_runtime_view.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Conceptos Transversales →](08_crosscutting_concepts.md)
