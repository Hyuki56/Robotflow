package mbc.aiseat.service;

import mbc.aiseat.dto.MemberFormDto;
import mbc.aiseat.entity.Member;
import mbc.aiseat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long saveMember(MemberFormDto memberFormDto) throws Exception {
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByname(member.getName());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 아이디입니다");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        log.info("------loadUserByUsername 진입확인------");
        Member member = memberRepository.findByname(name);

        if (member == null) {
            throw new UsernameNotFoundException(name);
        }

        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
