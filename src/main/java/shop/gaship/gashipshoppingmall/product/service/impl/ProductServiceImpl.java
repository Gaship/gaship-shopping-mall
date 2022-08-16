package shop.gaship.gashipshoppingmall.product.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestViewDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.event.ProductSaveUpdateEvent;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.producttag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

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
    private final CommonFileRepository commonFileRepository;
    private final CommonFileService commonFileService;
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

        List<FileRequestDto> fileRequests = files.stream()
            .map(commonFileService::uploadMultipartFile)
            .collect(Collectors.toList());

        ProductSaveUpdateEvent event = new ProductSaveUpdateEvent(fileRequests, null);
        applicationEventPublisher.publishEvent(event);

        Product savedProduct = repository.save(product);
        addProductTags(savedProduct, createRequest.getTagNos());
        addProductImages(savedProduct, fileRequests);

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

        List<FileRequestDto> fileRequests = files.stream()
            .map(commonFileService::uploadMultipartFile)
            .collect(Collectors.toList());

        ProductSaveUpdateEvent event = new ProductSaveUpdateEvent(fileRequests, product);
        event.updateBeforeImages(product.getProductImages());
        applicationEventPublisher.publishEvent(event);

        Category category = categoryRepository.findById(modifyRequest.getCategoryNo())
            .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo())
            .orElseThrow(StatusCodeNotFoundException::new);

        product.removeAllProductImages();
        product.updateProduct(category, deliveryType, modifyRequest);

        updateProductTags(product, modifyRequest.getTagNos());
        addProductImages(product, fileRequests);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductAllInfoResponseDto> findProductByCode(String productCode,
                                                             Pageable pageable) {
        List<ElasticProduct> elasticProducts = elasticProductRepository.findByCode(productCode);

        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .pageable(pageable)
            .productNoList(getProductNoList(elasticProducts))
            .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
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
        Page<ProductAllInfoResponseDto> product = repository.findProduct(requestDto);
        findProductTagInfo(product);
        findFilePath(product);

        return product.getContent().get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductAllInfoResponseDto> findProductByPrice(Long min, Long max,
                                                              Pageable pageable) {
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .minAmount(min)
            .maxAmount(max)
            .pageable(pageable)
            .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
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
    public Page<ProductAllInfoResponseDto> findProductByCategory(Integer no,
                                                                 Pageable pageable) {
        if (categoryRepository.findById(no).isEmpty()) {
            throw new CategoryNotFoundException();
        }
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .categoryNo(no)
            .pageable(pageable)
            .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductAllInfoResponseDto> findProductByName(String name,
                                                             Pageable pageable) {
        List<ElasticProduct> elasticProducts = elasticProductRepository.findByProductName(name);

        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .pageable(pageable)
            .productNoList(getProductNoList(elasticProducts))
            .build();

        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);

        return products;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductAllInfoResponseDto> findProductsInfo(Pageable pageable) {
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .pageable(pageable)
            .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
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
    public Page<ProductAllInfoResponseDto> findProductStatusCode(String statusName,
                                                                 Pageable pageable) {
        if (statusCodeRepository.findByStatusCodeName(statusName).isEmpty()) {
            throw new StatusCodeNotFoundException();
        }
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .statusName(statusName)
            .pageable(pageable)
            .build();
        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
        findProductTagInfo(products);
        findFilePath(products);
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductAllInfoResponseDto> findProductByProductNos(List<Integer> productNos,
                                                                   Pageable pageable) {
        ProductRequestViewDto requestDto = ProductRequestViewDto.builder()
            .productNoList(productNos)
            .pageable(pageable)
            .build();

        Page<ProductAllInfoResponseDto> products = repository.findProduct(requestDto);
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

    /**
     * 하나의 상품에 다수의 이미지파일을 추가하는 메서드입니다.
     *
     * @param product      상품
     * @param fileRequests CommonFile 생성 요청 dto
     */
    private void addProductImages(Product product, List<FileRequestDto> fileRequests) {
        fileRequests.forEach(fileRequest -> product.addProductImage(
            commonFileService.createCommonFile(fileRequest)));
    }

    /**
     * 상품 태그를 업데이트하는 메서드입니다.
     *
     * @param product 상품
     * @param tagNos  수정할 태그 목록
     */
    private void updateProductTags(Product product, List<Integer> tagNos) {
        List<Integer> productTagNos = product.getProductTags().stream()
            .map(productTag -> productTag.getPk().getTagNo())
            .collect(Collectors.toList());

        productTagNos.forEach(productTagNo -> {
            if (!tagNos.contains(productTagNo)) {
                product.removeProductTag(productTagNo);
            } else {
                tagNos.remove(productTagNo);
            }
        });

        addProductTags(product, tagNos);
    }

    /**
     * 상품에대한 태그들의 명칭를 가져오는 메소드입니다.
     *
     * @param products 태그를 등록할 상품
     * @author 유호철
     */
    private void findProductTagInfo(Page<ProductAllInfoResponseDto> products) {
        products.getContent().forEach(product -> {
            List<String> tagNameList =
                productTagRepository.findTagsByProductNo(product.getProductNo());
            product.getTags().addAll(tagNameList);
        });
    }

    /**
     * 상품의 이미지 파일 경로를 찾아오는 메서드입니다.
     *
     * @param products 파일 경로를 찾을 상품 목록
     */
    private void findFilePath(Page<ProductAllInfoResponseDto> products) {
        products.getContent().forEach(product -> product.getFilePaths()
            .addAll(commonFileRepository.findPaths(product.getProductNo(), Product.SERVICE)));
    }

    /**
     * 엘라스틱서치로 검색한 결과에서 상품번호를 가져오는 메서드입니다.
     *
     * @param elasticProducts 엘라스틱서치 검색 결과 목록
     * @return 상품번호 목록
     */
    private List<Integer> getProductNoList(List<ElasticProduct> elasticProducts) {
        return elasticProducts.stream()
            .map(ElasticProduct::getId)
            .collect(Collectors.toList());
    }

    /**
     * 상품 생성 메서드입니다.
     *
     * @param category      상품의 카테고리
     * @param deliveryType  상품의 배송형태
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
