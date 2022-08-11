package shop.gaship.gashipshoppingmall.elastic.repository;

import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;

/**
 * 엘라스틱서치 레퍼지토리 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct, Integer> {
    @Query("{    \"multi_match\" : {\n"
        + "      \"query\":    \"?0\",\n"
        + "      \"fields\": [ \"name\", \"name.nori\" ] \n"
        + "    }}")
    List<ElasticProduct> findByProductName(String name);

    List<ElasticProduct> findByCode(String code);
}
