package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 1, max = 10, message = "name 의 길이는 최소 1 최대 10 입니다.")
    private String name;
    @NotNull(message = "accumulateAmount 은 필수 입력값입니다.")
    private Long accumulateAmount;
    @NotNull(message = "isDefault 는 필수 입력값입니다.")
    private Boolean isDefault;
}

