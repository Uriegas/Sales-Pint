# Actividad de recuperacion Unidad 2
## Caja registradora

## TODO
DELETE in GUI and DB
ADD using DB
Apply ofertongos

Se necesita la implementación de una caja registradora (stand alone) la cual tenga las funcionalidades completas de gestón de inventarios, gestión de promociones y  registro de compras. Se necesta implementar los siguientes requerimientos

  * Funcionales
    * CRUD de inventarios, (Altas, Bajas, Modificaciones de productos en el inventario) (In settings UI)
    * Datos del producto: Nombre, descripción, num. de código de barras (In main window UI) **Ready**
    * Busqueda de producto: Por nombre, código de barra (In main window UI) **Ready**
    * Registro y cancelación de promociones (Tipo de promosiones válidas: 2x1, 3x2, 10%, 20%, 30% y 50%) (In settings UI)
    * Registro de compras, cancelaciones de productos, aplicación de promociones.(Implemented with triggers in DB)
  * No funcionales
    * Uso de SQLite como gestor de base de datos. **Ready**
    * No se requiere inicio de sesión **Ready**
    * Diseño de pantallas "estéticas" mediante uso de JavaFX-CSS 
