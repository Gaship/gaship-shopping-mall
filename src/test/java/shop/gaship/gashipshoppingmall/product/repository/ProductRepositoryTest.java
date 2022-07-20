package shop.gaship.gashipshoppingmall.product.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
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

    Category category;

    Category upper;

    Category bottom;

    Product product;

    Product product2;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy();
        StatusCode deliveryType = new StatusCode("설치", 1, "배송 형태", "");
        StatusCode salesStatus = new StatusCode("판매중", 2, "판매 상태", "");
        ReflectionTestUtils.setField(product, "deliveryType", deliveryType);
        ReflectionTestUtils.setField(product, "salesStatus", salesStatus);

        product2 = ProductDummy.dummy2();
        category = CategoryDummy.dummy();
        upper = CategoryDummy.upperDummy();
        code = StatusCodeDummy.dummy();
        bottom = CategoryDummy.bottomDummy();

        codeRepository.save(code);
        categoryRepository.save(upper);
        categoryRepository.save(category);
        categoryRepository.save(bottom);
    }

    @DisplayName("상품 레퍼지토리 저장 테스트")
    @Test
    void saveProduct() {
        Product savedProduct = repository.save(product);
        ReflectionTestUtils.setField(product, "no", savedProduct.getNo());

        assertThat(savedProduct).isEqualTo(product);
    }

    @DisplayName("상품 엔티티 단건 조회 테스트")
    @Test
    void findProductById() {
        Product savedProduct = repository.save(product);

        Optional<Product> foundProduct = repository.findById(savedProduct.getNo());

        assertThat(foundProduct).isPresent();
        assertThat(savedProduct).isEqualTo(foundProduct.get());
    }

    @DisplayName("카테고리 번호를 통한 상품 엔티티 전체 조회 테스트")
    @Test
    void findAllByCategoryNo() {
        Product savedProduct = repository.save(product);

        List<Product> foundProducts = repository
                .findAllByCategoryNo(savedProduct.getCategory().getNo());

        assertThat(foundProducts).hasSize(1);
        assertThat(foundProducts.get(0)).isEqualTo(savedProduct);
    }

    @DisplayName("단건 조회 테스트입니다.")
    @Test
    void productFindOne(){
        //then
        repository.save(product2);

        ProductResponseDto result = repository.findByProductNo(product2.getNo()).get();
        checkListResponse(result);
    }

    @DisplayName("상품 전체 조회하기")
    @Test
    void productListTag(){
        //given
        Product product2 = ProductDummy.dummy2();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Tag tag = new Tag(1,"title");
        tagRepository.save(tag);

        ProductTag productTag = new ProductTag(new ProductTag.Pk(product2.getNo(), tag.getTagNo()), product2, tag);
        product2.add(productTag);

        repository.save(product2);
        // 아래부분이 없으면 get 에서 null 이 뜸 이유가 뭘까?.
        productTagRepository.save(productTag);
        ProductRequestDto requestDto = new ProductRequestDto();
        Page<ProductAllInfoResponseDto> page = repository.findProduct(requestDto);
        List<ProductAllInfoResponseDto> result = page.getContent();

        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getSize()).isEqualTo(pageRequest.getPageSize());

        assertThat(result.get(0).getProductName()).isEqualTo(product2.getName());
        assertThat(result.get(0).getProductCode()).isEqualTo(product2.getCode());
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
        assertThat(result.getNo()).isEqualTo(product2.getNo());
        assertThat(result.getAmount()).isEqualTo(product2.getAmount());
        assertThat(result.getName()).isEqualTo(product2.getName());
        assertThat(result.getColor()).isEqualTo(product2.getColor());
        assertThat(result.getManufacturer()).isEqualTo(product2.getManufacturer());
        assertThat(result.getManufacturerCountry()).isEqualTo(product2.getManufacturerCountry());
        assertThat(result.getSeller()).isEqualTo(product2.getSeller());
        assertThat(result.getImporter()).isEqualTo(product2.getImporter());
        assertThat(result.getShippingInstallationCost()).isEqualTo(product2.getShippingInstallationCost());
        assertThat(result.getQualityAssuranceStandard()).isEqualTo(product2.getQualityAssuranceStandard());
        assertThat(result.getStockQuantity()).isEqualTo(product2.getStockQuantity());
        assertThat(result.getImageLink1()).isEqualTo(product2.getImageLink1());
        assertThat(result.getImageLink2()).isEqualTo(product2.getImageLink2());
        assertThat(result.getImageLink3()).isEqualTo(product2.getImageLink3());
        assertThat(result.getImageLink4()).isEqualTo(product2.getImageLink4());
        assertThat(result.getImageLink5()).isEqualTo(product2.getImageLink5());
        assertThat(result.getExplanation()).isEqualTo(product2.getExplanation());
        assertThat(result.getCode()).isEqualTo(product2.getCode());
    }
}