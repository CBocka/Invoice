# PROYECTO INVOICE 
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/2314913b-5a18-4e7d-9ff7-811f273cff48" height="450" width="220" >
</p>
</br>

## GRUPO MARGARET-HAMILTON
#### Componentes del grupo:
- Carlos Bocka López.
- Antonio Jiménez Espejo.
- Carlos Rodríguez Pinazo.
- Sergio Silva Ortega.
</br>

## DESCRIPCIÓN DEL PROYECTO Y DISTRIBUCIÓN DE TAREAS

Invoice es la aplicación que se realizará de manera modular para el estudio de todo el temario de ✨DEINT.✨

La aplicación cuenta con los módulos de:
-  app: elementos generales en toda la aplicación.
- domain:  clases de datos, clase para la base de datos, repositorios de entidades, etc.
- features: vistas y lógica de aplicación para cada apartado de la aplicación, tales como clientes, productos, tareas y facturas.
- infrastructure: módulo para Firebase.
</br>

#### DISTRIBUCIÓN DEL TRABAJO
- Módulo app -> Carlos Bocka y Antonio.
- Módulo domain -> Carlos Bocka y Antonio.
- Fase inicial del apartado de tareas (Task) -> Sergio.
- Parte casi completa del apartado de facturas -> Carlos Rodríguez.
- Parte completa del apartado de cuentas y usuarios -> Trabajo en clase, Carlos Bocka y Antonio.
- Parte completa del apartado de clientes (Customer) -> Antonio.
- Parte completa del apartado de productos (Item) -> Carlos Bocka.
- Parte completa del apartado de tareas (Task) -> Carlos Bocka.
- Preferencias generales y por módulo -> Carlos Bocka y Antonio.
- Notificaciones -> Antonio.
- Pruebas unitarias de las entidades de los módulos account, item y task -> Carlos Bocka.
- Pruebas unitarias de la entidad del módulo customer -> Antonio.

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
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/ec2c984a-592c-4813-bb24-3ad0845c9b59" height="450" width="220" >
</p>
</br>
Funciona con Firebase. Loggeo con una cuenta registrada en Firebase para nuestra aplicación. Puede probarse con usuario hamilton@gmail.com y contraseña 123456789
</br>
En la opción de ¿No tienes una cuenta? Crea una aquí podemos navegar a la vista de registro de usuarios que veremos a continuación.

### - Sign Up
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/1412cae6-7db4-4573-8dde-a88413f94ad3" height="450" width="220" >
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
  
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/1474e36a-1882-4a1b-976a-e964d976218a" height="450" width="220" >
</p>
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/ab2190ed-8054-4eaa-b0e2-94b5e0ef4238" height="450" width="220" >
</p>
</br>

### - Drawer lateral para navegación
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/22ba4e60-64c9-4c24-9886-d6832145726a" height="450" width="220" >
</p>
</br>

### - Pruebas unitarias de las clases del modelo de datos
Se han realizado baterías de pruebas para lograr una cobertura del 100% en las entidades del módulo account, item, task y customer.

</br>

### - Notificaciones
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/3d160041-af1c-4158-bfac-9eb9080eb9fa" height="450" width="220" >
</p>
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/2d68a663-f155-4ea1-8284-a9db368d98f8" height="450" width="220" >
</p>
</br>

Estas capturas son de ejemplos de dos notificaciones lanzadas por la aplicación al realizar acciones en diferentes módulos. También funcionan para el módulo Task.
En cualquier caso, las notificaciones son lanzadas en los siguientes módulos:
- Customer: se lanza una notificación al modificar o crear un cliente.
- Item: se lanza una notificación al modificar o crear un item.
- Task: se lanza una notificación al modificar o crear una tarea.
- FeatureInvoice: se lanza una notificación al crear una factura.
</br>

En las preferencias hay una opción para activar las notificaciones en caso de que no estén activadas. 
Una vez activadas, no se pueden desactivar desde esta opción.

Al iniciar la aplicación por primera vez tras ser instalada se pedirán los permisos de notificaciones.

</br>

</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/1a5e9805-d5f6-4f3e-b350-16a229142586" height="450" width="220" >
</p>
</br>

### - Lista de Usuarios

</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/b55b27f1-cc45-4270-9830-28d46a95d80b" height="450" width="220" >
</p>
</br>

Listado con los usuarios creados mediante la vista de Sign Up. Pueden ser eliminados mediante una pulsación larga sobre el elemento deseado.

### - Módulo Item
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/907360ac-efc6-4bb1-884d-44ed1a9296f8" height="450" width="220" >
</p>
</br>

Listado de todos los productos y servicios en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de eliminar un elemento con una pulsación larga sobre él.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore. Ordenación por orden alfabético del nombre o por precio. Opción para filtrar por productos, por servicios o para ver todos los ítems.
- Opciones rápidas de ordenación sin persistencia en la ToolBar visible desde este fragment.
- Opción para navegar a la vista de edición a través de una opción exclusiva en la tool bar en el fragment de Details.

</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/04a26745-d019-4297-90ff-ea92785e9fee" height="450" width="220" >
</p>
</br>

- Control total de los datos que el usuario introduce en la vista de crear un nuevo ítem (CreationFragment). Los cuadros de texto informan de los errores y ayudan al usuario a la correcta introducción de los datos necesarios. 
- Cuadros de texto obligatorios y otros opcionales.
- El texto de error se elimina mediante TextWatcher cuando el usuario comienza a escribir de nuevo en ellos.
- El ID tiene un formato específico que es indicado al usuario mediante un diálogo en caso de error al introducirlo.

</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/803df2f2-362d-4468-8101-4025ecc8c47a" height="450" width="220" >
</p>
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/a3dce9cb-3b21-468c-95c1-2ad1cb1cf189" height="450" width="220" >
</p>
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/0e5a3157-40d9-4d46-8e92-e87238594bf0" height="450" width="220" >
</p>
</br>

### - Módulo Customer
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/b537b7e4-bc5a-4663-8313-e2f3d5e2615a" height="450" width="220" >
</p>
</br>

Listado de todos los clientes en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de eliminar un elemento con una pulsación larga sobre él.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore.
- Opciones rápidas de ordenación en la ToolBar visible desde este fragment.
- Opción para navegar a la vista de edición a través de un botón en el fragment de Details.
  
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/6d13ead4-6136-4ec3-bc4f-4bc88f887042" height="450" width="220" >
</p>
</br>

- Control total de los datos que el usuario introduce en la vista de crear un nuevo cliente (CreationFragment). Los cuadros de texto informan de los errores y ayudan al usuario a la correcta introducción de los datos necesarios. 
- Cuadros de texto obligatorios y otros opcionales.
- El texto de error se elimina mediante TextWatcher cuando el usuario comienza a escribir de nuevo en ellos.
  
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/a4d79e3d-a06d-4d89-a6e3-eff3bbb1fdff" height="450" width="220" >
</p>
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/43ca8431-58f3-45d9-a8f7-4a431d6fba02" height="450" width="220" >
</p>
</br>

### - Módulo Task
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/7a79842e-ccaa-4ac9-9193-fd1b6d295fa4" height="450" width="220" >
</p>
</br>

Listado de todos las tareas tanto pendientes como completadas en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de eliminar un elemento con una pulsación larga sobre él.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore. Filtrado de tareas en función de si están completas o pendientes y posibilidad de ordenación de la lista por nombre, por contacto o por fecha.
- Opciones rápidas de ordenación sin persistencia en la ToolBar visible desde este fragment.
- Opción para navegar a la vista de edición a través de una opción exclusiva en la tool bar en el fragment de Details.
  
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/22597a2e-e6f9-46d6-84f2-0c3b8c26c83a" height="450" width="220" >
</p>
</br>

- Control total de los datos que el usuario introduce en la vista de crear una nueva tarea (CreationFragment). Los cuadros de texto informan de los errores y ayudan al usuario a la correcta introducción de los datos necesarios.
- Cuadros de texto obligatorios y otros opcionales.
- El texto de error se elimina mediante TextWatcher cuando el usuario comienza a escribir de nuevo en ellos.
- El spinner de selección de contacto para la tarea es rellenado a partir de la tabla de clientes en la base de datos.
- Los cuadros de fecha de inicio y de fin generan un DatePicker para la selección de la fecha.

</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/c46b61e4-5a48-4045-ba31-cf40acd779b2" height="450" width="220" >
</p>
</br>
<p align="center">
<img src="https://github.com/CarlosBocka/Invoice/assets/103117717/258e1e97-788b-4c5c-96e2-8a4214112897" height="450" width="220" >
</p>
</br>

### - Módulo FeatureInvoice

- No hay capturas de pantalla por la inaccesibilidad temporal al repositorio.

Listado de todas las facturas en la base de datos de la aplicación:
- Persistencia completa de datos mediante tabla en la base de datos.
- Posibilidad de navegar a la vista de ver detalles (DetailsFragment) haciendo click sobre un elemento dela lista.
- Posibilidad de crear una factura desde el menú de creación.
- Implementado el MVVM.
- Implementado el modelo de base de datos del segundo trimestre.
- Eliminar no implementado por falta de tiempo.
- Editar no implementado por falta de tiempo.
- Listado responde a las preferencias marcadas por el usuario y dichas preferencias se almacenan con DataStore.