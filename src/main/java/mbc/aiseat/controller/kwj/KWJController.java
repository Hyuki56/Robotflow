package mbc.aiseat.controller.kwj;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KWJController {

    @GetMapping("/KWJ")
    public String test(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String Email = userDetails.getUsername(); // 로그인한 사용자의 이메일
        model.addAttribute("Email", Email);
        return "KWJ/test";
    }

}
