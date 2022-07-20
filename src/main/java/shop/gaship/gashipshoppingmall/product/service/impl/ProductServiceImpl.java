package shop.gaship.gashipshoppingmall.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

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
    public PageResponse<ProductAllInfoResponseDto> findProductByCode(String productCode, PageRequest pageRequest) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productCode(productCode)
                .pageable(pageRequest)
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        inputTags(products);
        return new PageResponse<>(products);
    }

    @Override
    public ProductAllInfoResponseDto findProduct(Integer no) {
        if (repository.findById(no).isEmpty()) {
            throw new ProductNotFoundException();
        }
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productNo(no)
                .build();
        Page<ProductAllInfoResponseDto> product = repository.findProduct(requestDto);
        inputTags(product);

        return product.getContent().get(0);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByPrice(Long min, Long max, Integer page, Integer size) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .minAmount(min)
                .maxAmount(max)
                .pageable(PageRequest.of(page, size))
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        inputTags(products);
        return new PageResponse<>(products);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByCategory(Integer no, Integer page, Integer size) {
        categoryRepository.findById(no).orElseThrow(CategoryNotFoundException::new);
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .categoryNo(no)
                .pageable(PageRequest.of(page, size))
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        inputTags(products);
        return new PageResponse<>(products);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByName(String name, Integer page, Integer size) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productName(name)
                .pageable(PageRequest.of(page, size))
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        inputTags(products);
        return new PageResponse<>(products);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductsInfo(Integer page, Integer size) {
        ProductRequestDto requestDto = new ProductRequestDto();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        inputTags(products);
        return new PageResponse<>(products);
    }

    private void inputTags(Page<ProductAllInfoResponseDto> products) {
        products.forEach(p -> {
            List<Tag> tag = productTagRepository.findTagByProductNo(p.getProductNo());
            tag.forEach(t -> p.getTags().add(t.getTitle()));
        });
    }
}
