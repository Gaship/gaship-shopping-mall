package shop.gaship.gashipshoppingmall.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

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
    private final CategoryRepository categoryRepository;
    private final ProductTagRepository productTagRepository;

    @Override
    public List<ProductResponseDto> findProductByCode(String productCode) {
        return repository.findByCode(productCode);
    }

    @Override
    public Page<ProductResponseDto> findProducts(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        return repository.findAllPage(request);
    }

    @Override
    public ProductResponseDto findProduct(Integer no) {
        return repository.findByProductNo(no).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<ProductResponseDto> findProductByPrice(Long min, Long max) {
        return repository.findByPrice(min, max);
    }

    @Override
    public List<ProductResponseDto> findProductByCategory(Integer no) {
        if (categoryRepository.findById(no).isEmpty()) {
            throw new CategoryNotFoundException();
        }

        return repository.findProductByCategory(no);
    }

    @Override
    public List<ProductResponseDto> findProductByName(String name) {
        return repository.findByProductName(name);
    }

    @Override
    public List<ProductAllInfoResponseDto> findProductsInfo() {
        List<ProductAllInfoResponseDto> products = repository.findProduct(null, null, 0, 0L, 0L, 0, null);
        products.forEach(p -> {
            List<Tag> tag = productTagRepository.findTagByProductNo(p.getProductNo());
            tag.forEach(t -> p.getTags().add(t.getTitle()));
        });
        return products;
    }
}
