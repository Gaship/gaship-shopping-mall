package shop.gaship.gashipshoppingmall.order.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.aspact.anntation.MemberOnlyAuthority;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.service.OrderService;

/**
 * 주문을 실행하거나 취소, 환불, 교환등을 수행하는 주문 컨트롤러 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @MemberOnlyAuthority
    @PostMapping
    public ResponseEntity<OrderResponseDto> doOrder(@Valid @RequestBody
                                                        OrderRegisterRequestDto orderRequest) {
        Integer orderNo = orderService.insertOrder(orderRequest);
        return ResponseEntity.ok(orderService.findOrderForPayments(orderNo));
    }
}
