package shop.gaship.gashipshoppingmall.product.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticRepository;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.event.ProductSaveUpdateEvent;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.producttag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.producttag.repository.ProductTagRepository;
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
    private static final String PRODUCT_DIR = File.separator + "products";
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final FileUploadUtil fileUploadUtil;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ElasticRepository elasticRepository;

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException   카테고리가 존재하지않을경우 발생합니다.
     * @throws StatusCodeNotFoundException 상태코드가 존재하지않을경우 발생합니다.
     * @throws FileUploadFailureException  파일 저장에 오류가 발생하였을 때 에외를 던집니다.
     */
    @Transactional
    @Override
    public void addProduct(List<MultipartFile> files, ProductCreateRequestDto createRequest) {
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

        applicationEventPublisher.publishEvent(new ProductSaveUpdateEvent(imageLinks));

        Product savedProduct = repository.save(product);
        addProductTags(product, createRequest.getTagNos());
        elasticRepository.save(new ElasticProduct(
            savedProduct.getNo(), savedProduct.getName(), savedProduct.getCode()));
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductNotFoundException    제품이 존재하지않을경우 발생합니다.
     * @throws StatusCodeNotFoundException 상태코드가 존재하지않을경우 발생합니다.
     */
    @Transactional
    @Override
    public void modifyProductSalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest) {
        Product product = repository.findById(salesStatusModifyRequest.getProductNo())
            .orElseThrow(ProductNotFoundException::new);
        StatusCode salesStatus = statusCodeRepository
            .findByStatusCodeName(salesStatusModifyRequest.getStatusCodeName())
            .orElseThrow(StatusCodeNotFoundException::new);

        product.updateSalesStatus(salesStatus);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductNotFoundException    제품이 존재하지않을경우 발생합니다.
     * @throws CategoryNotFoundException   카테고리가 존재하지않을경우 발생합니다.
     * @throws StatusCodeNotFoundException 상태코드가 존재하지않을경우 발생합니다.
     * @throws FileUploadFailureException  파일 저장에 오류가 발생하였을 때 에외를 던집니다.
     * @throws FileDeleteFailureException  파일 삭제에 오류가 발생하였을 때 에외를 던집니다.
     */
    @Transactional
    @Override
    public void modifyProduct(List<MultipartFile> files, ProductModifyRequestDto modifyRequest) {
        Product product = repository.findById(modifyRequest.getNo())
            .orElseThrow(ProductNotFoundException::new);

        fileUploadUtil.cleanUpFiles(product.getImageLinkList());

        Category category = categoryRepository.findById(modifyRequest.getCategoryNo())
            .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo())
            .orElseThrow(StatusCodeNotFoundException::new);
        List<String> imageLinks = fileUploadUtil.uploadFile(PRODUCT_DIR, files);

        applicationEventPublisher.publishEvent(new ProductSaveUpdateEvent(imageLinks));

        product.updateImageLinks(imageLinks);
        product.updateProduct(category, deliveryType, modifyRequest);

        productTagRepository.deleteAllByPkProductNo(product.getNo());
        addProductTags(product, modifyRequest.getTagNos());
        elasticRepository.save(
            new ElasticProduct(product.getNo(), product.getName(), product.getCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByCode(String productCode,
                                                                     Pageable pageable) {
        List<ElasticProduct> elasticProducts = elasticRepository.findByCode(productCode);

        ProductRequestDto requestDto = ProductRequestDto.builder()
            .pageable(pageable)
            .productNoList(getProductNoList(elasticProducts))
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return products;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductNotFoundException 제품이존재하지않을경우 예외가 발생합니다.
     */
    @Override
    public ProductAllInfoResponseDto findProduct(Integer no) {
        if (repository.findById(no).isEmpty()) {
            throw new ProductNotFoundException();
        }
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .productNo(no)
            .build();
        PageResponse<ProductAllInfoResponseDto> product = repository.findProduct(requestDto);
        findProductTagInfo(product);

        return product.getContent().get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByPrice(Long min, Long max,
                                                                      Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .minAmount(min)
            .maxAmount(max)
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return products;
    }

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException 카테고리값이 없을시 발생한다.
     */
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
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByName(String name,
                                                                     Pageable pageable) {
        List<ElasticProduct> elasticProducts = elasticRepository.findByProductName(name);

        ProductRequestDto requestDto = ProductRequestDto.builder()
            .pageable(pageable)
            .productNoList(getProductNoList(elasticProducts))
            .build();

        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);

        return products;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductsInfo(Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return products;
    }

    /**
     * 상품상태에 맞는 상품들의 정보를 반환합니다.
     *
     * @param statusName 상품상태의 정보
     * @param pageable   페이징 정보
     * @return the page response
     */

    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductStatusCode(String statusName,
                                                                         Pageable pageable) {
        if (statusCodeRepository.findByStatusCodeName(statusName).isEmpty()) {
            throw new StatusCodeNotFoundException();
        }
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .statusName(statusName)
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByProductNos(List<Integer> productNos,
                                                                           Pageable pageable) {
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .productNoList(productNos)
            .pageable(pageable)
            .build();

        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        return products;
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
    private void findProductTagInfo(PageResponse<ProductAllInfoResponseDto> products) {
        products.getContent().forEach(product -> {
            List<String> tagNameList =
                productTagRepository.findTagsByProductNo(product.getProductNo());
            product.getTags().addAll(tagNameList);
        });
    }


    private List<Integer> getProductNoList(List<ElasticProduct> elasticProducts) {
        return elasticProducts.stream()
            .map(ElasticProduct::getId)
            .collect(Collectors.toList());
    }
}
