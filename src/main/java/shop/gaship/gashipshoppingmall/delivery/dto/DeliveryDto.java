package shop.gaship.gashipshoppingmall.delivery.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송 API 서버에 배송 요청, 운송장 번호 생성을 요청 하기 위해 필요한 정보를 담기 위한 DTO 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class DeliveryDto {

    @NotBlank(message = "수취인 이름은 필수 입니다.")
    private String receiverName;

    @NotBlank(message = "수취인 주소는 필수 입니다.")
    private String receiverAddress;

    @NotBlank(message = "수취인 상세주소는 필수 입니다.")
    private String receiverDetailAddress;

    @NotBlank(message = "수취인 휴대번호는 필수 입니다.")
    private String receiverPhone;

    @NotBlank(message = "해당 주문에 대한 주문 상품 번호는 필수 입니다.")
    private Integer orderProductNo;

    private String successHost;

    public DeliveryDto(String receiverName, String receiverAddress, String receiverDetailAddress,
        String receiverPhone, Integer orderProductNo) {
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverDetailAddress = receiverDetailAddress;
        this.receiverPhone = receiverPhone;
        this.orderProductNo = orderProductNo;
    }

    public void setSuccessHost(String successHost) {
        this.successHost = successHost;
    }

    @JsonGetter("orderNo")
    public Integer getOrderProductNo() {
        return orderProductNo;
    }

    public void decodingReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void decodingReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void decodingReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public void decodingReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
