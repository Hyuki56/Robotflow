package mbc.aiseat.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberFindDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    @NotNull(message = "전화번호 인증이 필요합니다.")
    @AssertTrue(message = "전화번호 인증을 완료해주세요.")
    private Boolean phoneVerified;

}
