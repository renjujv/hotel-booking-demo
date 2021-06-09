package com.testsigma.onboarding.auth;

import com.testsigma.onboarding.rest.ResourceConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
public class BasicAuthController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean getBasicAuth(){
        return new AuthenticationBean("You are authenticated.");
    }

    @GetMapping(path = "/basicauthdummy")
    public AuthenticationBean getBasicAuthDummy(){
        return new AuthenticationBean("Not really authenticated. Dummy Auth.");
    }

    @GetMapping(path = "/user")
    public String getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String user = authentication.getName();
        StringBuilder stringBuilder = new StringBuilder(user);
        stringBuilder.append("<br>");
        stringBuilder.append(authentication.getPrincipal().toString());
        return stringBuilder.toString();
    }
}
