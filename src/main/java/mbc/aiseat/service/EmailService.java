package mbc.aiseat.service;

import mbc.aiseat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @Autowired
    public EmailService(JavaMailSender mailSender, MemberRepository memberRepository) {
        this.mailSender = mailSender;
        this.memberRepository = memberRepository;
    }

    public boolean sendVerificationCode(String email, String purpose) {
        if (!memberRepository.existsByEmail(email) & purpose.equals("password")) {
            return false;
        }

        String code = generateRandomCode();
        verificationCodes.put(email, code);  // 메모리에 저장

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);

        // 용도에 따라 제목/본문 다르게 설정
        if ("password".equalsIgnoreCase(purpose)) {
            message.setSubject("비밀번호 찾기 인증번호");
            message.setText("비밀번호 찾기를 위한 인증번호는 다음과 같습니다: " + code);
        } else {
            message.setSubject("회원가입 인증번호");
            message.setText("회원가입을 위한 인증번호는 다음과 같습니다: " + code);
        }

        mailSender.send(message);
        return true;
    }


    public boolean verifyCode(String email, String inputCode) {
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(inputCode);
    }

    private String generateRandomCode() {
        int code = (int)(Math.random() * 900000) + 100000; // 6자리 숫자
        return String.valueOf(code);
    }
}
