package io.security.springsecurity.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.springsecurity.domain.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        // 인증 성공 시 Authentication에 담긴 사용자 객체를 가져옴
        Member member = (Member) authentication.getPrincipal();

        // JSON으로 응답하기 위한 세팅
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Member 객체를 JSON으로 응답
        objectMapper.writeValue(response.getWriter(), member);
    }
}
