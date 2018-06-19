package co.com.s4n.training.java.vavr;

import io.vavr.Lazy;
import io.vavr.concurrent.Future;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

@RunWith(JUnitPlatform.class)
public class LazySuite {

        @Test
        public void testToModelLazy(){
            Lazy<Future<String>> f1 = Lazy.of(()-> Future.of(()->{
                Thread.sleep(500);
                return "mensaje 1";

            }));
            Lazy<Future<String>> f2 = Lazy.of(()-> Future.of(()->{
                Thread.sleep(800);
                return "mensaje 2";
            }));
            Lazy<Future<String>> f3 = Lazy.of(()-> Future.of(()->{
                Thread.sleep(300);
                return "mensaje 3";
            }));

            Long inicio = System.nanoTime();

            Future<String> res = f1.get()
                    .flatMap(s -> f2.get()
                            .flatMap(s1 -> f3.get()
                                    .flatMap(s3 -> Future.of(()-> s + s1 + s3))));

            res.await().get();
            Long fin = System.nanoTime();
            Long respNano = (fin-inicio);
            Double resp = (fin-inicio)*Math.pow(10, -6);
            System.out.println("nano "+respNano);
            System.out.println("mili "+resp);


            inicio = System.nanoTime();

            res = f1.get()
                    .flatMap(s -> f2.get()
                            .flatMap(s1 -> f3.get()
                                    .flatMap(s3 -> Future.of(()-> s + s1 + s3))));

            res.await().get();
            fin = System.nanoTime();
            respNano = (fin-inicio);
            resp = (fin-inicio)*Math.pow(10, -6);
            System.out.println("nano "+respNano);
            System.out.println("mili "+resp);


        }

        @Test
        public void testSupplier(){
            Supplier s1 = () ->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 4;
            };

            Lazy<Future<String>> f1 = Lazy.of(()-> Future.of(()->{
                Thread.sleep(500);
                return "mensaje 1";

            }));


            Long inicio1 = System.nanoTime();
            s1.get();
            Long fin1 = System.nanoTime();

            Long respNano = (fin1-inicio1);
            Double resp = (fin1-inicio1)*Math.pow(10, -6);

            System.out.println("Supplier 1: "+resp);

            inicio1 = System.nanoTime();
            s1.get();
            fin1 = System.nanoTime();

            resp = (fin1-inicio1)*Math.pow(10, -6);

            System.out.println("Supplier 2: "+resp);

            Long inicio2 = System.nanoTime();
            Future<String> res = f1.get();
            res.await();
            Long fin2 = System.nanoTime();

            Double resp2 = (fin2-inicio2)*Math.pow(10, -6);
            System.out.println("lazy 1: "+resp2);


            inicio2 = System.nanoTime();
            res = f1.get();
            res.await();
            fin2 = System.nanoTime();

            resp2 = (fin2-inicio2)*Math.pow(10, -6);
            System.out.println("lazy 2: "+resp2);
        }
}


