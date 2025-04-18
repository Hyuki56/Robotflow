package mbc.aiseat.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PasswordFindDto {

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotNull(message = "이메일 인증이 필요합니다.")
    @AssertTrue(message = "이메일 인증을 완료해주세요.")
    private Boolean emailVerified;

}
