package shop.gaship.gashipshoppingmall.member.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자가 수장 힐 수 있는 멤버의 닉네임, 상태입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberModifyByAdminDto {
    @NotNull(message = "memberNo 는 필수 입력값입니다.")
    @Min(value = 0, message = "정확한 회원 정보를 입력해주세요.")
    private Integer memberNo;

    @NotBlank(message = "회원 상태값을 입력해주세요.")
    private String status;

    @Builder
    public MemberModifyByAdminDto(Integer memberNo, String status) {
        this.memberNo = memberNo;
        this.status = status;
    }
}
