package com.social.network.controller;

import com.social.network.entity.User;
import com.social.network.exception.UserAlreadyExistsException;
import com.social.network.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthorisationController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public String loginPage(@ModelAttribute User user) {
        return "authorisation/login";
    }

    @GetMapping("/registration")
    public String registerPage(@ModelAttribute User user) {
        return "authorisation/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "authorisation/registration";
        }

        try {
            userService.registerUser(user);
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("message", e.getMessage());
            return "authorisation/registration";
        }
    }
}
