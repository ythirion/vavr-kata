package com.bil.katas.vavr.pet;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * By doing these exercises you should have learned :
 * {@link Option#isDefined()}<br>
 * {@link Option#isEmpty()}<br>
 * {@link Option#map(Function)}<br>
 * {@link Option#getOrElse(Object)}<br>
 * {@link Option#getOrElseThrow(Supplier)}<br>
 * {@link Option#peek(Consumer)}<br>
 * {@link Option#flatMap(Function)}<br>
 */
public class OptionExercises extends PetDomainKata {
    @Test
    public void filterAListOfPerson() {
        // Filter this list with only defined persons
        Seq<Option<Person>> persons = Vector.of(
                Option.none(),
                Option.of(new Person("John", "Doe")),
                Option.of(new Person("Mary", "Smith")),
                Option.none());


        Seq<Person> definedPersons = persons.filter(people -> people.isDefined())
                .flatMap(person -> person);

        Assert.assertEquals(2, definedPersons.length());
    }

    @Test
    public void workingWithNull() {
        // Instantiate an Option of null (of type String)
        // map it to an Upper case function
        // then it must return the string "Ich bin empty" if empty
        Option<String> iamAnOption = Option.of(null);
        String optionValue = iamAnOption
                .map(p -> p.toUpperCase())
                .getOrElse("Ich bin empty");

        Assert.assertTrue(iamAnOption.isEmpty());
        Assert.assertEquals("Ich bin empty", optionValue);
    }

    @Test
    public void findKaradoc() {
        // Find Karadoc in the people List or returns Perceval
        String foundPersonLastName = this.people.find(p -> p.named("Karadoc"))
                .map(person -> person.getLastName())
                .getOrElse("Perceval");

        Assert.assertEquals("Perceval", foundPersonLastName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findPersonOrDieTryin() {
        // Find a person matching firstName and lastName, throws an IllegalArgumentException if not found
        String firstName = "Rick";
        String lastName = "Sanchez";

        Person foundPerson = this.people.find(person -> person.getLastName() == lastName && person.getFirstName() == firstName)
                .getOrElseThrow(() -> new IllegalArgumentException("No matching person"));
    }

    @Test
    public void chainCall() {
        // Chain calls to the half method 4 times with start in argument
        // For each half append the value to the resultBuilder (side effect)
        Double start = 500d;
        StringBuilder resultBuilder = new StringBuilder();

        Option<Double> result = half(start)
                .peek(resultBuilder::append)
                .flatMap(this::half)
                .peek(resultBuilder::append)
                .flatMap(this::half)
                .peek(resultBuilder::append)
                .flatMap(this::half)
                .peek(resultBuilder::append);

        Assert.assertEquals(result, Option.none());
        Assert.assertEquals("250.0125.0", resultBuilder.toString());
    }

    private Option<Double> half(Double x) {
        return x % 2 == 0 ? Option.of(x / 2) : Option.none();
    }
}