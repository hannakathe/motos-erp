[← Volver al índice](arc42-template-ES.md)

# 10. Requisitos de Calidad

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). Se reorganiza en formato de escenario de calidad (Fuente/Estímulo → Contexto → Respuesta esperada → Medida) la información ya existente en [requisitos/No funcionales.md](<../requisitos/No funcionales.md>). Las métricas marcadas explícitamente como "**propuesta de esta revisión**" no están en ese documento — se agregan como arquitectura de calidad propuesta, a petición del equipo, y se distinguen tipográficamente de lo evidenciado en la fuente original.

## Árbol/resumen de metas de calidad

Ya presentado en [01. Introducción y Metas — 1.3](01_introduction_and_goals.md#13-metas-de-calidad). Esta sección desarrolla cada RNF como escenario.

## RNF-1 — Rendimiento

| Elemento | Detalle |
|---|---|
| Fuente/Estímulo | Un vendedor o el sistema de facturación solicita verificar la disponibilidad de una unidad (RF-2.1.1). |
| Contexto | Operación normal, durante el proceso de facturación de venta de moto. |
| Respuesta esperada | El sistema responde si la unidad está disponible. |
| Medida | Menos de 1 segundo de tiempo de respuesta. |

## RNF-2 — Seguridad

| Elemento | Detalle |
|---|---|
| Fuente/Estímulo | Un usuario del sistema intenta facturar una venta o cerrar una orden de servicio. |
| Contexto | Cualquier momento de operación del sistema. |
| Respuesta esperada | El sistema solo permite facturar ventas a usuarios con rol "Vendedor"; solo permite cerrar órdenes de servicio a usuarios con rol "Mecánico" o "Jefe de taller". |
| Medida | [MÉTRICA POR DEFINIR EN LA FUENTE ORIGINAL] — **propuesta de esta revisión**: 0% de operaciones de facturación o cierre de orden ejecutadas sin verificación previa de `Usuario.rol` en la capa `API Seguridad` (ver [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario)); todo intento no autorizado responde con un código de error HTTP 4xx explícito, sin excepciones no controladas. |

## RNF-3 — Escalabilidad

| Elemento | Detalle |
|---|---|
| Fuente/Estímulo | Múltiples sucursales consultan disponibilidad de inventario simultáneamente (RF-1.3.1). |
| Contexto | Operación con más de una sucursal del concesionario activa. |
| Respuesta esperada | Todas las sucursales consultan el mismo inventario en tiempo real, sin inconsistencias. |
| Medida | [MÉTRICA POR DEFINIR EN LA FUENTE ORIGINAL] — **propuesta de esta revisión**: hasta 10 sucursales concurrentes, con propagación de un cambio de stock visible en todas las sucursales en menos de 2 segundos, apoyada en el Load Balancer y el Servidor de Aplicaciones único agregados en la Vista Física ([AD-12](09_architecture_decisions.md#ad-12--load-balancer-en-la-arquitectura-de-despliegue-propuesta)). Nota: tanto el diagrama de despliegue original como la Vista Física siguen modelando un único cliente/sucursal explícito, sin representar múltiples sucursales simultáneas — ver [11. Riesgos](11_risks_and_technical_debt.md#r-04--despliegue-de-nodo-único-propuesto-frente-a-metas-de-escalabilidad-y-disponibilidad). |

## RNF-4 — Usabilidad

| Elemento | Detalle |
|---|---|
| Fuente/Estímulo | Un vendedor sin conocimientos técnicos necesita generar una factura de venta. |
| Contexto | Operación normal de venta en el punto de atención al cliente. |
| Respuesta esperada | El vendedor completa la facturación de una venta. |
| Medida | Máximo 3 pasos para generar la factura. |

## RNF-5 — Disponibilidad

| Elemento | Detalle |
|---|---|
| Fuente/Estímulo | El sistema debe estar operativo durante el horario comercial extendido de ventas y taller. |
| Contexto | Operación mensual continua. |
| Respuesta esperada | El sistema permanece disponible para su uso. |
| Medida | 99% de uptime mensual. |

## Atributos de calidad no evidenciados

Otros atributos de calidad típicos de un ERP (mantenibilidad, portabilidad, interoperabilidad, recuperación ante desastres, pruebas de carga) no tienen requisitos ni escenarios documentados en el repositorio. [POR DEFINIR]

---
[← Anterior: Decisiones de Arquitectura](09_architecture_decisions.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Riesgos y Deuda Técnica →](11_risks_and_technical_debt.md)
