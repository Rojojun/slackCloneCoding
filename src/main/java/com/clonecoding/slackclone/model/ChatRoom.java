package com.clonecoding.slackclone.model;

import com.clonecoding.slackclone.dto.ChatRoomRequestDto;
import com.clonecoding.slackclone.service.AuthService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(nullable = false)
    private String channel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id_joined")
    private List<Member> memberList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="User_id")
    private Member member;

    public ChatRoom(ChatRoomRequestDto requestDto, AuthService authService){
        this.channel = requestDto.getChannel();
        this.memberList.add(authService.getMemberInfo());
    }


}
