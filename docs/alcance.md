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

Estos elementos son parte del proyecto pero aún no están completos:

Nivel 2 y Nivel 3 del modelo C4 (desglose interno de la API REST en componentes más finos) — no aplica para el alcance de este taller.
Integración en tiempo real con proveedores o fábrica.
Aplicación móvil para vendedores o mecánicos (el frontend definido es web, en React).
Facturación electrónica ante entidad fiscal (DIAN u otra autoridad tributaria).
Soporte multi-sucursal con sincronización en la nube entre sedes (el despliegue actual contempla una sola sucursal cliente conectada a un único servidor).

Nota

Los 11 diagramas UML (`docs/diagramas/plantuml/`, imágenes en `docs/diagramas/img/`) son la fuente de verdad del alcance. Ante cualquier discrepancia entre este documento y un diagrama, prevalece el diagrama, y este texto debe corregirse para reflejarlo.
