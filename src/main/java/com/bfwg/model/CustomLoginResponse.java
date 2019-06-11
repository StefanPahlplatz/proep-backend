package com.bfwg.model;

public class CustomLoginResponse {

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

    public CustomLoginResponse(User user, UserTokenState token){
        this.loggedInUser = user;
        this.userToken = token;
    }
}
