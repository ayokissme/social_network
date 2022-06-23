package com.social.network.rest;

import com.social.network.entity.MessageChat;
import com.social.network.entity.User;
import com.social.network.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageApi {

    private final MessageRepo messageRepo;

    @Autowired
    public MessageApi(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<MessageChat> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public MessageChat getOne(@PathVariable("id") MessageChat msg) {
        return msg;
    }

    @PostMapping
    public MessageChat create(@RequestBody MessageChat message, @AuthenticationPrincipal User sender) {
        message.setSender(sender);
        message.setTimestamp(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public MessageChat update(@PathVariable("id") MessageChat msgFromDb, @RequestBody MessageChat messageChat) {
        BeanUtils.copyProperties(messageChat, msgFromDb, "id", "sender");
        return messageRepo.save(msgFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        messageRepo.deleteById(id);
    }
}
