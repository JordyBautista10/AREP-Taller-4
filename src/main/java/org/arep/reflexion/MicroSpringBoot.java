package org.arep.reflexion;

import org.arep.Spring.annotations.Component;
import org.arep.Spring.annotations.ResuestMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MicroSpringBoot {

    private static final Map<String, Method> componentes = new HashMap<String, Method>();

    public static void load() throws IllegalArgumentException {
        List<Path> classList = scanRoot();
        String pathAndClass;

        for (Path p : classList) {
                // Las siguientes tres lineas eliminan todos los elementos que impiden que Class.forName() encuentre la clase
            pathAndClass = p.toString().replace("\\", ".");
            if (pathAndClass.startsWith("src.main.java.")) pathAndClass = pathAndClass.replace("src.main.java.", "");
            if (pathAndClass.endsWith(".java")) pathAndClass = pathAndClass. replace(".java", "");

            try {
                Class<?> c = Class.forName(pathAndClass);
                System.out.println("clase: " + c.getName());
                if (c.isAnnotationPresent(Component.class)) {
                    //Almacenar todos los metodos, en una estructuca llave valor, la llave será el path del web service y el valor son metodos
                    //TODOS LOS METODOS DEBEN SER ESTATICOS
                    Method[] metodos = c.getMethods();

                    for (Method m : metodos) {
                        if (m.isAnnotationPresent(ResuestMapping.class)) {
                            System.out.println("    metodos: " + m.getName());
                            componentes.put(m.getAnnotation(ResuestMapping.class).path(), m);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Exception" + pathAndClass + ": no es clase java" );
            }
        }

        invocarMetodos("/index");
    }

    public static List<Path> scanRoot() {
        Path directoryPath = Paths.get("src");
        List<Path> fileList = null;

        try {
            fileList = Files.walk(directoryPath)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error occurred while scanning the directory.");
            e.printStackTrace();
        }
        return fileList;
    }

    public static void invocarMetodos(String pathDelGet){
        Method m = componentes.get(pathDelGet);

        if (m != null){
            try {
                System.out.println("Salida: " + m.invoke(null));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        //Si llega una ruta enlazada a un componente, se debera ejecutar el compónente, no olvide los encabezados
        //Implemente pasar parametros
    }
}
