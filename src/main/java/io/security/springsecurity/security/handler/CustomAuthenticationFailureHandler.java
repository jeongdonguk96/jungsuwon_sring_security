package io.security.springsecurity.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인 실패 핸들러
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";

        if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid_Secret_Key";
        }

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid_Password";
        }

        setDefaultFailureUrl("/loginView?error=true&exception="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
