package shop.gaship.gashipshoppingmall.gradehistory.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 등급이력 등록 요청 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class GradeHistoryAddRequestDto {
    @NotNull(message = "등록하려는 등급 이력의 memberNo 는 필수값입니다.")
    @Min(value = 1, message = "memberNo 는 1보다 작을 수 없습니다.")
    private final Integer memberNo;

    @NotNull(message = "등록하려는 등급 이력의 totalAmount 는 필수값입니다.")
    @Min(value = 0L, message = "totalAmount 는 0보다 작을 수 없습니다.")
    private final Long totalAmount;

    @NotNull(message = "등록하려는 등급 이력의 gradeName 은 필수값입니다.")
    @Length(min = 1, max = 10, message = "gradeName 의 길이는 최소 1 이상 최대 10 이하 입니다.")
    private final String gradeName;
}
