## DESCRIPCIÓN DEL PROYECTO

Invoice es la aplicación que se realizará de manera modular para el estudio de todo el temario de ✨DEINT.✨

La aplicación cuenta con los módulos de:
-  app: elementos generales en toda la aplicación.
- domain:  clases de datos, clase para la base de datos, repositorios de entidades, etc.
- features: vistas y lógica de aplicación para cada apartado de la aplicación, tales como clientes, productos, tareas y facturas.
- infrastructure: módulo para Firebase.
</br>

## Características

- Aplicación que se ha desarrollado con módulos dinámicos
- Nevagación a Fragment de forma directa
- Preferencias funcionales tanto generales para la aplicación como específicas para cada módulo.
- Notificaciones al realizar ciertas acciones en los diferentes módulos.
- Implementación completa de base de datos SQLite gestionada mediante la librería Room.
- Persistencia de datos mediante DataStore y Room.


## V1.0
- Planteamiento del proyecto.
- Distribución inicial de las tareas.
- Primer esquema de clases y packages.

## V2.0 (29/10/2023)
 - Se ha quitado carpetas del proyecto innecesarios en GitHub
 - Se ha añadido fichero README.md con las mejoreas de la versión 2.0
 - Se ha modificado la actividad y el fragmento principal <code>MainFragemnt</code> que contiene dos botones que nos mostrarán el <code>Sign In</code> y el registro <code>Sign Up</code>

## V3.0 (16/12/2023)
 - Se han implementado mejoras en los diferentes módulos.
 - Corregido el problema en los módulos customer e item en los que no se aplicaba correctamente el modelo MVVM.
 - En la lista de productos ahora se pregunta al usuario antes de eliminar un item.
 - En el módulo customer se ha implementado de manera más correcta el botón de editar.
 - En el módulo ítem se ha mejorado el diseño de la vista de detalles de producto.
 - Añadidas la funcionalidad de ordenar y refrescar accesibles desde la toolbar en los módulos account, customer e item.

## V4.0 (01/03/2024)
 Versión final del proyecto con la adición de la mayoría de las funcionalidades de la aplicación.
 </br>
 - Adición de menú de opciones de preferencias totalmente funcional.
 - Implementación de sistemas de notificaciones.
 - Navegación a través de un menú principal o utilizando un drawer lateral.
 - Módulos de account, item, customer y task con funcionalidad CRUD completa (posibilidad de crear o añadir elementos, ver listado de elementos y posibilidad de acceder a detalles de los mismos, actualizar o editar elementos y posibilidad de eliminarlos con seguridad).
 - Migración completa desde repositorios estáticos a repositorios que trabajan con la librería Room e interfaces DAO para la comunicación con base de datos.
 - Actualizacion del modulo de FeatureInvoice (facturas) con los requerimientos del segundo trimestre.

## DETALLES DE FUNCIONALIDADES
### - Sign In
</br>
<p align="center">
<img src="https://github.com/CBocka/Invoice/assets/156449965/0156cf3f-eb07-48cf-bb2d-a35bc6e42da3" height="450" width="220" >
</p>
</br>
Funciona con Firebase. Loggeo con una cuenta registrada en Firebase para nuestra aplicación. Puede probarse con usuario hamilton@gmail.com y contraseña 123456789
</br>
En la opción de ¿No tienes una cuenta? Crea una aquí podemos navegar a la vista de registro de usuarios que veremos a continuación.

### - Sign Up
</br>
<p align="center">
<img src="https://github.com/CBocka/Invoice/assets/156449965/90f7b90a-b45c-4f6f-ae83-0cd9f92c393a" height="450" width="220" >
</p>
</br>
Aquí podemos crear nuevas cuentas de usuario que se añaden a la tabla correspondiente en la base de datos y la cuál podemos consultar en su propia opción del menú principal.
</br>
Los cuadros de texto tienen control para el usuario. No es posible dejar ninguno de ellos vacío. Además, el cuadro de email tiene control de formato (debe introducirse un correo electrónico) y el de confirmar contraseña obliga a introducir el mismo contenido que en el introducir contraseña. Los errores se muestran en el propio cuadro en un área correspondiente y se eliminan mediante TextWatcher.

### - Preferencias de la aplicación
Vista de opciones de preferencias para la aplicación accesible desde una opción en la ToolBar. 
</br>La aplicación cuenta con preferencias generales para toda la aplicación:
- Posibilidad de cambiar el tema de la aplicación entre tema claro y oscuro mediante un switch (implementado y funcional).
- Activar o desactivar notificaciones en la aplicación (implementado y funcional).

Preferencias específicas de cada módulo:
- Opciones al usuario para elegir como quiere ordenar las distintas listas de ítems, clientes, tareas y facturas.
- Opciones de filtro de las listas de tareas e ítems en función de algunas propiedades de los elementos que contienen.
- Posibilidad de activar un nombre por defecto o de deshabilitar la creación de nuevos clientes.
- Opcion para filtrar las facturas por nombre alfabetico de factura o por nombre alfabetico del cliente de la factura.

### - Drawer lateral para navegación
</br>
<p align="center">
<img src="https://github.com/CBocka/Invoice/assets/156449965/14c60a88-871e-4459-88c1-8463f3ebfe63" height="450" width="220" >
</p>
</br>

### - Pruebas unitarias de las clases del modelo de datos
Se han realizado baterías de pruebas para lograr una cobertura del 100% en las entidades del módulo account, item, task y customer.
</br>

### - Notificaciones
Las notificaciones son lanzadas en los siguientes módulos:
- Customer: se lanza una notificación al modificar o crear un cliente.
- Item: se lanza una notificación al modificar o crear un item.
- Task: se lanza una notificación al modificar o crear una tarea.
- FeatureInvoice: se lanza una notificación al crear una factura.
</br>

En las preferencias hay una opción para activar las notificaciones en caso de que no estén activadas. 
Una vez activadas, no se pueden desactivar desde esta opción.

Al iniciar la aplicación por primera vez tras ser instalada se pedirán los permisos de notificaciones.

### - Lista de Usuarios

Listado con los usuarios creados mediante la vista de Sign Up. Pueden ser eliminados mediante una pulsación larga sobre el elemento deseado.

</br>

### - Módulo Item
</br>
<p align="center">
<img src="https://github.com/CBocka/Invoice/assets/156449965/ca8b1a8e-c109-4e0e-9690-383560eb653b" height="450" width="220" >
</p>
</br>

Listado de todos los productos y servicios en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de eliminar un elemento con una pulsación larga sobre él.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore. Ordenación por orden alfabético del nombre o por precio. Opción para filtrar por productos, por servicios o para ver todos los ítems.
- Opciones rápidas de ordenación sin persistencia en la ToolBar visible desde este fragment.
- Opción para navegar a la vista de edición a través de una opción exclusiva en la tool bar en el fragment de Details.
- Control total de los datos que el usuario introduce en la vista de crear un nuevo ítem (CreationFragment). Los cuadros de texto informan de los errores y ayudan al usuario a la correcta introducción de los datos necesarios. 
- Cuadros de texto obligatorios y otros opcionales.
- El texto de error se elimina mediante TextWatcher cuando el usuario comienza a escribir de nuevo en ellos.
- El ID tiene un formato específico que es indicado al usuario mediante un diálogo en caso de error al introducirlo.

</br>

### - Módulo Customer
</br>
<p align="center">
<img src="https://github.com/CBocka/Invoice/assets/156449965/817ba383-e2d1-439f-b3e2-ac6243989ea9" height="450" width="220" >
</p>
</br>

Listado de todos los clientes en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de eliminar un elemento con una pulsación larga sobre él.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore.
- Opciones rápidas de ordenación en la ToolBar visible desde este fragment.
- Opción para navegar a la vista de edición a través de un botón en el fragment de Details.
- Control total de los datos que el usuario introduce en la vista de crear un nuevo cliente (CreationFragment). Los cuadros de texto informan de los errores y ayudan al usuario a la correcta introducción de los datos necesarios. 
- Cuadros de texto obligatorios y otros opcionales.
- El texto de error se elimina mediante TextWatcher cuando el usuario comienza a escribir de nuevo en ellos.
  
</br>

### - Módulo Task
</br>
<p align="center">
<img src="https://github.com/CBocka/Invoice/assets/156449965/d88afa4d-b622-4898-b7eb-6bb1461a0bd2" height="450" width="220" >
</p>
</br>

Listado de todos las tareas tanto pendientes como completadas en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de eliminar un elemento con una pulsación larga sobre él.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore. Filtrado de tareas en función de si están completas o pendientes y posibilidad de ordenación de la lista por nombre, por contacto o por fecha.
- Opciones rápidas de ordenación sin persistencia en la ToolBar visible desde este fragment.
- Opción para navegar a la vista de edición a través de una opción exclusiva en la tool bar en el fragment de Details.
- Control total de los datos que el usuario introduce en la vista de crear una nueva tarea (CreationFragment). Los cuadros de texto informan de los errores y ayudan al usuario a la correcta introducción de los datos necesarios.
- Cuadros de texto obligatorios y otros opcionales.
- El texto de error se elimina mediante TextWatcher cuando el usuario comienza a escribir de nuevo en ellos.
- El spinner de selección de contacto para la tarea es rellenado a partir de la tabla de clientes en la base de datos.
- Los cuadros de fecha de inicio y de fin generan un DatePicker para la selección de la fecha.

</br>
