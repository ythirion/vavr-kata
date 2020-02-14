package com.bil.katas.vavr.account;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {
    private final UUID id;
    private final String email;
    private final String name;
    private final String password;
}
