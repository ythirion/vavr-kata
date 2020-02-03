package com.bil.katas.vavr.pet;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

public class Exercise2 extends PetDomainKata {
    @Test
    public void sortByAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList = people.flatMap(person -> person.getPets().map(Pet::getAge)).distinct().sorted();

        Assert.assertThat(sortedAgeList.size(), CoreMatchers.equalTo(4));
        Assert.assertEquals(Vector.of(1,2,3,4), sortedAgeList);
    }

    @Test
    public void sortByDescAge() {
        // Create a Seq<Integer> with ascending ordered age values.
        Seq<Integer> sortedAgeList = people.flatMap(person -> person.getPets().map(Pet::getAge)).distinct().sorted(Comparator.reverseOrder());

        Assert.assertThat(sortedAgeList.size(), CoreMatchers.equalTo(4));
        Assert.assertEquals(Vector.of(4,3,2,1), sortedAgeList);
    }

    @Test
    public void top3OlderPets() {
        // Create a Seq<Pet> with the 3 older pets.
        Seq<Pet> top3OlderPets = people.flatMap(Person::getPets)
                        .sortBy(Comparator.reverseOrder(), Pet::getAge)
                        .slice(0, 3);

        Assert.assertThat(top3OlderPets.size(), CoreMatchers.equalTo(3));
        Assert.assertEquals(Vector.of("Spike", "Dolly", "Tabby"), top3OlderPets.map(Pet::getName));
    }
}
