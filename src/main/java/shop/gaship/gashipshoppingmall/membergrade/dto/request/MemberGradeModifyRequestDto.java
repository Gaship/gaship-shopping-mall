package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

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
@Data
public class MemberGradeModifyRequestDto {
    @NotNull
    private Integer no;
    @Size(min = 1, max = 10)
    @NotBlank
    private String name;
    @NotNull
    private Long accumulateAmount;
    @NotNull
    private Boolean isDefault;
}
