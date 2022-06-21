package com.clonecoding.slackclone.controller;

import com.clonecoding.slackclone.dto.ChatRoomListDto;
import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import com.clonecoding.slackclone.dto.ChatRoomResponseDto;
import com.clonecoding.slackclone.model.Member;
import com.clonecoding.slackclone.service.AuthService;
import com.clonecoding.slackclone.service.ChatMessageService;
import com.clonecoding.slackclone.service.ChatRoomService;
import com.clonecoding.slackclone.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final AuthService authService;

    //채팅방 생성
    @PostMapping("/rooms")
    public ChatRoomResponseDto createChatRoom(@RequestBody ChatRoomRequestDto requestDto) {
        log.info("requestDto = {}", requestDto);
        requestDto.setMemberId(SecurityUtil.getCurrentMemberId());

        ChatRoomResponseDto chatRoom = chatRoomService.createChatRoom(requestDto);

        return chatRoom;
    }


    // 전체 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoomListDto> getAllChatRooms() {
        Member member = authService.getMemberInfo();

        return chatRoomService.getAllChatRooms(member);
    }




}
