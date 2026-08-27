Alcance del sistema — ERP Andina Motors

La guía pide definir el alcance del sistema mediante la triple restricción (tiempo, costo, alcance). Esta versión se ajusta con base en los 11 diagramas UML del equipo (clases, objetos, componentes, despliegue, paquetes y estructura compuesta...), para que el alcance describa el sistema real que se está construyendo.

Triple restricción
Tiempo

El proyecto se desarrolla dentro de la duración del semestre académico, siguiendo el cronograma del taller de arquitectura de software:

Sesiones 1 y 2: conceptos de arquitectura y modelo 4+1.
Sesiones 3 y 4: elaboración de los 11 diagramas UML del ERP.
Entrega final: presentación de 10-15 minutos en inglés con toda la documentación del proyecto.
Costo

Al ser un proyecto académico, no se maneja un presupuesto monetario real. El "costo" se mide en horas/persona del equipo (Hanna, Ingrid y Marlon), distribuidas según el reparto de tareas ya definido para cada integrante.

Alcance (scope)

Se define qué queda dentro y qué queda fuera del proyecto, con base en lo que ya está modelado en los 11 diagramas.

Dentro del alcance (ya modelado)

Tecnologías seleccionadas (diagrama de despliegue):

Frontend: React, servido al cliente en la sucursal.
Backend: API REST con Spring Boot, desplegado en servidor cloud (AWS EC2).
Base de datos: PostgreSQL (AndinaMotorsDB), en AWS RDS.
Comunicación: HTTPS entre cliente y backend, TCP/5432 entre backend y base de datos.

Nota: "modelado" y "desplegado" aquí describen lo representado en el diagrama de despliegue como arquitectura propuesta, no una infraestructura AWS actualmente operativa. Para el desarrollo y la demostración académica, el sistema se ejecuta localmente; no se realiza un despliegue real en AWS porque implicaría costos y complejidad de infraestructura no necesarios para los objetivos del taller. Ver detalle en [arc-42/07_deployment_view.md](arc-42/07_deployment_view.md).

Entidades del dominio (diagrama de clases y objetos):

Cliente, Vendedor, Mecanico — actores que interactúan con el sistema.
Factura, DetalleFactura — comprobante de venta y sus líneas.
Moto, Repuesto — inventario vendible (con herencia desde ItemVendible).
OrdenTaller — solicitud de servicio de taller.

Componentes y sus interfaces (diagrama de componentes):

Facturación — consume verificarDisponibilidad() y descontarRepuesto() de Inventario, y consultarComision() de Empleados.
Empleados — consume asignarMecanico() de Facturación.
Compras — consume consultarHistoricoVentas() de EIS.
ActivosFijos, EIS — componentes definidos en la arquitectura general.

Paquetes por módulo (diagrama de paquetes) — los 6 módulos:

ActivosFijos → paquete ControlActivos, con RegistrarActivo y CalcularDepreciacion.
Compras → paquetes PedidosFabrica (SeleccionarModelosYCantidades, RegistrarFechaEntrega), que depende de EIS, y RecepcionPedidos (ValidarUnidadesContraOrden), que depende de PedidosFabrica.
Empleados → paquetes GestionPersonal (RegistrarVendedor, RegistrarMecanico) y AsignacionTaller (AsignarOrdenAMecanico, ConsultarDisponibilidadTaller), que depende de GestionPersonal.
Facturación → paquetes VentaMotos (VerificarDisponibilidad, CalcularImpuestos, GenerarComprobantePDF) y OrdenServicio (RegistrarRepuestosUsados, RegistrarManoDeObra), ambos dependientes de Inventario (Stock/Costos), y Comisiones (CalcularComisionVendedor), dependiente de Empleados.
Inventario (Stock/Costos) → paquetes GestionMotos (RegistrarMotoNueva, ConsultarDisponibilidad), GestionRepuestos (RegistrarRepuesto, DefinirStockMinimo) y Costos (ActualizarCostoInventario); GestionRepuestos y Costos dependen de GestionMotos.
EIS → paquete Reportes, con ConsultarHistoricoVentas y GenerarIndicadores.

Estructura interna (diagrama de estructura compuesta):

Interior del componente Facturación: GeneradorComprobante, ValidadorComision, ConectorInventario y ConectorEmpleados, conectados con Inventario y Empleados.
Pendiente / en construcción

Estos elementos se habían declarado inicialmente fuera del alcance de este taller. Siguiendo la regla de esta misma sección ("ante discrepancia, prevalece el diagrama"), se actualiza su estado porque el equipo agregó posteriormente diagramas adicionales que sí los modelan:

- ~~Nivel 2 y Nivel 3 del modelo C4~~ → **Resuelto parcialmente**: el equipo agregó [diagrama_c4_contexto.plantuml](diagramas/plantuml/diagrama_c4_contexto.plantuml) (Nivel 1) y [diagrama_c4_contenedores.plantuml](diagramas/plantuml/diagrama_c4_contenedores.plantuml) (Nivel 2). El Nivel 3 (código) sigue sin modelarse — sigue fuera de alcance.
- ~~Integración en tiempo real con proveedores o fábrica~~ → **Ahora modelada** en la Vista Física y la Vista de Escenarios (carpeta [`Vista de Procesos (4+1)/`](<../Vista de Procesos (4+1)>)): "Sistema Proveedores (Compras B2B)" y actor "Proveedor". El diagrama de componentes y los diagramas de paquetes originales (`docs/diagramas/plantuml/`) **no** se actualizaron para reflejar esto — ver inconsistencia señalada en [arc-42/11_risks_and_technical_debt.md](arc-42/11_risks_and_technical_debt.md).
- ~~Aplicación móvil para vendedores o mecánicos~~ → **Ahora modelada** en la Vista Física ("App Móvil (Vendedores)"). El diagrama de despliegue original ([diagrama_despliegue.plantuml](diagramas/plantuml/diagrama_despliegue.plantuml)) **no** se actualizó para incluirla — sigue mostrando solo el frontend web. Ver inconsistencia en [arc-42/11_risks_and_technical_debt.md](arc-42/11_risks_and_technical_debt.md).
- ~~Facturación electrónica ante entidad fiscal (DIAN u otra autoridad tributaria)~~ → **Ahora modelada**: la Vista Lógica agrega la clase `Factura` (con `cufe`, `enviarADIAN()`), la Vista de Procesos muestra el envío asíncrono a DIAN, la Vista Física agrega "Sistema Externo DIAN (Facturación Electrónica)" y la Vista de Escenarios agrega el actor DIAN y el caso de uso `«include»` "Generar factura electrónica". El diagrama de clases original ([diagrama_clases.plantuml](diagramas/plantuml/diagrama_clases.plantuml)) **no** se actualizó para incluir `cufe` ni `enviarADIAN()` — ver inconsistencia en [arc-42/11_risks_and_technical_debt.md](arc-42/11_risks_and_technical_debt.md).
- Soporte multi-sucursal con sincronización en la nube entre sedes — **sigue fuera de alcance**: ni el diagrama de despliegue original ni la Vista Física modelan más de una sucursal cliente simultánea (la Vista Física modela un único Load Balancer/servidor de aplicaciones, sin distinguir sucursales).

Importante: todo lo anterior — incluida la integración con DIAN, la app móvil y la integración con proveedores — sigue siendo **arquitectura propuesta a nivel de diseño**, no una funcionalidad implementada ni desplegada. Ver [arc-42/07_deployment_view.md](arc-42/07_deployment_view.md) para el estado real (ejecución local, sin infraestructura activa).

Nota

Los diagramas del equipo (`docs/diagramas/plantuml/`, imágenes en `docs/diagramas/img/`, y la carpeta [`Vista de Procesos (4+1)/`](<../Vista de Procesos (4+1)>) agregada posteriormente) son la fuente de verdad del alcance. Ante cualquier discrepancia entre este documento y un diagrama, prevalece el diagrama, y este texto debe corregirse para reflejarlo. Cuando dos generaciones de diagramas entran en conflicto entre sí (por ejemplo, el diagrama de componentes original no modela DIAN/Proveedores, pero la Vista Física sí), esa discrepancia se documenta explícitamente en [arc-42/11_risks_and_technical_debt.md](arc-42/11_risks_and_technical_debt.md) en vez de resolverse silenciosamente.
