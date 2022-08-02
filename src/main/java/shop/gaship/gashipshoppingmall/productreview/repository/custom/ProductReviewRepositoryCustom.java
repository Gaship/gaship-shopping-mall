package shop.gaship.gashipshoppingmall.productreview.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;

/**
 * 상품평 커스텀 레퍼지토리입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductReviewRepositoryCustom {
    Page<ProductReviewResponseDto> findAllByProductNo(Integer productNo, Pageable pageable);
}
