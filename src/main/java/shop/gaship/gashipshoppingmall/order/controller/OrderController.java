package shop.gaship.gashipshoppingmall.order.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.aspact.anntation.MemberOnlyAuthority;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderDetailResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderListResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

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

    /**
     * GET Mapping
     * 주문번호와 멤버번호를 토대로 상세주문 정보를 얻기위한 GET 요청 입니다.
     *
     * @param orderNo  조회할 멤버의 상품번호.
     * @param memberNo 조회할 대상자.
     * @param pageable 페이징 객체.
     * @return ResponseEntity body 상세한 상품들의내용을 PageResponse 형태로 가집니다 응답은 200 ok.
     */
    @MemberOnlyAuthority
    @GetMapping("/{orderNo}/member/{memberNo}")
    public ResponseEntity<PageResponse<OrderDetailResponseDto>> orderDetails(
        @PathVariable("orderNo") Integer orderNo,
        @PathVariable("memberNo") Integer memberNo,
        Pageable pageable) {
        Page<OrderDetailResponseDto> content =
            orderService.findOrderDetails(orderNo, memberNo, pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(content));
    }

    /**
     * GET Mapping
     * 회원 번호를 통해 회원의 주문햇던 목록을 보여주기위한 GET 요청.
     *
     * @param memberNo 회원번호
     * @param pageable 페이징요청값
     * @return 주문했던 내용들이 전부기입됩니다.
     */
    @MemberOnlyAuthority
    @GetMapping("/member/{memberNo}")
    public ResponseEntity<PageResponse<OrderListResponseDto>> orderList(
        @PathVariable("memberNo") Integer memberNo,
        Pageable pageable) {

        Page<OrderListResponseDto> content =
            orderService.findAllOrders(memberNo, pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(content));
    }

    /**
     * GET mapping
     * 멤버 번호와 상태값을 통해 취소 혹은 교환된 주문을 보기위한 GET 요청.
     *
     * @param memberNo   조회하기 위한 회원번호
     * @param statusName 취소, 교환 등을 조회하기위한 파라미터입니다.
     * @param pageable   페이징 처리하기위한 값입니다.
     * @return ResponseEntity body 로 취소된 상품들에대한 값을 PageResponse 형태로 가집니다 응답은 200 ok.
     */
    @MemberOnlyAuthority
    @GetMapping(value = "/member/{memberNo}/status/{status}")
    public ResponseEntity<PageResponse<OrderCancelResponseDto>> orderCancelList(
        @PathVariable("memberNo") Integer memberNo,
        @PathVariable("status") String statusName,
        Pageable pageable) {

        Page<OrderCancelResponseDto> content =
            orderService.findCancelOrders(memberNo, statusName, pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(content));
    }
}
