package shop.gaship.gashipshoppingmall.config.dayLabor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 지역별물량 조회시 반환되는 정보를 가지는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class GetDayLaborResponseDto {
    private String local;

    private Integer maxLabor;
}
