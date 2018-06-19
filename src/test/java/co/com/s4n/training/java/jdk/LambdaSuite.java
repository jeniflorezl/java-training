package co.com.s4n.training.java.jdk;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class LambdaSuite {

    @FunctionalInterface
    interface InterfaceDeEjemplo{
        int metodoDeEjemplo(int x, int y);
    }


    class ClaseDeEjemplo{
        public int metodoDeEjemplo1(int z, InterfaceDeEjemplo i){ return z + i.metodoDeEjemplo(1,2); }

        public int metodoDeEjemplo2(int z, BiFunction<Integer, Integer, Integer> fn){
            return z + fn.apply(1,2);
        }
    }

    @Test
    public void smokeTest() {
        assertTrue(true);
    }

    @Test
    public void usarUnaInterfaceFuncional1(){

        InterfaceDeEjemplo i = (x,y)->x+y;

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo1(1,i);

        assertTrue(resultado==4);
    }

    @Test
    public void usarUnaInterfaceFuncional1Prueba(){

        InterfaceDeEjemplo i = (x,y)->x*y;

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo1(1,i);

        assertTrue(resultado==3);
    }

    @Test
    public void usarUnaInterfaceFuncional2(){

        BiFunction<Integer, Integer, Integer> f = (x, y) -> new Integer(x.intValue()+y.intValue());

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo2(1,f);

        assertTrue(resultado==4);
    }

    @Test
    public void usarUnaInterfaceFuncional2Prueba(){

        BiFunction<Integer, Integer, Integer> f = (x, y) -> new Integer(x.intValue()-y.intValue());

        ClaseDeEjemplo instancia = new ClaseDeEjemplo();

        int resultado = instancia.metodoDeEjemplo2(1,f);

        assertTrue(resultado==0);
    }

    class ClaseDeEjemplo2{

        public int metodoDeEjemplo2(int x, int y, IntBinaryOperator fn){
            return fn.applyAsInt(x,y);
        }
    }
    @Test
    public void usarUnaFuncionConTiposPrimitivos(){
        IntBinaryOperator f = (x, y) -> x + y;

        ClaseDeEjemplo2 instancia = new ClaseDeEjemplo2();

        int resultado = instancia.metodoDeEjemplo2(1,2,f);

        assertEquals(3,resultado);
    }

    @Test
    public void usarUnaFuncionConTiposPrimitivosPrueba(){
        IntBinaryOperator f = (x, y) -> x - y;

        ClaseDeEjemplo2 instancia = new ClaseDeEjemplo2();

        int resultado = instancia.metodoDeEjemplo2(1,2,f);

        assertEquals(-1,resultado);
    }

    class ClaseDeEjemplo3{

        public String operarConSupplier(Supplier<Integer> s){
            return "El int que me han entregado es: " + s.get();
        }
    }

    class supliers{

        public int operarConSupplier(Supplier<Integer> s){
            return s.get();
        }
    }

    @Test
    public void usarUnaFuncionConSupplier(){
        Supplier s1 = () -> {
            System.out.println("Cuándo se evalúa esto? (1)");
            return 4;
        };

        Supplier s2 = () -> {
            System.out.println("Cuándo se evalúa esto? (2)");
            return 4;
        };

        ClaseDeEjemplo3 instancia = new ClaseDeEjemplo3();

        String resultado = instancia.operarConSupplier(s2);

        assertEquals("El int que me han entregado es: 4",resultado);
    }

    class ClaseDeEjemplo4{

        private int i = 0;

        public void operarConConsumer(Consumer<Integer> c){
            c.accept(i);
        }
    }

    class ClaseDeEjemplo5{

        public void operarConConsumer(Consumer<Integer> c){
            c.accept(5);
        }
    }

    @Test
    public void usarUnaFuncionConConsumer(){
        Consumer<Integer> c1 = x -> {
            System.out.println("Me han entregado este valor: "+x);
        };

        ClaseDeEjemplo4 instancia = new ClaseDeEjemplo4();

        instancia.operarConConsumer(c1);


    }

    @Test
    public void usarUnaFuncionConConsumerPrueba(){
        Consumer<Integer> c1 = x -> {
            System.out.println("Me han entregado este valor: "+x);
        };

        ClaseDeEjemplo5 instancia = new ClaseDeEjemplo5();

        instancia.operarConConsumer(c1);


    }

    @FunctionalInterface
    interface InterfaceDeEjercicio{
        public Consumer<Integer> metodoEjercicio(Supplier<Integer> s1, Supplier<Integer> s2, Supplier<Integer> s3);
    }


    @Test
    public void test(){

        InterfaceDeEjercicio i = (a,b,d)->{
            //Integer partial = a.get() + b.get() + d.get() + n;
            Consumer<Integer> c = n -> {
                //Integer suma = partial.intValue() + n;
                Integer suma = a.get() + b.get() + d.get() + n;
                System.out.println("El resultado es: " + suma);
            };

            return c;

        };

        Supplier a = () -> 1;
        Supplier b = () -> 2;
        Supplier d = () -> 3;

        Consumer<Integer> consumer = i.metodoEjercicio(a,b,d);

        consumer.accept(new Integer(9));

    }


}
