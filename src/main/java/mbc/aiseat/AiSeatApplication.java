package mbc.aiseat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 기능 활성화
@EnableJpaAuditing
public class AiSeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiSeatApplication.class, args);
    }

}
