package com.bil.katas.vavr.pet;

import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import org.junit.Assert;
import org.junit.Test;

public class Exercise8 extends PetDomainKata {
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

        Assert.assertEquals(Vector.of("Jurrassic", "Central", "Hippy"), possibleParksForAWalkPerPerson.get("John Doe").get());
        Assert.assertEquals(Vector.of("Jurrassic", "Hippy"), possibleParksForAWalkPerPerson.get("Jake Snake").get());
    }
}