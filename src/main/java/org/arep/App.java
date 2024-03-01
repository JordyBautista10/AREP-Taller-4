package org.arep;

import org.arep.reflexion.MicroSpringBoot;

import static org.arep.JordySpark.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException {
        MicroSpringBoot.load();

        get("/hola", (req) -> req);

        // start the server
        if (!JordySpark.running) JordySpark.getInstance().start(args);
    }
}
