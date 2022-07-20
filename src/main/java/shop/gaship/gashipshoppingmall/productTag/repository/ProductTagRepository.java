package shop.gaship.gashipshoppingmall.productTag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;

/**
 * 상품 태그 jpa 레퍼지토리 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductTagRepository extends JpaRepository<ProductTag, ProductTag.Pk> {
    /**
     * 해당 상품이 가진 상품태그들을 삭제하는 메서드입니다.
     *
     * @param productNo 상품 번호
     * @author 김보민
     */
    void deleteAllByPkProductNo(Integer productNo);
}
