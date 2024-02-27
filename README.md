# TALLER 3: MICROFRAMEWORKS WEB

Este programa fue creado para simular el microframework Spark a través de funciones lambda, soportando peticiones de tipo GET y POST. También podrá leer archivos alojados en el disco, estos archivos pueden contener las extensiones HTML, JS, CSS o incluso imágenes.

## Para Comenzar

#### Repositorio

En primera instancia, debemos obtener el código del proyecto, por lo que se ejecutara el comando desde consola. (tenga en cuenta que debe estar en la carpeta deseada antes de clonar el repositorio)

~~~
https://github.com/JordyBautista10/AREP-Taller-3.git
~~~

Posteriormente, descargamos las dependencias necesarias y compilamos el código

~~~
mvn clean install compile
~~~

#### Ejecución

Para correr este codigo usando un ID, hay que ingresar a la carpeta que se muestra a continuación y ejecutar el archivo llamado App.java

![image](https://github.com/JordyBautista10/AREP-Taller-3/assets/123812969/8274fdab-d50b-4c15-aae4-7221907618f6)


#### Funcionamiento
Este proyecto manejará dos formas de visualizar el contenido del servidor, la primera es accediendo a los archivos y la segunda es mediante la petición GET creada con el lambda

Primero, la versión sin lambda, la cual consiste en simplemente colocar el nombre del archivo dentro de la carpeta /target/classes/public, como ejemplo, podemos buscar los elementos index.html o nitro.jpg

~~~
http://localhost:35000/[archivo que desee observar]
~~~

Ahora bien, para la petición GET, debemos poner la siguiente dirección URL en el navegador, he indicar el archivo que desea consultar (solamente traerá el archivo en mención sin tener en cuenta sus complementos)

~~~
http://localhost:35000/action/[archivo que desee observar]
~~~

La peticion de Index.html deberia verse asi:

![image](https://github.com/JordyBautista10/AREP-Taller-3/assets/123812969/0ead8365-ff22-48f4-a019-302df7b57cd3)

Ahora, para las peticiones POST se debe realizar el mismo tipo de petición a la siguiente URL. Cabe aclarar que la información que este método retorna es el caché del API fachada, por lo que a medida que se vayan buscando películas, el resultado de esta petición será más grande

~~~
http://localhost:35000/action
~~~

Por ejemplo, si no se han buscado peliculas, la peticion POST entregara:

![image](https://github.com/JordyBautista10/AREP-Taller-3/assets/123812969/f2dbef2e-15f9-4f6f-86fd-f8eb2aba9850)

Sin embargo luego de hacer varias consultas el resultado será:

![image](https://github.com/JordyBautista10/AREP-Taller-3/assets/123812969/60519529-184a-4cd9-8766-308e14bf3afa)

Estos resultados tambien se pueden evidenciar, pulsando el segundo boton que aparece en la pagina principal "index.html"

### Arquitectura
#### App
Es el método el cual actúa como clase main del programa y configura el path que recibirá el método get y como retornará la información retornada por este mismo.

#### JordySpark
Esta clase funciona como la simulación de Spark, pues tiene los métodos necesarios, para abrir la conexión, recibir las peticiones, tratarlas, ponerles sus respectivos encabezados y enviar la información al cliente. Esta clase define el metodo get que usa la clase App ya mencionada.

### Uso del framework para desarrollador
Si se quiere añadir un nuevo endpoint para configurar un comportamiento distinto, se debe acceder al la clase JordySpark y añadir un nuevo else if donde se indique cuál es el nuevo endpoint y el comportamiento se definiría diferente, también se puede usar la variable "method" si lo que se quiere es restringir o cambiar el comportamiento según el tipo de petición que llegue.

![image](https://github.com/JordyBautista10/AREP-Taller-3/assets/123812969/28e6f502-9409-41c8-9679-af90f8ff18a4)


### Prerequisitos

Para que el código corra de forma satisfactoria y se puedan seguir todos los pasos se necesitara de: Java, Maven y Git; sin embargo,  para la descarga e instalación de estos elementos, adjunto los link de material de apoyo de otros autores

* [Tutorial instalación Java] (https://youtu.be/4WKo13f2Qpc?si=lHG84Jp_k7YbBFmp)
* [Tutorial instalación Git] (https://youtu.be/jpTrSSjPlEo?si=VdcaXSaNEFkR3hCk)
* [Tutorial instalación Maven] (https://youtu.be/biBOXvSNaXg?si=wfySIfBTUERGEVZC)

## Built With

* [Java](http://www.dropwizard.io/1.0.2/docs/) - Lenguaje con el cual funciona la mayor parte del proyecto
* [Html](https://developer.mozilla.org/es/docs/Web/HTML) - Usado para la sección del cliente
* [JavaScript](https://developer.mozilla.org/es/docs/Web/JavaScript) - Este lenguaje le permite al cliente realizar las peticiones necesarias
* [Maven](https://maven.apache.org/) - Usado para la construcción de la estructura del proyecto
* [Git](https://git-scm.com) - Usado para el versionamiento
  
## Versioning

Para el versionamiento se usó [Git](https://git-scm.com). Si necesita volver en alguna versión del código, visite los commits.

## Autor

* **Jordy Santiago Bautista Sepulveda** 
