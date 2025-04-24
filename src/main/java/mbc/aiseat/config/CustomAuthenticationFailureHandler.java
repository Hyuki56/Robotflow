package mbc.aiseat.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.error("소셜 로그인 실패: {}", exception.getMessage());

        String errorCode = "unknown";

        if (exception instanceof OAuth2AuthenticationException oAuth2Ex) {
            OAuth2Error error = oAuth2Ex.getError();
            errorCode = error.getErrorCode();
        }

        // 에러 메시지를 쿼리 파라미터로 전달
        response.sendRedirect("/oauth-login-error.html?error=" + errorCode);
    }
}
