package com.bil.katas.vavr.account;

import java.util.UUID;

public interface BusinessLogger {
    void logSuccessRegister(UUID id);

    void logFailureRegister(UUID id, Throwable exception);
}
