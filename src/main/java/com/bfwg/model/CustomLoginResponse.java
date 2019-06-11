package com.bfwg.model;

public class CustomLoginResponse {

    private User loggedInUser;

    private UserTokenState userToken;

    public CustomLoginResponse(User user, UserTokenState token){
        this.loggedInUser = user;
        this.userToken = token;
    }
}
