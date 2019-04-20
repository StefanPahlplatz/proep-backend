package com.bfwg.service;

import com.bfwg.model.User;
import com.bfwg.model.Vehicle;

import javax.mail.MessagingException;

public interface EmailService {

    void completeRegistration(User user) throws MessagingException;

    void completeRegistrationOwner(User user, Vehicle vehicle) throws MessagingException;

}
