package mbc.aiseat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import mbc.aiseat.entity.Member;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberEditDto {

    private Long memberId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

    // 소셜 연동 여부
    private boolean googleLinked;
    private boolean naverLinked;
    private boolean kakaoLinked;

    // Member 객체를 기반으로 DTO 초기화
    public MemberEditDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.phone = member.getPhone();
    }
}