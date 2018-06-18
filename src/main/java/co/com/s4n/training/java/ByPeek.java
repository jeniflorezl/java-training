package co.com.s4n.training.java;

import io.vavr.control.Either;

import java.util.function.Consumer;

public class ByPeek<A,B> {

    public Either<A,B> ByPeek(Either<A,B> either, Consumer<A> myConsumer1, Consumer<B> myConsumer2) {
        return either.isRight() ? either.peek(myConsumer2) : either.peekLeft(myConsumer1);
    }
}
