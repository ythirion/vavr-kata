package com.bil.katas.vavr.account;

public class TwitterService {
    public String register(String email, String name){
        return "TwitterAccountId";
    }

    public String authenticate(String email, String password){
        return "ATwitterToken";
    }

    public String tweet(String token, String message){
        return "TweetUrl";
    }
}