package com.clonecoding.slackclone.service;

import com.clonecoding.slackclone.dto.ChatRoomListDto;
import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import com.clonecoding.slackclone.dto.ChatRoomResponseDto;
import com.clonecoding.slackclone.model.ChatRoom;
import com.clonecoding.slackclone.model.Member;
import com.clonecoding.slackclone.repository.ChatRoomRepository;
import com.clonecoding.slackclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    //레디스 저장소 사용
    //key hashKey value 구조
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;

    private final ChatRoomRepository chatRoomRepository;
    private final AuthService authService;
    private final MemberRepository memberRepository;
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId 와 채팅룸 id 를 맵핑한 정보 저장


    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom chatRoom = new ChatRoom(requestDto, authService);
        chatRoomRepository.save(chatRoom);
        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom, authService.getMemberInfo());

        return chatRoomResponseDto;
    }

    // 전체 채팅방 조회
    public List<ChatRoomListDto> getAllChatRooms(Member member) {

        List<ChatRoomListDto> userChatRoom = new ArrayList<>();
        for (ChatRoom chatRoom : chatRoomRepository.findAllByOrderByCreatedAtDesc()) {
            if(chatRoom.getMemberList().contains(member)){
                userChatRoom.add(new ChatRoomListDto(chatRoom, chatRoom.getMemberList().get(0)));
            }
        }
        return userChatRoom;
    }
}
