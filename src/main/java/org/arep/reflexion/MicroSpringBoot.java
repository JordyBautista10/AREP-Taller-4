package org.arep.reflexion;

import org.arep.reflexion.annotations.Component;
import org.arep.reflexion.annotations.ResuestMapping;

@Component
public class MicroSpringBoot {

    @ResuestMapping(path = "/index")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
