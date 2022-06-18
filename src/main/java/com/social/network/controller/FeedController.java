package com.social.network.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {

    @GetMapping
    public String feed() {
        return "/feed/feed";
    }
}
