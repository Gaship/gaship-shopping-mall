package shop.gaship.gashipshoppingmall.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<ProductResponseDto>> productListSearchCode(
            @RequestParam("code") String productCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByCode(productCode));
    }

    /**
     * get 요청을 받아서 제품들을 페이징 처리하기위한 메서드입니다.
     *
     * @param page 페이지정보입니다.
     * @param size 사이즈정보입니다.
     * @return 검색된 제품들을 페이징해서 반환합니다.
     * @author 유호철
     */
    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponseDto>> productList(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProducts(page, size));
    }

    /**
     * get 요청을 받아서 제품의 정보를 얻기위한 메서드입니다.
     *
     * @param productNo 제품의 번호가 기입됩니다.
     * @return response entity 검색된 제품의 정보가 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/{productNo}")
    public ResponseEntity<ProductResponseDto> productDetails(
            @PathVariable("productNo") Integer productNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProduct(productNo));
    }

    /**
     * 금액으로 제품들을 정렬하기위한 메서드입니다.
     *
     * @param minAmount 최소금액입니다.
     * @param maxAmount 최대금액입니다.
     * @return response entity 정렬된 제품들이반환됩니다.
     * @author 유호철
     */
    @GetMapping("/price")
    public ResponseEntity<List<ProductResponseDto>> productAmountList(
            @RequestParam("min") Long minAmount,
            @RequestParam("max") Long maxAmount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByPrice(minAmount, maxAmount));
    }

    /**
     * 카테고리 번호를 통해 전체상품들을 조회하는 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호가들어간다.
     * @return response entity 조회된 상품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/category/{categoryNo}")
    public ResponseEntity<List<ProductResponseDto>> productCategoryList(
            @PathVariable("categoryNo") Integer categoryNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByCategory(categoryNo));

    }
}
