package org.arep.Spring;

import org.arep.Spring.annotations.Component;
import org.arep.Spring.annotations.ResuestMapping;

@Component
public class ControllerSpringBoot {

    @ResuestMapping(path = "/index")
    public static String index() {
        return "Greetings from Spring Boot!";
    }
}
