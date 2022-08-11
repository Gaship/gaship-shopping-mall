package shop.gaship.gashipshoppingmall.delivery.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배송 API 서버에 배송 요청, 운송장 번호 생성을 요청 하기 위해 필요한 정보를 담기 위한 DTO 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class DeliveryDto {

    @NotBlank(message = "수취인 이름은 필수 입니다.")
    private String receiverName;

    @NotBlank(message = "수취인 주소는 필수 입니다.")
    private String receiverAddress;

    @NotBlank(message = "수취인 휴대번호는 필수 입니다.")
    private String receiverPhone;

    @NotBlank(message = "해당 주문에 대한 주문 번호는 필수 입니다.")
    private Integer orderProductNo;

    public void decodingReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void decodingReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void decodingReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
