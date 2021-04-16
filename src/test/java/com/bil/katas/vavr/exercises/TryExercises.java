package com.bil.katas.vavr.exercises;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static me.grison.vavr.matchers.VavrMatchers.isSuccess;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * By doing these exercises you should have learned :
 * {@link Try#get()}<br>
 * {@link Try#isSuccess()}<br>
 * {@link Try#isEmpty()}<br>
 * {@link Try#isFailure()}<br>
 * {@link Try#getOrElse(Object)}<br>
 * {@link Try#onFailure(Consumer)}<br>
 * {@link Try#onSuccess(Consumer)}<br>
 * {@link Try#flatMap(Function)}<br>
 */
public class TryExercises extends PetDomainKata {

    private final String SUCCESS_MESSAGE = "I m a fucking genius the result is ";

    @Test
    public void getTheResultOfDivide() {
        // Divide x = 9 by y = 2
        Try<Integer> tryResult = Divide(9, 2);
        Integer result = tryResult.get();

        assertThat(tryResult, isSuccess(equalTo(4)));
    }

    @Test
    public void mapTheResultOfDivide() {
        // Divide x = 9 by y = 2 and add z to the result
        Integer z = 3;
        Integer result = Divide(9, 2).map(a -> a + z).get();

        assertThat(result, equalTo(7));
    }

    @Test
    public void divideByZeroIsAlwaysAGoodIdea() {
        // Divide x by 0 and get the result
        assertThrows(ArithmeticException.class, () -> Divide(1, 0).get());
    }

    @Test
    public void divideByZeroOrElse() {
        // Divide x by 0, on exception returns 0
        Integer x = 1;
        Integer result = Divide(x, 0).getOrElse(0);

        assertThat(result, equalTo(0));
    }

    @Test
    public void mapTheFailure() {
        // Divide x by 0, log the failure message to the console and get 0
        Integer x = 1;

        Integer result = Divide(x, 0)
                .onFailure(failure -> System.out.println(failure.getMessage()))
                .getOrElse(0);

        assertThat(result, equalTo(0));
    }

    @Test
    public void mapTheSuccess() {
        // Divide x by y
        // log the failure message to the console
        // Log your success to the console
        // Get the result or 0 if exception
        Integer x = 8;
        Integer y = 4;

        Integer result = Divide(x, y)
                .onFailure(failure -> System.out.println(failure.getMessage()))
                .onSuccess(success -> System.out.println(SUCCESS_MESSAGE + success))
                .getOrElse(0);

        assertThat(result, equalTo(2));
    }

    @Test
    public void chainTheTry() {
        // Divide x by y
        // Chain 2 other calls to divide with x = previous Divide result
        // log the failure message to the console
        // Log your success to the console
        // Get the result or 0 if exception
        Integer x = 27;
        Integer y = 3;

        Integer result = Divide(x, y)
                .flatMap(previous -> Divide(previous, y))
                .flatMap(previous -> Divide(previous, y))
                .onFailure(failure -> System.out.println(failure.getMessage()))
                .onSuccess(success -> System.out.println(SUCCESS_MESSAGE + success))
                .getOrElse(12);

        assertThat(result, equalTo(1));
    }

    private Try<Integer> Divide(Integer x, Integer y) {
        return Try.of(() -> x / y);
    }
}