package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.dto.request
 * fileName       : MemberGradeModifyRequestDto
 * author         : Semi Kim
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        Semi Kim       최초 생성
 */
@Getter
@Setter
public class MemberGradeModifyRequestDto {
    @NotNull
    private Integer no;
    @Size(min = 1, max = 10)
    @NotBlank(message = "name 은 필수 입력값입니다.")
    private String name;
    @NotNull(message = "accumulateAmount 는 필수 입력값입니다.")
    private Long accumulateAmount;
    @NotNull(message = "isDefault 는 필수 입력값입니다.")
    private Boolean isDefault;
}
