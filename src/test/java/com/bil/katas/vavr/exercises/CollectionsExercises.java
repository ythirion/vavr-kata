package com.bil.katas.vavr.exercises;

import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.bil.katas.vavr.exercises.PetType.*;

/**
 * By doing these exercises you should have learned about the following APIs.
 * <p/>
 * {@link Seq#map(Function)}<br>
 * {@link Seq#filter(Predicate)}<br>
 * {@link Seq#reject(Predicate)}<br>
 * {@link Seq#find(Predicate)}<br>
 * {@link Seq#forAll(Predicate)}<br>
 * {@link Seq#count(Predicate)}<br>
 * {@link Seq#map(Function)}<br>
 * {@link Seq#flatMap(Function)}<br>
 * <p>
 * {@link Seq#count(Predicate)}<br>
 * <p>
 */
public class CollectionsExercises extends PetDomainKata {
    @Test
    public void getFirstNamesOfAllPeople() {
        // Replace null, with a transformation method on Seq.
        Seq<String> firstNames = null;

        Seq<String> expectedFirstNames = Vector.of("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John");
        Assert.assertEquals(expectedFirstNames, firstNames);
    }

    @Test
    public void getNamesOfMarySmithsPets() {
        Person person = this.getPersonNamed("Mary Smith");
        Seq<Pet> pets = person.getPets();

        // Replace null, with a transformation method on Seq.
        Seq<String> names = null;

        Assert.assertEquals("Tabby", names.mkString());
    }

    @Test
    public void getPeopleWithCats() {
        // Replace null, with a positive filtering method on Seq.
        Seq<Person> peopleWithCats = null;

        Assert.assertThat(peopleWithCats.size(), CoreMatchers.equalTo(2));
    }

    @Test
    public void getPeopleWithoutCats() {
        // Replace null, with a negative filtering method on Seq.
        Seq<Person> peopleWithoutCats = null;

        Assert.assertThat(peopleWithoutCats.size(), CoreMatchers.equalTo(6));
    }

    @Test
    public void doAnyPeopleHaveCats() {
        //replace null with a Predicate lambda which checks for PetType.CAT
        boolean doAnyPeopleHaveCats = false;
        Assert.assertTrue(doAnyPeopleHaveCats);
    }

    @Test
    public void doAllPeopleHavePets() {
        //replace with a method call send to this.people that checks if all people have pets
        Predicate<Person> predicate = null;
        boolean result = people.forAll(predicate);
        Assert.assertFalse(result);
    }

    @Test
    public void howManyPeopleHaveCats() {
        // replace 0 with the correct answer
        int count = 0;
        Assert.assertEquals(2, count);
    }

    @Test
    public void findMarySmith() {
        Person result = this.getPersonNamed("Mary Smith");
        Assert.assertEquals("Mary", result.getFirstName());
        Assert.assertEquals("Smith", result.getLastName());
    }

    @Test
    public void getPeopleWithPets() {
        // replace with only the pets owners
        Seq<Person> petPeople = null;
        Assert.assertThat(petPeople.size(), CoreMatchers.equalTo(7));
    }

    @Test
    public void getAllPetTypesOfAllPeople() {
        // retrieve all pet types owned by the people
        Seq<PetType> petTypes = null;

        Assert.assertEquals(
                Vector.of(CAT, DOG, SNAKE, BIRD, TURTLE, HAMSTER),
                petTypes);
    }

    @Test
    public void howManyPersonHaveCats() {
        // count the number of persons who owns cats
        int count = 0;
        Assert.assertEquals(2, count);
    }

    @Test
    public void whoOwnsTheYoungestPet() {
        // find the person who owns the youngest pet
        Option<Person> person = null;
        Assert.assertEquals("Jake", person.get().getFirstName());
    }

    @Test
    public void whoOwnsTheOldestPet() {
        // find the person who owns the oldest pet
        Option<Person> person = null;
        Assert.assertEquals("Ted", person.get().getFirstName());
    }

    @Test
    public void averagePetAge() {
        // replace null by the average pet age
        Option<Double> average = null;
        Assert.assertEquals("1.89", new BigDecimal(average.get())
                .setScale(2, RoundingMode.HALF_EVEN).toPlainString());
    }

    @Test
    public void totalPetAge() {
        // replace 0 by the total age of all the pets
        Number average = 0;
        Assert.assertEquals(17L, average);
    }
    //endregion

    @Test
    public void petsNameSorted() {
        // sort pet names alphabetically
        String sortedPetNames = null;
        Assert.assertEquals("Dolly,Fuzzy,Serpy,Speedy,Spike,Spot,Tabby,Tweety,Wuzzy", sortedPetNames);
    }
    //endregion


    @Test
    public void sortByAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList = null;

        Assert.assertThat(sortedAgeList.size(), CoreMatchers.equalTo(4));
        Assert.assertEquals(Vector.of(1, 2, 3, 4), sortedAgeList);
    }

    @Test
    public void sortByDescAge() {
        // Create a Seq<Integer> with descending ordered age values.
        Seq<Integer> sortedAgeList = null;

        Assert.assertThat(sortedAgeList.size(), CoreMatchers.equalTo(4));
        Assert.assertEquals(Vector.of(4, 3, 2, 1), sortedAgeList);
    }

    @Test
    public void top3OlderPets() {
        // get the names of the 3 older pets
        Seq<Pet> top3OlderPets = null;

        Assert.assertThat(top3OlderPets.size(), CoreMatchers.equalTo(3));
        Assert.assertEquals(Vector.of("Spike", "Dolly", "Tabby"), top3OlderPets.map(Pet::getName));
    }

    @Test
    public void getFirstPersonWithAtLeast2Pets() {
        // Find the first person who owns at least 2 pets
        Option<Person> firstPersonWithAtLeast2Pets = null;

        Assert.assertTrue(firstPersonWithAtLeast2Pets.isDefined());
        Assert.assertEquals("Bob", firstPersonWithAtLeast2Pets.get().getFirstName());
    }

    @Test
    public void isThereAnyPetOlderThan4() {
        // Check whether any pets older than 4 exists or not
        boolean isThereAnyPetOlderThan4 = false;

        Assert.assertFalse(isThereAnyPetOlderThan4);
    }

    @Test
    public void isEveryPetsOlderThan1() {
        // Check whether all pets are older than 1 or not
        boolean allOlderThan1 = false;

        Assert.assertTrue(allOlderThan1);
    }

    private Seq<String> filterParksFor(Seq<PetType> petTypes) {
        return this.parks.filter(park -> park.getAuthorizedPetTypes().containsAll(petTypes)).map(Park::getName);
    }

    @Test
    public void getListOfPossibleParksForAWalkPerPerson() {
        // For each person described as "firstName lastName" returns the list of names possible parks to go for a walk
        Map<String, Seq<String>> possibleParksForAWalkPerPerson = null;

        Assert.assertEquals(Vector.of("Jurassic", "Central", "Hippy"), possibleParksForAWalkPerPerson.get("John Doe").get());
        Assert.assertEquals(Vector.of("Jurassic", "Hippy"), possibleParksForAWalkPerPerson.get("Jake Snake").get());
    }
}