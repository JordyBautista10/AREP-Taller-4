# TALLER 4: ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN

Este programa fue creado para simular el framework Spark junto con el de Springboot, ya que para esta entrega, el código es capaz de escanear las clases del proyecto y determinar cuáles son las que cuentan con anotaciones, tal y como funciona Springboot, además se contara con funciones de reflexión para ejecutar clases desde comandos y desde la ejecución del propio proyecto.

### Prerequisitos

Para que el código corra de forma satisfactoria y se puedan seguir todos los pasos se necesitara de: Java, Maven y Git; sin embargo,  para la descarga e instalación de estos elementos, adjunto los link de material de apoyo de otros autores

* [Tutorial instalación Java] (https://youtu.be/4WKo13f2Qpc?si=lHG84Jp_k7YbBFmp)
* [Tutorial instalación Git] (https://youtu.be/jpTrSSjPlEo?si=VdcaXSaNEFkR3hCk)
* [Tutorial instalación Maven] (https://youtu.be/biBOXvSNaXg?si=wfySIfBTUERGEVZC)

## Construido con:

* [Java](http://www.dropwizard.io/1.0.2/docs/) - Lenguaje con el cual funciona la mayor parte del proyecto
* [Html](https://developer.mozilla.org/es/docs/Web/HTML) - Usado para la sección del cliente
* [JavaScript](https://developer.mozilla.org/es/docs/Web/JavaScript) - Este lenguaje le permite al cliente realizar las peticiones necesarias
* [Maven](https://maven.apache.org/) - Usado para la construcción de la estructura del proyecto
* [Git](https://git-scm.com) - Usado para el versionamiento

## Diseño

La clase contoller tendra como objetivo definir el comportamiento de los respectivos endpoints, si como de los parametros que le envie el MicroSpringBoot: 

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/9f5bb003-6b99-4062-a5c5-ad9699f0951e)

ControllerSpringBoot obtendrá los datos a través de la clase MicroSpringBoot, ya que esta clase contiene un hash con los métodos @ResuestMapping dentro de una clase con la anotación @Component. Para invocar los métodos esta clase analiza el path que le suministran, para luego buscar el método que tenga como llave el path mencionado:

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/bf4176f6-557f-42cc-ada6-1518175f19cb)

Ahora JordySpark es la clase que inicia el servidor, recibe peticiones y hace la devolución según el recurso solicitado, por lo cual en esta clase hace uso del método anterior pasándole la URL para que el método pueda comparar la URL con las llaves que tiene guardadas en su hash:

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/724ec4a8-7874-4caf-8fbb-6d081a195250)


## Para Comenzar

#### Repositorio

En primera instancia, debemos obtener el código del proyecto, por lo que se ejecutara el comando desde consola. (tenga en cuenta que debe estar en la carpeta deseada antes de clonar el repositorio)

~~~
https://github.com/JordyBautista10/AREP-Taller-4.git
~~~

Posteriormente, descargamos las dependencias necesarias y compilamos el código

~~~
mvn clean install compile
~~~

#### Ejecución

Para correr este codigo usando un ID, hay que ingresar a la carpeta que se muestra a continuación y ejecutar el archivo llamado App.java

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/f141bd98-5811-4300-bbcc-099b3254e919)

Tambien se puede ejecutar desde consola de la siguiente forma:

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/c35000b4-1764-4eef-99ec-070f39f351d3)

Como se puede ver, el servicio de MicroSpringBoot carga todas las clases con las anotaciones ya mencionadas

#### Pruebas

Una vez se ejecuta el proyecto dirijase al browser de su preferencia y coloque la siguiente dirección URL en el navegador, he indique el archivo que desea consultar:

~~~
http://localhost:35000/Cliente/[archivo que desee observar]
~~~

En este caso colocaremos http://localhost:35000/Cliente/index.html, con el fin de demostrar lo que hace el endpoint /Cliente.

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/dd59eead-14cf-4af9-bb43-8354f93c3f88)

Como podemos observar esta es la encargada de retornar los archivos que se encuentren en disco sin importar la extension que este archivo tenga.

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/89a12912-d199-4a9b-9014-1801f578284a)

Para ver una imagen almacenada en la carpeta (resources/public) se usará la siguiente direccion:

~~~
http://localhost:35000/Cliente/nitro.jpg
~~~

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/61246d71-2b80-4288-8758-af7fe471d50d)

Ademas de las pruebas realizadas en el browser se puede ejecutar pruebas en la linea de comando de la siguiente manera, por cierto, cabe aclarar que dependiendo el endopoint que se quiera probar en la linea de comandos cambia los parametros que se le envian y el endopint, aca podremos observar diferentes pruebas:

~~~
java -cp target/classes org.arep.reflexion.MicroSpringBoot org.arep.Spring.ControllerSpringBoot /Accion /action/index.html GET  
~~~

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/d7382968-06c4-49d5-a5eb-20f7597ba86e)

~~~
java -cp target/classes org.arep.reflexion.MicroSpringBoot org.arep.Spring.ControllerSpringBoot /Busqueda /Busqueda/Thor
~~~

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/647def18-3659-47ea-8a32-9b6e7c1c6934)

### Arquitectura
#### App
Es el método el cual actúa como clase main del programa y configura el path que recibirá el método get y como retornará la información retornada por este mismo.

#### JordySpark
Esta clase funciona como la simulación de Spark, pues tiene los métodos necesarios, para abrir la conexión, recibir las peticiones, tratarlas, ponerles sus respectivos encabezados y enviar la información al cliente. Esta clase define el metodo get que usa la clase App ya mencionada.

#### MicroSpringBoot
Esta clase se encarga de cargar todas las clases que contengan anotaciones @Component y @ResuestMapping, a los metodos guarda en un hash. Tambien tiene la capacidad de invocar metodos a travez de su hash de metodos.

#### ControllerSpringBoot
Esta clase define los endpoints que ve van a manejar además de también definir el comportamiento de estos endpoints usando metodos de otras clases o también si el desarrollador lo desea, se puede realizar todo el comportamiento dentro del método, cabe aclarar que los endpoints deben ser diferentes entre sí para evitar conflictos

### Uso del framework para desarrollador
Si se quiere añadir un nuevo endpoint para configurar un comportamiento distinto, se debe acceder al la clase ControllerSpringBoot y añadir un nuevo metodo donde se indique cuál es el nuevo endpoint a traves de la variable dentro de la anotacion @ResuestMapping. Configure el comportamiento del metodo, la respuesta debe estar en formato byte[], pues es de la forma que JordySpark puede interpretar la informacion recibida para imprimirla

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/ab248e19-ac83-4d4c-8e95-7b18c605e429)

Si quiere añadir otras anotaciones para definir nuevos comportamientos, añadalas de la siguiente forma:

Si desea que la anotacion anterior sea cargada por MicroSpringBoot, entonces modifique el metodo pojoCargue(). En la siguiente imagen se muestra de color amarillo, si desea añadir una nueva anotacion de clase, entonces añada "|| c.isAnnotationPresent( [nuevaAnotacion].class)", por otro lado si desea añadir una nueva anotacion de metodo, agreguela en la anotacion de color rojo de la misma forma "|| c.isAnnotationPresent( [nuevaAnotacion].class)":

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/9110b5cf-63d6-4a81-935f-5dad83e34ac6)


## Versioning

Para el versionamiento se usó [Git](https://git-scm.com). Si necesita volver en alguna versión del código, visite los commits.

## Autor

* **Jordy Santiago Bautista Sepulveda** 
