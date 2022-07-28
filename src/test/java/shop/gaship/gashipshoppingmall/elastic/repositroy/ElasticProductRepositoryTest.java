package shop.gaship.gashipshoppingmall.elastic.repositroy;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticRepository;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 엘라스틱 서치 레퍼지토리 테스트입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@SpringBootTest
@Slf4j
class ElasticProductRepositoryTest {

    @Autowired
    ElasticRepository repository;

    @Autowired
    ElasticsearchClient client;

    private ElasticProduct elasticProduct;

    @BeforeEach
    void setUp() {
        elasticProduct = ElasticProduct
            .builder()
            .id(100)
            .name("모던침대")
            .code("CD0011")
            .build();
    }

    @Test
    void productSaveTest() {
        repository.save(elasticProduct);

        Integer productNo = 100;
        assertThat(repository.findById(productNo)).isPresent();
        assertThat(repository.findById(productNo).get().getId()).isEqualTo(elasticProduct.getId());
        assertThat(repository.findById(productNo).get().getName()).isEqualTo(elasticProduct.getName());
        assertThat(repository.findById(productNo).get().getCode()).isEqualTo(elasticProduct.getCode());

        repository.deleteById(productNo);
    }

    @Test
    void productSearch() {
        List<ElasticProduct> bed = repository.findByProductName("ㅁㄷ");
        assertThat(bed).hasSize(2);
    }

    @Test
    void productSearchCode() {
        List<ElasticProduct> bed = repository.findByCode("c");
        assertThat(bed).hasSize(4);
    }
}