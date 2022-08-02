package shop.gaship.gashipshoppingmall.productreview.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;

/**
 * 상품평 더미 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class ProductReviewDummy {

    public static ProductReview dummy() {
        return ProductReview.builder()
                .title("상품평 제목")
                .content("상품평 내용")
                .imagePath("이미지 경로")
                .starScore(5)
                .registerDatetime(LocalDateTime.now())
                .build();
    }

    public static ProductReviewRequestDto createRequestDummy() {
        return ProductReviewRequestDto.builder()
                .orderProductNo(1)
                .title("상품평 제목")
                .content("상품평 내용")
                .starScore(5)
                .build();
    }

    public static ProductReviewRequestDto modifyRequestDummy() {
        return ProductReviewRequestDto.builder()
                .orderProductNo(1)
                .title("수정 상품평 제목")
                .content("수정 상품평 내용")
                .starScore(3)
                .build();
    }
}
