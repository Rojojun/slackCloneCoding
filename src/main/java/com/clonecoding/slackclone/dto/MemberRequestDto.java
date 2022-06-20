package com.clonecoding.slackclone.dto;

import com.clonecoding.slackclone.model.Authority;
import com.clonecoding.slackclone.model.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String useremail;
    private String password;
    private String username;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .useremail(useremail)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .username(username)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(useremail, password);
    }
}