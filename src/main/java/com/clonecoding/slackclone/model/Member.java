package com.clonecoding.slackclone.model;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String useremail;
    private String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String useremail, String password, Authority authority, String username) {
        this.useremail = useremail;
        this.password = password;
        this.authority = authority;
        this.username = username;
    }
}

