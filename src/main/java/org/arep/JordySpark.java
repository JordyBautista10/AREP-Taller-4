package org.arep;

import org.arep.reflexion.MicroSpringBoot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;

import static org.arep.conection.HttpConection.makeRequest;

/**
 * @author Jordy Bautista
 */
public class JordySpark {

    private static final HashMap<String, String> cache = new HashMap<>();   // Key: Movie name and Value: Info about the movie
    private static String serviceURI = "";
    private static Function service = null;
    public static boolean running = false;
    private static JordySpark instance = null;

    private JordySpark() {}

    public static JordySpark getInstance() {
        if (instance == null) {
            instance = new JordySpark();
        }
        return instance;
    }

    /**
     * Constructor
     * @param args por defecto
     * @throws IOException Esta clase es la clase general de excepciones producidas por operaciones de E/S fallidas o interrumpidas.
     */
    public void start(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = null;

            boolean firstLine = true;
            String uriStr ="";
            String method = "";

            while ((inputLine = in.readLine()) != null) {
                if(firstLine){
                    method = inputLine.split(" ")[0];
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            try (OutputStream os = clientSocket.getOutputStream()) {
                if (uriStr.startsWith("/Busqueda") && uriStr.length() > 12){  // Se asegura de que la uri no tenga busqueda vacia
                    outputLine = cacheSearch(uriStr);
                } else {
                    os.write((byte[]) MicroSpringBoot.invocarMetodos(uriStr,  uriStr, method));
                }
                if (outputLine != null) os.write(outputLine.getBytes());
            } catch (Exception e) {
                System.out.println("Error sending response body");
                outputLine = httpError();
                System.err.println(e.getMessage());
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public static byte[] callService(URI requestUri, String method) throws IOException, URISyntaxException {
        // obtenemos el elemento solicitado quitando el inicio /action
        URI calledServiceUri = new URI(requestUri.getPath().substring(7));
        String output = "";
        // TODO: make multiple services in MAP, search in the map for the service

        if (method.equals("POST")){
            output += "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n";
            output += service.handle(cache.keySet().toString());
            return output.getBytes();
        } else  {           // si el metodo es (method.equals("GET"))
            return httpClientHtml(calledServiceUri);
        }
    }

    public static void get(String path, Function service) {
        JordySpark.serviceURI = path;
        JordySpark.service = service;
    }

    /**
     * Este metodo retorna la informacion de la pelicula que se consulte al servidor, para devolver esta informacion
     * busca en el cache y en caso de no tener registro de la pelicula, realiza la busqueda en el API publico y almacena
     * la informacion relacionada con esa pelicula
     * @param uriStr: url de la peticion que recibe el servidor
     * @return Informacion de la pelucula
     * @throws IOException Esta clase es la clase general de excepciones producidas por operaciones de E/S fallidas o interrumpidas.
     */
    public static String cacheSearch(String uriStr) throws IOException {
        String nameMovie = uriStr.substring(12).toLowerCase();          // de la Uri Obtiene el nombre de la pelicula
        if (!cache.containsKey(nameMovie)){
            System.out.println("No se encontro en el cache");
            uriStr =  makeRequest("http://www.omdbapi.com/?apikey=b1060e61&t=" + nameMovie);
            cache.put(nameMovie, uriStr);
            return uriStr;
        } else {
            System.out.println("Se encontro en el cache");
            return cache.get(nameMovie);
        }
    }


    /**
     * Este metodo retorna una pagian vacia de HTML en caso de no consultar la URL apropiada
     * @return String de una pagina web vacia
     */
    private static String httpError() {
        return  "HTTP/1.1 400 Not Found\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Error Not found</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Error</h1>\n"
                + "    </body>\n";

    }

    /**
     * Este metodo retorna una pagina de HTML con la cual se pueden realizar busquedas de peliculas y la informacion
     * @return String de una pagina web de busqueda
     */
    public static byte[] httpClientHtml(URI requestedUri) throws IOException {

        File fileSrc = new File(requestedUri.getPath());
        String fileType = Files.probeContentType(fileSrc.toPath());
        System.out.println("filetype---------------------------- " + fileType + " ---- " + requestedUri.getPath());

        Path file = Paths.get("target/classes/public" + requestedUri.getPath());
        String outputLine =  "HTTP/1.1 200 OK\r\n"
                + "Content-Type:" + fileType + "\r\n"
                + "\r\n"; // Necesario para los nuevos navegadores

        if (fileType.startsWith("image")) {
            BufferedImage image = ImageIO.read(file.toFile());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, fileType.split("/")[1],baos);

            ArrayList<Byte> arrayList = new ArrayList<>();
            for (byte i: outputLine.getBytes()) {arrayList.add(i);}
            for (byte i: baos.toByteArray()) {arrayList.add(i);}
            byte[] salida = new byte[arrayList.size()];
            for (int i = 0; i < salida.length; i++){salida[i]=arrayList.get(i);}
            return salida;
        } else {
            Charset charset = StandardCharsets.UTF_8;
            BufferedReader reader = Files.newBufferedReader(file, charset);
            String line;
            while ((line = reader.readLine()) != null){
                System.out.print(line);
                outputLine = outputLine + line;
            }
            return outputLine.getBytes();
        }
    }

}