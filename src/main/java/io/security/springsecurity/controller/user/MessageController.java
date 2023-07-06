package io.security.springsecurity.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @GetMapping("/messages")
    public String mypage() throws Exception {

        return "user/messages";
    }

    @GetMapping("/api/messages")
    public String apiMessage() {
        return "messages ok";
    }
}