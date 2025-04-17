//package mbc.aiseat.config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//@Component
//@RequiredArgsConstructor
//public class DBInitializer {
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String user;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @PostConstruct
//    public void initAdminUser() {
//        try (Connection conn = DriverManager.getConnection(url, user, password)) {
//
//            String checkSql = "SELECT COUNT(*) FROM MEMBER WHERE EMAIL = ?";
//            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
//                checkStmt.setString(1, "admin@admin.com");
//                ResultSet rs = checkStmt.executeQuery();
//                if (rs.next() && rs.getInt(1) > 0) {
//                    System.out.println("✅ admin 계정이 이미 존재합니다.");
//                    return;
//                }
//            }
//
//            // 비밀번호 암호화
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            String encodedPassword = encoder.encode("1234");
//
//            String insertSql = "INSERT INTO MEMBER (EMAIL, PASSWORD, NAME, ROLE) VALUES (?, ?, ?, ?)";
//            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
//                insertStmt.setString(1, "admin@admin");
//                insertStmt.setString(2, encodedPassword); // 암호화된 비밀번호 저장
//                insertStmt.setString(3, "관리자");
//                insertStmt.setString(4, "ADMIN");
//                insertStmt.executeUpdate();
//                System.out.println("✅ admin 계정 생성 완료 (암호화된 비밀번호)");
//            }
//
//        } catch (Exception e) {
//            System.err.println("❌ admin 계정 생성 중 오류 발생");
//            e.printStackTrace();
//        }
//    }
//}
