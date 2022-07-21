package shop.gaship.gashipshoppingmall.product.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

/**
 * 상품 서비스 구현체 입니다.
 *
 * @author : 김보민
 * @author : 유호철
 * @see shop.gaship.gashipshoppingmall.product.service.ProductService
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
    private final FileUploadUtil fileUploadUtil;

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

        List<String> imageLinks = fileUploadUtil.uploadFile(PRODUCT_DIR, files);
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
        fileUploadUtil.deleteFiles(PRODUCT_DIR, product.getImageLinkList());

        List<String> imageLinks = fileUploadUtil.uploadFile(PRODUCT_DIR, files);
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

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByCode(String productCode,
                                                                     Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .code(productCode)
                .pageable(pageable)
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
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
        findProductTagInfo(product);

        return product.getContent().get(0);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByPrice(Long min, Long max,
                                                                      Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .minAmount(min)
                .maxAmount(max)
                .pageable(pageable)
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return new PageResponse<>(products);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByCategory(Integer no,
                                                                         Pageable pageable) {
        if (categoryRepository.findById(no).isEmpty()) {
            throw new CategoryNotFoundException();
        }
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .categoryNo(no)
                .pageable(pageable)
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return new PageResponse<>(products);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByName(String name,
                                                                     Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productName(name)
                .pageable(pageable)
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return new PageResponse<>(products);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductsInfo(Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .pageable(pageable)
                .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return new PageResponse<>(products);
    }

    /**
     * 상품 태그 등록 메서드입니다.
     *
     * @param product 태그를 등록할 상품
     * @param tagNos  등록할 태그 번호 목록
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

    /**
     * 상품에대한 태그들의 명칭를 가져오는 메소드입니다.
     *
     * @param products 태그를 등록할 상품
     * @author 유호철
     */
    private void findProductTagInfo(Page<ProductAllInfoResponseDto> products) {
        products.forEach(p -> {
            List<Tag> tag = productTagRepository.findTagByProductNo(p.getProductNo());
            tag.forEach(t -> p.getTags().add(t.getTitle()));
        });
    }
}
