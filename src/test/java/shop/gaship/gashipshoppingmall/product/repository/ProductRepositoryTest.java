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
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

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

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ProductTagRepository productTagRepository;

    StatusCode code;
    Product product;

    Category category;

    Category upper;

    Category bottom;
    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy2();
        category = CategoryDummy.dummy();
        upper = CategoryDummy.upperDummy();
        code = StatusCodeDummy.dummy();
        bottom = CategoryDummy.bottomDummy();

        codeRepository.save(code);
        categoryRepository.save(upper);
        categoryRepository.save(category);
        categoryRepository.save(bottom);
        repository.save(product);

    }

    @DisplayName("전체 조회 paging 테스트 입니다.")
    @Test
    void productFindAllPageTest(){
        //then
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<ProductResponseDto> result = repository.findAllPage(pageRequest);

        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(pageRequest.getPageSize());
    }
    @DisplayName("다건 조회  테스트 입니다.")
    @Test
    void productsFindCodeTest(){
        //then
        List<ProductResponseDto> result
                = repository.findByCode(product.getProductCode());
        checkListResponse(result.get(0));
    }

    @DisplayName("단건 조회 테스트입니다.")
    @Test
    void productFindOne(){
        //then
        ProductResponseDto result = repository.findByProductNo(product.getNo()).get();
        checkListResponse(result);
    }

    @DisplayName("가격별로 정렬하기")
    @Test
    void productListPriceTest(){
        //then
        List<ProductResponseDto> result = repository.findByPrice(0L, product.getAmount());
        checkListResponse(result.get(0));
    }

    @DisplayName("카테고리 번호로 조회하기")
    @Test
    void productListCategory(){
        //then
        List<ProductResponseDto> result = repository.findProductByCategory(product.getCategory().getNo());
        checkListResponse(result.get(0));
    }

    @DisplayName("상품 이름으로 조회하기")
    @Test
    void productListProductName(){
        //given
        List<ProductResponseDto> result = repository.findByProductName(product.getName());
        checkListResponse(result.get(0));
    }

    @DisplayName("상품 태그로 조회하기")
    @Test
    void productListTag(){
        //given
        Product product2 = ProductDummy.dummy2();
        PageRequest pageRequest = PageRequest.of(1, 10);
        Tag tag = new Tag(1,"title");
        tagRepository.save(tag);

        ProductTag productTag = new ProductTag(new ProductTag.Pk(product2.getNo(), tag.getTagNo()), product2, tag);
        product.add(productTag);

        repository.save(product2);
        // 아래부분이 없으면 get 에서 null 이 뜸 이유가 뭘까?.
        productTagRepository.save(productTag);

        List<ProductAllInfoResponseDto> result = repository.findProduct(null, null, 0, 0L, 0L, 0, null);

        assertThat(result.get(0).getUpperName()).isEqualTo(category.getName().concat("-").concat(upper.getName()));
        assertThat(result.get(0).getProductNo()).isEqualTo(1);
        assertThat(result.get(0).getProductName()).isEqualTo(product2.getName());
        assertThat(result.get(0).getProductCode()).isEqualTo(product2.getProductCode());
        assertThat(result.get(0).getCategoryName()).isEqualTo(product2.getCategory().getName());
        assertThat(result.get(0).getAmount()).isEqualTo(product2.getAmount());
        assertThat(result.get(0).getCountry()).isEqualTo(product2.getManufacturerCountry());
        assertThat(result.get(0).getManufacturer()).isEqualTo(product2.getManufacturer());
        assertThat(result.get(0).getColor()).isEqualTo(product2.getColor());
        assertThat(result.get(0).getQuality()).isEqualTo(product2.getQualityAssuranceStandard());
        assertThat(result.get(0).getQuantity()).isEqualTo(product2.getStockQuantity());
        assertThat(result.get(0).getImg1()).isEqualTo(product2.getImageLink1());
        assertThat(result.get(0).getImg2()).isEqualTo(product2.getImageLink2());
        assertThat(result.get(0).getImg3()).isEqualTo(product2.getImageLink3());
        assertThat(result.get(0).getImg4()).isEqualTo(product2.getImageLink4());
        assertThat(result.get(0).getImg5()).isEqualTo(product2.getImageLink5());
        assertThat(result.get(0).getLevel()).isEqualTo(product2.getCategory().getLevel());
    }

    private void checkListResponse(ProductResponseDto result) {
        assertThat(result.getNo()).isEqualTo(2);
        assertThat(result.getAmount()).isEqualTo(product.getAmount());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getColor()).isEqualTo(product.getColor());
        assertThat(result.getManufacturer()).isEqualTo(product.getManufacturer());
        assertThat(result.getManufacturerCountry()).isEqualTo(product.getManufacturerCountry());
        assertThat(result.getSeller()).isEqualTo(product.getSeller());
        assertThat(result.getImporter()).isEqualTo(product.getImporter());
        assertThat(result.getShippingInstallationCost()).isEqualTo(product.getShippingInstallationCost());
        assertThat(result.getQualityAssuranceStandard()).isEqualTo(product.getQualityAssuranceStandard());
        assertThat(result.getStockQuantity()).isEqualTo(product.getStockQuantity());
        assertThat(result.getImageLink1()).isEqualTo(product.getImageLink1());
        assertThat(result.getImageLink2()).isEqualTo(product.getImageLink2());
        assertThat(result.getImageLink3()).isEqualTo(product.getImageLink3());
        assertThat(result.getImageLink4()).isEqualTo(product.getImageLink4());
        assertThat(result.getImageLink5()).isEqualTo(product.getImageLink5());
        assertThat(result.getExplanation()).isEqualTo(product.getExplanation());
        assertThat(result.getProductCode()).isEqualTo(product.getProductCode());
    }
}