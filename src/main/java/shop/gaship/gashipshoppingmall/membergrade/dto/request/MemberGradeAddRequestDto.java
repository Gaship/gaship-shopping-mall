package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.request
 * fileName       : MemberGradeAddRequest
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@Getter
@Setter
public class MemberGradeAddRequestDto {
    @NotBlank(message = "name 은 필수 입력값입니다.")
    @Size(min = 1, max = 10)
    private String name;
    @NotNull(message = "accumulateAmount 은 필수 입력값입니다.")
    private Long accumulateAmount;
    @NotNull(message = "isDefault 는 필수 입력값입니다.")
    private Boolean isDefault;
}

