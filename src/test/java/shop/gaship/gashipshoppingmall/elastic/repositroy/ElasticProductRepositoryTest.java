package shop.gaship.gashipshoppingmall.elastic.repositroy;

import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 엘라스틱 서치 레퍼지토리 테스트입니다.
 *
 * @author : 유호철
 * @author : 김보민
 * @since 1.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ElasticProductRepositoryTest {
    @Autowired
    ElasticProductRepository repository;

    private final Integer productNo = 100;
    private ElasticProduct elasticProduct;

    @Order(1)
    @Test
    void productSaveTest() {
        elasticProduct = ElasticProduct
                .builder()
                .id(productNo)
                .name("뚫뛕띫딹")
                .code("ZZ0001")
                .build();

        repository.save(elasticProduct);

        assertThat(repository.findById(productNo)).isPresent();
        assertThat(repository.findById(productNo).get().getId()).isEqualTo(elasticProduct.getId());
        assertThat(repository.findById(productNo).get().getName()).isEqualTo(elasticProduct.getName());
        assertThat(repository.findById(productNo).get().getCode()).isEqualTo(elasticProduct.getCode());
    }

    @Order(2)
    @Test
    void productSearchTest() {
        List<ElasticProduct> bed = repository.findByProductName("ㄸㄸㄸ");
        assertThat(bed).hasSize(1);
    }

    @Order(3)
    @Test
    void productSearchCodeTest() {
        List<ElasticProduct> bed = repository.findByCode("z");
        assertThat(bed).hasSize(1);
    }

    @Order(4)
    @Test
    void deleteByIdTest() {
        repository.deleteById(productNo);

        assertThat(repository.findById(productNo)).isEmpty();
    }
}