# Sistema de Gestión de Rutinas de Entrenamiento

## Descripción del proyecto

Este proyecto corresponde a la Tarea 2 de la asignatura Paradigmas de Programación. Consiste en un sistema de gestión de rutinas de entrenamiento personalizadas desarrollado en Java, utilizando Swing para la interfaz gráfica y SQLite para la persistencia de datos.

La aplicación permite cargar ejercicios desde una base de datos SQLite, generar una rutina de entrenamiento según parámetros ingresados por el usuario, revisar los ejercicios de la rutina uno por uno y visualizar un resumen final con cantidad de ejercicios por tipo, cantidad por intensidad y tiempo total estimado.

## Tecnologías utilizadas

* Java
* Java Swing
* SQLite
* JDBC SQLite
* IntelliJ IDEA

## Tipo de proyecto

El proyecto fue creado como un proyecto Java normal en IntelliJ IDEA.
No utiliza Maven ni Gradle.

Por esta razón, la librería de SQLite se incluye manualmente dentro de la carpeta `lib`.

## Estructura del proyecto

Rutinas

* src

    * backend

        * Ejercicio.java
        * GestorEjerciciosBD.java
        * RutinaService.java
    * frontend

        * VentanaPrincipal.java
    * Main.java
    * PruebaSQLite.java
* data

    * ejercicios.csv
    * ejercicios.db
* lib

    * sqlite-jdbc-3.53.1.0.jar
* README.md

## Descripción de carpetas

### src/backend

Contiene la lógica principal del sistema.

* `Ejercicio.java`: clase modelo que representa un ejercicio. Contiene código, nombre, tipo, intensidad, tiempo estimado, descripción y última semana de uso.
* `GestorEjerciciosBD.java`: clase encargada de la conexión con SQLite. Permite crear la tabla, eliminar datos, insertar ejercicios y cargar ejercicios desde la base de datos.
* `RutinaService.java`: clase central del backend. Administra los ejercicios cargados, guarda información en SQLite, genera rutinas, mantiene la rutina generada en memoria y calcula los resúmenes.

### src/frontend

Contiene la interfaz gráfica del sistema.

* `VentanaPrincipal.java`: ventana desarrollada con Swing. Permite cargar ejercicios desde SQLite, generar una rutina, revisar ejercicio por ejercicio y mostrar el resumen final.

### src

Contiene también las clases de entrada y prueba.

* `Main.java`: punto de entrada principal del programa. Crea el backend y abre la interfaz gráfica.
* `PruebaSQLite.java`: clase utilizada para probar la conexión con SQLite durante el desarrollo.

### data

Contiene los archivos de datos del sistema.

* `ejercicios.csv`: archivo base con los ejercicios utilizados como respaldo o fuente inicial de datos.
* `ejercicios.db`: base de datos SQLite utilizada por el programa.

### lib

Contiene la librería externa necesaria para conectar Java con SQLite.

* `sqlite-jdbc-3.53.1.0.jar`

## Instrucciones de ejecución

1. Abrir el proyecto en IntelliJ IDEA.
2. Verificar que exista la carpeta `lib`.
3. Verificar que dentro de `lib` esté el archivo `sqlite-jdbc-3.53.1.0.jar`.
4. Hacer clic derecho sobre `sqlite-jdbc-3.53.1.0.jar`.
5. Seleccionar `Add as Library`.
6. Verificar que la librería aparezca en `External Libraries`.
7. Verificar que exista la carpeta `data`.
8. Verificar que dentro de `data` estén los archivos `ejercicios.csv` y `ejercicios.db`.
9. Ejecutar la clase `Main.java`.
10. En la ventana principal, presionar `Cargar ejercicios desde SQLite`.
11. Luego presionar `Generar rutina`.
12. Ingresar:

    * cantidad de ejercicios cardiovasculares,
    * cantidad de ejercicios de fuerza,
    * intensidad para ejercicios cardiovasculares,
    * intensidad para ejercicios de fuerza,
    * semana actual.
13. Revisar la rutina usando los botones `Anterior` y `Siguiente`.
14. Al llegar al último ejercicio, el botón cambia a `Resumen de la rutina`.
15. Presionar `Resumen de la rutina` para visualizar el resumen final.

## Flujo de funcionamiento

El flujo principal del sistema es el siguiente:

1. El usuario ejecuta `Main.java`.
2. Se abre `VentanaPrincipal`.
3. El usuario carga los ejercicios desde SQLite.
4. El sistema muestra estadísticas de carga:

    * cantidad total de ejercicios,
    * tiempo total disponible,
    * cantidad por tipo,
    * cantidad por intensidad.
5. El usuario genera una rutina ingresando los parámetros solicitados.
6. El sistema selecciona ejercicios disponibles según tipo, intensidad y semana actual.
7. El usuario revisa la rutina ejercicio por ejercicio.
8. El sistema muestra el resumen final de la rutina.

## Formato del archivo CSV

El archivo `ejercicios.csv` utiliza punto y coma como separador.

Formato:

codigo;nombre;tipo;intensidad;tiempo;descripcion;ultimaSemanaUsado

Ejemplo:

1;Trote suave;Cardiovascular;Básico;15;Realizar trote continuo a ritmo cómodo manteniendo respiración controlada;0

## Base de datos SQLite

La base de datos se encuentra en:

data/ejercicios.db

La tabla principal utilizada por el sistema es `ejercicios`.

Campos considerados:

* codigo
* nombre
* tipo
* intensidad
* tiempo
* descripcion
* ultima_semana_usado

## Supuestos del sistema

* El valor `0` en `ultimaSemanaUsado` indica que el ejercicio no ha sido usado anteriormente.
* Los tipos de ejercicios considerados son `Cardiovascular` y `Fuerza`.
* Los niveles de intensidad utilizados son `Básico`, `Intermedio`, `Avanzado` y `Alto rendimiento`.
* La aplicación carga los ejercicios desde SQLite durante la ejecución.
* El archivo CSV se mantiene como respaldo y fuente inicial de datos.
* La rutina generada se mantiene en memoria durante la ejecución del programa.
* Las funcionalidades de carga, generación, revisión y resumen se integran en una única ventana principal para simplificar el uso del sistema.

## Consideración sobre la interfaz

Aunque el enunciado considera distintas ventanas para cada etapa del sistema, esta implementación concentra el flujo completo en `VentanaPrincipal`. La ventana principal permite realizar la carga, generación, revisión y resumen sin cambiar de ventana, manteniendo de todas formas la separación entre lógica e interfaz mediante los paquetes `backend` y `frontend`.

## Manejo de errores

El sistema utiliza bloques `try/catch` para manejar errores durante la ejecución.

Se consideran, entre otros:

* errores de conexión con SQLite,
* problemas al cargar ejercicios,
* errores de conversión numérica,
* falta de ejercicios disponibles para generar una rutina,
* datos ingresados incorrectamente por el usuario.

Los errores se informan mediante mensajes en la interfaz gráfica usando `JOptionPane`.

## Advertencia sobre mensajes de SQLite

Al ejecutar el programa, pueden aparecer advertencias relacionadas con el driver SQLite y Java. Estas advertencias no impiden el funcionamiento del sistema mientras la aplicación cargue correctamente los ejercicios, genere rutinas y finalice sin errores de ejecución.

## Archivos importantes para GitHub

Para que el proyecto funcione correctamente al ser descargado desde GitHub, deben incluirse las siguientes carpetas y archivos:

* `src`
* `data`
* `lib`
* `README.md`
* `data/ejercicios.csv`
* `data/ejercicios.db`
* `lib/sqlite-jdbc-3.53.1.0.jar`

No se debe ignorar la carpeta `data` ni la carpeta `lib`, ya que contienen archivos necesarios para ejecutar el proyecto.

## Archivos que pueden ignorarse

Se recomienda ignorar archivos generados automáticamente, como:

* `out/`
* `.idea/`
* `*.class`
* `*.log`

## Autor

Mary González
Ingeniería en Computación e Informática
Universidad Andrés Bello
Asignatura: Paradigmas de Programación
