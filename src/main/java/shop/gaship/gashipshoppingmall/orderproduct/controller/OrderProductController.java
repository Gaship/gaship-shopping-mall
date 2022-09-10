package shop.gaship.gashipshoppingmall.orderproduct.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.aspact.annotation.MemberValid;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * @author : 유호철
 * @since 1.0
 */

@RestController
@RequestMapping("/api/order-products")
@RequiredArgsConstructor
public class OrderProductController {
    private final OrderProductService orderProductService;

    /**
     * Order product by member response entity.
     *
     * @param memberNo the member no
     * @param pageable the pageable
     * @return the response entity
     */
    @MemberValid
    @GetMapping("/member/{memberNo}")
    public ResponseEntity<PageResponse<OrderProductResponseDto>> orderProductByMember(@PathVariable("memberNo") Integer memberNo,
                                                                                      Pageable pageable) {
        Page<OrderProductResponseDto> content = orderProductService.findMemberOrders(memberNo, pageable);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(content));
    }

    /**
     * Order product detail response entity.
     *
     * @param orderNo the order product no
     * @return the response entity
     */
    @GetMapping("/{orderNo}")
    public ResponseEntity<PageResponse<OrderProductDetailResponseDto>> orderProductDetail(
        @PathVariable("orderNo") Integer orderNo,
        @RequestParam(value = "memberNo") Integer memberNo,
        Pageable pageable) {
        Page<OrderProductDetailResponseDto> page = orderProductService
            .findMemberOrderProductDetail(orderNo, memberNo, pageable);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }
}
