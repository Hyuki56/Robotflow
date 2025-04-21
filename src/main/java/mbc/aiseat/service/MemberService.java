package mbc.aiseat.service;

import jakarta.validation.Valid;
import mbc.aiseat.constant.MemberStatus;
import mbc.aiseat.constant.Role;
import mbc.aiseat.dto.MemberEditDto;
import mbc.aiseat.dto.MemberFormDto;
import mbc.aiseat.entity.Member;
import mbc.aiseat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long saveMember(MemberFormDto memberFormDto) throws Exception {

        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
        member.setPhone(memberFormDto.getPhone());
        member.setRole(Role.USER);
        member.setStatus(MemberStatus.ACTIVE);

        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다");
        }
    }

    @Transactional(readOnly = true)
    public boolean isemailTaken(String email) {
        return memberRepository.existsByEmail(email); // true면 이미 사용 중
    }

    @Transactional(readOnly = true)
    public boolean isphoneTaken(String phone) {
        return memberRepository.existsByPhone(phone); // true면 이미 사용 중
    }

    public void updateMember(MemberEditDto memberEditDto) {

        Member member = memberRepository.findByEmail(memberEditDto.getEmail());

        // 회원 정보 업데이트
        member.setName(memberEditDto.getName());
        member.setPhone(memberEditDto.getPhone());

        memberRepository.save(member);

    }

    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            member.setStatus(MemberStatus.DELETED);
            memberRepository.save(member); // 변경 사항 저장
        }
    }

    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member;
    }

    @Transactional
    public void unlinkSocialAccount(String email, String provider) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다.");
        }

        // 특정 provider인 경우에만 초기화
        if (provider.equals(member.getProvider())) {
            member.setProvider(null);
            member.setProviderId(null);
            memberRepository.save(member);
        }
    }

    // 이름과 전화번호로 회원 이메일 찾기
    public String findEmailByNameAndPhone(String name, String phone) {
        Member member = memberRepository.findByNameAndPhone(name, phone);
        if (member != null) {
            return member.getEmail();
        }
        return null; // 회원이 없으면 null 반환
    }


    // ✅ 비밀번호 재설정 메서드
    public void updatePassword(String email, String newPassword) {
        log.info("비번재설정 서비스진입");
        log.info("email: " + email);
        Member member = memberRepository.findByEmail(email);
        log.info("member: " + member);

        if (member == null) {
            throw new IllegalArgumentException("해당 이메일의 회원을 찾을 수 없습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }



}
