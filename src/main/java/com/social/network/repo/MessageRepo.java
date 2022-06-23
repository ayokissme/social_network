package com.social.network.repo;

import com.social.network.entity.MessageChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<MessageChat, Long> {

}
