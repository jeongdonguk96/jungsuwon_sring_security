package io.security.springsecurity.dto.member;

import lombok.Data;

import java.util.List;

@Data
public class JoinDto {
    private String username;
    private String password;
    private String email;
    private String age;
    private List<String> roles;
}
