package com.bil.katas.vavr.pet;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import org.junit.Before;

public abstract class PetDomainKata {
    protected Seq<Person> people;
    protected Seq<Park> parks;

    @Before
    public void setUp() throws Exception {
        this.people = Vector.of(
                new Person("Mary", "Smith").addPet(PetType.CAT, "Tabby", 2),
                new Person("Bob", "Smith")
                        .addPet(PetType.CAT, "Dolly", 3)
                        .addPet(PetType.DOG, "Spot", 2),
                new Person("Ted", "Smith").addPet(PetType.DOG, "Spike", 4),
                new Person("Jake", "Snake").addPet(PetType.SNAKE, "Serpy", 1),
                new Person("Barry", "Bird").addPet(PetType.BIRD, "Tweety", 2),
                new Person("Terry", "Turtle").addPet(PetType.TURTLE, "Speedy", 1),
                new Person("Harry", "Hamster")
                        .addPet(PetType.HAMSTER, "Fuzzy", 1)
                        .addPet(PetType.HAMSTER, "Wuzzy", 1),
                new Person("John", "Doe")
        );

        this.parks = Vector.of(
                new Park("Jurassic")
                        .addAuthorizedPetType(PetType.BIRD)
                        .addAuthorizedPetType(PetType.SNAKE)
                        .addAuthorizedPetType(PetType.TURTLE),
                new Park("Central")
                        .addAuthorizedPetType(PetType.BIRD)
                        .addAuthorizedPetType(PetType.CAT)
                        .addAuthorizedPetType(PetType.DOG),
                new Park("Hippy")
                        .addAuthorizedPetType(PetType.BIRD)
                        .addAuthorizedPetType(PetType.CAT)
                        .addAuthorizedPetType(PetType.DOG)
                        .addAuthorizedPetType(PetType.TURTLE)
                        .addAuthorizedPetType(PetType.HAMSTER)
                        .addAuthorizedPetType(PetType.SNAKE)
        );
    }

    public Person getPersonNamed(String fullName) {
        return this.people.find(p -> p.named(fullName)).getOrElseThrow(
                () -> new IllegalArgumentException("Can't find person named: " + fullName));
    }
}