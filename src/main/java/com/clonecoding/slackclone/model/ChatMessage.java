package com.clonecoding.slackclone.model;

import com.clonecoding.slackclone.dto.ChatMessageRequestDto;
import com.clonecoding.slackclone.service.AuthService;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK, QUIT
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String roomId;

    @Column
    private Long memberId;

    @Column
    private String sender;
    // 내용
    @Column
    private String message;

    @Column
    private String createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id_joined")
    private Member member;

    @Builder
    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto, AuthService authService) {
        this.type = chatMessageRequestDto.getType();
        this.roomId = chatMessageRequestDto.getRoomId();
//        this.user = authService.getMyInfo(chatMessageRequestDto.getMemberId());
        this.member = authService.getMemberInfo();
        this.memberId = chatMessageRequestDto.getMemberId();
        this.sender = chatMessageRequestDto.getSender();
        this.message = chatMessageRequestDto.getMessage();
        this.createdAt = chatMessageRequestDto.getCreatedAt();
    }


}
