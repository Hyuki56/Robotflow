package mbc.aiseat.dto;

import jakarta.validation.constraints.*;
import mbc.aiseat.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberFormDto {

    private Long memberId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=4, max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    @NotNull(message = "이메일 인증이 필요합니다.")
    @AssertTrue(message = "이메일 인증을 완료해주세요.")
    private Boolean emailVerified;


    // Member 객체를 기반으로 DTO 초기화
    public MemberFormDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.phone = member.getPhone();
    }
}
