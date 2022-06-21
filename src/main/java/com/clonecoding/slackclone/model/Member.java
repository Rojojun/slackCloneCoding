package com.clonecoding.slackclone.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String useremail;
    @NotNull
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{6,20}$", message = "비밀번호는 숫자와 문자를 1개 이상 포함해야하며 최소 6자에서 최대 20자까지 허용합니다.")
    private String password;
    @NotNull
    @NotBlank(message = "이름 입력은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣/]{2,10}$", message = "2~10자리의 한글, 알파벳만 사용 가능합니다.")
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

