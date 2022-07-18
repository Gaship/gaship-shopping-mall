package shop.gaship.gashipshoppingmall.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 상품 레퍼지토리 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StatusCodeRepository codeRepository;

    StatusCode code;
    Product product;

    Category category;

    Category upper;
    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy2();
        category = CategoryDummy.dummy();
        upper = CategoryDummy.upperDummy();
        code = StatusCodeDummy.dummy();
    }

    @DisplayName("전체 조회 paging 테스트 입니다.")
    @Test
    void productFindAllPageTest(){
        //given & when
        codeRepository.save(code);
        categoryRepository.save(upper);
        categoryRepository.save(category);
        repository.save(product);

        //then
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<ProductResponseDto> result = repository.findAllPage(pageRequest);

        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(pageRequest.getPageSize());
    }
    @DisplayName("다건 조회  테스트 입니다.")
    @Test
    void productsFindCodeTest(){
        //given & when
        codeRepository.save(code);
        categoryRepository.save(upper);
        categoryRepository.save(category);
        repository.save(product);
        //then
        List<ProductResponseDto> result
                = repository.findByCode(product.getProductCode());
        assertThat(result.get(0).getNo()).isEqualTo(product.getNo());
        assertThat(result.get(0).getAmount()).isEqualTo(product.getAmount());
        assertThat(result.get(0).getName()).isEqualTo(product.getName());
        assertThat(result.get(0).getColor()).isEqualTo(product.getColor());
        assertThat(result.get(0).getManufacturer()).isEqualTo(product.getManufacturer());
        assertThat(result.get(0).getManufacturerCountry()).isEqualTo(product.getManufacturerCountry());
        assertThat(result.get(0).getSeller()).isEqualTo(product.getSeller());
        assertThat(result.get(0).getImporter()).isEqualTo(product.getImporter());
        assertThat(result.get(0).getShippingInstallationCost()).isEqualTo(product.getShippingInstallationCost());
        assertThat(result.get(0).getQualityAssuranceStandard()).isEqualTo(product.getQualityAssuranceStandard());
        assertThat(result.get(0).getStockQuantity()).isEqualTo(product.getStockQuantity());
        assertThat(result.get(0).getImageLink1()).isEqualTo(product.getImageLink1());
        assertThat(result.get(0).getImageLink2()).isEqualTo(product.getImageLink2());
        assertThat(result.get(0).getImageLink3()).isEqualTo(product.getImageLink3());
        assertThat(result.get(0).getImageLink4()).isEqualTo(product.getImageLink4());
        assertThat(result.get(0).getImageLink5()).isEqualTo(product.getImageLink5());
        assertThat(result.get(0).getExplanation()).isEqualTo(product.getExplanation());
        assertThat(result.get(0).getProductCode()).isEqualTo(product.getProductCode());
    }
}