package io.security.springsecurity.security.provider;

import io.security.springsecurity.security.common.FormWebAuthenticationDetails;
import io.security.springsecurity.security.service.CustomUserDetailsService;
import io.security.springsecurity.security.service.MemberContext;
import io.security.springsecurity.security.token.AjaxAuthenticationToken;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AjaxAuthenticationProvider(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 폼 태그에서 사용자가 입력한 아이디와 패스워드
        String findUsername = authentication.getName();
        String password = (String) authentication.getCredentials();

        // DB에서 조회한 사용자 정보를 담은 UserDetails 객체
        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(findUsername);

        // 입력한 패스워드와 DB상 패스워드가 맞는지 검증 후 아니면 예외 던짐
        if (!passwordEncoder.matches(password, memberContext.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 로그인 시 아이디 패스워드 외 입력한 정보들도 검증
        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = formWebAuthenticationDetails.getSecretKey();

        if (!"secret".equals(secretKey)) {
            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
        }

        // UsernamePasswordAuthenticationToken은 Authentication 인터페이스의 구현체로, 시큐리티 컨텍스트에 등록됨
        return new AjaxAuthenticationToken(memberContext.getMember(), null, memberContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
