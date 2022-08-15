package shop.gaship.gashipshoppingmall.employee.dto.request;

import javax.validation.constraints.Min;
import lombok.Getter;

/**
 * 배송직원이 고객의 주문 상품 중 시공 배송 주문을 수락할 때 사용되는 DTO 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class InstallOrderRequestDto {
    @Min(value = 1, message = "직원고유번호를 확인해주세요.")
    private Integer employeeNo;
    @Min(value = 1, message = "주문고유번호를 확인해주세요.")
    private Integer orderNo;
}
