package shop.gaship.gashipshoppingmall.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

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
    @GetMapping("/code")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productListSearchCode(
            @RequestParam("code") String productCode,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByCode(productCode, PageRequest.of(page, size)));
    }

    /**
     * get 요청을 받아서 제품의 정보를 얻기위한 메서드입니다.
     *
     * @param productNo 제품의 번호가 기입됩니다.
     * @return response entity 검색된 제품의 정보가 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/{productNo}")
    public ResponseEntity<ProductAllInfoResponseDto> productDetails(
            @PathVariable("productNo") Integer productNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProduct(productNo));
    }

    /**
     * get 요청을 받아서 금액으로 제품들을 정렬하기위한 메서드입니다.
     *
     * @param minAmount 최소금액입니다.
     * @param maxAmount 최대금액입니다.
     * @return response entity 정렬된 제품들이반환됩니다.
     * @author 유호철
     */
    @GetMapping("/price")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productAmountList(
            @RequestParam("min") Long minAmount,
            @RequestParam("max") Long maxAmount,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByPrice(minAmount, maxAmount, page, size));
    }

    /**
     * get 요청을 받아서 카테고리 번호를 통해 전체상품들을 조회하는 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호가들어간다.
     * @return response entity 조회된 상품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/category/{categoryNo}")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productCategoryList(
            @PathVariable("categoryNo") Integer categoryNo,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByCategory(categoryNo, page, size));

    }

    /**
     * get 요청을 받아서 상품이름을 통해 전체상품들을 조회하는 메서드입니다.
     *
     * @param name 조회할 상품의 이름이 들어갑니다.
     * @return response entity 조회된 상품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/name")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productNameList(
            @RequestParam("name") String name,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductByName(name, page, size));
    }

    /**
     * get 요청을 받아서 전체상품들을 조회하는 메서드입니다.
     *
     * @return response entity 전체 상품들 조회하는 메서드입니다.
     * @author 유호철
     */
    @GetMapping()
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productListAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findProductsInfo(page, size));
    }
}
