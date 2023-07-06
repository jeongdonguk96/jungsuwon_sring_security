package io.security.springsecurity.security.configs;

import io.security.springsecurity.security.common.FormAuthenticationDetailsSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Order(1)
@Configuration
@EnableWebSecurity // @EnableWebSecurity 어노테이션은 @Configuration의 빈 등록 기능을 포함한다.
@RequiredArgsConstructor
public class SecurityConfig  {

    // CustomAuthenticationProvider 인증 클래스를 별도로 빈등록 하지 않고 생성자 주입 방식으로 의존관계 주입 (다형성으로 인터페이스 DI)
    private final AuthenticationProvider customAuthenticationProvider;
    
    // 로그인 시 아이디/패스워드 외 데이터 저장
    private final FormAuthenticationDetailsSource formAuthenticationDetailsSource;
    
    // 로그인 성공 핸들러 (다형성으로 인터페이스 DI)
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // 로그인 실패 핸들러 (다형성으로 인터페이스 DI)
    private final AuthenticationFailureHandler customAuthenticationFailureHandler;

    // 권한 예외 핸들러 (다형성으로 인터페이스 DI)
    private final AccessDeniedHandler customAccessDeniedHandler;


    /**
     * 시큐리티 설정
     * @param http 시큐리티 설정을 담당하는 주체
     * @return http 객체
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers("/", "/join", "/loginView*").permitAll()
                .requestMatchers("/mypage").hasRole("USER")
                .requestMatchers("/messages").hasRole("MANAGER")
                .requestMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()

        .and()
                .formLogin()
                .loginPage("/loginView")
                .loginProcessingUrl("/login")
                .authenticationDetailsSource(formAuthenticationDetailsSource)
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()

        .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)

        ;

        return http.build();
    }


    /**
     * 인증 클래스 매니저
     * @param authenticationManagerBuilder 빌더
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);

        return authenticationManagerBuilder.getOrBuild();
    }


    /**
     * 비밀번호 암호화
     * @return 암호화된 비밀번호
     */
    @Bean
    @Lazy
    public static BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * css, js와 같은 정적 리소스들을 보안 검증에서 제외
     * @return 정적 리소스 무시 WebSecurityCustomizer 구현
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }


    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource();
    }


    /**
     * 프로그램 실행 시에만 메모리에 저장되는 UserDetails 객체 생성
     *
     * @return 생성한 UserDetails 객체
     */
//    @Bean
//    public UserDetailsManager users() {
//        String password = passwordEncoder().encode("1234");
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(password)
//                .roles("USER")
//                .build();
//
//        UserDetails manager = User.builder()
//                .username("manager")
//                .password(password)
//                .roles("MANAGER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(password)
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, manager, admin);
//    }

}
