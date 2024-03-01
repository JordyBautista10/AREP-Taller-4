package org.arep;

import static org.arep.JordySpark.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException {
        get("/hola", (req) -> {
            return req;
        });

        // start the server
        if (!JordySpark.running) JordySpark.getInstance().start(args);
    }
}
