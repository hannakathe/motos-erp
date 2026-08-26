[← Volver al índice](arc42-template-ES.md)

# 8. Conceptos Transversales

> Nota metodológica: esta sección no formaba parte del alcance original del taller (ver nota en [arc42-template-ES.md](arc42-template-ES.md)). Se documentan aquí únicamente los conceptos que afectan a varios módulos y que pueden justificarse con evidencia del repositorio. Todo lo que no tiene evidencia se marca como [POR DEFINIR].

## 8.1 Seguridad y control de acceso

Evidencia: RNF-2 en [requisitos/No funcionales.md](<../requisitos/No funcionales.md>).

- Solo el rol **Vendedor** puede facturar ventas.
- Solo **Mecánico** o **Jefe de taller** pueden cerrar órdenes de servicio.

Este es un concepto transversal porque afecta tanto a Facturación (venta y orden de taller) como, potencialmente, a Empleados (gestión de roles). No hay evidencia de:

- Mecanismo de autenticación (login, tokens, sesiones). [POR DEFINIR]
- Mecanismo técnico de autorización (middleware, anotaciones de seguridad, políticas). [POR DEFINIR]
- Modelo de usuarios/roles como entidad del sistema — el diagrama de clases no incluye una clase `Usuario` ni `Rol`; los roles Vendedor y Mecánico existen como clases de dominio (`Vendedor`, `Mecanico`), no como perfiles de acceso al sistema. [POR DEFINIR si son la misma entidad o entidades distintas.]

## 8.2 Persistencia

Base de datos relacional única (PostgreSQL — AndinaMotorsDB), compartida por todos los módulos, según el [diagrama de despliegue](../diagramas/plantuml/diagrama_despliegue.plantuml). No hay evidencia de: esquema de tablas, ORM/framework de persistencia específico, estrategia de migraciones, ni particionamiento de datos por módulo o por sucursal. [POR DEFINIR]

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

---
[← Anterior: Vista de Despliegue](07_deployment_view.md) · [Volver al índice](arc42-template-ES.md) · [Siguiente: Decisiones de Arquitectura →](09_architecture_decisions.md)
