package shop.gaship.gashipshoppingmall.elastic.repositroy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticRepository;

/**
 * 엘라스틱 서치 레퍼지토리 테스트입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@SpringBootTest
class ElasticProductRepositoryTest {

    @Autowired
    ElasticRepository repository;

    @Autowired
    ElasticsearchClient client;

    private Integer productNo = 100;

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

        assertThat(repository.findById(productNo)).isPresent();
        assertThat(repository.findById(productNo).get().getId()).isEqualTo(elasticProduct.getId());
        assertThat(repository.findById(productNo).get().getName()).isEqualTo(elasticProduct.getName());
        assertThat(repository.findById(productNo).get().getCode()).isEqualTo(elasticProduct.getCode());

        repository.deleteById(productNo);
    }

//    TODO search 테스트는 adapter에서 할 예정입니다. 이후에 지우겠습니다.
//    @Test
//    void search() throws IOException {
//        SearchResponse<ElasticProduct> search = client.search(s -> s
//                        .index("gaship-product-test")
//                        .query(q -> q
//                                .term(t -> t
//                                        .field("name")
//                                        .value(v -> v.stringValue(elasticProduct.getName()))
//                                )),
//                ElasticProduct.class);
//
//        List<Hit<ElasticProduct>> hits = search.hits().hits();
//
//        for (Hit<ElasticProduct> hit : hits) {
//            assertThat(hit.source()).isNotNull();
//            assertThat(hit.source().getName()).isEqualTo(elasticProduct.getName());
//        }
//    }
//
//    @Test
//    void searchAll() throws IOException {
//        SearchResponse<ElasticProduct> search = client.search(s -> s
//                        .index("gaship-product-test")
//                , ElasticProduct.class);
//
//        for (Hit<ElasticProduct> hit : search.hits().hits()) {
//            assertThat(hit.source()).isNotNull();
//            assertThat(hit.source().getName()).isEqualTo(elasticProduct.getName());
//        }
//    }
//
//    @Test
//    void updateTest() throws IOException {
//        ElasticProduct elasticProduct = repository.findById(100).get();
//
//        elasticProduct.setName("updateP2");
//        repository.save(elasticProduct);
//        //변경감지가 적용이안된다.
//        SearchResponse<ElasticProduct> search = client.search(s -> s.index("gaship-product-test")
//                , ElasticProduct.class);
//
//        for (Hit<ElasticProduct> hit : search.hits().hits()) {
//            assertThat(hit.source()).isNotNull();
//            assertThat(hit.source().getName()).isEqualTo(elasticProduct.getName());
//        }
//    }
}