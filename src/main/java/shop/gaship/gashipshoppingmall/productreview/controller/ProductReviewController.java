package shop.gaship.gashipshoppingmall.productreview.controller;

import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.service.ProductReviewService;

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
}
