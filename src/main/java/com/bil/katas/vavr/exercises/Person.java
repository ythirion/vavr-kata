package com.bil.katas.vavr.exercises;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private final String firstName;
    private final String lastName;
    private final Seq<Pet> pets;

    public Person(String firstName, String lastName) {
        this(firstName, lastName, Vector.empty());
    }

    public boolean named(String name) {
        return name.equals(firstName + " " + lastName);
    }

    public Map<PetType, Integer> getPetTypes() {
        return pets.groupBy(Pet::getType)
                .map(e -> Map.entry(e._1, e._2.size()))
                .collect(HashMap.collector());
    }

    public boolean hasPetType(PetType type) {
        return getPetTypes().containsKey(type);
    }

    public Person addPet(PetType petType, String name, int age) {
        return new Person(firstName, lastName, pets.append(new Pet(petType, name, age)));
    }

    public boolean isPetPerson() {
        return this.getNumberOfPets() >= 1;
    }

    public int getNumberOfPets() {
        return this.pets.size();
    }
}
