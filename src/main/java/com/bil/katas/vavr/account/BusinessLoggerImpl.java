package com.bil.katas.vavr.account;

import java.util.UUID;

public class BusinessLoggerImpl implements BusinessLogger {

    @Override
    public void logSuccessRegister(UUID id) {
        System.out.println("We successfully registered the user : " + id);
    }

    @Override
    public void logFailureRegister(UUID id, Throwable exception) {
        System.out.println("We failed to register the user : " + id);
        System.out.println("Here is why : " + exception.getMessage());
        System.out.println("Stack trace : " + exception.getStackTrace().toString());
    }
}