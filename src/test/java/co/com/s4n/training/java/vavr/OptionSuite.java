package co.com.s4n.training.java.vavr;

import co.com.s4n.training.java.Person;

import io.vavr.PartialFunction;
import io.vavr.control.Option;

import static io.vavr.API.None;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

import java.util.Optional;

import static io.vavr.API.Some;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class OptionSuite {

    @Test
    public void testConstruction1(){
        Option<Integer> o = Option(1);
        assertTrue(o.isDefined());
        assertEquals(o.getOrElse(666), new Integer(1));
    }

    @Test
    public void testConstruction2(){
        Option<Integer> o = Option(null);
        assertFalse(o.isDefined());
        assertEquals(o, Option.none());
    }

    private Boolean esParPosibleNull(int i){
        if (i%2==0){
            return new Boolean(true);
        }else{
            return null;
        }
    }

    private Integer identidadPosibleNull(int i){
        if (i%2==0){
            return new Integer(i);
        }else{
            return null;
        }
    }

    @Test
    public void testConstruccion3(){
       Option<Boolean> b = Option(esParPosibleNull(1));
       assertEquals(b, Option.none());
    }

    @Test
    public void testFilter(){
        Option<Integer> b = Option(identidadPosibleNull(2));
        Option<Integer> r = b.filter(x -> x.intValue() < 4);
        assertEquals(r.getOrElse(666).intValue(),2);
    }

    @Test
    public void testFilterNone(){
        Option<Integer> b = Option(identidadPosibleNull(1));
        Option<Integer> r = b.filter(x -> x.intValue() < 4);
        assertEquals(r, Option.none());
    }

    @Test
    public void mapInOption(){
        Option<Integer> o1 = Option(identidadPosibleNull(8));
        Option<Integer> o2 = o1.map(x->x-8);
        assertEquals(o2, Some(0));
    }

    @Test
    public void mapInOptionNone(){
        Option<Integer> o1 = Option(identidadPosibleNull(3));
        Option<Integer> o2 = o1.map(x->x);
        assertEquals(o2, Option.none());
    }



    /**
     * Un option se puede filtar, y mostrar un some() o un none si no encuentra el resultado
     */
    @Test
    public void testOptionWithFilter() {
        Option<Integer> o = Option(3);

        assertEquals(Some(3),
                o.filter(it -> it >= 3), "Does not Exist the filter");

        assertEquals(None(),
                o.filter(it -> it > 3), "Does not Exist the filter");
    }

    /**
     * Se puede hacer pattern matching a un option y comparar entre Some y None.
     */
    private String patternMatchSimple(Option<Integer> number) {
        String result = Match(number).of(
                Case($Some($()),"Existe"),
                Case($None(),"Imaginario")
        );
        return result;
    }

    @Test
    public void testOptionWithPatternMatching() {
        Option<Integer> o1 = Option(1);
        Option<Integer> o2 = None();

        //Comparacion de Some o None()
        assertEquals("Existe", patternMatchSimple(o1),"Failure match optionList");
        assertEquals("Imaginario", patternMatchSimple(o2), "Failure match optionList2");
    }
    /**
     *
     * el metodo peek aplica una funcion lambda o un metodo con el valor de Option cuando esta definido
     * este metodo se usa para efectos colaterales y retorna el mismo Option que lo llamó
     */
    @Test
    public void testPeekMethod(){
        Option<String> defined_option = Option.of("Hello!");
        /* Se debe utilizar una variable mutable para reflejar los efectos colaterales*/
        final List<String> list = new ArrayList<>();
        Option<String> peek = defined_option.peek(list::add);// the same as defined_option.peek(s -> list.add(s))

        System.out.println("peek: "+ peek);

        assertEquals(Option.of("Hello!"),
                defined_option, "failed - peek did not return the same Option value");

        assertEquals("Hello!",
                list.get(0), "failed - peek did not apply the side effect");
    }

    /**
     * Un option se puede transformar dada una función
     */
    @Test
    public void testOptionTransform() {
        String textToCount = "Count this text";
        Option<String> text = Option.of(textToCount);
        Option<Integer> count = text.transform(s -> Option.of(s.getOrElse("DEFAULT").length()));

        assertEquals(Option.of(textToCount.length()),
                count, "failure - Option was not transformed");

        Option<String> hello = Option.of("Hello");
        Tuple2<String, String> result = hello.transform(s -> Tuple.of("OK", s.getOrElse("DEFAULT")));

        assertEquals(Tuple.of("OK", "Hello"),
                result, "failure - Option was not transformed");

    }

    /**
     * el metodo getOrElse permite obtener el valor de un Option o un sustituto en caso de ser None
     */
    @Test
    public void testGetOrElse(){
        Option<String> defined_option = Option.of("Hello!");
        Option<String> none = None();
        assertEquals( "Hello!", defined_option.getOrElse("Goodbye!"),
                "failure - getOrElse did not get the current value of Option");
        assertEquals( "Goodbye!", none.getOrElse("Goodbye!"),
                "failure - getOrElse did not replace None");
    }

    /**
     * el metodo 'when' permite crear un Some(valor) o None utilizando condicionales booleanos
     */
    @Test
    public void testWhenMethod(){
        Option<String> valid = Option.when(true, "Good!");
        Option<String> invalid = Option.when(false, "Bad!");
        assertEquals(Some("Good!"), valid, "failed - the Option value must contain a Some('Good!')");
        assertEquals(None(), invalid, "failed - the Option value must contein a None because the condtion is false");
    }

    @Test
    public void testOptionCollect() {
        final PartialFunction<Integer, String> pf = new PartialFunction<Integer, String>() {
            @Override
            public String apply(Integer i) {
                return String.valueOf(i);
            }

            @Override
            public boolean isDefinedAt(Integer i) {
                return i % 2 == 1;
            }
        };
        assertEquals(None(),Option.of(2).collect(pf), "Failure, it returned Some() it should returned None()");
        assertEquals(None(),Option.<Integer>none().collect(pf), "Failure, it returned Some() it should returned None()");
    }
    /**
     * En este test se prueba la funcionalidad para el manejo de Null en Option con FlatMap
     */
    @Test
    public void testMananagementNull(){
        Option<String> valor = Option.of("pepe");
        Option<String> someN = valor.map(v -> null);

        /* Se valida que devuelve un Some null lo cual podria ocasionar en una Excepcion de JavanullPointerExcepcion*/
        assertEquals(
                someN.get(),
                null, "The option someN is Some(null)");

        Option<String> buenUso = someN
                .flatMap(v -> {
                    System.out.println("testManagementNull - Esto se imprime? (flatMap)");
                    return Option.of(v);
                })
                .map(x -> {
                    System.out.println("testManagementNull - Esto se imprime? (map)");
                    return x.toUpperCase() +"Validacion";
                });

        assertEquals(None(),
                buenUso, "The option is not defined because result is None");
    }

    /**
     * En este test se prueba la funcionalidad para transformar un Option por medio de Map y flatMap
     */
    @Test
    public void testMapAndFlatMapToOption() {
        Option<String> myMap = Option.of("mi mapa");

        Option<String> myResultMapOne = myMap.map(s -> s + " es bonito");

        assertEquals(Option.of("mi mapa es bonito"),
                myResultMapOne, "Transform Option with Map");

        Option<String> myResultMapTwo = myMap
                .flatMap(s -> Option.of(s + " es bonito"))
                .map(v -> v + " con flat map");


        assertEquals(Option.of("mi mapa es bonito con flat map"),
                myResultMapTwo, "Transform Option with flatMap");
    }

    @Test
    public void optionFromNull(){
        Option<Object> of = Option.of(null);
        assertEquals(of, None());
    }

    @Test
    public void optionFromOptional(){
        Optional optional = Optional.of(1);
        Option option = Option.ofOptional(optional);
    }

    Option<Integer> esPar(int i){
        return (i%2==0)?Some(i):None();
    }

    @Test
    public void forCompEnOption1(){
        Option<Integer> integers = For(esPar(2), d -> Option(d)).toOption();
        assertEquals(integers,Some(2));
    }

    @Test
    public void forCompEnOption2(){
        Option<Integer> integers = For(esPar(2), d ->
                                   For(esPar(4), c -> Option(d+c))).toOption();
        assertEquals(integers,Some(6));
    }

    @Test
    public void flatMapInOption(){
        Option<Integer> o1 = Option.of(1);
        Option<Option<Integer>> m = o1.map(i -> Option.of(identidadPosibleNull(i.intValue()-3)));
        Option<Integer> x = o1.flatMap(i -> Option.of(identidadPosibleNull(i.intValue()-3)));
    }

    private Option<Integer> sumar(int a, int b){
        System.out.println("sumando " + a + "+" + b);
        return Option.of(a+b);

    }

    private Option<Integer> restar(int a, int b){
        System.out.println("restando " + a + "-" + b);
        return a-b>0 ? Option.of(a-b) : None();
    }

    @Test
    public void flatMapInOption2(){
         Option<Integer> resultado =
             sumar(1,1).flatMap(a -> sumar(a,1))
                .flatMap(b -> sumar(b, 1)
                    .flatMap(c -> sumar(c, 1)
                        .flatMap(d -> sumar(d, 1)
                )));
         assertEquals(resultado.getOrElse(666).intValue(),6);
    }

    @Test
    public void flatMapInOptionNone(){
        Option<Integer> resultado =
                sumar(1,1).flatMap(a -> sumar(a,1))
                        .flatMap(b -> restar(b, 4)
                                .flatMap(c -> sumar(c, 1)
                                        .flatMap(d -> sumar(d, 1)
                                        )));
        assertEquals(resultado,None());
        assertEquals(resultado.getOrElse(666).intValue(),666);
    }

    @Test
    public void flatMapInOptionConFor(){

        Option<Integer> res =
            For(sumar(1,1), r1 ->
            For(sumar(r1,1), r2 ->
            For(sumar(r2, 1), r3 ->
            sumar(r3,r1)))).toOption();


        assertEquals(res.getOrElse(666).intValue(),6);
    }

    @Test
    public void exerciseflatMapInOption(){

        Option<Integer> resultado =
                Person.person("Jeniffer", 22)
                        .flatMap(s -> Person.format(s)
                                .flatMap(r -> Person.tamaño(r)
                                ));
        System.out.println("exercise FlatMap"+resultado);
        assertEquals(8, resultado.getOrElse(666).intValue());

        Option<String> resultado2 =
                Person.person("Jeniffer", 22)
                        .flatMap(s -> Person.career(s, "ing informatica")
                                .flatMap(r -> Person.state("Terminado")
                                ));
        System.out.println("exercise FlatMap1"+resultado2);
        //assertEquals(resultado2,None());
        assertEquals(Some("Felicitaciones"), resultado2);


    }

    @Test
    public void exerciseflatMapInOptionConFor(){

        Option<Integer> resultado =
                For(Person.person("Jeniffer", 22), s1 ->
                    For(Person.format(s1), s2 ->
                        Person.tamaño(s2))).toOption();

        assertEquals(8, resultado.getOrElse(666).intValue());

        Option<String> resultado2 =
                For(Person.person("Jeniffer", 22), s1 ->
                    For(Person.career(s1, "ing informatica"), s2 ->
                        Person.state("Terminado"))).toOption();

        assertEquals(Some("Felicitaciones"), resultado2);
    }
}
