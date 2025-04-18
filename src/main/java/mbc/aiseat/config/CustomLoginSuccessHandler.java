package mbc.aiseat.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        log.info("Saved request: " + savedRequest);

        // 사용자 권한 목록 확인
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("Redirecting to " + targetUrl);

            // live 페이지 접근 요청이면, 권한에 따라 처리
            if (targetUrl.contains("frontlive") || targetUrl.contains("backlive")) {
                if (isAdmin) {
                    log.info("authentication Principal " + authentication.getPrincipal().toString());
                    if (authentication.getPrincipal() instanceof OAuth2User) {
                        // 소셜 로그인: 팝업 페이지로 이동
                        String redirectUrl = savedRequest.getRedirectUrl();
                        log.info("OAuth 로그인 요청한 원래 URL: " + redirectUrl);
                        request.getSession().setAttribute("redirectAfterOAuth", redirectUrl);
                        response.sendRedirect("/oauth-success.html");
                    } else {
                        // ✅ 일반 로그인: 요청한 원래 URL로 바로 redirect
                        log.info("Admin 권한 확인됨. 원래 요청 URL로 이동.");
                        String redirectUrl = savedRequest.getRedirectUrl();
                        log.info("일반 로그인 요청한 원래 URL: " + redirectUrl);
                        response.sendRedirect(redirectUrl);
                    }
                } else {
                    log.warn("Admin 권한 없음. 403 페이지로 리디렉션.");
                    response.sendRedirect("/error/403.html");
                }
            } else {
                if (authentication.getPrincipal() instanceof OAuth2User) {
                    // 소셜 로그인일 경우: 팝업 처리
                    log.info("OAuth 로그인 (권한 없음 URL). 원래 URL: " + targetUrl);
                    request.getSession().setAttribute("redirectAfterOAuth", targetUrl);
                    response.sendRedirect("/oauth-success.html");
                } else {
                    // 일반 로그인일 경우: 요청한 URL로 이동
                    log.info("일반 로그인. 권한 체크 불필요. 원래 URL로 이동: " + targetUrl);
                    response.sendRedirect(targetUrl);
                }
            }
        }
        else {
            if (authentication.getPrincipal() instanceof OAuth2User){
                response.sendRedirect("/oauth-success.html");
            }
            else {
                response.sendRedirect("/");
            }
        }
    }

}

