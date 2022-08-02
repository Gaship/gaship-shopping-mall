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

    @PostMapping
    public ResponseEntity<Void> productReviewAdd(
            @RequestPart(value = "image", required = false) MultipartFile file,
            @Valid @RequestPart ProductReviewRequestDto createRequest) {
        productReviewService.addProductReview(file, createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

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

    @DeleteMapping("/{orderProductNo}")
    public ResponseEntity<Void> productReviewRemove(
            @PathVariable("orderProductNo") Integer orderProductNo) {
        productReviewService.removeProductReview(orderProductNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping("/{orderProductNo}")
    public ResponseEntity<ProductReviewResponseDto> productReviewDetails(
            @PathVariable("orderProductNo") Integer orderProductNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productReviewService.findReview(orderProductNo));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductReviewResponseDto>> productReviewList(
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PageResponse<>(productReviewService.findReviews(pageable)));
    }

    @GetMapping("/product/{productNo}")
    public ResponseEntity<PageResponse<ProductReviewResponseDto>> productReviewListByProduct(
            @PathVariable("productNo") Integer productNo,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PageResponse<>(productReviewService.findReviewsByProductNo(
                        productNo, pageable)));
    }

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
