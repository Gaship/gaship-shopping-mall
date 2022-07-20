package shop.gaship.gashipshoppingmall.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import java.io.IOException;
import java.util.List;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
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
     * 상품 post 요청 매핑 메서드입니다.
     *
     * @param files 리스트 형태의 다중 이미지 파일
     * @param createRequest 상품 등록 요청 dto
     * @return responseEntity 응답 바디는 없습니다.
     * @throws IOException 파일 업로드 시 IOException 이 발생할 수 있습니다.
     * @author 김보민
     */
    @PostMapping
    public ResponseEntity<Void> productAdd(@RequestPart("image") List<MultipartFile> files,
                                           @RequestPart ProductCreateRequestDto createRequest) throws IOException {
        service.addProduct(files, createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 상품 put 요청 메서드입니다.
     *
     * @param files 리스트 형태의 다중 이미지 파일
     * @param modifyRequest 상품 수정 요청 dto
     * @return responseEntity 응답 바디는 없습니다.
     * @throws IOException 파일 업로드 시 IOException 이 발생할 수 있습니다.
     * @author 김보민
     */
    @PutMapping
    public ResponseEntity<Void> productModify(@RequestPart("image") List<MultipartFile> files,
                                              @RequestPart ProductModifyRequestDto modifyRequest) throws IOException {
        service.modifyProduct(files, modifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 상품 판매상태 put 요청 메서드입니다.
     *
     * @param salesStatusModifyRequest 상품 판매상태 수정 요청 dto
     * @return responseEntity 응답 바디는 없습니다.
     * @author 김보민
     */
    @PutMapping("/salesStatus")
    public ResponseEntity<Void> salesStatusModify(@RequestBody SalesStatusModifyRequestDto salesStatusModifyRequest) {
        service.modifyProductSalesStatus(salesStatusModifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


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
