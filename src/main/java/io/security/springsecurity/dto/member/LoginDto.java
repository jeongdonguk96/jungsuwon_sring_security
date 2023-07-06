package io.security.springsecurity.dto.member;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
