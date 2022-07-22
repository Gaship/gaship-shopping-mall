package shop.gaship.gashipshoppingmall.gradehistory.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * 등급이력 응답 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class GradeHistoryResponseDto {
    private LocalDate at;
    private Long totalAmount;
    private String gradeName;
}
