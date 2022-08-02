package shop.gaship.gashipshoppingmall.productreview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;

/**
 * 상품평 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewService {
    void addProductReview(MultipartFile file, ProductReviewRequestDto createRequest);

    void modifyProductReview(MultipartFile file, ProductReviewRequestDto modifyRequest);

    void removeProductReview(Integer orderProductNo);

    ProductReviewResponseDto findReview(Integer orderProductNo);

    Page<ProductReviewResponseDto> findReviews(Pageable pageable);

    Page<ProductReviewResponseDto> findReviewsByProductNo(Integer productNo, Pageable pageable);

    Page<ProductReviewResponseDto> findReviewsByMemberNo(Integer memberNo, Pageable pageable);
}
