package com.clonecoding.slackclone.model;

import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class ChatRoom extends Timestamped{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(nullable = false)
    private String roomName;


    public ChatRoom(ChatRoomRequestDto requestDto){
        this.roomName=requestDto.getRoomName();
    }
}
