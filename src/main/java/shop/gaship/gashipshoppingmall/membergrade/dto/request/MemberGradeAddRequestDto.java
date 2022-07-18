package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 회원등급 등록 요청 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class MemberGradeAddRequestDto {
    @NotBlank(message = "name 은 필수 입력값입니다.")
    @Length(min = 1, max = 10, message = "name 의 길이는 최소 1 최대 10 입니다.")
    private String name;
    @NotNull(message = "accumulateAmount 은 필수 입력값입니다.")
    @Min(value = 0, message = "기준금액은 0보다 작을 수 없습니다.")
    private Long accumulateAmount;
    @NotNull(message = "isDefault 는 필수 입력값입니다.")
    private Boolean isDefault;
}

