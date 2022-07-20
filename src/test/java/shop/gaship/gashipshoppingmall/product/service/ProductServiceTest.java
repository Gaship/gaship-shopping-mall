package shop.gaship.gashipshoppingmall.product.service;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.dummy.ProductResponseDtoDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    ProductTagRepository productTagRepository;

    @MockBean
    CategoryRepository categoryRepository;

    ProductResponseDto responseDto;

    Product product;
    ProductAllInfoResponseDto response;
    Tag tag;
    Page<ProductAllInfoResponseDto> page;
    PageRequest pageRequest;

    Category category;

    @BeforeEach
    void setUp() {
        category = CategoryDummy.bottomDummy();
        product = ProductDummy.dummy2();
        responseDto = ProductResponseDtoDummy.dummy();
        response = new ProductAllInfoResponseDto(1, "a", "d", "카테", 100L, LocalDateTime.now(), "아", "한국", "판매원", "가나다라", 100L, "w", "#RRRR", 1, "img", null, null, null, null, "설명", 3, "카테");
        tag = new Tag(1, "title");
        response.getTags().add(tag.getTitle());
        pageRequest = PageRequest.of(0, 10);
        page = new PageImpl(List.of(response), pageRequest, 1);
    }

    @DisplayName("상품코드로 다건 조회하기")
    @Test
    void productFindByCodeTest() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productCode(product.getProductCode())
                .pageable(pageRequest)
                .build();

        given(repository.findProduct(requestDto))
                .willReturn(page);
        given(productTagRepository.findTagByProductNo(any()))
                .willReturn(List.of(tag));
        //when

        PageResponse<ProductAllInfoResponseDto> result = service.findProductByCode(requestDto.getProductCode(), pageRequest);

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
                .willReturn(page);
        given(productTagRepository.findTagByProductNo(response.getProductNo()))
                .willReturn(List.of(tag));
        //when
        ProductAllInfoResponseDto result = service.findProduct(requestDto.getProductNo());
        //then
        verify(repository, times(1))
                .findById(any());

        verify(repository, times(1))
                .findProduct(any());

        verify(productTagRepository, times(1))
                .findTagByProductNo(tag.getTagNo());

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
                .willReturn(page);

        given(productTagRepository.findTagByProductNo(any()))
                .willReturn(List.of(tag));

        //when
        service.findProductByPrice(0L, product.getAmount(),
                pageRequest.getPageNumber(), pageRequest.getPageSize());

        //then
        verify(repository, times(1))
                .findProduct(any());

        verify(productTagRepository, times(1))
                .findTagByProductNo(any());

    }

    @DisplayName("카테고리 번호로 조회하기 실패")
    @Test
    void productFindByCategoryFail() {
        //given
        given(categoryRepository.findById(responseDto.getNo()))
                .willReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> service.findProductByCategory(responseDto.getNo(),
                pageRequest.getPageNumber(), pageRequest.getPageSize()))
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
                .willReturn(page);
        given(productTagRepository.findTagByProductNo(any()))
                .willReturn(List.of(tag));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(category));

        //when
        PageResponse<ProductAllInfoResponseDto> result = service.findProductByCategory(1, pageRequest.getPageNumber(), pageRequest.getPageSize());

        //then
        verify(repository, times(1)).findProduct(any());
        verify(productTagRepository, times(1)).findTagByProductNo(tag.getTagNo());
        verify(categoryRepository, times(1)).findById(any());

        checkContent(result);
    }

    @DisplayName("제품이름으로 조회하기")
    @Test
    void productFindByProductName() {
        //given
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productName(response.getProductName())
                .pageable(pageRequest)
                .build();

        given(repository.findProduct(requestDto))
                .willReturn(page);
        given(productTagRepository.findTagByProductNo(any()))
                .willReturn(List.of(tag));

        //when
        PageResponse<ProductAllInfoResponseDto> result = service.findProductByName(response.getProductName(), pageRequest.getPageNumber(), pageRequest.getPageSize());
        //then
        verify(repository, times(1)).findProduct(any());
        verify(productTagRepository, times(1)).findTagByProductNo(tag.getTagNo());

        checkContent(result);
    }

    @DisplayName("조건들을 통해 제품들 조회하기")
    @Test
    void findProductInfo() {
        //given
        ProductRequestDto requestDto = new ProductRequestDto();
        given(repository.findProduct(requestDto))
                .willReturn(page);
        given(productTagRepository.findTagByProductNo(any()))
                .willReturn(List.of(tag));
        //when
        PageResponse<ProductAllInfoResponseDto> result = service.findProductsInfo(pageRequest.getPageNumber(), pageRequest.getPageSize());

        //then
        verify(repository, times(1)).findProduct(requestDto);

        checkContent(result);
    }

    private void checkContent(PageResponse<ProductAllInfoResponseDto> result) {
        assertThat(result.getContent().get(0).getUpperName()).isEqualTo(response.getUpperName());
        assertThat(result.getContent().get(0).getProductNo()).isEqualTo(response.getProductNo());
        assertThat(result.getContent().get(0).getProductName()).isEqualTo(response.getProductName());
        assertThat(result.getContent().get(0).getProductCode()).isEqualTo(response.getProductCode());
        assertThat(result.getContent().get(0).getCategoryName()).isEqualTo(response.getCategoryName());
        assertThat(result.getContent().get(0).getAmount()).isEqualTo(response.getAmount());
        assertThat(result.getContent().get(0).getCountry()).isEqualTo(response.getCountry());
        assertThat(result.getContent().get(0).getManufacturer()).isEqualTo(response.getManufacturer());
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

    private void checkDtoList(ProductResponseDto result) {
        assertThat(result.getRegisterDatetime()).isEqualTo(responseDto.getRegisterDatetime());
        assertThat(result.getAmount()).isEqualTo(responseDto.getAmount());
        assertThat(result.getName()).isEqualTo(responseDto.getName());
        assertThat(result.getColor()).isEqualTo(responseDto.getColor());
        assertThat(result.getManufacturer()).isEqualTo(responseDto.getManufacturer());
        assertThat(result.getManufacturerCountry()).isEqualTo(responseDto.getManufacturerCountry());
        assertThat(result.getSeller()).isEqualTo(responseDto.getSeller());
        assertThat(result.getImporter()).isEqualTo(responseDto.getImporter());
        assertThat(result.getShippingInstallationCost()).isEqualTo(responseDto.getShippingInstallationCost());
        assertThat(result.getQualityAssuranceStandard()).isEqualTo(responseDto.getQualityAssuranceStandard());
        assertThat(result.getStockQuantity()).isEqualTo(responseDto.getStockQuantity());
        assertThat(result.getImageLink1()).isEqualTo(responseDto.getImageLink1());
        assertThat(result.getImageLink2()).isEqualTo(responseDto.getImageLink2());
        assertThat(result.getImageLink3()).isEqualTo(responseDto.getImageLink3());
        assertThat(result.getImageLink4()).isEqualTo(responseDto.getImageLink4());
        assertThat(result.getImageLink5()).isEqualTo(responseDto.getImageLink5());
        assertThat(result.getExplanation()).isEqualTo(responseDto.getExplanation());
        assertThat(result.getProductCode()).isEqualTo(responseDto.getProductCode());
    }
}