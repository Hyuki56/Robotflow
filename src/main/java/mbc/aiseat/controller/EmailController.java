package mbc.aiseat.controller;

import mbc.aiseat.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // 인증번호 전송
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        emailService.sendVerificationCode(email);
        return ResponseEntity.ok("인증번호 전송 완료");
    }

    // 인증번호 검증
    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        boolean isValid = emailService.verifyCode(email, code);
        return ResponseEntity.ok(isValid);
    }
}
