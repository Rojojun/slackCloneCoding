package com.clonecoding.slackclone.service;

import com.clonecoding.slackclone.model.ChatMessage;
import com.clonecoding.slackclone.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {


    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final AuthService authService;

    // 메시지 전송
    public void sendChatMessage(ChatMessage chatMessage) {
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage("");
            chatMessage.setSender("");
            log.info("ENTER 데이터 날라갑니다~!");
            //*chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            //chatMessage.setSender("[알림]");*//*
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage("");
            chatMessage.setSender("");
            log.info("QUIT 데이터 날라갑니다~!");
            //*chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            //chatMessage.setSender("[알림]");
        }
        //topic은 chatroom 이다.
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }

    // 메시지 저장
    public void save(ChatMessage chatMessage) {
        ChatMessage message = new ChatMessage();
        message.setType(chatMessage.getType());
        message.setRoomId(chatMessage.getRoomId());
        message.setMember(authService.getMemberInfo());
        message.setMemberId(chatMessage.getMemberId());
        message.setSender(chatMessage.getSender());
        message.setMessage(chatMessage.getMessage());
        message.setCreatedAt(chatMessage.getCreatedAt());
        chatMessageRepository.save(message);
    }
}
