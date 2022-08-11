package shop.gaship.gashipshoppingmall.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipshoppingmall.delivery.service.DeliveryService;

/**
 * 배송 관련 요청을 받고 요청에 대한 응답을 하는 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@RequestMapping
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 요청을 통해 운송장 번호를 요청하는 메서드 입니다.
     *
     * @param orderProductNo 배송 요청시 전달하는 주문 상품 번호 입니다.
     * @return 운송장 번호 생성 요청을 잘 받았다는 응답을 반환합니다.
     */
    @GetMapping(value = "/api/delivery/tracking-no/{orderProductNo}")
    public ResponseEntity<Void> createTrackingNo(@PathVariable(value = "orderProductNo") Integer orderProductNo) {
        deliveryService.createTrackingNo(orderProductNo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 배송 API 서버에서 전달한 운송장 번호를 받는 메서드 입니다.
     *
     * @param trackingNoResponseDto 운송장 번호와 해당 주문 상품 번호를 담은 dto 객체 입니다.
     * @return 운송장 번호를 잘 받았다는 응답을 반환 합니다.
     */
    @PostMapping(value = "/eggplant/tracking-no")
    public ResponseEntity<Void> addTrackingNo(@RequestBody TrackingNoResponseDto trackingNoResponseDto) {
        deliveryService.addTrackingNo(trackingNoResponseDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
