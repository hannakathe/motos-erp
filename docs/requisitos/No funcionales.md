# Requisitos No Funcionales — ERP Andina Motors

## Identidad del producto
- **Nombre:** AndiMotors ERP
- **Colores:** Rojo (#C1272D) y Negro (#1A1A1A), con acentos en gris

> **Sincronización con Jira (2026-09-01).** Se conservan los RNF-1 a RNF-5 de la línea
> de base. Se agregan RNF-6 a RNF-15 para cubrir el alcance ampliado del backlog del
> proyecto **SCRUM** (movimientos y ajustes de inventario, costo promedio, cartera y
> notas crédito/débito, envío a sistema contable/DIAN, nómina, activos fijos y
> depreciación, tableros y reportes EIS). Los RNF marcados como **propuesto** no
> provienen de la fuente original de requisitos; se alinean con decisiones ya
> registradas en `arc-42/` y con [alcance.md](../alcance.md), y siguen siendo
> arquitectura de diseño, no funcionalidad implementada.

## Requisitos No Funcionales

| ID | Requisito | Descripción |
|---|---|---|
| RNF-1 | Rendimiento (facturación) | La verificación de disponibilidad de una unidad (RF-2.2.1) debe responder en menos de 1 segundo. |
| RNF-2 | Seguridad (control de acceso) | Solo usuarios con rol "Vendedor" pueden facturar ventas; solo "Mecánico" o "Jefe de taller" pueden cerrar órdenes de servicio. Ampliado en RNF-11. |
| RNF-3 | Escalabilidad | El sistema debe soportar múltiples sucursales del concesionario consultando el mismo inventario en tiempo real (RF-1.5.1). |
| RNF-4 | Usabilidad (venta) | Un vendedor sin conocimientos técnicos debe poder generar una factura de venta en máximo 3 pasos. |
| RNF-5 | Disponibilidad | El sistema debe garantizar 99% de uptime mensual, considerando que ventas y taller operan en horario comercial extendido. |
| RNF-6 | Integridad transaccional | Las operaciones que combinan varios registros —facturar (RF-2.2/2.3), recibir pedido e ingresar a inventario (RF-3.6.3), registrar un movimiento (RF-1.3), liquidar nómina (RF-4.4)— deben ser atómicas: si un paso falla, ninguno se aplica y no queda stock, cartera ni comprobantes en estado inconsistente. |
| RNF-7 | Auditoría y trazabilidad | Todo movimiento de inventario (RF-1.3), ajuste manual (RF-1.9), aprobación/cancelación de orden de compra (RF-3.5, RF-3.7.2), nota crédito/débito (RF-2.4), traslado y baja de activo (RF-5.4, RF-5.7) debe quedar registrado con usuario, fecha/hora y documento origen. Los registros de auditoría no son editables ni se eliminan físicamente. |
| RNF-8 | Interoperabilidad — facturación electrónica *(propuesto)* | El envío de la factura al sistema contable / DIAN (RF-2.6) debe cumplir el formato y los plazos exigidos por la DIAN, con reintento automático ante fallo y notificación al usuario cuando el documento sea rechazado. Alineado con [AD-10](../arc-42/09_architecture_decisions.md) y [06.4](../arc-42/06_runtime_view.md); no implementado. |
| RNF-9 | Exactitud de cálculo | Los cálculos de costo promedio ponderado (RF-1.6), impuestos y accesorios (RF-2.2.2), comisiones (RF-2.8), nómina (RF-4.4) y depreciación (RF-5.3) deben redondear a 2 decimales y coincidir con la verificación contable manual para los casos de prueba definidos. |
| RNF-10 | Rendimiento de reportería | El dashboard ejecutivo (RF-6.1) y los reportes consolidados (RF-6.3) deben cargar en menos de 5 segundos para un rango de hasta 12 meses de datos históricos. |
| RNF-11 | Seguridad por rol (ampliada) | El sistema define los perfiles: Gerente, Encargado de Compras, Encargado de Inventario/Bodega, Vendedor, Mecánico, Jefe de taller y Contador/Administrativo. Cada operación sensible se restringe a su rol: aprobar orden de compra (RF-3.5), autorizar ajuste de inventario (RF-1.9.2), emitir nota crédito/débito (RF-2.4), generar nómina (RF-4.4), dar de baja un activo (RF-5.7). Todo intento no autorizado responde con un error explícito y queda registrado (RNF-7). Extiende RNF-2. |
| RNF-12 | Concurrencia multiusuario | Ante operaciones simultáneas de varias sucursales o usuarios, el sistema debe preservar la consistencia de las existencias (RF-1.4) y la unicidad y continuidad de la numeración de comprobantes (RF-2.2.3, RF-2.6). Complementa RNF-3. |
| RNF-13 | Conservación de datos | La información contable, de facturación, de cartera, de nómina y de activos fijos debe conservarse como mínimo 5 años. No hay borrado físico: las bajas (clientes, productos, activos, empleados) son lógicas. |
| RNF-14 | Usabilidad de reportería | Un usuario con rol Gerente debe poder generar y exportar un reporte segmentado (RF-6.3 / RF-6.4) en máximo 3 pasos, sin asistencia técnica. |
| RNF-15 | Localización | Los montos se manejan en pesos colombianos (COP) y las fechas en formato dd/mm/aaaa. Las tarifas de impuestos (IVA, INC) y los parámetros de nómina y depreciación son configurables sin cambios de código. |

## Escenarios de calidad

Los RNF-1 a RNF-5 están desarrollados como escenarios de calidad (Fuente/Estímulo →
Contexto → Respuesta esperada → Medida) en
[arc-42/10_quality_requirements.md](../arc-42/10_quality_requirements.md). Los RNF-6 a
RNF-15 quedan pendientes de reescribir en ese mismo formato en una próxima revisión de
esa sección.

## Tecnologías Seleccionadas

| Categoría | Tecnología | Justificación |
|---|---|---|
| Backend | Java + Spring Boot | Robusto para lógica transaccional (facturación, control de stock, nómina, depreciación) |
| Frontend | React | Interfaz ágil para vendedores y mecánicos |
| Base de datos | PostgreSQL | Relacional, ideal para inventario con trazabilidad por VIN y para cartera/contabilidad |
| Despliegue | AWS (EC2 + RDS) — arquitectura propuesta, no desplegada actualmente | Permite acceso multi-sucursal en tiempo real, en un escenario de despliegue futuro |
| Herramientas | Docker | Contenerización del backend para un despliegue reproducible en AWS EC2 |

> Nota: AWS (EC2 + RDS) es la tecnología de despliegue seleccionada como propuesta arquitectónica del taller. El sistema no está desplegado en AWS; para desarrollo y demostración académica se ejecuta localmente. Ver [arc-42/07_deployment_view.md](../arc-42/07_deployment_view.md).
