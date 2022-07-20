package shop.gaship.gashipshoppingmall.productTag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@DataJpaTest
class ProductTagRepositoryTest {
    @Autowired
    ProductTagRepository productTagRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StatusCodeRepository statusCodeRepository;

    @DisplayName("제품번호로 테그조회테스트")
    @Test
    void productNoTest() {
        //TODO : 전체 테스트시 깨진다... 이유가뭘까?
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