package com.clonecoding.slackclone.controller;

import com.clonecoding.slackclone.dto.ChatMessageRequestDto;
import com.clonecoding.slackclone.jwt.TokenProvider;
import com.clonecoding.slackclone.model.ChatMessage;
import com.clonecoding.slackclone.model.Member;
import com.clonecoding.slackclone.repository.ChatMessageRepository;
import com.clonecoding.slackclone.service.AuthService;
import com.clonecoding.slackclone.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


@RestController
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService,
                                 AuthService authService,
                                 TokenProvider tokenProvider,
                                 ChatMessageRepository chatMessageRepository) {

        this.chatMessageService = chatMessageService;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.chatMessageRepository = chatMessageRepository;
    }

    // 채팅 메시지를 @MessageMapping 형태로 받는다
    // 웹소켓으로 publish 된 메시지를 받는 곳이다
    // 메시지 보내고 DB에 채팅 메시지 저장하기.
    @MessageMapping("/api/chat/message")
    public void message(@RequestBody ChatMessageRequestDto requestDto) {

        // 현재 로그인한 member 찾아옴
        Member member = authService.getMemberInfo();
        requestDto.setMemberId(member.getId());
        requestDto.setSender(member.getNickname());

        // 메시지 생성 시간 삽입
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateResult = sdf.format(date);
        requestDto.setCreatedAt(dateResult);

        // DTO 로 채팅 메시지 객체 생성
        ChatMessage chatMessage = new ChatMessage(requestDto, authService);

        // 웹소켓 통신으로 채팅방 토픽 구독자들에게 메시지 보내기
        chatMessageService.sendChatMessage(chatMessage);

        // MySql DB에 채팅 메시지 저장
        chatMessageService.save(chatMessage);
    }


    // 채팅방 내 메시지 전체 조회
    public Page<ChatMessage> getChatMessageByRoomId(String roomId, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 150);
        return chatMessageRepository.findByRoomId(roomId, pageable);
    }


}
