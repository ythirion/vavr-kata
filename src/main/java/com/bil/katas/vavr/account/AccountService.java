package com.bil.katas.vavr.account;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AccountService {

    private final UserService userService;
    private final TwitterService twitterService;
    private final BusinessLogger businessLogger;

    public Option<String> register(UUID id) {
        return retrieveUserDetails(id)
                .flatMap(this::registerAccountOnTwitter)
                .flatMap(this::authenticateOnTwitter)
                .flatMap(this::tweetHello)
                .andThen(this::updateTwitterAccount)
                .onSuccess(context -> businessLogger.logSuccessRegister(id))
                .onFailure(exception -> businessLogger.logFailureRegister(id, exception))
                .map(Context::getTweetUrl)
                .toOption();
    }

    private Try<Context> retrieveUserDetails(UUID id) {
        return Try.of(() -> this.userService.findById(id))
                .map(Context::new);
    }

    private Try<Context> registerAccountOnTwitter(Context context) {
        return Try.of(() -> this.twitterService.register(context.getEmail(), context.getName()))
                .map(context::withAccountId);
    }

    private Try<Context> authenticateOnTwitter(Context context) {
        return Try.of(() -> this.twitterService.authenticate(context.getEmail(), context.getPassword()))
                .map(context::withToken);
    }

    private Try<Context> tweetHello(Context context) {
        return Try.of(() -> this.twitterService.tweet(context.getToken(), "Hello I am " + context.getName()))
                .map(context::withTweetUrl);
    }

    private void updateTwitterAccount(Context context) {
        this.userService.updateTwitterAccountId(context.getId(), context.getAccountId());
    }
}