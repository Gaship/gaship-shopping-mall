package shop.gaship.gashipshoppingmall.productreview.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;

/**
 * 상품평 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewService {
    void addProductReview(MultipartFile file, ProductReviewRequestDto createRequest)
            throws IOException;

    void modifyProductReview(MultipartFile file, ProductReviewRequestDto modifyRequest)
            throws IOException;

    void removeProductReview(Integer orderProductNo);
}
