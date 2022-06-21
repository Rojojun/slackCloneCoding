package com.clonecoding.slackclone.conrtoller;

import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import com.clonecoding.slackclone.dto.ChatRoomResponseDto;
import com.clonecoding.slackclone.model.ChatRoom;
import com.clonecoding.slackclone.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController2 {


    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController2(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // 채팅방 생성
    @PostMapping("/rooms")
    public ChatRoomResponseDto createroom(@RequestBody ChatRoomRequestDto requestDto) {
       return  chatRoomService.createChatRoom(requestDto);

    }

    // 전체 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getChatRoom();
    }
    @DeleteMapping("/rooms")
    public Boolean deleteroom(@PathVariable Long roomid){
        return chatRoomService.deleteChatRoom(roomid);
    }










}
