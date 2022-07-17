package shop.gaship.gashipshoppingmall.statuscode.dummy;

import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;

/**
 * 상태코드 테스트시 사용되는 StatusCode dummy data
 *
 * @author : 김세미
 * @since 1.0
 */
public class StatusCodeDummy {
    private StatusCodeDummy(){}

    public static StatusCode dummy(){
        return StatusCode.builder()
                .statusCodeName(SalesStatus.SALE.getValue())
                .groupCodeName(SalesStatus.GROUP)
                .priority(1)
                .explanation("상품 판매 상태가 판매중이라는 상태 코드 입니다.")
                .build();
    }
}
