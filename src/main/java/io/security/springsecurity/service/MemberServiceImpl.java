package io.security.springsecurity.service;

import io.security.springsecurity.domain.Member;
import io.security.springsecurity.domain.Role;
import io.security.springsecurity.dto.member.JoinDto;
import io.security.springsecurity.repository.MemberRepository;
import io.security.springsecurity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void join(Member member){

        Role role = roleRepository.findByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        member.setUserRoles(roles);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void modifyUser(JoinDto joinDto){

        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(joinDto, Member.class);

        if(joinDto.getRoles() != null){
            Set<Role> roles = new HashSet<>();
            joinDto.getRoles().forEach(role -> {
                Role r = roleRepository.findByRoleName(role);
                roles.add(r);
            });
            member.setUserRoles(roles);
        }
        member.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        memberRepository.save(member);

    }

    @Transactional
    public JoinDto getUser(Long id) {

        Member account = memberRepository.findById(id).orElse(new Member());
        ModelMapper modelMapper = new ModelMapper();
        JoinDto accountDto = modelMapper.map(account, JoinDto.class);

        List<String> roles = account.getUserRoles()
                .stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toList());

        accountDto.setRoles(roles);
        return accountDto;
    }

    @Transactional
    public List<Member> getUsers() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        memberRepository.deleteById(id);
    }
}
