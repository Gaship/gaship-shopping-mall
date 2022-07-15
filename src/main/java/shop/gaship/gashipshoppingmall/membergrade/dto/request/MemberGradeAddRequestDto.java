package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

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
@Data
public class MemberGradeAddRequestDto {
    @NotBlank
    @Size(min = 1, max = 10)
    private String name;
    @NotNull
    private Long accumulateAmount;
    @NotNull
    private Boolean isDefault;
}
