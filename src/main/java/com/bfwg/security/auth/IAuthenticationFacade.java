package com.bfwg.security.auth;

import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface IAuthenticationFacade {
    Object getPrincipal();
}
