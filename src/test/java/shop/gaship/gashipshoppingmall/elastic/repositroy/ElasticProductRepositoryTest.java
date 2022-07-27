package shop.gaship.gashipshoppingmall.elastic.repositroy;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticRepository;

import java.io.IOException;
import java.util.Objects;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@Slf4j
@SpringBootTest
class ElasticProductRepositoryTest {

    @Autowired
    ElasticRepository repository;

    @Autowired
    ElasticsearchClient client;

    @Test
    void test() {
        ElasticProduct elasticProduct = ElasticProduct
                .builder()
                .id(100)
                .name("가구1")
                .code("code1")
                .build();

        repository.save(elasticProduct);

        log.error(repository.findById(100).get().toString());

//        elasticProductRepository.deleteById(1L);
    }

    @Test
    void search() throws IOException {
        SearchResponse<ElasticProduct> search = client.search(s -> s
                        .index("gaship-product")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value(v -> v.stringValue("productTest1"))
                                )),
                ElasticProduct.class);

        for (Hit<ElasticProduct> hit : search.hits().hits()) {
            log.error(Objects.requireNonNull(hit.source()).toString());
        }
    }

    @Test
    void searchAll() throws IOException {
        SearchResponse<ElasticProduct> search = client.search(s -> s
                        .index("gaship-product")
                , ElasticProduct.class);

        for (Hit<ElasticProduct> hit : search.hits().hits()) {
            log.error(Objects.requireNonNull(hit.source()).toString());
        }
    }

    @Test
    void updateTest() throws IOException {
        ElasticProduct elasticProduct = repository.findById(100).get();

        elasticProduct.setName("updateP2");
        repository.save(elasticProduct);
        //변경감지가 적용이안된다.
        SearchResponse<ElasticProduct> search = client.search(s -> s.index("gaship-product")
                , ElasticProduct.class);

        for (Hit<ElasticProduct> hit : search.hits().hits()) {
            log.error(Objects.requireNonNull(hit.source()).toString());
        }
    }

    @Test
    void searchName() throws IOException{

    }
}