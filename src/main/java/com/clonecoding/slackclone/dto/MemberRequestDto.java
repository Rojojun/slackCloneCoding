package com.clonecoding.slackclone.dto;

import com.clonecoding.slackclone.model.UserRole;
import com.clonecoding.slackclone.model.Member;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String useremail;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .useremail(useremail)
                .password(passwordEncoder.encode(password))
                .authority(UserRole.USER)
                .nickname(nickname)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(useremail, password);
    }
}