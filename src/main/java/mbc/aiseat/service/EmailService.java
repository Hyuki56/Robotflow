package mbc.aiseat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 인증번호 생성 및 이메일 전송
    public void sendVerificationCode(String email) {
        String code = generateRandomCode();
        verificationCodes.put(email, code);  // 메모리에 저장

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원가입 인증번호");
        message.setText("인증번호: " + code);
        mailSender.send(message);
    }

    // 인증번호 검증
    public boolean verifyCode(String email, String inputCode) {
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(inputCode);
    }

    private String generateRandomCode() {
        int code = (int)(Math.random() * 900000) + 100000; // 6자리 숫자
        return String.valueOf(code);
    }
}
