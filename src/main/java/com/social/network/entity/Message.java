package com.social.network.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.social.network.entity.enums.MessageStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue
    private String id;

    @Column(name = "chat_id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String chatId;

    @OneToMany
    @JoinColumn(nullable = false, name = "sender_id")
    private User sender;

    @OneToMany
    @JoinColumn(nullable = false, name = "recipient_id")
    private User recipient;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:ss")
    private LocalDateTime timestamp;

    @Enumerated(STRING)
    @Column(name = "status")
    private MessageStatus status;
}
