package com.social.network.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedController {

    @GetMapping
    public @ResponseBody String feed(Authentication authentication) {
        return "Hello, " + (authentication == null? "Chel": authentication.getName()) + "!";
    }
}
