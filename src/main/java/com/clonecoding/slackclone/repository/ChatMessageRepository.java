package com.clonecoding.slackclone.repository;

import com.clonecoding.slackclone.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    Page<ChatMessage> findByRoomId(String roomId, Pageable pageable);

    List<ChatMessage> findByRoomId(Long channelId);
}
