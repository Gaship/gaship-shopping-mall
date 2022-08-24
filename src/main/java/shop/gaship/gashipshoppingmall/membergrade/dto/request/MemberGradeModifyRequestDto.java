package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 회원등급 수정 요청 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class MemberGradeModifyRequestDto {
    @Length(min = 1, max = 10)
    @NotBlank(message = "name 은 필수 입력값입니다.")
    private String name;
    @NotNull(message = "accumulateAmount 는 필수 입력값입니다.")
    private Long accumulateAmount;
}
