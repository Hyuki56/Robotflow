package mbc.aiseat.controller;

import mbc.aiseat.dto.MemberFormDto;
import mbc.aiseat.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LoginController {

    private final MemberService memberService;

    @GetMapping(value = "/signup") // 회원가입 페이지
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "signup";
    }

    @PostMapping(value = "/signup") // 회원가입 처리
    public String memberForm(@Valid MemberFormDto memberFormdto,
                             BindingResult bindingResult, Model model) throws Exception
    {
        if(bindingResult.hasErrors()) {
            return "signup";
        }

        try{
            memberService.saveMember(memberFormdto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login") // 로그인 페이지
    public String login() {
        return "/login"; // 로그인 페이지로 이동
    }
}
