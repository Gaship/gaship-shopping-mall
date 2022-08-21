package shop.gaship.gashipshoppingmall.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class DeliveryInfoStatusResponseDto {

    @NotBlank(message = "운송장 번호는 필수 사항 입니다.")
    @JsonProperty(value = "orderNo")
    private String orderProductNo;

    @NotNull(message = "배송 상태는 필수 사항 입니다.")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;

}
