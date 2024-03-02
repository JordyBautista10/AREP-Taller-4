# TALLER 3: MICROFRAMEWORKS WEB

Este programa fue creado para simular el microframework Spark a través de funciones lambda, soportando peticiones de tipo GET y POST. También podrá leer archivos alojados en el disco, estos archivos pueden contener las extensiones HTML, JS, CSS o incluso imágenes.


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

La clase contoller tendra como objetivo recibir los respectivos endpoints y parametros que le envie el MicroSpringBoot, ésta obtendra los datos a traves de la clase JordySpark, la cual se encarga de recibir la url y arrancar el servidor. Además, la clase MicroSpringBootde se encargara de invocar los metodos de ControllerSpringBoot.

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/9f5bb003-6b99-4062-a5c5-ad9699f0951e)


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

#### Pruebas

Una vez se ejecuta el proyecto dirijase al browser de su preferencia y coloque la siguiente dirección URL en el navegador, he indique el archivo que desea consultar:

~~~
http://localhost:35000/[archivo que desee observar]
~~~

En este caso colocaremos http://localhost:35000/Cliente/index.html, con el fin de demostrar lo que hace el endpoint /Cliente.

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/dd59eead-14cf-4af9-bb43-8354f93c3f88)

Como podemos observar esta es la encargada de retornar los archivos que se encuentren en disco sin importar la extension que este archivo tenga.

![image](https://github.com/JordyBautista10/AREP-Taller-4/assets/123812969/89a12912-d199-4a9b-9014-1801f578284a)

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

#### ControllerSpringBoot

### Uso del framework para desarrollador

Si se quiere añadir un nuevo endpoint para configurar un comportamiento distinto, se debe acceder al la clase JordySpark y añadir un nuevo else if donde se indique cuál es el nuevo endpoint y el comportamiento se definiría diferente, también se puede usar la variable "method" si lo que se quiere es restringir o cambiar el comportamiento según el tipo de petición que llegue.

![image](https://github.com/JordyBautista10/AREP-Taller-3/assets/123812969/28e6f502-9409-41c8-9679-af90f8ff18a4)


  
## Versioning

Para el versionamiento se usó [Git](https://git-scm.com). Si necesita volver en alguna versión del código, visite los commits.

## Autor

* **Jordy Santiago Bautista Sepulveda** 
