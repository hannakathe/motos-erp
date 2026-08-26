[← Volver al índice](arc42-template-ES.md)

# 3. Alcance y Contexto del Sistema

## 3.1 Contexto de negocio

El sistema no tiene un diagrama de contexto (C1) dedicado; el contexto de negocio se reconstruye a partir del [diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml) y de los requisitos no funcionales de seguridad (RNF-2, en [No funcionales.md](<../requisitos/No funcionales.md>)).

Actores/roles que interactúan con AndiMotors ERP:

| Actor | Interacción con el sistema |
|---|---|
| Cliente | Adquiere motos y/o repuestos, o solicita servicio de taller (`Cliente` en el diagrama de clases). |
| Vendedor | Registra ventas (facturas) a nombre de un cliente; único rol autorizado a facturar ventas (RNF-2). |
| Mecánico | Atiende órdenes de taller y registra diagnósticos; autorizado a cerrar órdenes de servicio junto al Jefe de taller (RNF-2). |
| Jefe de taller | Autorizado a cerrar órdenes de servicio (RNF-2). [PENDIENTE: no modelado como clase en el diagrama de clases actual]. |

Entre módulos internos, el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml) documenta las siguientes dependencias de negocio:

- Facturación consume `verificarDisponibilidad()` y `descontarRepuesto()` de Inventario, y `consultarComision()` de Empleados.
- Empleados consume `asignarMecanico()` de Facturación.
- Compras consume `consultarHistoricoVentas()` de EIS.

## 3.2 Contexto técnico

Del [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml) (ver también [Vista de Despliegue](07_deployment_view.md)):

| Origen | Destino | Canal/Protocolo |
|---|---|---|
| Cliente - Sucursal (Frontend React) | Servidor Cloud (API Backend Spring Boot, AWS EC2) | HTTPS |
| Servidor Cloud (API Backend Spring Boot) | Servidor BD (PostgreSQL - AndinaMotorsDB, AWS RDS) | TCP/5432 |

No hay sistemas externos (proveedores, fábrica, pasarelas de pago, autoridad tributaria) modelados en el diagrama de despliegue actual. Según [alcance.md](../alcance.md), quedan explícitamente fuera del alcance del taller: la integración en tiempo real con proveedores o fábrica, la facturación electrónica ante entidad fiscal, la aplicación móvil y el soporte multi-sucursal con sincronización en la nube entre sedes (el despliegue actual contempla una sola sucursal cliente conectada a un único servidor).

---
[← Anterior: Restricciones de la Arquitectura](02_architecture_constraints.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Estrategia de Solución →](04_solution_strategy.md)
