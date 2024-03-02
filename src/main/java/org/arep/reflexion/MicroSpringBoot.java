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

    public static void main(String[] args) throws Exception {
        pojoCargue(args[0]);
        Object o = null;

        if (args.length == 2) o = invocarMetodos(args[1], null, null);
        if (args.length == 3) o = invocarMetodos(args[1], args[2], null);
        if (args.length == 4) o = invocarMetodos(args[1], args[2], args[3]);

        System.out.println("respuesta final: " + o.toString());
    }

    public static void load() throws IllegalArgumentException {
        List<Path> classList = scanRoot();
        String pathAndClass;

            // p es el path de cada clase dentro del directorio src
        for (Path p : classList) {
                // Las siguientes tres lineas eliminan todos los elementos que impiden que Class.forName() encuentre la clase
            pathAndClass = p.toString().replace("\\", ".");
            if (pathAndClass.startsWith("src.main.java.")) pathAndClass = pathAndClass.replace("src.main.java.", "");
            if (pathAndClass.endsWith(".java")) pathAndClass = pathAndClass. replace(".java", "");

                // Verifica las anotaciones @Component y @RquestMapping, si las cumple, las adiciona en componentes (hash)
            pojoCargue(pathAndClass);
        }

        invocarMetodos("/index", null, null);
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

    public static Object invocarMetodos(String pathDelGet, String param, String param2){

        Object resp = null;
        Method m = null;

        for (String path: componentes.keySet()){
            if (pathDelGet.startsWith(path)) {
                m = componentes.get(path);
                break;
            }
        }

        if (m != null){
            try {
                if (m.getParameterCount()==0) resp=  m.invoke(null);
                if (m.getParameterCount()==1) resp = m.invoke(null, param);
                if (m.getParameterCount()==2) resp = m.invoke(null, param, param2);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return resp;
    }

    public static void pojoCargue(String pathAndClass) {
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
}
