package mbc.aiseat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // WebSocket을 이용해서 Python 의 imshow를 받아서 처리
    // Controller 대체용

    @Override
    public void addViewControllers(ViewControllerRegistry registry){

        registry.addViewController("/frontlive").setViewName("frontlive");
        // localhost/frontlive 요청이 오면 frontlive.html 을 호출
        registry.addViewController("/backlive").setViewName("backlive");
    }
}
