package shop.gaship.gashipshoppingmall.productreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.repository.custom.ProductReviewRepositoryCustom;

/**
 * 상품평 레퍼지토리 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewRepository
        extends JpaRepository<ProductReview, Integer>, ProductReviewRepositoryCustom {
}
