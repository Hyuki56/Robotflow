package mbc.aiseat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorTestController {

    @GetMapping("/error-test")
    public String triggerError() {
        // 일부러 NullPointerException 발생
        String str = null;
        return str.toString(); // 여기가 예외 발생 지점
    }
}
