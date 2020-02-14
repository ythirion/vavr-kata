package com.bil.katas.vavr.account;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountService {

    private final UserService userService;
    private final TwitterService twitterService;
    private final BusinessLogger businessLogger;

    private Try<RegistrationContext> createContext(UUID userId){
        return Try.of(() -> userService.findById(userId)).map(RegistrationContext::new);
    }

    private Try<RegistrationContext> registerOnTwitter(RegistrationContext context){
        return Try.of(() -> twitterService.register(context.getEmail(), context.getName())).map(context::setAccountId);
    }

    private Try<RegistrationContext> authenticateOnTwitter(RegistrationContext context){
        return Try.of(() -> twitterService.authenticate(context.getEmail(), context.getPassword())).map(context::setToken);
    }

    private Try<RegistrationContext> tweet(RegistrationContext context){
        return Try.of(() -> twitterService.tweet(context.getToken(), "Hello I am " + context.getName())).map(context::setTweetUrl);
    }

    private void updateUser(RegistrationContext context){
        Try.run(() -> userService.updateTwitterAccountId(context.getId(), context.getAccountId()));
    }


    public Option<String> register(UUID id){
        return createContext(id)
                .flatMap(this::registerOnTwitter)
                .flatMap(this::authenticateOnTwitter)
                .flatMap(this::tweet)
                .andThen(this::updateUser)
                .andThen(context -> businessLogger.logSuccessRegister(context.getId()))
                .onFailure(exception -> businessLogger.logFailureRegister(id, exception))
                .map(RegistrationContext::getTweetUrl)
                .toOption();
    }
}
