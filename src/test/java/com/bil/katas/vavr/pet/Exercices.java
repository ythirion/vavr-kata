package com.bil.katas.vavr.pet;

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

import static com.bil.katas.vavr.pet.PetType.*;
import static java.lang.Integer.MAX_VALUE;

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
public class Exercices extends PetDomainKata {
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
        ; // null;  // this.people...

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
        Seq<Person> petPeople = this.people.filter(Person::isPetPerson); // replace with only the pet owners
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
        Option<Person> person = people.maxBy(p -> p.getPets().map(Pet::getAge).max().getOrElse(MAX_VALUE));
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

}