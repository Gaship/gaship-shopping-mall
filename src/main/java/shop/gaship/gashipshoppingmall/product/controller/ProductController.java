package shop.gaship.gashipshoppingmall.product.controller;

import lombok.RequiredArgsConstructor;
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
    private final ProductService productService;

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
        productService.addProduct(files, createRequest);

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
        productService.modifyProduct(files, modifyRequest);

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
        productService.modifyProductSalesStatus(salesStatusModifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


}
