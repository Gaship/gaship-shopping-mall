package shop.gaship.gashipshoppingmall.employee.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 직원이 설치배송해야 할 주문의 목록을 조회 할 때 필요한 DTO 데이터 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@Builder
public class InstallOrderResponseDto {
    private Integer orderNo;
    private String address;
    private String addressDetail;
    private String zipCode;
    private String memberName;
    private String phoneNumber;
}
