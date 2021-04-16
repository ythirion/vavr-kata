package com.bil.katas.vavr.exercises;

import com.bil.katas.vavr.account.AccountService;
import com.bil.katas.vavr.account.BusinessLoggerImpl;
import com.bil.katas.vavr.account.TwitterService;
import com.bil.katas.vavr.account.UserService;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import static me.grison.vavr.matchers.VavrMatchers.isDefined;
import static me.grison.vavr.matchers.VavrMatchers.isEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

    @BeforeEach
    public void setup() {
        accountService = new AccountService(
                new UserService(),
                new TwitterService(),
                new BusinessLoggerImpl());
    }

    @Test
    public void register_BudSpencer_should_return_a_new_tweet_url() {
        Option<String> tweetUrl = accountService.register(BUD_SPENCER);
        assertThat(tweetUrl, isDefined(equalTo("TweetUrl")));
    }

    @Test
    public void register_an_unknown_user_should_return_an_error_message() {
        Option<String> tweetUrl = accountService.register(UNKNOWN_USER);
        assertThat(tweetUrl, isEmpty());
    }
}
