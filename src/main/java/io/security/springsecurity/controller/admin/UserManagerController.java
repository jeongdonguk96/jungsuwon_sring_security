package io.security.springsecurity.controller.admin;


import io.security.springsecurity.domain.Member;
import io.security.springsecurity.domain.Role;
import io.security.springsecurity.dto.member.JoinDto;
import io.security.springsecurity.service.MemberService;
import io.security.springsecurity.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserManagerController {
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private RoleService roleService;

	@GetMapping(value="/admin/users")
	public String getUsers(Model model) throws Exception {
		List<Member> members = memberService.getUsers();
		model.addAttribute("users", members);
		return "admin/user/list";
	}

	@PostMapping(value="/admin/users")
	public String createUser(JoinDto joinDto) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		Member member = modelMapper.map(joinDto, Member.class);
		memberService.join(member);

		return "redirect:/admin/users";
	}

	@GetMapping(value = "/admin/users/{id}")
	public String getUser(@PathVariable(value = "id") Long id, Model model) {
		JoinDto joinDto = memberService.getUser(id);
		List<Role> roleList = roleService.getRoles();

		model.addAttribute("act", (id > 0)? "modify":"add");
		model.addAttribute("user", joinDto);
		model.addAttribute("roleList", roleList);

		return "admin/user/detail";
	}

	@GetMapping(value = "/admin/users/delete/{id}")
	public String removeUser(@PathVariable(value = "id") Long id, Model model) {
		memberService.deleteUser(id);
		return "redirect:/admin/users";
	}
}
