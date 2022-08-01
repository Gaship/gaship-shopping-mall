package shop.gaship.gashipshoppingmall.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticRepository;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;
import shop.gaship.gashipshoppingmall.producttag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.producttag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 상품 서비스 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(ProductServiceImpl.class)
class ProductServiceTest {
    @Autowired
    ProductService service;

    @MockBean
    ProductRepository repository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @MockBean
    TagRepository tagRepository;

    @MockBean
    ProductTagRepository productTagRepository;

    @MockBean
    FileUploadUtil fileUploadUtil;

    @MockBean
    ElasticRepository elasticRepository;

    Product product;
    ProductAllInfoResponseDto response;
    ProductTag productTag;
    Tag tag;
    Page<ProductAllInfoResponseDto> page;
    PageRequest pageRequest;

    Category category;

    MockMultipartFile multipartFile;
    PageResponse<ProductAllInfoResponseDto> pageResponse;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
            "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));

        category = CategoryDummy.bottomDummy();
        product = ProductDummy.dummy();
        response = new ProductAllInfoResponseDto(1, "a", "d", "카테", 100L, LocalDateTime.now(), "아",
            "한국", "판매원", "가나다라", 100L, "w", "#RRRR", 1, "img", null, null, null, null, "설명", 3,
            "카테");
        tag = new Tag(1, "title");
        response.getTags().add(tag.getTitle());

        pageRequest = PageRequest.of(0, 10);
        page = new PageImpl(List.of(response), pageRequest, 1);
        pageResponse = new PageResponse<>(page);
        productTag = new ProductTag(new ProductTag.Pk(1, 1), product, tag);
    }

    @DisplayName("상품 등록 성공")
    @Test
    void addProduct() {
        ProductCreateRequestDto createRequest = ProductDummy.createRequestDummy();
        List<MultipartFile> files = List.of(multipartFile);
        String uploadDir = File.separator + "products";
        ReflectionTestUtils.setField(product, "no", 1);

        when(repository.save(any(Product.class))).thenReturn(product);
        when(categoryRepository.findById(createRequest.getCategoryNo()))
            .thenReturn(Optional.of(CategoryDummy.dummy()));
        when(statusCodeRepository.findById(createRequest.getDeliveryTypeNo()))
            .thenReturn(Optional.of(
                new StatusCode("설치", createRequest.getDeliveryTypeNo(), "배송형태", "")));
        when(statusCodeRepository.findByStatusCodeName(SalesStatus.SALE.getValue()))
            .thenReturn(Optional.of(new StatusCode("판매중", 2, "판매상태", "")));
        when(tagRepository.findById(createRequest.getTagNos().get(0)))
            .thenReturn(Optional.of(new Tag(1, "태그")));
        when(fileUploadUtil.uploadFile(uploadDir, files))
            .thenReturn(List.of());

        service.addProduct(files, createRequest);

        verify(categoryRepository).findById(createRequest.getCategoryNo());
        verify(statusCodeRepository).findById(createRequest.getDeliveryTypeNo());
        verify(statusCodeRepository).findByStatusCodeName(SalesStatus.SALE.getValue());
        verify(fileUploadUtil).uploadFile(uploadDir, files);
    }

    @DisplayName("상품 수정 성공")
    @Test
    void modifyProduct() {
        ProductModifyRequestDto modifyRequest = ProductDummy.modifyRequestDummy();
        Product product = ProductDummy.dummy();
        ReflectionTestUtils.setField(product, "no", modifyRequest.getNo());
        List<MultipartFile> files = List.of(multipartFile);
        String uploadDir = File.separator + "products";

        when(repository.findById(modifyRequest.getNo()))
            .thenReturn(Optional.of(product));
        when(categoryRepository.findById(modifyRequest.getCategoryNo()))
            .thenReturn(Optional.of(CategoryDummy.dummy()));
        when(statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo()))
            .thenReturn(Optional.of(
                new StatusCode("설치", modifyRequest.getDeliveryTypeNo(), "배송형태", "")));
        when(fileUploadUtil.uploadFile(uploadDir, files))
            .thenReturn(List.of());

        service.modifyProduct(List.of(multipartFile), modifyRequest);

        verify(repository).findById(modifyRequest.getNo());
        verify(categoryRepository).findById(modifyRequest.getCategoryNo());
        verify(statusCodeRepository).findById(modifyRequest.getDeliveryTypeNo());
        verify(fileUploadUtil).uploadFile(uploadDir, files);
    }

    @DisplayName("상품 수정 실패 - 해당 상품 찾기 불가")
    @Test
    void modifyProductFail_NotFoundProduct() {
        ProductModifyRequestDto modifyRequest = ProductDummy.modifyRequestDummy();
        Integer productNo = modifyRequest.getNo();

        when(repository.findById(productNo))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.modifyProduct(List.of(multipartFile), modifyRequest))
            .isInstanceOf(ProductNotFoundException.class);

        verify(repository).findById(productNo);
    }

    @DisplayName("상품 상태 수정 성공")
    @Test
    void modifyProductSalesStatus() {
        SalesStatusModifyRequestDto salesStatusModifyRequest = new SalesStatusModifyRequestDto(
            1, SalesStatus.SOLD_OUT.getValue()
        );
        StatusCode statusCode = new StatusCode(
            SalesStatus.SOLD_OUT.getValue(), 1, SalesStatus.GROUP, "");
        Product product = ProductDummy.dummy();
        ReflectionTestUtils.setField(product, "no", salesStatusModifyRequest.getProductNo());

        when(repository.findById(salesStatusModifyRequest.getProductNo()))
            .thenReturn(Optional.of(product));
        when(statusCodeRepository.findByStatusCodeName(
            salesStatusModifyRequest.getStatusCodeName()))
            .thenReturn(Optional.of(statusCode));

        service.modifyProductSalesStatus(salesStatusModifyRequest);

        verify(repository)
            .findById(salesStatusModifyRequest.getProductNo());
        verify(statusCodeRepository)
            .findByStatusCodeName(salesStatusModifyRequest.getStatusCodeName());
    }

    @DisplayName("상품코드로 다건 조회하기")
    @Test
    void productFindByCodeTest() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .pageable(pageRequest)
            .build();

        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);
        given(productTagRepository.findTagsByProductNo(any()))
            .willReturn(List.of(productTag.getTag().getTitle()));
        //when

        PageResponse<ProductAllInfoResponseDto> result =
            service.findProductByCode("c1", pageRequest);

        //then
        verify(repository, times(1))
            .findProduct(requestDto);

        checkContent(result);

    }

    @DisplayName("상품단건 조회 테스트")
    @Test
    void productFindOneTest() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .productNo(response.getProductNo())
            .build();

        given(repository.findById(response.getProductNo()))
            .willReturn(Optional.of(product));
        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);
        given(productTagRepository.findTagsByProductNo(response.getProductNo()))
            .willReturn(List.of(productTag.getTag().getTitle()));
        //when
        ProductAllInfoResponseDto result = service.findProduct(requestDto.getProductNo());
        //then
        verify(repository, times(1))
            .findById(any());

        verify(repository, times(1))
            .findProduct(any());

        verify(productTagRepository, times(1))
            .findTagsByProductNo(tag.getTagNo());

        assertThat(result.getUpperName()).isEqualTo(response.getUpperName());
        assertThat(result.getProductNo()).isEqualTo(response.getProductNo());
        assertThat(result.getProductName()).isEqualTo(response.getProductName());
        assertThat(result.getProductCode()).isEqualTo(response.getProductCode());
        assertThat(result.getCategoryName()).isEqualTo(response.getCategoryName());
        assertThat(result.getAmount()).isEqualTo(response.getAmount());
        assertThat(result.getCountry()).isEqualTo(response.getCountry());
        assertThat(result.getManufacturer()).isEqualTo(response.getManufacturer());
        assertThat(result.getColor()).isEqualTo(response.getColor());
        assertThat(result.getQuality()).isEqualTo(response.getQuality());
        assertThat(result.getQuantity()).isEqualTo(response.getQuantity());
        assertThat(result.getImg1()).isEqualTo(response.getImg1());
        assertThat(result.getImg2()).isEqualTo(response.getImg2());
        assertThat(result.getImg3()).isEqualTo(response.getImg3());
        assertThat(result.getImg4()).isEqualTo(response.getImg4());
        assertThat(result.getImg5()).isEqualTo(response.getImg5());
        assertThat(result.getLevel()).isEqualTo(response.getLevel());
    }

    @DisplayName("상품 단건 조회테스트 실패")
    @Test
    void productFineOneTestFail() {
        //given
        given(repository.findById(any()))
            .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> service.findProduct(1))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("상품 금액으로 조회")
    @Test
    void productFindByPrice() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .minAmount(0L)
            .maxAmount(product.getAmount())
            .pageable(PageRequest.of(0, 10))
            .build();

        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);

        given(productTagRepository.findTagsByProductNo(any()))
            .willReturn(List.of(tag.getTitle()));

        //when
        service.findProductByPrice(0L, product.getAmount(),
            pageRequest);

        //then
        verify(repository, times(1))
            .findProduct(any());

        verify(productTagRepository, times(1))
            .findTagsByProductNo(any());

    }

    @DisplayName("카테고리 번호로 조회하기 실패")
    @Test
    void productFindByCategoryFail() {
        //given
        given(categoryRepository.findById(any(Integer.class)))
            .willReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.findProductByCategory(product.getNo(),
            pageRequest))
            .isInstanceOf(CategoryNotFoundException.class);
    }

    @DisplayName("카테고리 번호로 조회하기 성공")
    @Test
    void productFindByCategorySuccess() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .categoryNo(1)
            .pageable(pageRequest)
            .build();

        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);
        given(productTagRepository.findTagsByProductNo(any()))
            .willReturn(List.of(tag.getTitle()));
        given(categoryRepository.findById(any()))
            .willReturn(Optional.of(category));

        //when
        PageResponse<ProductAllInfoResponseDto> result =
            service.findProductByCategory(1, pageRequest);

        //then
        verify(repository, times(1)).findProduct(any());
        verify(productTagRepository, times(1)).findTagsByProductNo(tag.getTagNo());
        verify(categoryRepository, times(1)).findById(any());

        checkContent(result);
    }

    @DisplayName("제품이름으로 조회하기")
    @Test
    void productFindByProductName() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .pageable(pageRequest)
            .build();

        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);
        given(productTagRepository.findTagsByProductNo(any()))
            .willReturn(List.of(productTag.getTag().getTitle()));

        //when
        PageResponse<ProductAllInfoResponseDto> result =
            service.findProductByName(response.getProductName(), pageRequest);
        //then
        verify(repository, times(1)).findProduct(any());
        verify(productTagRepository, times(1)).findTagsByProductNo((tag.getTagNo()));

        checkContent(result);
    }

    @DisplayName("조건들을 통해 제품들 조회하기")
    @Test
    void findProductInfo() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto();
        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);
        given(productTagRepository.findTagsByProductNo(any()))
            .willReturn(List.of(productTag.getTag().getTitle()));
        //when
        PageResponse<ProductAllInfoResponseDto> result = service.findProductsInfo(pageRequest);

        //then
        verify(repository, times(1)).findProduct(requestDto);

        checkContent(result);
    }

    @DisplayName("상태코드를 통해 제품들 조회하기 실패경우")
    @Test
    void findProductByStatusNameFail() {
        //given
        given(statusCodeRepository.findByStatusCodeName(any()))
            .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> service.findProductStatusCode("aaaa", pageRequest))
            .isInstanceOf(StatusCodeNotFoundException.class);
    }

    @DisplayName("상태코드를 통해 제품들을 조회하기 성공하는경우")
    @Test
    void findProductByStatusNameSuccess() {
        //given
        StatusCode d1 = StatusCodeDummy.dummy();
        ProductRequestDto requestDto = ProductRequestDto.builder()
            .statusName(d1.getStatusCodeName())
            .pageable(pageRequest)
            .build();
        given(repository.findProduct(requestDto))
            .willReturn(pageResponse);
        given(statusCodeRepository.findByStatusCodeName(requestDto.getStatusName()))
            .willReturn(Optional.of(d1));
        //when
        PageResponse<ProductAllInfoResponseDto> result = service.findProductStatusCode(requestDto.getStatusName(), pageRequest);

        //then
        verify(repository, times(1)).findProduct(requestDto);

        checkContent(result);
    }

    private void checkContent(PageResponse<ProductAllInfoResponseDto> result) {
        assertThat(result.getContent().get(0).getUpperName()).isEqualTo(response.getUpperName());
        assertThat(result.getContent().get(0).getProductNo()).isEqualTo(response.getProductNo());
        assertThat(result.getContent().get(0).getProductName()).isEqualTo(
            response.getProductName());
        assertThat(result.getContent().get(0).getProductCode()).isEqualTo(
            response.getProductCode());
        assertThat(result.getContent().get(0).getCategoryName()).isEqualTo(
            response.getCategoryName());
        assertThat(result.getContent().get(0).getAmount()).isEqualTo(response.getAmount());
        assertThat(result.getContent().get(0).getCountry()).isEqualTo(response.getCountry());
        assertThat(result.getContent().get(0).getManufacturer()).isEqualTo(
            response.getManufacturer());
        assertThat(result.getContent().get(0).getColor()).isEqualTo(response.getColor());
        assertThat(result.getContent().get(0).getQuality()).isEqualTo(response.getQuality());
        assertThat(result.getContent().get(0).getQuantity()).isEqualTo(response.getQuantity());
        assertThat(result.getContent().get(0).getImg1()).isEqualTo(response.getImg1());
        assertThat(result.getContent().get(0).getImg2()).isEqualTo(response.getImg2());
        assertThat(result.getContent().get(0).getImg3()).isEqualTo(response.getImg3());
        assertThat(result.getContent().get(0).getImg4()).isEqualTo(response.getImg4());
        assertThat(result.getContent().get(0).getImg5()).isEqualTo(response.getImg5());
        assertThat(result.getContent().get(0).getLevel()).isEqualTo(response.getLevel());
    }
}
