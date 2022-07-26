package shop.gaship.gashipshoppingmall.gradehistory.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 멤버 별 등급 이력 다건 조회 요청 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class GradeHistoryFindRequestDto {
    @NotNull(message = "memberNo 는 필수값 입니다.")
    private final Integer memberNo;

    @NotNull(message = "page 는 필수 값입니다.")
    private final Integer page;

    @NotNull(message = "size 는 필수 값입니다.")
    private final Integer size;
}
