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
 * @author : 조재철
 * @since 1.0
 */
@RequestMapping
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping(value = "/api/delivery/tracking-no/{orderProductNo}")
    public ResponseEntity<Void> createTrackingNo(@PathVariable(value = "orderProductNo") Integer orderProductNo) {
        deliveryService.createTrackingNo(orderProductNo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/eggplant/tracking-no")
    public ResponseEntity<Void> addTrackingNo(@RequestBody TrackingNoResponseDto trackingNoResponseDto) {
        deliveryService.addTrackingNo(trackingNoResponseDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
