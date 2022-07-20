package shop.gaship.gashipshoppingmall.productTag.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 상품태그 레퍼지토리 테스트
 *
 * @author : 김보민
 * @since 1.0
 */
@DataJpaTest
class ProductTagRepositoryTest {
    @Autowired
    ProductTagRepository productTagRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StatusCodeRepository statusCodeRepository;

    private ProductTag productTag;


    @BeforeEach
    void setUp() {
        Product product = ProductDummy.dummy();
        StatusCode deliveryType = new StatusCode("설치", 1, "배송 형태", "");
        StatusCode salesStatus = new StatusCode("판매중", 2, "판매 상태", "");
        ReflectionTestUtils.setField(product, "deliveryType", deliveryType);
        ReflectionTestUtils.setField(product, "salesStatus", salesStatus);
        productRepository.save(product);

        Tag tag = Tag.builder()
                .title("태그")
                .build();
        tagRepository.save(tag);

        ProductTag.Pk pk = new ProductTag.Pk(product.getNo(), tag.getTagNo());
        productTag = new ProductTag(pk, product, tag);
    }

    @DisplayName("상품태그 전체 저장 테스트")
    @Test
    void saveAllProductTags() {
        List<ProductTag> savedProductTags = productTagRepository.saveAll(List.of(productTag));

        assertThat(savedProductTags).hasSize(1);
        assertThat(savedProductTags.get(0)).isEqualTo(productTag);
    }

    @DisplayName("상품번호로 상품 태그 전체 삭제 테스트")
    @Test
    void deleteAllByPkProductNo() {
        List<ProductTag> savedProductTags = productTagRepository.saveAll(List.of(productTag));
        Integer productNo = savedProductTags.get(0).getProduct().getNo();
        ProductTag.Pk pk = savedProductTags.get(0).getPk();

        assertThat(productTagRepository.findById(pk)).isPresent();

        productTagRepository.deleteAllByPkProductNo(productNo);

        assertThat(productNo).isNotNull();
        assertThat(productTagRepository.findById(pk)).isEmpty();
    }

    @DisplayName("제품번호로 테그조회테스트")
    @Test
    void productNoTest() {
        statusCodeRepository.save(StatusCodeDummy.dummy());
        categoryRepository.save(CategoryDummy.dummy());
        categoryRepository.save(CategoryDummy.upperDummy());
        categoryRepository.save(CategoryDummy.bottomDummy());
        Tag tag = new Tag(null, "title");
        tagRepository.save(tag);

        Product product = ProductDummy.dummy2();
        ProductTag productTag = new ProductTag(new ProductTag.Pk(product.getNo(), tag.getTagNo()), product, tag);
        product.add(productTag);

        productRepository.save(product);
        productTagRepository.save(productTag);

        List<Tag> result = productTagRepository.findTagByProductNo(product.getNo());

        assertThat(result.get(0).getTagNo()).isEqualTo(tag.getTagNo());
        assertThat(result.get(0).getTitle()).isEqualTo(tag.getTitle());
    }

}