package shop.gaship.gashipshoppingmall.gradehistory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 등급이력 등록 요청 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class GradeHistoryAddRequestDto {
    private final Integer memberNo;
    private final Long totalAmount;
    private final String gradeName;
}
