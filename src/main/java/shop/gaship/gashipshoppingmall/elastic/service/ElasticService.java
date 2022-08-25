package shop.gaship.gashipshoppingmall.elastic.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;

/**
 * 엘라스틱서치를 서비스레이어에서 사용하기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface ElasticService {
    /**
     * elastic 에 저장되어있는 doc 의 내용들이 검색어들을 통해 알맞게 반환됩니다.
     *
     * @param name 검색될 상품의 이름이 기입됩니다.
     * @return 검색된 상품들의 간략한 내용들이 반환됩니다.
     */
    List<ElasticProduct> findName(String name);

    /**
     * elastic 에 저장되어있는 doc 의 내용들이 상품코드들을 통해 알맞게 반환됩니다.
     *
     * @param code 검색할 상품코드입니다.
     * @return 검색된 상품들의 간략한 내용들이 반환됩니다.
     */
    List<ElasticProduct> findCode(String code);
}
