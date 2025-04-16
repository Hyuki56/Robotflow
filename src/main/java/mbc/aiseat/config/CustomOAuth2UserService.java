package mbc.aiseat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mbc.aiseat.constant.MemberStatus;
import mbc.aiseat.constant.Role;
import mbc.aiseat.entity.Member;
import mbc.aiseat.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

//    public String getCurrentUserEmail() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        log.info("authentication: " + authentication);
//
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof CustomUserDetails userDetails) {
//                log.info("CustomUserDetails: " + userDetails);
//                return userDetails.getUsername(); // 여기서 꺼내면 됩니다
//            }
//        }
//
//        return null; // 로그인된 사용자가 없을 경우
//    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, naver, kakao
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String providerId = null;
        String email = null;

// 소셜별 응답 구조 다르므로 분기
        if ("google".equals(registrationId)) {
            providerId = (String) attributes.get("sub");
            email = (String) attributes.get("email");

        } else if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            providerId = (String) response.get("id");
            email = (String) response.get("email");

        } else if ("kakao".equals(registrationId)) {
            providerId = String.valueOf(attributes.get("id"));
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
        }

        log.info("소셜 로그인 요청: provider={}, providerId={}, email={}", registrationId, providerId, email);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Member member = null;

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            // 로그인된 상태에서 소셜 연동
            member = memberRepository.findByEmail(userDetails.getUsername());
            if (member != null) {
                // providerId가 다른 사용자에게 연결되어 있는지 확인
                Member existingLinkedMember = memberRepository.findByProviderId(providerId);
                if (existingLinkedMember != null && !existingLinkedMember.getEmail().equals(member.getEmail())) {
                    // 다른 계정에 이미 연결된 소셜 계정 → 연동 거부
                    log.warn("연동 실패: 이미 다른 계정에 연동된 소셜 계정입니다. providerId={}, email={}", providerId, existingLinkedMember.getEmail());
                    throw new OAuth2AuthenticationException("이미 다른 계정에 연동된 소셜 계정입니다.");
                }

                member.setProvider(registrationId);
                member.setProviderId(providerId);
                memberRepository.save(member);
            }

        } else {
            // 비로그인 상태에서 소셜 로그인 → providerId로 조회
            member = memberRepository.findByProviderId(providerId);

            if (member == null) {
                log.warn("소셜 로그인 실패: 등록된 providerId 없음 -> 로그인 거부");
                throw new OAuth2AuthenticationException("소셜 로그인 실패: 등록된 계정이 없습니다.");
            }
        }

        return new CustomUserDetails(member, attributes);
    }

}

