package com.clonecoding.slackclone.controller;

import com.clonecoding.slackclone.dto.ChatRoomListDto;
import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import com.clonecoding.slackclone.dto.ChatRoomResponseDto;
import com.clonecoding.slackclone.model.ChatRoom;
import com.clonecoding.slackclone.model.Member;
import com.clonecoding.slackclone.service.AuthService;
import com.clonecoding.slackclone.service.ChatMessageService;
import com.clonecoding.slackclone.service.ChatRoomService;
import com.clonecoding.slackclone.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final AuthService authService;

    // 프론트엔드 테스트용
    @GetMapping("/channel")
    public String rooms() {
        return "/chat/room";
    }


    // 채팅방 생성
    @PostMapping("/channel")
    @ResponseBody
    public ChatRoomResponseDto createChatRoom(@RequestBody ChatRoomRequestDto requestDto) {
        log.info("requestDto = {}", requestDto);
//        requestDto.setMemberId(SecurityUtil.getCurrentMemberId());
        String useremail = SecurityUtil.getCurrentUsername();
        log.info("현재 유저의 이메일 = {}", useremail);

        ChatRoomResponseDto chatRoom = chatRoomService.createChatRoom(requestDto);

        return chatRoom;
    }


    // 전체 채팅방 목록 조회
    @GetMapping("/channel")
    @ResponseBody
    public List<ChatRoomListDto> getAllChatRooms() {
        Member member = authService.getMemberInfo();

        return chatRoomService.getAllChatRooms(member);
    }


    // 특정 채팅방 조회
    @GetMapping("/channel/{roomId}")
    @ResponseBody
    public ChatRoomResponseDto showChatRoom(@PathVariable Long roomId) {
        Member member = authService.getMemberInfo();
        return chatRoomService.showChatRoom(roomId, member);
    }
    //특정 채팅방 삭제

    @DeleteMapping("/channel")
    @ResponseBody
    public Boolean deleteChatRoom(@PathVariable Long roomId){
        Long memberid = new ChatRoom().getMember().getId();
        return chatRoomService.deleteChatRoom(roomId,memberid);
    }






}
