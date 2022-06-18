package com.social.network.entity;

import java.util.Date;

public class Message {
    private String id;
    private String chatId;
    private User sender;
    private User recipient;
    private String content;
    private Date timestamp;
}
