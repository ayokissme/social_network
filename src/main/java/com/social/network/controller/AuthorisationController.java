package com.social.network.controller;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;
import com.social.network.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AuthorisationController {

    private final AuthServiceImpl authService;

    @Autowired
    public AuthorisationController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute User user) {
        return "authorisation/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute User user) {
        return "authorisation/registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute User user, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "authorisation/registration";
        }

        try {
            authService.registerUser(user);
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("message", e.getMessage());
            return "authorisation/registration";
        }
    }

    @GetMapping("/registration/confirm")
    @ResponseBody
    public String confirm(@RequestParam("token") String token) {
        try {
            return authService.confirmToken(token);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
