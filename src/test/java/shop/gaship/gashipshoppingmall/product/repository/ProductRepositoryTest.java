package shop.gaship.gashipshoppingmall.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 상품 레퍼지토리 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy();
        StatusCode deliveryType = new StatusCode("설치", 1, "배송 형태", "");
        StatusCode salesStatus = new StatusCode("판매중", 2, "판매 상태", "");
        ReflectionTestUtils.setField(product, "deliveryType", deliveryType);
        ReflectionTestUtils.setField(product, "salesStatus", salesStatus);
    }

    @DisplayName("상품 레퍼지토리 저장 테스트")
    @Test
    void saveProduct() {
        Product savedProduct = productRepository.save(product);
        ReflectionTestUtils.setField(product, "no", savedProduct.getNo());

        assertThat(savedProduct).isEqualTo(product);
    }

    @DisplayName("상품 엔티티 단건 조회 테스트")
    @Test
    void findProductById() {
        Product savedProduct = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(savedProduct.getNo());

        assertThat(foundProduct).isPresent();
        assertThat(savedProduct).isEqualTo(foundProduct.get());
    }

    @DisplayName("카테고리 번호를 통한 상품 엔티티 전체 조회 테스트")
    @Test
    void findAllByCategoryNo() {
        Product savedProduct = productRepository.save(product);

        List<Product> foundProducts = productRepository
                .findAllByCategoryNo(savedProduct.getCategory().getNo());

        assertThat(foundProducts).hasSize(1);
        assertThat(foundProducts.get(0)).isEqualTo(savedProduct);
    }
}