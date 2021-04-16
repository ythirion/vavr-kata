package com.bil.katas.vavr.exercises;

import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.bil.katas.vavr.exercises.PetType.*;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static me.grison.vavr.matchers.VavrMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

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
        Seq<String> firstNames = this.people.map(Person::getFirstName);

        assertThat(firstNames, not(isEmpty()));
        assertThat(firstNames, hasLength(8));
        assertThat(firstNames, containsInOrder("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John"));
    }

    @Test
    public void getNamesOfMarySmithsPets() {
        Person person = this.getPersonNamed("Mary Smith");
        Seq<Pet> pets = person.getPets();

        // Replace null, with a transformation method on Seq.
        Seq<String> names = pets.map(Pet::getName);

        assertThat("Tabby", equalTo(names.mkString()));
    }

    @Test
    public void getPeopleWithCats() {
        // Replace null, with a positive filtering method on Seq.
        Seq<Person> peopleWithCats = people.filter(p -> p.hasPetType(CAT));// null;  // this.people...
        assertThat(peopleWithCats, hasLength(2));
    }

    @Test
    public void getPeopleWithoutCats() {
        // Replace null, with a negative filtering method on Seq.
        Seq<Person> peopleWithoutCats = people.reject(p -> p.hasPetType(CAT));
        assertThat(peopleWithoutCats, hasLength(6));
    }

    @Test
    public void doAnyPeopleHaveCats() {
        boolean doAnyPeopleHaveCats = people.find(p -> p.hasPetType(CAT)).isDefined();
        assertThat(doAnyPeopleHaveCats, is(true));
    }

    @Test
    public void doAllPeopleHavePets() {
        Predicate<Person> predicate = Person::isPetPerson;
        boolean result = people.forAll(predicate); // true; //replace with a method call send to this.people that checks if all people have pets
        assertThat(result, is(false));
    }

    @Test
    public void howManyPeopleHaveCats() {
        int count = people.count(p -> p.hasPetType(CAT)); // replace 0 with the correct answer
        assertThat(count, equalTo(2));
    }

    @Test
    public void findMarySmith() {
        Person result = this.getPersonNamed("Mary Smith");

        assertThat(result.getFirstName(), equalTo("Mary"));
        assertThat(result.getLastName(), equalTo("Smith"));
    }

    @Test
    public void getPeopleWithPets() {
        Seq<Person> petPeople = this.people.filter(Person::isPetPerson); // replace with only the exercises owners
        assertThat(petPeople, hasLength(7));
    }

    @Test
    public void getAllPetTypesOfAllPeople() {
        Seq<PetType> petTypes = people.map(Person::getPetTypes)
                .map(Map::keySet)
                .flatMap(Function.identity()).distinct();

        assertThat(petTypes, hasLength(6));
        assertThat(petTypes, containsInOrder(CAT, DOG, SNAKE, BIRD, TURTLE, HAMSTER));
    }

    //region count, minBy, maxBy, min, max
    @Test
    public void howManyPersonHaveCats() {
        // use count
        int count = people.count(p -> p.hasPetType(CAT));
        assertThat(count, equalTo(2));
    }

    @Test
    public void whoOwnsTheYoungestPet() {
        // use minBy + min
        Option<Person> person = people.minBy(p -> p.getPets().map(Pet::getAge).min().getOrElse(MAX_VALUE));
        assertThat(person, isDefined());
        assertThat(person.get().getFirstName(), equalTo("Jake"));
    }

    @Test
    public void whoOwnsTheOldestPet() {
        // use maxBy + max
        Option<Person> person = people.maxBy(p -> p.getPets().map(Pet::getAge).max().getOrElse(MIN_VALUE));
        assertThat(person, isDefined());
        assertThat(person.get().getFirstName(), equalTo("Ted"));
    }

    @Test
    public void averagePetAge() {
        Option<Double> average = people.flatMap(Person::getPets).map(Pet::getAge).average();
        assertThat(average, isDefined());
        assertThat(average.get(), closeTo(1.89, 0.1));
    }

    @Test
    public void totalPetAge() {
        Number average = people.flatMap(Person::getPets).map(Pet::getAge).sum();
        assertThat(average, equalTo(17L));
    }
    //endregion

    //region joining (mkString)
    @Test
    public void petsNameSorted() {
        String sortedPetNames = people.flatMap(Person::getPets).map(Pet::getName).sorted().mkString(",");
        assertThat(sortedPetNames, equalTo("Dolly,Fuzzy,Serpy,Speedy,Spike,Spot,Tabby,Tweety,Wuzzy"));
    }
    //endregion


    @Test
    public void sortByAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList =
                people.flatMap(person -> person.getPets().map(Pet::getAge))
                        .distinct()
                        .sorted();

        assertThat(sortedAgeList, hasLength(4));
        assertThat(sortedAgeList, containsInOrder(1, 2, 3, 4));
    }

    @Test
    public void sortByDescAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList =
                people.flatMap(person -> person.getPets().map(Pet::getAge))
                        .distinct()
                        .sorted(Comparator.reverseOrder());

        assertThat(sortedAgeList, hasLength(4));
        assertThat(sortedAgeList, containsInOrder(4, 3, 2, 1));
    }

    @Test
    public void top3OlderPets() {
        // Create a Seq<Pet> with the 3 older pets.
        Seq<Pet> top3OlderPets =
                people.flatMap(Person::getPets)
                        .sortBy(Comparator.reverseOrder(), Pet::getAge)
                        .slice(0, 3);

        assertThat(top3OlderPets, hasLength(3));
        assertThat(top3OlderPets.map(Pet::getName), containsInOrder("Spike", "Dolly", "Tabby"));
    }

    @Test
    public void getFirstPersonWithAtLeast2Pets() {
        // Find the first person who owns at least 2 pets
        Option<Person> firstPersonWithAtLeast2Pets =
                people.filter(person -> person.getPets().size() >= 2)
                        .headOption();

        assertThat(firstPersonWithAtLeast2Pets, isDefined());
        assertThat(firstPersonWithAtLeast2Pets.get().getFirstName(), equalTo("Bob"));
    }

    @Test
    public void isThereAnyPetOlderThan4() {
        // Check whether any exercises older than 4 exists or not
        boolean isThereAnyPetOlderThan4 =
                people.flatMap(Person::getPets)
                        .find(pet -> pet.getAge() > 4)
                        .isDefined();

        assertThat(isThereAnyPetOlderThan4, is(false));
    }

    @Test
    public void isEveryPetsOlderThan1() {
        // Check whether all pets are older than 1 or not
        boolean allOlderThan1 =
                people.flatMap(Person::getPets)
                        .filter(pet -> pet.getAge() < 1)
                        .isEmpty();

        assertThat(allOlderThan1, is(true));
    }

    private Seq<String> filterParksFor(Seq<PetType> petTypes) {
        return this.parks.filter(park -> park.getAuthorizedPetTypes().containsAll(petTypes)).map(Park::getName);
    }

    @Test
    public void getListOfPossibleParksForAWalkPerPerson() {
        // For each person described as "firstName lastName" returns the list of names possible parks to go for a walk
        Map<String, Seq<String>> possibleParksForAWalkPerPerson =
                people.groupBy(person -> person.getFirstName() + " " + person.getLastName())
                        .mapValues(persons -> persons.flatMap(person -> filterParksFor(person.getPets().map(Pet::getType))));

        assertThat(possibleParksForAWalkPerPerson.get("John Doe").get(), containsInAnyOrder("Jurassic", "Central", "Hippy"));
        assertThat(possibleParksForAWalkPerPerson.get("Jake Snake").get(), containsInAnyOrder("Jurassic", "Hippy"));
    }
}