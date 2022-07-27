package shop.gaship.gashipshoppingmall.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @see
 * @since 1.0
 */
public interface ElasticRepository extends ElasticsearchRepository<ElasticProduct,Integer> {
}
