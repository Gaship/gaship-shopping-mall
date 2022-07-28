package shop.gaship.gashipshoppingmall.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;

/**
 * 엘라스틱서치 레퍼지토리 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface ElasticRepository extends ElasticsearchRepository<ElasticProduct,Integer> {
}
