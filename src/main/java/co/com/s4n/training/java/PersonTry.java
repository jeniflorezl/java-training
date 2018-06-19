package co.com.s4n.training.java;

import io.vavr.control.Try;

import static io.vavr.API.None;

public class PersonTry {

    public static Try<String> person(String name, int age){
        return Try.of(() -> name + " " + String.valueOf(age));
    }

    public static Try<String> format(String persona){
        return Try.of(() -> persona.toUpperCase());
    }

    public static Try<Integer> tamaño(String persona){
        String[] name = persona.split("\\ ");
        return Try.of(() ->  name[0].length());
    }

    public static Try<String> career(String persona, String career){
        String[] res = persona.split("\\ ");
        return Integer.parseInt(res[1])<25 ? Try.of(()->"S") : Try.failure(new Exception(""));
    }

    public static Try<String> state(String estado){
        return estado=="Terminado" ? Try.of(()->"Felicitaciones") : Try.failure(new Exception(""));
    }
}
