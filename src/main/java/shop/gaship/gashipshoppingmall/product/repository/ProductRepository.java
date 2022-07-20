package shop.gaship.gashipshoppingmall.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.custom.ProductRepositoryCustom;

/**
 * 상품 jpa 레퍼지토리입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductRepository
        extends JpaRepository<Product, Integer>, ProductRepositoryCustom {

    /**
     * 카테고리에 속한 상품들을 찾는 메서드입니다.
     *
     * @param categoryNo 카테고리 번호
     * @return list 상품 리스트
     * @author 김보민
     */
    List<Product> findAllByCategoryNo(Integer categoryNo);
}
