package shop.gaship.gashipshoppingmall.productreview.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewViewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;

/**
 * 상품평 커스텀 레퍼지토리입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductReviewRepositoryCustom {
    /**
     * 상품평을 페이지 형태로 찾는 메서드입니다.
     *
     * @param viewRequest 조회 요청 dto
     * @return page 상품평 데이터 정보를 담은 페이지 객체
     */
    Page<ProductReviewResponseDto> findProductReviews(ProductReviewViewRequestDto viewRequest);
}
