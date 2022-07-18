package shop.gaship.gashipshoppingmall.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.service.ProductService;

import java.util.List;

/**
 * 상품 컨트롤러 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;


    /**
     * get 요청을 받아서 제품코드로 제품들을 조회하기위한 메서드입니다.
     *
     * @param productCode 입력된 제품코드 입니다.
     * @return 검색된 제품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> productList(
            @RequestParam("code") String productCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByCode(productCode));
    }
}
