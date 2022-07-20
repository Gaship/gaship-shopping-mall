package shop.gaship.gashipshoppingmall.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import java.util.List;

/**
 * 상품 서비스 구현체 입니다.
 *
 * @see shop.gaship.gashipshoppingmall.product.service.ProductService
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    private static final String PRODUCT_DIR = File.separator + "products";

    @Transactional
    @Override
    public void addProduct(List<MultipartFile> files, ProductCreateRequestDto createRequest)
            throws IOException {
        Category category = categoryRepository.findById(createRequest.getCategoryNo())
                .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(createRequest.getDeliveryTypeNo())
                .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode salesStatus = statusCodeRepository
                .findByStatusCodeName(SalesStatus.SALE.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        Product product = Product.create(category, deliveryType, createRequest);
        product.updateSalesStatus(salesStatus);

        List<String> imageLinks = FileUploadUtil.uploadFile(PRODUCT_DIR, files);
        product.updateImageLinks(imageLinks);

        repository.save(product);
        addProductTags(product, createRequest.getTagNos());
    }

    @Transactional
    @Override
    public void modifyProduct(List<MultipartFile> files, ProductModifyRequestDto modifyRequest)
            throws IOException {
        Product product = repository.findById(modifyRequest.getNo())
                .orElseThrow(ProductNotFoundException::new);

        Category category = categoryRepository.findById(modifyRequest.getCategoryNo())
                .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo())
                .orElseThrow(StatusCodeNotFoundException::new);

        product.updateProduct(category, deliveryType, modifyRequest);
        FileUploadUtil.deleteFiles(PRODUCT_DIR, product.getImageLinkList());

        List<String> imageLinks = FileUploadUtil.uploadFile(PRODUCT_DIR, files);
        product.updateImageLinks(imageLinks);

        repository.save(product);

        productTagRepository.deleteAllByPkProductNo(product.getNo());
        addProductTags(product, modifyRequest.getTagNos());
    }

    @Transactional
    @Override
    public void modifyProductSalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest) {
        Product product = repository.findById(salesStatusModifyRequest.getProductNo())
                .orElseThrow(ProductNotFoundException::new);
        StatusCode salesStatus = statusCodeRepository
                .findByStatusCodeName(salesStatusModifyRequest.getStatusCodeName())
                .orElseThrow(StatusCodeNotFoundException::new);

        product.updateSalesStatus(salesStatus);
        repository.save(product);
    }

    /**
     * 상품 태그 등록 메서드입니다.
     *
     * @param product 태그를 등록할 상품
     * @param tagNos 등록할 태그 번호 목록
     * @author 김보민
     */
    private void addProductTags(Product product, List<Integer> tagNos) {
        List<ProductTag> productTags = new ArrayList<>();

        tagNos.forEach(tagNo -> {
            Tag tag = tagRepository.findById(tagNo).orElseThrow(TagNotFoundException::new);

            ProductTag.Pk pk = new ProductTag.Pk(product.getNo(), tagNo);
            productTags.add(new ProductTag(pk, product, tag));
        });

        productTagRepository.saveAll(productTags);
    }

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
