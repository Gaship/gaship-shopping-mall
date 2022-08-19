package shop.gaship.gashipshoppingmall.elastic.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.service.ElasticService;

/**
 * 엘라스틱서치 관련 rest controller 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class ElasticSearchController {
    private final ElasticService service;

    /**
     * GET 요청을 통해 상품의 이름이 기입되고 결과로 상품의 정보들이 간략하게 반환됩니다.
     * 성공시 200 입니다.
     *
     * @param productName 검색할 상품의 이름들이 기입됩니다.
     * @return 검색된 상품의 정보들이 반환됩니다.
     */
    @GetMapping(params = "productName")
    public ResponseEntity<List<ElasticProduct>> findName(
        @RequestParam("productName") String productName) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.findName(productName));
    }

    /**
     * GET 요청을 통해 상품의 코드명이 기입되고 결과로 상품의 정보들이 간략하게 반환됩니다.
     * 성공시 200 입니다.
     *
     * @param productCode 검색할 상품의 code 값이 기입됩니다.
     * @return 검색된 상품의 정보들이 반환됩니다.
     */
    @GetMapping(params = "productCode")
    public ResponseEntity<List<ElasticProduct>> findCode(
        @RequestParam("productCode") String productCode) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.findCode(productCode));
    }
}
