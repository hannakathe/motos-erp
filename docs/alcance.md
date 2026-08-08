# Alcance del sistema — ERP Concesionario de Motos

La guía pide definir el alcance del sistema mediante la **triple restricción** (tiempo, costo, alcance). Esta versión se ajusta con base en lo que ya está modelado en el diagrama de clases y objetos (vista lógica), para que el alcance describa el sistema real que se está construyendo y no solo una intención general.

## Triple restricción

### Tiempo

El proyecto se desarrolla dentro de la duración del semestre académico, siguiendo el cronograma del taller de arquitectura de software:

- Sesiones 1 y 2: conceptos de arquitectura y modelo 4+1.
- Sesiones 3 y 4: elaboración de los 6 diagramas UML del ERP.
- Entrega final: presentación de 10-15 minutos en inglés con toda la documentación del proyecto.

### Costo

Al ser un proyecto académico, no se maneja un presupuesto monetario real. El "costo" se mide en horas/persona del equipo (Hanna, Ingrid y Marlon), distribuidas según el reparto de tareas ya definido para cada integrante.

### Alcance (scope)

Se define qué queda dentro y qué queda fuera del proyecto, ajustado al dominio que ya está modelado en clases y objetos.

## Dentro del alcance (ya modelado / en desarrollo)

**Entidades del dominio (vista lógica — diagrama de clases y objetos):**

- `Cliente` — persona que adquiere motos/repuestos y/o solicita servicio de taller.
- `Vendedor` — registra ventas (facturas) a nombre de un cliente.
- `ItemVendible` (clase abstracta) — agrupa el atributo común `precio`; de ella heredan `Moto` y `Repuesto`. Existe para que `DetalleFactura` tenga una única asociación obligatoria en vez de dos asociaciones simultáneas.
- `Moto` — unidad de inventario vendible, identificada por su chasis.
- `Repuesto` — ítem de inventario usado tanto en ventas directas como en órdenes de taller.
- `Factura` — comprobante de venta, compuesto por una o más líneas (`DetalleFactura`).
- `DetalleFactura` — línea de factura que referencia un `ItemVendible` (una `Moto` o un `Repuesto`).
- `OrdenTaller` — solicitud de servicio de un cliente, atendida por un `Mecanico`, que puede consumir `Repuesto`.
- `Mecanico` — atiende órdenes de taller y registra diagnósticos.

**Procesos cubiertos por este modelo:**

- Venta de motos y repuestos, con factura y detalle de factura.
- Órdenes de servicio de taller, incluyendo diagnóstico y consumo de repuestos.
- Registro de vendedores (con comisión) y mecánicos (con especialidad).

**Diagramas UML:**

- Diagrama de clases y diagrama de objetos — completos.
- Diagrama de componentes, despliegue, paquetes y estructura compuesta — en desarrollo según el reparto de tareas del equipo.

## Pendiente / en construcción (no confundir con "fuera de alcance")

Estos elementos sí son parte del proyecto, pero aún no están completos al momento de este documento:

- Diagrama de Contexto.
- Diagrama de Contenedores.
- Diagrama de Secuencia.
- Modelo Entidad-Relación (MER).
- Reconciliación del dominio de clases con el resto de la Vista de Bloques, una vez estén listos los diagramas anteriores.

## Fuera del alcance (no se desarrolla en este taller)

- Nivel 2 y Nivel 3 del modelo C4 (desglose interno de la API REST en componentes) — no aplica para el alcance de este taller; se documentaría solo si el proyecto avanzara a ese nivel de detalle.
- Integración en tiempo real con proveedores o fábrica.
- Aplicación móvil para vendedores o mecánicos.
- Facturación electrónica ante entidad fiscal (DIAN u otra autoridad tributaria).
- Soporte multi-sucursal con sincronización en la nube entre sedes.
- Módulo de Activos Fijos y módulo EIS con funcionalidades avanzadas — no están representados en el diagrama de clases actual; si se requieren, deben modelarse aparte y añadirse a este alcance antes de construirlos.

## Nota

Este documento debe actualizarse cada vez que se complete uno de los diagramas pendientes (Contexto, Contenedores, Secuencia, MER), o si el dominio de clases cambia al reconciliarse con el resto de la Vista de Bloques.
