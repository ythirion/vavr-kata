package com.bil.katas.vavr.exercises;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Park {
    private final String name;
    private final Seq<PetType> authorizedPetTypes;

    public Park(String name) {
        this(name, Vector.empty());
    }

    public Park addAuthorizedPetType(PetType petType) {
        return new Park(name, authorizedPetTypes.append(petType));
    }
}
