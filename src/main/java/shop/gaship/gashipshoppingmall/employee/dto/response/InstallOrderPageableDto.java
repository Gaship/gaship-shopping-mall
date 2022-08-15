package shop.gaship.gashipshoppingmall.employee.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 직원의 설치 주문 페이징 클래스입니다.(임시)
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class InstallOrderPageableDto {
    private List<InstallOrderResponseDto> installOrders;
    private Integer totalPage;
    private Integer size;
    private Boolean hasPrev;
    private Boolean hasNext;
}
