package mbc.aiseat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {

    private String email; // 이메일 추가

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[\\]{};:'\"<>,.?/\\\\|`~]).{9,20}$",
            message = "비밀번호는 9자 이상 20자 이하이며, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String newPassword;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String confirmPassword;

    // 추가: 비밀번호 일치 여부 확인을 위한 헬퍼 메서드
    public boolean isPasswordMatched() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
