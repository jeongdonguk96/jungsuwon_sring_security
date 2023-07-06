package io.security.springsecurity.service;

import io.security.springsecurity.domain.Member;
import io.security.springsecurity.dto.member.JoinDto;

import java.util.List;

public interface MemberService {

    void join(Member member);

    void modifyUser(JoinDto joinDto);

    List<Member> getUsers();

    JoinDto getUser(Long id);

    void deleteUser(Long idx);
}
