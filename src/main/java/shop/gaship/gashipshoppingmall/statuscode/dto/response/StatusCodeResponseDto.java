package shop.gaship.gashipshoppingmall.statuscode.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 상태코드 요청에 대한 응답 data transfer obj.
 *
 * @author : 김세미
 * @since 1.0
 */
@Setter
@Getter
public class StatusCodeResponseDto {
    private String statusCodeName;
    private Integer priority;
}
