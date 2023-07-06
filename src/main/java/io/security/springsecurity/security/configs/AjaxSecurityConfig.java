//package io.security.springsecurity.security.configs;
//
//import io.security.springsecurity.security.ajax.AjaxLoginProcessFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//@Order(0)
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class AjaxSecurityConfig {
//
//    private AuthenticationConfiguration authenticationConfiguration;
//
//    // AjaxAuthenticationProvider 인증 클래스를 별도로 빈등록 하지 않고 생성자 주입 방식으로 의존관계 주입 (다형성으로 인터페이스 DI)
//    private final AuthenticationProvider ajaxAuthenticationProvider;
//
//    // ajax 로그인 성공 핸들러 (다형성으로 인터페이스 DI)
//    private final AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
//
//    // ajax 로그인 실패 핸들러 (다형성으로 인터페이스 DI)
//    private final AuthenticationFailureHandler ajaxAuthenticationFailureHandler;
//
//    // ajax 인증 예외 핸들러 (다형성으로 인터페이스 DI)
//    private final AuthenticationEntryPoint ajaxLoginAuthenticationEntryPoint;
//
//    // ajax 권한 예외 핸들러 (다형성으로 인터페이스 DI)
//    private final AccessDeniedHandler ajaxAccessDeniedHandler;
//
//
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .authorizeHttpRequests()
//                .requestMatchers("/api/**")
//                .permitAll()
//                .requestMatchers("/api/messages").hasRole("MANAGER")
//                .anyRequest().authenticated()
//
//        .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(ajaxLoginAuthenticationEntryPoint)
//                .accessDeniedHandler(ajaxAccessDeniedHandler)
//
////        .and()
////                .addFilterBefore(ajaxLoginProcessFilter(), UsernamePasswordAuthenticationFilter.class)
//
//        ;
//
//        http.csrf().disable();
//
//        customConfigurerAjax(http);
//
//        return http.build();
//    }
//
//
//    private void customConfigurerAjax(HttpSecurity http) throws Exception {
//        http
//                .apply(new AjaxLoginConfigurer<>())
//                .successHandlerAjax(ajaxAuthenticationSuccessHandler)
//                .failureHandlerAjax(ajaxAuthenticationFailureHandler)
//                .setAuthenticationManager(authenticationManager(authenticationConfiguration))
//                .loginProcessingUrl("/api/login");
//    }
//
//
//    /**
//     * 인증 클래스 매니저
//     * @param authenticationConfiguration 빌더
//     * @return 인증 관리 매니저
//     * @throws Exception
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        ProviderManager providerManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
//        providerManager.getProviders().add(ajaxAuthenticationProvider);
//
//        return providerManager;
//    }
//
//
//    /**
//     * ajax 요청 인증 필터
//     * @return 필터
//     */
//    @Bean
//    public AjaxLoginProcessFilter ajaxLoginProcessFilter() throws Exception {
//        AjaxLoginProcessFilter ajaxLoginProcessFilter = new AjaxLoginProcessFilter();
//        ajaxLoginProcessFilter.setAuthenticationManager(authenticationManager());
//        ajaxLoginProcessFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
//        ajaxLoginProcessFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
//
//        return ajaxLoginProcessFilter;
//    }
//
//}
