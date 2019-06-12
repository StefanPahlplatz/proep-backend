package com.bfwg.model;


import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;


public class CustomUserLoginResponse implements Serializable {

    private User loggedInUser;

    private UserTokenState userToken;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public UserTokenState getUserToken() {
        return userToken;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setUserToken(UserTokenState userToken) {
        this.userToken = userToken;
    }

    public CustomUserLoginResponse(User user, UserTokenState state){
        this.loggedInUser = user;
        this.userToken = state;
    }


}
