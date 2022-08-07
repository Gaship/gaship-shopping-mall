package shop.gaship.gashipshoppingmall.product.service.impl;

import java.io.File;
import java.time.LocalDateTime;
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
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestViewDto;
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
    private final CommonFileRepository fileRepository;
    private final CommonFileService fileService;
    private final FileUploadUtil fileUploadUtil;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ElasticProductRepository elasticProductRepository;

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException   카테고리가 존재하지않을경우 발생합니다.
     * @throws StatusCodeNotFoundException 상태코드가 존재하지않을경우 발생합니다.
     * @throws FileUploadFailureException  파일 저장에 오류가 발생하였을 때 에외를 던집니다.
     */
    @Transactional
    @Override
    public void addProduct(List<MultipartFile> files, ProductRequestDto createRequest) {
        Category category = categoryRepository.findById(createRequest.getCategoryNo())
            .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(createRequest.getDeliveryTypeNo())
            .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode salesStatus = statusCodeRepository
            .findByStatusCodeName(SalesStatus.SALE.getValue())
            .orElseThrow(StatusCodeNotFoundException::new);

        Product product = createProduct(category, deliveryType, createRequest);
        product.updateSalesStatus(salesStatus);

        List<String> imageLinks = fileUploadUtil.uploadFile(PRODUCT_DIR, files);

        ProductSaveUpdateEvent event = new ProductSaveUpdateEvent(imageLinks, null);
        applicationEventPublisher.publishEvent(event);

        Product savedProduct = repository.save(product);
        addProductTags(savedProduct, createRequest.getTagNos());
        addProductImages(savedProduct, imageLinks);

        event.setSavedProduct(savedProduct);
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
    public void modifyProduct(List<MultipartFile> files, ProductRequestDto modifyRequest) {
        Product product = repository.findById(modifyRequest.getNo())
            .orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(modifyRequest.getCategoryNo())
                .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo())
                .orElseThrow(StatusCodeNotFoundException::new);

        //기존에 업로드되어있던 이미지 파일들 삭제
        fileUploadUtil.cleanUpFiles(product.getProductImages().stream()
                .map(CommonFile::getPath)
                .collect(Collectors.toList()));

        List<String> imageLinks = fileUploadUtil.uploadFile(PRODUCT_DIR, files);

        applicationEventPublisher.publishEvent(new ProductSaveUpdateEvent(imageLinks, product));

        product.updateProduct(category, deliveryType, modifyRequest);

        productTagRepository.deleteAllByPkProductNo(product.getNo());
        addProductTags(product, modifyRequest.getTagNos());
        addProductImages(product, imageLinks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByCode(String productCode,
                                                                     Pageable pageable) {
        List<ElasticProduct> elasticProducts = elasticProductRepository.findByCode(productCode);

        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .pageable(pageable)
            .productNoList(getProductNoList(elasticProducts))
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
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
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .productNo(no)
            .build();
        PageResponse<ProductAllInfoResponseDto> product = repository.findProduct(requestDto);
        findProductTagInfo(product);
        findFilePath(product);

        return product.getContent().get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByPrice(Long min, Long max,
                                                                      Pageable pageable) {
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .minAmount(min)
            .maxAmount(max)
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
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
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .categoryNo(no)
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByName(String name,
                                                                     Pageable pageable) {
        List<ElasticProduct> elasticProducts = elasticProductRepository.findByProductName(name);

        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .pageable(pageable)
            .productNoList(getProductNoList(elasticProducts))
            .build();

        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);

        return products;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductsInfo(Pageable pageable) {
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
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
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .statusName(statusName)
            .pageable(pageable)
            .build();
        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> findProductByProductNos(List<Integer> productNos,
                                                                           Pageable pageable) {
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .productNoList(productNos)
            .pageable(pageable)
            .build();

        PageResponse<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
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
        tagNos.forEach(tagNo -> product.addProductTag(
                tagRepository.findById(tagNo).orElseThrow(TagNotFoundException::new)));
    }

    private void addProductImages(Product product, List<String> imageLinks) {
        imageLinks.forEach(imageLink -> product.addProductImage(
                fileService.createCommonFile(imageLink)));
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

    private void findFilePath(PageResponse<ProductAllInfoResponseDto> products) {
        products.getContent().forEach(product -> product.getFilePaths()
                .addAll(fileRepository.findPaths(product.getProductNo(), Product.SERVICE)));
    }


    private List<Integer> getProductNoList(List<ElasticProduct> elasticProducts) {
        return elasticProducts.stream()
            .map(ElasticProduct::getId)
            .collect(Collectors.toList());
    }

    /**
     * 상품 생성 메서드입니다.
     *
     * @param category 상품의 카테고리
     * @param deliveryType 상품의 배송형태
     * @param createRequest 상품 생성 요청 dto
     * @return product 생성 상품
     * @author 김보민
     */
    private Product createProduct(Category category, StatusCode deliveryType,
                                 ProductRequestDto createRequest) {
        return Product.builder()
                .category(category)
                .deliveryType(deliveryType)
                .name(createRequest.getName())
                .amount(createRequest.getAmount())
                .registerDatetime(LocalDateTime.now())
                .manufacturer(createRequest.getManufacturer())
                .manufacturerCountry(createRequest.getManufacturerCountry())
                .seller(createRequest.getSeller())
                .importer(createRequest.getImporter())
                .shippingInstallationCost(createRequest.getShippingInstallationCost())
                .qualityAssuranceStandard(createRequest.getQualityAssuranceStandard())
                .color(createRequest.getColor())
                .stockQuantity(createRequest.getStockQuantity())
                .explanation(createRequest.getExplanation())
                .code(createRequest.getCode())
                .build();
    }
}
