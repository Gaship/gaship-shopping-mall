package shop.gaship.gashipshoppingmall.productTag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;

/**
 * 상품 태그 레퍼지토리 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductTagRepository extends JpaRepository<ProductTag, ProductTag.Pk> {
}
