package com.social.network.controller;

import com.social.network.entity.User;
import com.social.network.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping
    public String feed(@AuthenticationPrincipal User user, Model model) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);
        return "feed/feed";
    }
}
