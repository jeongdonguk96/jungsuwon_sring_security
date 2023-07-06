package io.security.springsecurity.controller.user;

import io.security.springsecurity.domain.Member;
import io.security.springsecurity.dto.member.JoinDto;
import io.security.springsecurity.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberServiceImpl memberService;


    // 마이페이지 화면
    @GetMapping(value = "/mypage")
    public String myPage() throws Exception {

        return "user/mypage";
    }


    // 회원가입 화면
    @GetMapping("/join")
    public String joinView() {
        return "user/join";
    }


    // 회원가입
    @PostMapping("/join")
    public String join(JoinDto joinDto) {
        System.out.println("joinDto = " + joinDto);
        Member member = modelMapper.map(joinDto, Member.class);
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberService.join(member);

        return "redirect:/";
    }


    // 로그인 화면
    @GetMapping("/loginView")
    public String loginView(@RequestParam(value="error", required = false) String error,
                            @RequestParam(value="exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "user/login";
    }


    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }


    // 권한 예외
    @GetMapping("/denied")
    public String deniedView(@RequestParam(value = "exception", required = false) String exception,
                             Model model) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("username", member.getUsername());
        model.addAttribute("exception", exception);

        return "user/denied";
    }
}