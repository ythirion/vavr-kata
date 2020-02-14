package com.bil.katas.vavr.exercises;

import com.bil.katas.vavr.account.*;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * In this real life example you will have to combine what you have learned on vavr
 * Refactor the AccountService, write cleaner code by using vavr
 * You will use at least :
 * <p/>
 * {@link Option#getOrElse(Object)}<br>
 * {@link Try#map(Function)}<br>
 * {@link Try#flatMap(Function)}<br>
 * {@link Try#onSuccess(Consumer)}<br>
 * {@link Try#onFailure(Consumer)}<br>
 */
public class RealLifeExample {
    private final UUID BUD_SPENCER = UUID.fromString("376510ae-4e7e-11ea-b77f-2e728ce88125");
    private final UUID UNKNOWN_USER = UUID.fromString("376510ae-4e7e-11ea-b77f-2e728ce88121");

    private AccountService accountService;

    @Before
    public void setup(){
        accountService = new AccountService(
                new UserService(),
                new TwitterService(),
                new BusinessLoggerImpl());
    }

    @Test
    public void register_BudSpencer_should_return_a_new_tweet_url(){
        String tweetUrl  = accountService.register(BUD_SPENCER).getOrElse("Registration failed");
        Assert.assertEquals("TweetUrl", tweetUrl);
    }

    @Test
    public void register_an_unknown_user_should_return_an_error_message(){
        String tweetUrl  = accountService.register(UNKNOWN_USER).getOrElse("Registration failed");
        Assert.assertEquals("Registration failed", tweetUrl);
    }
}
