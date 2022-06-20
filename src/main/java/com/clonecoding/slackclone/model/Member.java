package com.clonecoding.slackclone.model;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String useremail;
    private String password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserRole authority;

    @Builder
    public Member(String useremail, String password, UserRole authority, String nickname) {
        this.useremail = useremail;
        this.password = password;
        this.authority = authority;
        this.nickname = nickname;
    }
}

