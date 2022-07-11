package shop.gaship.gashipshoppingmall.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.product.entity.Product;

/**
 * packageName    : shop.gaship.gashipshoppingmall.product.repository
 * fileName       : ProductRepository
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 상품 레퍼지토리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
public interface ProductRepository 
        extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryNo(Integer categoryNo);
}
