package com.bil.katas.vavr.account;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class RegistrationContext {
    private UUID id;
    private String email, name, password;
    private String accountId, token, tweetUrl;

    public RegistrationContext(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
    }
}
