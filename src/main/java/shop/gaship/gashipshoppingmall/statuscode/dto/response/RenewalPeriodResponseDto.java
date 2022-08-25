package shop.gaship.gashipshoppingmall.statuscode.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 갱신기간 조회 요청에 대한 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class RenewalPeriodResponseDto {
    private Integer statusCodeNo;
    private String explanation;
}
