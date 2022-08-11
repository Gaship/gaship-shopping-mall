package shop.gaship.gashipshoppingmall.productreview.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.aspact.anntation.MemberAuthority;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.service.ProductReviewService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 상품평 컨트롤러 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    /**
     * 상품평 post 요청 매핑 메서드입니다.
     *
     * @param file          이미지 파일
     * @param createRequest 상품평 등록 요청 dto
     * @return responseEntity 응답 바디는 없습니다.
     */
    @MemberAuthority
    @PostMapping
    public ResponseEntity<Void> productReviewAdd(
            @RequestPart(value = "image", required = false) MultipartFile file,
            @Valid @RequestPart ProductReviewRequestDto createRequest) {
        productReviewService.addProductReview(file, createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 상품평 put 요청 매핑 메서드입니다.
     *
     * @param file           이미지 파일
     * @param modifyRequest  상품평 수정 요청 dto
     * @param orderProductNo 수정할 상품평 번호
     * @return responseEntity 응답 바디는 없습니다.
     */
    @MemberAuthority
    @PutMapping("/{orderProductNo}")
    public ResponseEntity<Void> productReviewModify(
            @RequestPart(value = "image", required = false) MultipartFile file,
            @Valid @RequestPart ProductReviewRequestDto modifyRequest,
            @PathVariable Integer orderProductNo) {
        productReviewService.modifyProductReview(file, modifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 상품평 delete 매핑 메서드입니다.
     *
     * @param orderProductNo 삭제할 상품평 번호
     * @return responseEntity 응답 바디는 없습니다.
     */
    @MemberAuthority
    @DeleteMapping("/{orderProductNo}")
    public ResponseEntity<Void> productReviewRemove(
            @PathVariable("orderProductNo") Integer orderProductNo) {
        productReviewService.removeProductReview(orderProductNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 상품평 단건조회 get 매핑 메서드입니다.
     *
     * @param orderProductNo 조회할 상품평 번호
     * @return responseEntity 조회한 상품평 응답 dto를 포함하고 있습니다.
     */
    @GetMapping("/{orderProductNo}")
    public ResponseEntity<ProductReviewResponseDto> productReviewDetails(
            @PathVariable("orderProductNo") Integer orderProductNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productReviewService.findReview(orderProductNo));
    }

    /**
     * 상품평 전체조회 get 매핑 메서드입니다.
     *
     * @param pageable 페이지 정보
     * @return responseEntity 조회한 상품평들의 페이지 응답 객체를 포함하고 있습니다.
     */
    @GetMapping
    public ResponseEntity<PageResponse<ProductReviewResponseDto>> productReviewList(
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PageResponse<>(productReviewService.findReviews(pageable)));
    }

    /**
     * 상품번호로 상품평 다건 조회 get 매핑 메서드입니다.
     *
     * @param productNo 상품번호
     * @param pageable  페이지 정보
     * @return responseEntity 조회한 상품평들의 페이지 응답 객체를 포함하고 있습니다.
     */
    @GetMapping("/product/{productNo}")
    public ResponseEntity<PageResponse<ProductReviewResponseDto>> productReviewListByProduct(
            @PathVariable("productNo") Integer productNo,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PageResponse<>(productReviewService.findReviewsByProductNo(
                        productNo, pageable)));
    }

    /**
     * 회원번호로 상품평 다건 조회 get 매핑 메서드입니다.
     *
     * @param memberNo 회원번호
     * @param pageable 페이지 정보
     * @return responseEntity 조회한 상품평들의 페이지 응답 객체를 포함하고 있습니다.
     */
    @GetMapping("/member/{memberNo}")
    public ResponseEntity<PageResponse<ProductReviewResponseDto>> productReviewListByMember(
            @PathVariable("memberNo") Integer memberNo,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PageResponse<>(productReviewService.findReviewsByMemberNo(
                        memberNo, pageable)));
    }
}
