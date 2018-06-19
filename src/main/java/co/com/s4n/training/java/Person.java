package co.com.s4n.training.java;

import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.stream.Stream;

import static io.vavr.API.None;

public class Person {

    public static Option<String> person(String name, int age){
        return Option.of(name + " " + String.valueOf(age));
    }

    public static Option<String> format(String persona){
        return Option.of(persona.toUpperCase());
    }

    public static Option<Integer> tamaño(String persona){
        String[] name = persona.split("\\ ");
        return Option.of(name[0].length());
    }

    public static Option<String> career(String persona, String career){
        String[] res = persona.split("\\ ");
        return Integer.parseInt(res[1])<25 ? Option.of("S") : None();
    }

    public static Option<String> state(String estado){
        return estado=="Terminado" ? Option.of("Felicitaciones") : None();
    }

    public static Option<List<String>> filtro(List<String> personas){
        List<String> p = personas.map(i -> i + " " + filter(i));
        return Option.of(p);
    }

    public static Option<List<String>> filtro2(List<String> personas){
        List<String> p = personas.map(i -> {
            String[] res = i.split("\\ ");
            if (res[1] == "esPar"){
                return res[0];
            }else{
                return null;
            }
        });
        return Option.of(p);
    }

    public static Option<List<String>> filtro3(List<String> personas){
        List<String> p = personas.pop();
        return Option.of(p);
    }

    public static String filter(String name){
        int size = name.length();
        if (size%2==0){
            return "esPar";
        }else{
            return "esImpar";
        }
    }
}
