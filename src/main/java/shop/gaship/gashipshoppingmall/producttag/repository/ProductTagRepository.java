package shop.gaship.gashipshoppingmall.producttag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.producttag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.producttag.repository.custom.ProductTagRepositoryCustom;

/**
 * 상품 태그 jpa 레퍼지토리 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductTagRepository extends JpaRepository<ProductTag, ProductTag.Pk>,
        ProductTagRepositoryCustom {
    /**
     * 해당 상품이 가진 상품태그들을 삭제하는 메서드입니다.
     *
     * @param productNo 상품 번호
     * @author 김보민
     */
    void deleteAllByPkProductNo(Integer productNo);

    List<ProductTag> findByProductNoIn(List<Integer> productNos);
}
