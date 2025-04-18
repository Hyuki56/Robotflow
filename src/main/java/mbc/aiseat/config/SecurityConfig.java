package mbc.aiseat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("----------SecurityFilterChain 진입 체크----------");

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/frontlive", "/backlive","/info").hasRole("ADMIN")
                        .requestMatchers("/hyuk_index", "/KWJ","/frontend", "/edit").hasAnyRole("ADMIN","USER")
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(customLoginSuccessHandler) // 여기에 설정
                        .usernameParameter("email")
                        .failureUrl("/login/error")
                )
                .oauth2Login(oauth2->oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)  // 이 줄 중요!
                        )
                        .successHandler(customLoginSuccessHandler) // 소셜도 동일 처리
                        .failureUrl("/oauth-login-error.html") // 수정된 부분
                )

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                ) //403 처리
                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/get-user-name") // 이 엔드포인트는 누구나 접근 가능하도록 설정
                                .disable()
                );



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
