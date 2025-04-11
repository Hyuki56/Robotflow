package mbc.aiseat.controller.hyuk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HyukController {

    @GetMapping("/hyuk_index")
    public String hyukIndex() {
        return "hyuk/hyuk_index";  // templates/hyuk/hyuk_index.html 경로를 의미
    }

    // 클래스 경로가 /hyuk/classRoom/class1.html 형식으로 올 거니까
    @GetMapping("/classRoom/class{num}.html")
    public String classPage(@PathVariable String num) {
        return "hyuk/classRoom/class" + num;
    }


}
