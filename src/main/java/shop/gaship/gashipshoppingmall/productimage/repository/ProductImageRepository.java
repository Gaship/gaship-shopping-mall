package shop.gaship.gashipshoppingmall.productimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.productimage.entity.ProductImage;

/**
 * 상품 이미지 레퍼지토리 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductImageRepository extends JpaRepository<ProductImage, ProductImage.Pk> {
}
