package io.security.springsecurity.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인 성공 핸들러
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache(); // 요청 정보와 관련된 객체
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // 리다이렉트 관련 객체
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response); // 요청 URL
        setDefaultTargetUrl("/");

        // 요청 URL이 있을 경우 해당 URL로 리다이렉트
        if (savedRequest != null) {
            String requestUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, requestUrl);
        }

        // 요청 URL이 없을 경우 초기 화면으로 리다이렉트
        else {
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }
    }
}
