package shop.gaship.gashipshoppingmall.member.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 관리자가 수장 힐 수 있는 멤버의 닉네임, 상태입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@Builder
public class MemberModifyByAdminDto {
    @NotNull
    @Min(value = 0, message = "정확한 회원 정보를 입력해주세요.")
    private Integer memberNo;
    @NotBlank
    @Length(max = 20, message = "닉네임이 너무 깁니다.")
    private String nickname;
    @NotBlank(message = "회원 상태값을 입력해주세요.")
    private String status;
}
