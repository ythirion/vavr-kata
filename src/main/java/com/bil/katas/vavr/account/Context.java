package com.bil.katas.vavr.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.util.UUID;

@AllArgsConstructor
@With
@Getter
public class Context {
    private UUID id;
    private String email, name, password;
    private String accountId, token, tweetUrl;

    public Context(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
    }
}