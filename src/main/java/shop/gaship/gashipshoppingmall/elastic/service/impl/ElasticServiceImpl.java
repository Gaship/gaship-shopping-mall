package shop.gaship.gashipshoppingmall.elastic.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.elastic.service.ElasticService;

/**
 * 엘라스틱서치를 서비스레이어에서 사용하기위한 구현클래스입니다.
 *
 * @author : 유호철
 * @see ElasticService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ElasticServiceImpl implements ElasticService {
    private final ElasticProductRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ElasticProduct> findName(String name) {
        return repository.findByProductName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ElasticProduct> findCode(String code) {
        return repository.findByCode(code);
    }
}
