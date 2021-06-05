package com.testsigma.onboarding.auth;

import com.testsigma.onboarding.rest.ResourceConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
public class BasicAuthController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean getBasicAuth(){
        return new AuthenticationBean("You are authenticated");
    }

}
