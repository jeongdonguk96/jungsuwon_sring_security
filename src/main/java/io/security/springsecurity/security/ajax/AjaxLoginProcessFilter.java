//package io.security.springsecurity.security.ajax;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.security.springsecurity.dto.member.LoginDto;
//import io.security.springsecurity.security.token.AjaxAuthenticationToken;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//
//// ajax 인증 요청 필터
//@Component
//public class AjaxLoginProcessFilter extends AbstractAuthenticationProcessingFilter {
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    public AjaxLoginProcessFilter() {
//        super(new AntPathRequestMatcher("/api/login", HttpMethod.POST.name()));
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//
//        if (isAjax(request)) {
//            throw new IllegalStateException("Authentication is not supported");
//        }
//
//        // json으로 온 데이터를 JoinDto에 맞춰 파싱
//        LoginDto loginDto = objectMapper.readValue(request.getReader(), LoginDto.class);
//
//        // 아이디나 패스워드가 비어있을 경우 예외 던짐
//        if (StringUtils.isEmpty(loginDto.getUsername()) || StringUtils.isEmpty(loginDto.getPassword())) {
//            throw new IllegalArgumentException("Username or Password is empty");
//        }
//
//        // 요청온 아이디와 패스워드로 ajax 인증 토큰
//        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
//
//        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
//
//    }
//
//    // 요청이 ajax인지 확인
//    private boolean isAjax(HttpServletRequest request) {
//        return "XMLHttpRequest".equals(request.getHeader("X-Requested-with"));
//    }
//}
