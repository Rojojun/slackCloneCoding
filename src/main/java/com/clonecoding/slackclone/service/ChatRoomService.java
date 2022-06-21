package com.clonecoding.slackclone.service;


import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import com.clonecoding.slackclone.dto.ChatRoomResponseDto;
import com.clonecoding.slackclone.model.ChatRoom;
import com.clonecoding.slackclone.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatRoomService {



    private final ChatRoomRepository chatRoomRepository;





    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom chatRoom = new ChatRoom(requestDto);
        chatRoomRepository.save(chatRoom);
        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom);
        return chatRoomResponseDto;
    }
    public List<ChatRoom> getChatRoom(){
        return chatRoomRepository.findAllByOrderByCreatedAtDesc();

    }
    public Boolean deleteChatRoom(@PathVariable Long roomid){
        chatRoomRepository.deleteById(roomid);
        return true;
    }





}
