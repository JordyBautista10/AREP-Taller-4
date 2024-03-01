package org.arep.conection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpConection {

    /**
     * Este metodo realiza una petición get a la URL que se le proporcione y retorna la respuesta
     * @param url: Es la URL de la pagina a la cual se le desea realizar la petición
     * @return Es la respuesta de la peticion realizada en formato de String
     * @throws IOException Esta clase es la clase general de excepciones producidas por operaciones de E/S
     * fallidas o interrumpidas.
     */
    public static String makeRequest(String url) throws IOException {

        URI uri = URI.create(url);
        URL obj = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        // Encabezado necesario en todas las peticiones
        response.append("""
                HTTP/1.1 200 OK\r
                Content-Type:application/json\r
                Access-Control-Allow-Origin: *\r
                \r
                """);
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
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");

        return response.toString();
    }
}
