package shop.gaship.gashipshoppingmall.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배송 API 서버에서 운송장 번호와 해당 주문 상품 번호를 전달할때 받기 위한 DTO 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class TrackingNoResponseDto {

    private final String trackingNo;

    @JsonProperty(value = "orderNo")
    private final String orderProductNo;

}
