package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;

/**
 * @author Jordy Bautista
 */
public class JordySpark {

    private static HashMap<String, String> cache = new HashMap<String, String>();   // Key: Movie name and Value: Info about the movie
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
    public void start(String[] args) throws IOException, URISyntaxException {
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
            String inputLine, outputLine;

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

            try {
                if (uriStr.startsWith("/Busqueda") && uriStr.length() > 12){  // Se asegura de que la uri no tenga busqueda vacia
                    outputLine = cacheSearch(uriStr);
                } else if (uriStr.startsWith("/action") && uriStr.length() > 7){
                    System.out.println("----------------------------------------------------" + method);
                    outputLine = callService(new URI(uriStr), method, clientSocket.getOutputStream());
                } else {
                    outputLine = httpClientHtml(new URI(uriStr), clientSocket.getOutputStream());
                }
            } catch (Exception e) {
                outputLine = httpError();
            }

            try (OutputStream os = clientSocket.getOutputStream()) {
                os.write(outputLine.getBytes());
            } catch (IOException e) {
                System.out.println("Error sending response body");
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private String callService(URI requestUri, String method, OutputStream out) throws IOException, URISyntaxException {
        // TODO: search how to implement uri with relative path
        String calledServiceUri = requestUri.getPath().substring(7);
        String output = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n";
        // TODO: make multiple services in MAP, search in the map for the service
        if (method.equals("GET")) {
            output += httpClientHtml(new URI(requestUri.toString().substring(7)), out);
        } else if (method.equals("POST")){
            output += service.handle(cache.keySet().toString());
        }
        return output;
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
     * Este metodo realiza una petición get a la URL que se le proporcione y retorna la respuesta
     * @param url: Es la URL de la pagina a la cual se le desea realizar la petición
     * @return Es la respuesta de la peticion realizada en formato de String
     * @throws IOException Esta clase es la clase general de excepciones producidas por operaciones de E/S
     * fallidas o interrumpidas.
     */
    public static String makeRequest(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer();
        // Encabezado necesario en todas las peticiones
        response.append("HTTP/1.1 200 OK\r\n"
                + "Content-Type:application/json\r\n"
                + "Access-Control-Allow-Origin: *\r\n"
                + "\r\n");
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");

        return response.toString();
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
    public static String httpClientHtml(URI requestedUri, OutputStream outStm) throws IOException {

        File fileSrc = new File(requestedUri.getPath());
        String fileType = Files.probeContentType(fileSrc.toPath());
        System.out.println("filetype----------------------------" + fileType);

        Path file = Paths.get("target/classes/public" + requestedUri.getPath());
        String outputLine =  "HTTP/1.1 200 OK\r\n"
                + "Content-Type:" + fileType + "\r\n"
                + "\r\n"; // Necesario para los nuevos navegadores

        if (fileType.startsWith("image")) {
            BufferedImage image = ImageIO.read(file.toFile());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, fileType.split("/")[1],baos);
            byte[] imageBytes = baos.toByteArray();
            outStm.write(outputLine.getBytes());
            outStm.write(imageBytes);
        } else {
            Charset charset = StandardCharsets.UTF_8;
            BufferedReader reader = Files.newBufferedReader(file, charset);
            String line = null;
            while ((line = reader.readLine()) != null){
                System.out.print(line);
                outputLine = outputLine + line;
            }
        }

        return outputLine;
    }

}