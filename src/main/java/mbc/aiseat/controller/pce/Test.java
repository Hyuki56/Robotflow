package mbc.aiseat.controller.pce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {

    @GetMapping("/frontend")
    public String frontend() {
        return "PCE/frontend";
    }   // localhost 접속시 index.html 반환
}
