package com.social.network.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("not-found")
    public String notFound() {
        return "not found";
    }

    @GetMapping("forbidden")
    public String forbidden() {
        return "forbidden";
    }
}
