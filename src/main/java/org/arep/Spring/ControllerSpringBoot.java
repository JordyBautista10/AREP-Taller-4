package org.arep.Spring;

import org.arep.JordySpark;
import org.arep.Spring.annotations.Component;
import org.arep.Spring.annotations.ResuestMapping;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ControllerSpringBoot {

    @ResuestMapping(path = "/index")
    public static String index() {
        return "Greetings from Spring Boot!";
    }

    @ResuestMapping(path = "/Busqueda")
    public static String search(String path) throws IOException {
        return JordySpark.cacheSearch(path);
    }

    @ResuestMapping(path = "/Accion")
    public static byte[] action(String path, String method) throws IOException, URISyntaxException {
        return JordySpark.callService(URI.create(path), method);
    }

    @ResuestMapping(path = "/Cliente")
    public static byte[] cliente(String path) throws IOException, URISyntaxException {
        path = path.substring(8);
        System.out.println("Cliente " + path);
        return JordySpark.httpClientHtml(URI.create(path));
    }
}
