package shop.gaship.gashipshoppingmall.orderproduct.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipshoppingmall.config.ServerConfig;

/**
 * 주문상품에서 타 서버에 요청을 하기위한 어댑터 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class OrderProductAdapter {
    private final ServerConfig serverConfig;

    /**
     * 쿠폰서버에 주문에 사용되었던 쿠폰들을 사용완료로 상태 변경 요청하는 어댑터 메서드입니다.
     *
     * @param couponNos 구매자가 주문에 사용했던 쿠폰들입니다.
     */
    public void useCouponRequest(List<Integer> couponNos) {
        WebClient.create(serverConfig.getCouponUrl())
            .patch()
            .uri("/api/coupons/coupon-generations-issues/used")
            .bodyValue(couponNos)
            .retrieve()
            .toEntity(void.class)
            .block();
    }

    /**
     * 주문 취소 혹은 주문 반품에 대한 요청시 쿠폰을 사용가능하도록 다시 되돌리기 위한 요청을 수행하는 메서드입니다.
     *
     * @param couponNos 사용했던 쿠폰 번호들입니다.
     */
    public void useCancelCouponRequest(List<Integer> couponNos) {
        WebClient.create(serverConfig.getCouponUrl())
            .patch()
            .uri("/api/coupons/coupon-generations-issues/used-to-cancle")
            .bodyValue(couponNos)
            .retrieve()
            .toEntity(void.class)
            .block();
    }
}
