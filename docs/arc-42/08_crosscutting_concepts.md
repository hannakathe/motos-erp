[← Volver al índice](arc42-template-ES.md)

# 8. Conceptos Transversales

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). Se documentan aquí únicamente los conceptos que afectan a varios módulos y que pueden justificarse con evidencia del repositorio. Todo lo que no tiene evidencia se marca como [POR DEFINIR].

## 8.1 Seguridad y control de acceso

Evidencia: RNF-2 en [requisitos/No funcionales.md](<../requisitos/No funcionales.md>).

- Solo el rol **Vendedor** puede facturar ventas.
- Solo **Mecánico** o **Jefe de taller** pueden cerrar órdenes de servicio.

Este es un concepto transversal porque afecta a todos los módulos con operaciones sensibles (Facturación, Empleados, Compras, ActivosFijos). **Resuelto a nivel de diseño propuesto** en esta revisión, con la Vista Lógica actualizada (clase `Usuario`: `id`, `rol`, `usuario`, `passwordHash`) y la Vista de Desarrollo (capa `API Seguridad`) — ver detalle completo en [4.5](04_solution_strategy.md#45-seguridad) y [AD-09](09_architecture_decisions.md#ad-09--autenticación-y-autorización-basada-en-la-entidad-usuario).

Sigue sin evidencia (ni en el diagrama de clases original ni en la Vista Lógica nueva):

- Protocolo de autenticación concreto (JWT, sesiones de servidor, OAuth2). [POR DEFINIR]
- Algoritmo de hashing usado para `passwordHash` (bcrypt, PBKDF2, etc.). [POR DEFINIR]
- Relación explícita entre `Usuario` (nueva) y `Vendedor`/`Mecanico` (clases de dominio del diagrama original) — [POR DEFINIR si `Usuario` es una entidad separada vinculada a `Vendedor`/`Mecanico`, o si los reemplaza como el perfil de acceso].

## 8.2 Persistencia

Base de datos relacional única (PostgreSQL — AndinaMotorsDB), compartida por todos los módulos, según el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml). La Vista de Desarrollo agrega un patrón **Repository** explícito (`Repositorio Ventas`, `Repositorio Compras`, `Repositorio Inventario`, sobre un `ORM` en la capa de Infraestructura) — ver [5.2.1](05_building_block_view.md#521-vista-de-desarrollo--arquitectura-en-capas). Sigue sin evidencia: esquema de tablas, framework ORM concreto (JPA/Hibernate u otro), estrategia de migraciones, ni particionamiento de datos por módulo o por sucursal. [POR DEFINIR]

## 8.3 Comunicación entre módulos

Los módulos se comunican mediante llamadas directas a operaciones expuestas (dependencias `..>` en el [diagrama de componentes](../diagramas/plantuml/diagrama_componentes.plantuml)):

| Módulo consumidor | Módulo proveedor | Operación |
|---|---|---|
| Facturación | Inventario | `verificarDisponibilidad()`, `descontarRepuesto()` |
| Facturación | Empleados | `consultarComision()` |
| Compras | EIS | `consultarHistoricoVentas()` |
| Empleados | Facturación | `asignarMecanico()` |

Es un patrón de **llamada síncrona punto a punto** entre componentes; no hay evidencia de un bus de eventos, cola de mensajes o gateway de integración compartido. Ver también [4.3](04_solution_strategy.md#43-descomposición-y-modularización).

## 8.4 Validación de datos

Única evidencia concreta encontrada: el [plan de pruebas del Sprint 1](../sprint/sprint-1-planning.md) (épica Compras) especifica comportamientos esperados de validación:

- PT-12.2: un pedido sin ningún modelo seleccionado debe ser rechazado, con notificación al usuario.
- PT-13.2: consultar histórico de ventas de un modelo sin ventas registradas debe mostrar histórico vacío, sin error.
- PT-15.2: si faltan unidades respecto a la orden de compra, el sistema debe señalar el faltante y el modelo/cantidad afectado.

Estas reglas están documentadas solo para la épica Compras (Sprint 1); no hay validaciones equivalentes documentadas para Facturación, Inventario, Empleados, EIS o ActivosFijos. [POR DEFINIR para el resto de módulos.]

## 8.5 Manejo de errores

[POR DEFINIR — no hay una estrategia de manejo de errores documentada (códigos de error, formato de respuesta de error, páginas o mensajes estándar). Las únicas menciones de comportamiento ante error son los casos de prueba de Compras citados en 8.4.]

## 8.6 Logging y auditoría

[POR DEFINIR — no se encontró evidencia de logging, trazabilidad de cambios o auditoría en el repositorio.]

## 8.7 Configuración

[POR DEFINIR — no existen archivos de configuración de la aplicación (application.properties, .env, docker-compose, etc.) en el repositorio; solo hay una configuración de editor para renderizar PlantUML ([.vscode/settings.json](../../.vscode/settings.json)), que no es configuración del sistema en ejecución.]

## 8.8 Reglas de negocio compartidas entre paquetes

Los diagramas de paquetes (sección [5.2](05_building_block_view.md#52-caja-blanca-por-componente-nivel-2--diagramas-de-paquetes)) documentan dependencias explícitas entre paquetes dentro de un mismo módulo, que reflejan reglas de negocio compartidas:

- `GestionRepuestos` y `Costos` dependen de `GestionMotos` (Inventario).
- `VentaMotos` y `OrdenServicio` dependen del módulo Inventario (Stock/Costos).
- `Comisiones` depende de Empleados.
- `AsignacionTaller` depende de `GestionPersonal` (Empleados).
- `RecepcionPedidos` depende de `PedidosFabrica`, que a su vez depende de EIS (Compras).

## 8.9 Manejo de identidad de entidades de dominio

**Inferencia**: el sistema usa identificadores de negocio explícitos para trazabilidad individual, no solo conteo por cantidad — evidenciado por el atributo `chasis` en la clase `Moto` ([diagrama de clases](../diagramas/plantuml/diagrama_clases.plantuml)) y por RF-1.1.1 ("Capturar número de VIN/chasis... necesario para trazabilidad individual"). Este concepto de trazabilidad por identificador único es transversal a Inventario, Facturación (`DetalleFactura` referencia una `Moto` específica) y Compras (RF-3.2.1 valida unidades recibidas contra la orden).

## 8.10 Integración con sistemas externos

**Nuevo en esta revisión**, a partir de la Vista Física, la Vista de Procesos y la Vista de Escenarios:

- **DIAN** (facturación electrónica): integración vía un `Cliente DIAN (SOAP/REST)` en la capa de Infraestructura, invocado por el `Servicio Facturación`, con llamada modelada como **asíncrona** en la Vista de Procesos.
- **Proveedores** (compras B2B): integración HTTPS (API) modelada solo en la Vista Física, sin componente de infraestructura equivalente dibujado en la Vista de Desarrollo (no hay `Cliente Proveedores` junto al `Cliente DIAN`). [POR DEFINIR el mecanismo de integración con proveedores a nivel de capa de Infraestructura.]

Ambas integraciones son transversales porque afectan Facturación (DIAN) y Compras (Proveedores), y ambas son **propuestas de diseño sin implementación**, y contradicen el alcance declarado originalmente en `alcance.md` (ver [4.7](04_solution_strategy.md#47-integración-con-sistemas-externos-dian-y-proveedores) y [11. Riesgos — R-11](11_risks_and_technical_debt.md#r-11--integración-con-dian-y-proveedores-modelada-en-diagramas-nuevos-pero-ausente-en-los-diagramas-originales)).

---
[← Anterior: Vista de Despliegue](07_deployment_view.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Decisiones de Arquitectura →](09_architecture_decisions.md)
