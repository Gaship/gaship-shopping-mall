package shop.gaship.gashipshoppingmall.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Ref;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

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

    Category upperCategory;

    Category middleCategory;

    Category bottomCategory;

    Product product;

    StatusCode deliveryType;

    StatusCode salesStatus;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy();
        deliveryType = new StatusCode("설치", 1, "배송 형태", "");
        salesStatus = new StatusCode("판매중", 2, "판매 상태", "");
        ReflectionTestUtils.setField(product, "deliveryType", deliveryType);
        ReflectionTestUtils.setField(product, "salesStatus", salesStatus);

        bottomCategory = product.getCategory();
        middleCategory = product.getCategory().getUpperCategory();
        upperCategory = product.getCategory().getUpperCategory().getUpperCategory();
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
    void productFindOne() {
        Tag tag = new Tag(null, "title");
        Tag savedTag = tagRepository.save(tag);

        Product savedProduct = repository.save(product);
        ProductTag productTag =
                new ProductTag(new ProductTag.Pk(savedProduct.getNo(), savedTag.getTagNo()),
                        savedProduct, savedTag);
        productTagRepository.save(productTag);

        ProductRequestDto requestDto = ProductRequestDto.builder()
                .productNo(savedProduct.getNo())
                .build();

        List<ProductAllInfoResponseDto> result =
                repository.findProduct(requestDto).getContent();


        assertThat(result.get(0).getProductNo()).isEqualTo(savedProduct.getNo());
        assertThat(result.get(0).getProductName()).isEqualTo(product.getName());
        assertThat(result.get(0).getProductCode()).isEqualTo(product.getCode());
        assertThat(result.get(0).getCategoryName()).isEqualTo(product.getCategory().getName());
        assertThat(result.get(0).getAmount()).isEqualTo(product.getAmount());
        assertThat(result.get(0).getCountry()).isEqualTo(product.getManufacturerCountry());
        assertThat(result.get(0).getManufacturer()).isEqualTo(product.getManufacturer());
        assertThat(result.get(0).getColor()).isEqualTo(product.getColor());
        assertThat(result.get(0).getQuality()).isEqualTo(product.getQualityAssuranceStandard());
        assertThat(result.get(0).getQuantity()).isEqualTo(product.getStockQuantity());
        assertThat(result.get(0).getImg1()).isEqualTo(product.getImageLink1());
        assertThat(result.get(0).getImg2()).isEqualTo(product.getImageLink2());
        assertThat(result.get(0).getImg3()).isEqualTo(product.getImageLink3());
        assertThat(result.get(0).getImg4()).isEqualTo(product.getImageLink4());
        assertThat(result.get(0).getImg5()).isEqualTo(product.getImageLink5());
        assertThat(result.get(0).getLevel()).isEqualTo(product.getCategory().getLevel());


    }

    @DisplayName("상품 전체 조회하기")
    @Test
    void productListTag() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Tag tag = new Tag(null, "title2");
        Tag savedTag = tagRepository.save(tag);

        Product savedProduct = repository.save(product);

        ProductTag productTag =
                new ProductTag(new ProductTag.Pk(savedProduct.getNo(), savedTag.getTagNo()), savedProduct, savedTag);

        productTagRepository.save(productTag);

        ProductRequestDto requestDto = new ProductRequestDto();

        PageResponse<ProductAllInfoResponseDto> page = repository.findProduct(requestDto);
        List<ProductAllInfoResponseDto> result = page.getContent();

        assertThat(page.getSize()).isEqualTo(pageRequest.getPageSize());

        assertThat(result.get(0).getProductName()).isEqualTo(product.getName());
        assertThat(result.get(0).getProductCode()).isEqualTo(product.getCode());
        assertThat(result.get(0).getCategoryName()).isEqualTo(product.getCategory().getName());
        assertThat(result.get(0).getAmount()).isEqualTo(product.getAmount());
        assertThat(result.get(0).getCountry()).isEqualTo(product.getManufacturerCountry());
        assertThat(result.get(0).getManufacturer()).isEqualTo(product.getManufacturer());
        assertThat(result.get(0).getColor()).isEqualTo(product.getColor());
        assertThat(result.get(0).getQuality()).isEqualTo(product.getQualityAssuranceStandard());
        assertThat(result.get(0).getQuantity()).isEqualTo(product.getStockQuantity());
        assertThat(result.get(0).getImg1()).isEqualTo(product.getImageLink1());
        assertThat(result.get(0).getImg2()).isEqualTo(product.getImageLink2());
        assertThat(result.get(0).getImg3()).isEqualTo(product.getImageLink3());
        assertThat(result.get(0).getImg4()).isEqualTo(product.getImageLink4());
        assertThat(result.get(0).getImg5()).isEqualTo(product.getImageLink5());
        assertThat(result.get(0).getLevel()).isEqualTo(product.getCategory().getLevel());
    }
}