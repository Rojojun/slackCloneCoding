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

    // 유저가 입장한 채팅방 ID 와 유저 세션 ID 맵핑 정보 저장
    // Enter라는 곳에 sessionId와 roomId를 맵핑시켜놓음
    public void setUserEnterInfo(String sessionId, String roomId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방 ID 삭제
    //한 유저는 하나의 룸 아이디에만 맵핑되어있다!
    // 실시간으로 보는 방은 하나이기 떄문이다.
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }


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

    // 특정 채팅방 조회
    public ChatRoomResponseDto showChatRoom(Long roomId, Member member) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("찾는 채팅방이 존재하지 않습니다.")
        );

        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom, member);
        return chatRoomResponseDto;
    }
}
