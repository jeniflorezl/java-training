package co.com.s4n.training.java.vavr;

import co.com.s4n.training.java.PersonTry;
import io.vavr.control.Try;
import org.junit.Test;

import static io.vavr.API.For;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;


public class TestTry {

    @Test
    public void testSuccesWithFlatMap(){
        Try<Integer> resultado =
                PersonTry.person("Jeniffer", 22)
                        .flatMap(s -> PersonTry.format(s)
                                .flatMap(r -> PersonTry.tamaño(r)
                                ));

        assertEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                PersonTry.person("Jeniffer", 22)
                        .flatMap(s -> PersonTry.career(s, "ing informatica")
                                .flatMap(r -> PersonTry.state("Terminado")
                                ));

        assertEquals(Try.of(() -> "Felicitaciones"),resultado2);


    }

    @Test
    public void testFailureWithFlatMap(){
        Try<Integer> resultado =
                PersonTry.person("Jeniffer", 22)
                        .flatMap(s -> PersonTry.format(s)
                                .flatMap(r -> PersonTry.tamaño(r)
                                ));

        assertNotEquals(Try.of(()-> 7), resultado);

        Try<String> resultado2 =
                PersonTry.person("Jeniffer", 26)
                        .flatMap(s -> PersonTry.career(s, "ing informatica")
                                .flatMap(r -> PersonTry.state("Terminado")
                                ));

        assertTrue(resultado2.isFailure());

    }

    @Test
    public void testFailureWithFlatMapWithRecover(){
        Try<Integer> resultado =
                PersonTry.person("Jeniffer", 22)
                        .flatMap(s -> PersonTry.format(s)
                                .flatMap(r -> PersonTry.tamaño(r).recover(Exception.class, e -> 8)
                                ));

        assertEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                PersonTry.person("Jeniffer", 26)
                        .flatMap(s -> PersonTry.career(s, "ing informatica")
                                .recover(Exception.class, e -> "S")
                                .flatMap(r -> PersonTry.state("Terminado")
                                ));

        assertEquals(Try.of(() -> "Felicitaciones"),resultado2);

    }

    @Test
    public void testFailureWithFlatMapWithRecoverWith(){
        Try<Integer> resultado =
                PersonTry.person("Jeniffer", 22)
                        .flatMap(s -> PersonTry.format(s)
                                .flatMap(r -> PersonTry.tamaño(r).recoverWith(Exception.class, e -> Try.of(()->8))
                                ));

        assertEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                PersonTry.person("Jeniffer", 26)
                        .flatMap(s -> PersonTry.career(s, "ing informatica")
                                .recoverWith(Exception.class, e -> Try.of(()->"S"))
                                .flatMap(r -> PersonTry.state("Terminado")
                                ));

        assertEquals(Try.of(() -> "Felicitaciones"),resultado2);

    }


    @Test
    public void testSuccesWithFor(){
        Try<Integer> resultado =
                For(PersonTry.person("Jeniffer", 22), s ->
                    For(PersonTry.format(s), s1 ->
                        PersonTry.tamaño(s1))).toTry();


        assertEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                For(PersonTry.person("Jeniffer", 22), s ->
                    For(PersonTry.career(s, "ing informatica"), s1 ->
                            PersonTry.state("Terminado"))).toTry();


        assertEquals(Try.of(() -> "Felicitaciones"),resultado2);


    }

    @Test
    public void testFailureWithFor(){
        Try<Integer> resultado =
                For(PersonTry.person("Jeniffe", 22), s ->
                        For(PersonTry.format(s), s1 ->
                                PersonTry.tamaño(s1))).toTry();

        assertNotEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                For(PersonTry.person("Jeniffer", 26), s ->
                        For(PersonTry.career(s, "ing informatica"), s1 ->
                                PersonTry.state("Terminado"))).toTry();

        assertTrue(resultado2.isFailure());

    }

    @Test
    public void testFailureWithForRecover(){
        Try<Integer> resultado =
                For(PersonTry.person("Jeniffer", 22), s ->
                    For(PersonTry.format(s), s1 ->
                            PersonTry.tamaño(s1)
                                .recover(Exception.class, 8))).toTry();

        assertEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                For(PersonTry.person("Jeniffer", 26), s ->
                    For(PersonTry.career(s, "ing informatica")
                            .recover(Exception.class, e -> "S"), s1 ->
                            PersonTry.state("Terminado"))).toTry();

        assertEquals(Try.of(() -> "Felicitaciones"),resultado2);

    }

    @Test
    public void testFailureWithForRecoverWith(){
        Try<Integer> resultado =
                For(PersonTry.person("Jeniffer", 22), s ->
                        For(PersonTry.format(s), s1 ->
                                PersonTry.tamaño(s1)
                                        .recoverWith(Exception.class, e -> Try.of(()->8)))).toTry();

        assertEquals(Try.of(()-> 8), resultado);

        Try<String> resultado2 =
                For(PersonTry.person("Jeniffer", 26), s ->
                        For(PersonTry.career(s, "ing informatica")
                                .recoverWith(Exception.class, e -> Try.of(()->"S")), s1 ->
                                PersonTry.state("Terminado"))).toTry();

        assertEquals(Try.of(() -> "Felicitaciones"),resultado2);

    }

}
