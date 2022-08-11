package shop.gaship.gashipshoppingmall.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
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
