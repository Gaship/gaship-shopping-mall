package shop.gaship.gashipshoppingmall.orderproduct.adapter;

import java.util.List;
import java.util.Map;
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
            .post()
            .uri("") // FIXME : 쿠폰의 서버가 더 개발이 되면 진행
            .bodyValue(Map.of("couponNos", couponNos))
            .retrieve()
            .toEntity(void.class)
            .block();

    }
}
