package com.clonecoding.slackclone.dto;

import com.clonecoding.slackclone.model.ChatRoom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@ToString
@Getter
@Setter
public class ChatRoomResponseDto {

    private Long id;
    private String chatRoomName;


    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.chatRoomName = chatRoom.getRoomName();
    }
}