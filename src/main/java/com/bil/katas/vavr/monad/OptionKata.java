package com.bil.katas.vavr.monad;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;

import java.util.Currency;
import java.util.Optional;
import java.util.Set;

/**
 * @author Alexandre Grison (eqk83)
 */
public class OptionKata {

    Map<String, String> currencies = HashMap.ofEntries(
            Map.entry("USD", "$"),
            Map.entry("EUR", "€"),
            Map.entry("GBP", "£")
    );

    Currency getCurrencyInstance(short numericCode) {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        Optional<Currency> returnedCurrency = currencies.stream()
                .filter(currency -> currency.getNumericCode() == numericCode)
                .findFirst();

        // Check if we found a value
        if(returnedCurrency.isPresent()) {
            return returnedCurrency.get();
        } else {
            throw new IllegalArgumentException("Currency with numeric code "  + numericCode + " not found");
        }
    }

    Currency getCurrencyInstanceBetter(short numericCode) {
        return Stream.ofAll(Currency.getAvailableCurrencies())
                .find(currency -> currency.getNumericCode() == numericCode)
                .getOrElseThrow(() -> new IllegalArgumentException("Currency with numeric code " + numericCode + " not found"));
    }

}
