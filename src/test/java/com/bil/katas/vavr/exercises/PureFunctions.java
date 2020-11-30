package com.bil.katas.vavr.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PureFunctions {
    @Test
    public void calculate_rentals() {
        // Identify the design problems related to the rental calculator
        // Refactor it by using Pure Functions
        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental(LocalDate.of(2020, 10, 9), "Le Refuge des Loups (LA BRESSE)", 1089.90));
        rentals.add(new Rental(LocalDate.of(2020, 10, 12), "Au pied de la Tour (NOUILLORC)", 1276.45));
        rentals.add(new Rental(LocalDate.of(2020, 10, 24), "Le moulin du bonheur (GLANDAGE)", 670.89));

        RentalCalculator calculator = new RentalCalculator(rentals);
        calculator.calculateRental();

        Assert.assertTrue(calculator.isCalculated());
        Assert.assertEquals(3037.24, calculator.getAmount(), 0.01);
    }
}

@AllArgsConstructor
@Data
class Rental {
    private final LocalDate date;
    private final String label;
    private final double amount;
}

@Getter
class RentalCalculator {
    private double amount;
    private boolean calculated;

    private final List<Rental> rentals;

    public RentalCalculator(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public String calculateRental() {
        StringBuilder result = new StringBuilder();

        for (Rental rental : rentals) {
            if (!calculated)
                this.amount += rental.getAmount();

            result.append(formatLine(rental, amount));
        }
        result.append(String.format("Total amount | %f", this.amount));
        calculated = true;

        return result.toString();
    }

    private String formatLine(Rental rental, double amount) {
        return String.format("%tF : %s | %f \n",
                rental.getDate(),
                rental.getLabel(),
                rental.getAmount());
    }
}