package shop.gaship.gashipshoppingmall.delivery.adaptor.impl;

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

    @Override
    public void createTrackingNo(
        DeliveryDto deliveryDto) {
        WebClient webClient = WebClient.builder().baseUrl(deliveryUrl).build();

        webClient.post().uri(uriBuilder -> uriBuilder.path("/eggplant-delivery/tracking-no").build())
                 .bodyValue(deliveryDto)
                 .retrieve()
                 .toEntity(Void.class)
                 .block();
    }
}
