package com.bil.katas.vavr.account;

import io.vavr.collection.Vector;
import java.util.NoSuchElementException;
import java.util.UUID;

public class UserService {
    private Vector<User> repository = Vector.of(
            User.builder()
                .id(UUID.fromString("376510ae-4e7e-11ea-b77f-2e728ce88125"))
                .email("bud.spencer@gmail.com")
                .name("Bud Spencer")
                .password("OJljaefp0')")
                .build(),
            User.builder()
                    .id(UUID.fromString("37651306-4e7e-11ea-b77f-2e728ce88125"))
                    .email("terrence.hill@gmail.com")
                    .name("Terrence Hill")
                    .password("àu__udsv09Ll")
                    .build());

    public User findById(UUID id) throws NoSuchElementException {
        return repository.filter(p -> p.getId() == id).single();
    }

    public void updateTwitterAccountId(UUID id, String twitterAccountId){

    }
}