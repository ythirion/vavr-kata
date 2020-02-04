package com.bil.katas.vavr.pet;

import io.vavr.control.Option;
import org.junit.Assert;
import org.junit.Test;

public class Exercise4 extends PetDomainKata {
    @Test
    public void getFirstPersonWithAtLeast2Pets() {
        // Find the first person who owns at least 2 pets
        Option<Person> firstPersonWithAtLeast2Pets =
                people.filter(person -> person.getPets().size() >= 2)
                        .headOption();

        Assert.assertTrue(firstPersonWithAtLeast2Pets.isDefined());
        Assert.assertEquals("Bob", firstPersonWithAtLeast2Pets.get().getFirstName());
    }

    @Test
    public void isThereAnyPetOlderThan4() {
        // Check whether any pet older than 4 exists or not
        boolean isThereAnyPetOlderThan4 =
                people.flatMap(Person::getPets)
                        .find(pet -> pet.getAge() > 4)
                        .isDefined();

        Assert.assertFalse(isThereAnyPetOlderThan4);
    }

    @Test
    public void isEveryPetsOlderThan1() {
        // Check whether all pets are older than 1 or not
        boolean allOlderThan1 =
                people.flatMap(Person::getPets)
                        .filter(pet -> pet.getAge() < 1)
                        .isEmpty();

        Assert.assertTrue(allOlderThan1);
    }
}