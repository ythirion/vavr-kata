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
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.bil.katas.vavr.exercises.PetType.*;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

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
        Seq<String> firstNames = this.people.map(Person::getFirstName);//null; // this.people...

        Seq<String> expectedFirstNames = Vector.of("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John");
        Assert.assertEquals(expectedFirstNames, firstNames);
    }

    @Test
    public void getNamesOfMarySmithsPets() {
        Person person = this.getPersonNamed("Mary Smith");
        Seq<Pet> pets = person.getPets();

        // Replace null, with a transformation method on Seq.
        Seq<String> names = pets.map(Pet::getName); // null; // pets...

        Assert.assertEquals("Tabby", names.mkString());
    }

    @Test
    public void getPeopleWithCats() {
        // Replace null, with a positive filtering method on Seq.
        Seq<Person> peopleWithCats = people.filter(p -> p.hasPetType(CAT));// null;  // this.people...

        Assert.assertThat(peopleWithCats.size(), CoreMatchers.equalTo(2));
    }

    @Test
    public void getPeopleWithoutCats() {
        // Replace null, with a negative filtering method on Seq.
        Seq<Person> peopleWithoutCats = people.reject(p -> p.hasPetType(CAT));
        // null;  // this.people...

        Assert.assertThat(peopleWithoutCats.size(), CoreMatchers.equalTo(6));
    }

    @Test
    public void doAnyPeopleHaveCats() {
        boolean doAnyPeopleHaveCats = people.find(p -> p.hasPetType(CAT)).isDefined(); // null; //replace null with a Predicate lambda which checks for PetType.CAT
        Assert.assertTrue(doAnyPeopleHaveCats);
    }

    @Test
    public void doAllPeopleHavePets() {
        Predicate<Person> predicate = Person::isPetPerson;
        boolean result = people.forAll(predicate); // true; //replace with a method call send to this.people that checks if all people have pets
        Assert.assertFalse(result);
    }

    @Test
    public void howManyPeopleHaveCats() {
        int count = people.count(p -> p.hasPetType(CAT)); // replace 0 with the correct answer
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
        Seq<Person> petPeople = this.people.filter(Person::isPetPerson); // replace with only the exercises owners
        Assert.assertThat(petPeople.size(), CoreMatchers.equalTo(7));
    }

    @Test
    public void getAllPetTypesOfAllPeople() {
        Seq<PetType> petTypes = people.map(Person::getPetTypes)
                .map(Map::keySet)
                .flatMap(Function.identity()).distinct();
        Assert.assertEquals(
                Vector.of(CAT, DOG, SNAKE, BIRD, TURTLE, HAMSTER),
                petTypes);
    }


    //region count, minBy, maxBy, min, max
    @Test
    public void howManyPersonHaveCats() {
        // use count
        int count = people.count(p -> p.hasPetType(CAT));
        Assert.assertEquals(2, count);
    }

    @Test
    public void whoOwnsTheYoungestPet() {
        // use minBy + min
        Option<Person> person = people.minBy(p -> p.getPets().map(Pet::getAge).min().getOrElse(MAX_VALUE));
        Assert.assertEquals("Jake", person.get().getFirstName());
    }

    @Test
    public void whoOwnsTheOldestPet() {
        // use maxBy + max
        Option<Person> person = people.maxBy(p -> p.getPets().map(Pet::getAge).max().getOrElse(MIN_VALUE));
        Assert.assertEquals("John", person.get().getFirstName());
    }

    @Test
    public void averagePetAge() {
        Option<Double> average = people.flatMap(Person::getPets).map(Pet::getAge).average();
        Assert.assertEquals("1.89", new BigDecimal(average.get())
                .setScale(2, RoundingMode.HALF_EVEN).toPlainString());
    }

    @Test
    public void totalPetAge() {
        Number average = people.flatMap(Person::getPets).map(Pet::getAge).sum();
        Assert.assertEquals(17L, average);
    }
    //endregion

    //region joining (mkString)
    @Test
    public void petsNameSorted() {
        String sortedPetNames = people.flatMap(Person::getPets).map(Pet::getName).sorted().mkString(",");
        Assert.assertEquals("Dolly,Fuzzy,Serpy,Speedy,Spike,Spot,Tabby,Tweety,Wuzzy", sortedPetNames);
    }
    //endregion


    @Test
    public void sortByAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList =
                people.flatMap(person -> person.getPets().map(Pet::getAge))
                        .distinct()
                        .sorted();

        Assert.assertThat(sortedAgeList.size(), CoreMatchers.equalTo(4));
        Assert.assertEquals(Vector.of(1, 2, 3, 4), sortedAgeList);
    }

    @Test
    public void sortByDescAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList =
                people.flatMap(person -> person.getPets().map(Pet::getAge))
                        .distinct()
                        .sorted(Comparator.reverseOrder());

        Assert.assertThat(sortedAgeList.size(), CoreMatchers.equalTo(4));
        Assert.assertEquals(Vector.of(4, 3, 2, 1), sortedAgeList);
    }

    @Test
    public void top3OlderPets() {
        // Create a Seq<Pet> with the 3 older pets.
        Seq<Pet> top3OlderPets =
                people.flatMap(Person::getPets)
                        .sortBy(Comparator.reverseOrder(), Pet::getAge)
                        .slice(0, 3);

        Assert.assertThat(top3OlderPets.size(), CoreMatchers.equalTo(3));
        Assert.assertEquals(Vector.of("Spike", "Dolly", "Tabby"), top3OlderPets.map(Pet::getName));
    }

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
        // Check whether any exercises older than 4 exists or not
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

    private Seq<String> filterParksFor(Seq<PetType> petTypes) {
        return this.parks.filter(park -> park.getAuthorizedPetTypes().containsAll(petTypes)).map(Park::getName);
    }

    @Test
    public void getListOfPossibleParksForAWalkPerPerson() {
        // For each person described as "firstName lastName" returns the list of names possible parks to go for a walk
        Map<String, Seq<String>> possibleParksForAWalkPerPerson =
                people.groupBy(person -> person)
                        .mapKeys(person -> person.getFirstName() + " " + person.getLastName())
                        .mapValues(persons -> persons.flatMap(person -> filterParksFor(person.getPets().map(Pet::getType))));

        Assert.assertEquals(Vector.of("Jurassic", "Central", "Hippy"), possibleParksForAWalkPerPerson.get("John Doe").get());
        Assert.assertEquals(Vector.of("Jurassic", "Hippy"), possibleParksForAWalkPerPerson.get("Jake Snake").get());
    }
}