import org.arep.conection.HttpConection;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
public class HttpConectionTest {

    String movie = "http://www.omdbapi.com/?apikey=b1060e61&t=" ;

    @Test
    public void testHttpRequest_MovieExist() throws IOException {
        // Se buscará la pelicula de avatar
        String searchMovie = "Avatar";

        // Se llama el servicio de la peticion para que busque esa pelicula
        String resp = HttpConection.makeRequest(movie+searchMovie);

        // Se convierte la peticion a JSON, con el substring se elimina el encabezado de la peticion
        JSONObject convert  = new JSONObject(resp.substring(82));

        // EL titulo de respuesta deberia ser la pelicula buscada
        assertEquals(convert.getString("Title"), searchMovie);
    }

    @Test
    public void testHttpRequest_MovieNoExist() throws IOException {
        // Se buscará la pelicula de avatar
        String searchMovie = "--------------------------";

        // Se llama el servicio de la peticion para que busque esa pelicula
        String resp = HttpConection.makeRequest(movie+searchMovie);

        // Se convierte la peticion a JSON, con el substring se elimina el encabezado de la peticion
        JSONObject convert  = new JSONObject(resp.substring(82));

        // EL titulo de respuesta deberia ser la pelicula buscada
        assertEquals(convert.getString("Error"), "Movie not found!");
    }
}
