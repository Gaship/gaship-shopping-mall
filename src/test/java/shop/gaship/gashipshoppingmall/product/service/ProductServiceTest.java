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
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.dummy.ProductResponseDtoDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;

import java.util.ArrayList;
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
    CategoryRepository categoryRepository;

    ProductResponseDto responseDto;

    Product product;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy2();
        responseDto = ProductResponseDtoDummy.dummy();
    }

    @DisplayName("상품코드로 다건 조회하기")
    @Test
    void productFindByCodeTest() {
        //given
        given(repository.findByCode(responseDto.getProductCode()))
                .willReturn(List.of(responseDto));
        //when
        List<ProductResponseDto> result
                = service.findProductByCode(responseDto.getProductCode());

        //then
        verify(repository, times(1))
                .findByCode(any());
        checkDtoList(result.get(0));
    }

    @DisplayName("상품전체조회 페이징")
    @Test
    void productFindAllPage() {
        //given
        List<ProductResponseDto> list = new ArrayList<>();
        list.add(responseDto);
        PageRequest req = PageRequest.of(1, 10);
        Page<ProductResponseDto> pages = new PageImpl<>(list, req, list.size());

        given(repository.findAllPage(any()))
                .willReturn(pages);

        //when
        Page<ProductResponseDto> result = service.findProducts(req.getPageNumber(), req.getPageSize());

        assertThat(result.getTotalPages()).isEqualTo(pages.getTotalPages());
        assertThat(result.getSize()).isEqualTo(req.getPageSize());
    }

    @DisplayName("상품단건 조회 테스트")
    @Test
    void productFindOneTest() {
        //given
        given(repository.findByProductNo(any()))
                .willReturn(Optional.of(responseDto));

        //when
        ProductResponseDto result = service.findProduct(responseDto.getNo());

        //then
        verify(repository, times(1))
                .findByProductNo(any());
        checkDtoList(result);
    }

    @DisplayName("상품 단건 조회테스트 실패")
    @Test
    void productFineOneTestFail() {
        //given
        given(repository.findByProductNo(any()))
                .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> service.findProduct(1))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("상품 금액으로 조회")
    @Test
    void productFindByPrice() {
        //given
        given(repository.findByPrice(0L, responseDto.getAmount()))
                .willReturn(List.of(responseDto));

        //when
        List<ProductResponseDto> result = service.findProductByPrice(0L, responseDto.getAmount());

        //then
        verify(repository, times(1))
                .findByPrice(any(Long.class), any(Long.class));

        checkDtoList(result.get(0));
    }

    @DisplayName("카테고리 번호로 조회하기 실패")
    @Test
    void productFindByCategoryFail() {
        //given
        given(categoryRepository.findById(responseDto.getNo()))
                .willReturn(Optional.empty());
        given(repository.findProductByCategory(responseDto.getNo()))
                .willReturn(List.of(responseDto));
        //when
        assertThatThrownBy(() -> service.findProductByCategory(responseDto.getNo()))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @DisplayName("카테고리 번호로 조회하기 성공")
    @Test
    void productFindByCategorySuccess(){
        //given
        given(categoryRepository.findById(product.getCategory().getNo()))
                .willReturn(Optional.of(product.getCategory()));
        given(repository.findProductByCategory(product.getCategory().getNo()))
                .willReturn(List.of(responseDto));

        //when
        List<ProductResponseDto> result = service.findProductByCategory(product.getCategory().getNo());

        //then
        verify(repository,times(1)).findProductByCategory(any());

        checkDtoList(result.get(0));
    }

    @DisplayName("제품이름으로 조회하기")
    @Test
    void productFindByProductName(){
        //given
        given(repository.findByProductName(product.getName()))
                .willReturn(List.of(responseDto));

        //when
        List<ProductResponseDto> result = service.findProductByName(product.getName());

        //then
        verify(repository, times(1)).findByProductName(any());

        checkDtoList(result.get(0));
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