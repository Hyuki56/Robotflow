package mbc.aiseat.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
@RequiredArgsConstructor
public class DBInitializer {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void initDefaultUsers() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            createUserIfNotExists(conn, "admin@admin", "1234", "관리자", "ADMIN");
            createUserIfNotExists(conn, "user@user", "1234", "사용자", "USER");
        } catch (Exception e) {
            System.err.println("❌ 계정 생성 중 오류 발생");
            e.printStackTrace();
        }
    }

    private void createUserIfNotExists(Connection conn, String email, String rawPassword, String name, String role) {
        try {
            String checkSql = "SELECT COUNT(*) FROM MEMBER WHERE EMAIL = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.printf("✅ %s 계정이 이미 존재합니다.%n", email);
                    return;
                }
            }

            String encodedPassword = encoder.encode(rawPassword);
            String insertSql = "INSERT INTO MEMBER (EMAIL, PASSWORD, NAME, ROLE) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, email);
                insertStmt.setString(2, encodedPassword);
                insertStmt.setString(3, name);
                insertStmt.setString(4, role);
                insertStmt.executeUpdate();
                System.out.printf("✅ %s 계정 생성 완료 (%s)%n", email, role);
            }
        } catch (Exception e) {
            System.err.printf("❌ %s 계정 생성 실패%n", email);
            e.printStackTrace();
        }
    }
}
