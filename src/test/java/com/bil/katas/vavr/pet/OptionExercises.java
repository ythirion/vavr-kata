package com.bil.katas.vavr.pet;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

public class OptionExercises extends PetDomainKata {
    @Test
    public void filterAListOfPerson() {
        // Instantiate a list of defined persons
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
        // Instantiate an Option of null, this option must return the string "Ich bin empty" when empty
        Option<String> iamAnOption = Option.of(null);
        String optionValue = iamAnOption.getOrElse("Ich bin empty");

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
        // Find a person matching firstName and lastName ("",""), throws an IllegalArgumentException if not
        String firstName = "Rick";
        String lastName = "Sanchez";

        Person foundPerson = this.people.find(person -> person.getLastName() == lastName && person.getFirstName() == firstName)
                .getOrElseThrow(() -> new IllegalArgumentException("No matching person"));
    }

    @Test
    public void chainCall() {
        // Chain calls to the half method 4 times with start in argument
        // Assert the value of result
        Double start = 100d;

        Option<Double> result = half(start)
                .flatMap(this::half)
                .flatMap(this::half)
                .flatMap(this::half)
                .flatMap(this::half);

        Assert.assertEquals(result, Option.none());
    }

    private Option<Double> half(Double x) {
        return x % 2 == 0 ? Option.of(x / 2) : Option.none();
    }
}