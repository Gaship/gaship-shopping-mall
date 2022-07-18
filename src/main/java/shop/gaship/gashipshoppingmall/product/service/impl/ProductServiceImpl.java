package shop.gaship.gashipshoppingmall.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;

import java.util.List;

/**
 * 상품 서비스 구현체 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public List<ProductResponseDto> findProductByCode(String productCode) {
        return repository.findByCode(productCode);
    }
}
