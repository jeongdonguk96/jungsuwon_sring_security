package io.security.springsecurity.security.service;

import io.security.springsecurity.domain.Member;
import io.security.springsecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에서 아이디로 조회
        Member findMember = memberRepository.findByUsername(username);
        System.out.println("CustomUserDetailsService findMember = " + findMember);

        // 조회 결과가 없을 경우 예외를 던짐
        if (findMember == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        // 조회한 Member 객체가 가진 권한들을 List<GrantedAuthority>에 담음
        List<GrantedAuthority> collect = findMember.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRoleName())
                .collect(Collectors.toSet())
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // (UserDetails를 구현한) MemberContext 객체 반환
        return new MemberContext(findMember, collect);
    }
}
