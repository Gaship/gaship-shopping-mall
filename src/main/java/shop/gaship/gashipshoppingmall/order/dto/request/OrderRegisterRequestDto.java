package shop.gaship.gashipshoppingmall.order.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;

/**
 * 주문을 등록 요청을 위한 DTO입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class OrderRegisterRequestDto {
    @Min(value = 0, message = "주소를 등록한 뒤 결제 요청을 해주세요.")
    private Integer addressListNo;

    @Min(value = 0, message = "올바르지 못한 회원정보 요청입니다.")
    private Integer memberNo;

    @NotNull(message = "적어도 하나 이상의 상품을 주문하여야합니다.")
    @Size(min = 1, message = "적어도 하나 이상의 상품을 주문하여야합니다.")
    @Valid
    private List<OrderProductSpecificDto> orderProductSpecific;

    @NotBlank(message = "수령인 이름을 입력해주세요.")
    private String receiverName;

    @NotBlank(message = "수령인 전화번호를 입력해주세요.")
    @Pattern(regexp = "^010(\\d{7,8})$", message = "전화번호를 정확히 입력하십시오.")
    private String receiverPhoneNo;

    @Pattern(regexp = "(^$)|^010(\\d{7,8})$", message = "전화번호를 정확히 입력하십시오.")
    private String receiverSubPhoneNo;

    @Length(max = 255, message = "")
    private String deliveryRequest;
}
