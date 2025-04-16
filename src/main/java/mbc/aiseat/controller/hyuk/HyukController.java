package mbc.aiseat.controller.hyuk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HyukController {

    @GetMapping("/hyuk_index")
    public String hyukIndex() {
        return "hyuk/hyuk_index";  /// templates/hyuk/hyuk_index.html 경로를 의미
    }

    @GetMapping("/classRoom/class{num}.html")
    public String classPage(@PathVariable String num) {
        return "hyuk/classRoom/class" + num;  // 경로: templates/hyuk/classRoom/class1.html
    }

    @GetMapping("/hyuk_reservations")
    public String showReservations() {
        return "hyuk/hyuk_reservations";  // 예약 페이지 보여주기
    }
}
