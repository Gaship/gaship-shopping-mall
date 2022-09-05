package shop.gaship.gashipshoppingmall.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductByCategoryResponseDto;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * 상품 컨트롤러 입니다.
 *
 * @author : 김보민
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    /**
     * 상품 post 요청 매핑 메서드입니다.
     *
     * @param files         리스트 형태의 다중 이미지 파일
     * @param createRequest 상품 등록 요청 dto
     * @return responseEntity 응답 바디는 없습니다.
     * @author 김보민
     */
//    @AdminAuthority
    @PostMapping
    public ResponseEntity<Void> productAdd(@RequestPart("image") List<MultipartFile> files,
                                           @RequestPart ProductRequestDto createRequest) {
        service.addProduct(files, createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }

    /**
     * 상품 put 요청 메서드입니다.
     *
     * @param files         리스트 형태의 다중 이미지 파일
     * @param modifyRequest 상품 수정 요청 dto
     * @return responseEntity 응답 바디는 없습니다.
     * @author 김보민
     */
//    @AdminAuthority
    @PutMapping("/{productNo}")
    public ResponseEntity<Void> productModify(@RequestPart("image") List<MultipartFile> files,
                                              @RequestPart ProductRequestDto modifyRequest,
                                              @PathVariable("productNo") Integer productNo) {
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
    @PutMapping("/{productNo}/sales-status")
    public ResponseEntity<Void> salesStatusModify(
        @RequestBody SalesStatusModifyRequestDto salesStatusModifyRequest,
        @PathVariable("productNo") Integer productNo) {
        service.modifyProductSalesStatus(salesStatusModifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }


    /**
     * get 요청을 받아서 제품코드로 제품들을 조회하기위한 메서드입니다.
     *
     * @param productCode 입력된 제품코드 입니다.
     * @param pageable    페이징을 하기위한 값입니다.
     * @return 검색된 제품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/code")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productListSearchCode(
        @RequestParam("code") String productCode,
        Pageable pageable) {
        Page<ProductAllInfoResponseDto> page =
            service.findProductByCode(productCode, pageable);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
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
     * get 요청을 받아서 상태이름을 통해 상품들을 조회하기위한 메서드입니다.
     *
     * @param statusName 상태이름이 입력됩니다.
     * @param pageable   페이징정보가 들어갑니다.
     * @return 검색된 제품의 정보가 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/statusCode")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productListSearchStatusCode(
        @RequestParam("statusName") String statusName,
        Pageable pageable) {
        Page<ProductAllInfoResponseDto> page =
            service.findProductStatusCode(statusName, pageable);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }

    /**
     * get 요청을 받아서 금액으로 제품들을 정렬하기위한 메서드입니다.
     *
     * @param minAmount 최소금액입니다.
     * @param maxAmount 최대금액입니다.
     * @param pageable  페이징을 하기위한 값입니다.
     * @return response entity 정렬된 제품들이반환됩니다.
     * @author 유호철
     */

    @GetMapping("/price")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productAmountList(
        @RequestParam("min") Long minAmount,
        @RequestParam("max") Long maxAmount,
        Pageable pageable) {
        Page<ProductAllInfoResponseDto> page =
            service.findProductByPrice(minAmount, maxAmount, pageable);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }

    /**
     * get 요청을 받아서 카테고리 번호를 통해 전체상품들을 조회하는 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호가들어간다.
     * @param pageable   페이징을 하기위한 값입니다.
     * @return response entity 조회된 상품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/category/{categoryNo}")
    public ResponseEntity<PageResponse<ProductByCategoryResponseDto>> productCategoryList(
        @PathVariable("categoryNo") Integer categoryNo,
        @RequestParam("isUpper") boolean isUpper,
        @RequestParam(value = "minPrice", required = false) Long minPrice,
        @RequestParam(value = "maxPrice", required = false) Long maxPrice,
        Pageable pageable) {
        Page<ProductByCategoryResponseDto> page = null;
        if (isUpper) {
            page = service.findProductByUpperCategoryNo(categoryNo, minPrice, maxPrice, pageable);
        } else {
            page = service.findProductByLowerCategory(categoryNo, pageable);

        }
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));

    }

    /**
     * get 요청을 받아서 상품이름을 통해 전체상품들을 조회하는 메서드입니다.
     *
     * @param name     조회할 상품의 이름이 들어갑니다.
     * @param pageable 페이징을 하기위한 값이들어갑니다.
     * @return response entity 조회된 상품들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/name")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productNameList(
        @RequestParam("name") String name,
        Pageable pageable) {
        Page<ProductAllInfoResponseDto> page = service.findProductByName(name, pageable);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }

    /**
     * get 요청을 받아서 전체상품들을 조회하는 메서드입니다.
     *
     * @param pageable 페이징을 하기위한 값이들어갑니다.
     * @return response entity 전체 상품들 조회하는 메서드입니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productListAll(
        Pageable pageable,
        @RequestParam(required = false, value = "category") String category,
        @RequestParam(required = false, value = "minAmount") String minAmount,
        @RequestParam(required = false, value = "maxAmount") String maxAmount) {
        Page<ProductAllInfoResponseDto> page = service.findProductsInfo(pageable, category, minAmount, maxAmount);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }

    /**
     * 상품 번호들로 전체상품을 조회하는 메서드입니다.
     *
     * @param productNos 조회할 상품번호들이 들어옵니다.
     * @return productNos 가 들어온 상품들이 조회됩니다.
     * @author 유호철
     */
    @GetMapping(params = "productNos")
    public ResponseEntity<List<ProductAllInfoResponseDto>> productNosList(
        @RequestParam("productNos") List<Integer> productNos) {
        List<ProductAllInfoResponseDto> result;
        if (!productNos.isEmpty()) {
            result = service.findProductByProductNos(
                productNos, PageRequest.of(0, productNos.size())).getContent();
        } else {
            result = List.of();
        }
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result);
    }

    /**
     * TagNo 로 Get 요청시 상품정보들을 반환합니다.
     *
     * @param tagNo    the tag no
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping(params = "tagNo")
    public ResponseEntity<PageResponse<ProductAllInfoResponseDto>> productListByTag(
        @RequestParam("tagNo") Integer tagNo,
        Pageable pageable) {
        Page<ProductAllInfoResponseDto> page = service.findProductByTagNo(tagNo, pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }
}
