package shop.gaship.gashipshoppingmall.delivery.adaptor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipshoppingmall.delivery.adaptor.DeliveryAdaptor;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;

/**
 * 배송 API 서버에 요청을 하기 위한 어댑터 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "eggplant-server")
public class DeliveryAdaptorImpl implements DeliveryAdaptor {

    private String deliveryUrl;

    public String getDeliveryUrl() {
        return deliveryUrl;
    }

    public void setDeliveryUrl(String deliveryUrl) {
        this.deliveryUrl = deliveryUrl;
    }

    /**
     * 배송 서버에 배송 요청 (운송장 번호) 요청을 하는 메서드 입니다.
     *
     * @param deliveryDto 배송 요청 (운송장 번호 요청) 시에 필요한 정보가 저장된 dto 객체 입니다.
     */
    @Override
    public void createTrackingNo(DeliveryDto deliveryDto) {
        WebClient webClient = WebClient.builder().baseUrl(deliveryUrl).build();

        log.warn("운송장 번호 요청 로그 테스트 {}", deliveryDto.getSuccessHost());

        webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/eggplant-delivery/tracking-no").build())
            .bodyValue(deliveryDto)
            .retrieve()
            .toEntity(Void.class)
            .block();

        log.error("eggplant delivery에 요청을 잘 하는지 테스트 로그");
    }
}
