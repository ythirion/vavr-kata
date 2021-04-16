package com.bil.katas.vavr.exercises;

import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PureFunctions {

    @Test
    public void calculate_rentals() {
        Seq<Rental> rentals = Vector.of(
                new Rental(LocalDate.of(2020, 10, 9), "Le Refuge des Loups (LA BRESSE)", 1089.90),
                new Rental(LocalDate.of(2020, 10, 12), "Au pied de la Tour (NOUILLORC)", 1276.45),
                new Rental(LocalDate.of(2020, 10, 24), "Le moulin du bonheur (GLANDAGE)", 670.89));

        Assertions.assertEquals(3037.24, RentalCalculator.calculateRental.apply(rentals), 0.01);
    }
}

@AllArgsConstructor
@Data
class Rental {
    private final LocalDate date;
    private final String label;
    private final double amount;
}

@AllArgsConstructor
abstract class RentalCalculator {

    static Function1<Seq<Rental>, String> formatStatement =
            (rentals) -> rentals.foldLeft("", (statement, rental) -> statement + formatLine(rental))
                    .concat(String.format("Total amount | %f", RentalCalculator.calculateRental.apply(rentals)));

    static Function1<Seq<Rental>, Double> calculateRental =
            (rentals) -> rentals.foldLeft(0.0, (acc, rental) -> acc + rental.getAmount());

    private static String formatLine(Rental rental) {
        return String.format("%tF : %s | %f \n",
                rental.getDate(),
                rental.getLabel(),
                rental.getAmount());
    }
}