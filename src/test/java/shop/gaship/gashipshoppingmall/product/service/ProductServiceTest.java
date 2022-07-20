package shop.gaship.gashipshoppingmall.product.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
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
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @MockBean
    TagRepository tagRepository;

    @MockBean
    ProductTagRepository productTagRepository;

    private MockMultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));
    }

    @DisplayName("상품 등록 성공")
    @Test
    void addProduct() throws IOException {
        ProductCreateRequestDto createRequest = ProductDummy.createRequestDummy();

        when(categoryRepository.findById(createRequest.getCategoryNo()))
                .thenReturn(Optional.of(CategoryDummy.dummy()));
        when(statusCodeRepository.findById(createRequest.getDeliveryTypeNo()))
                .thenReturn(Optional.of(new StatusCode("설치", createRequest.getDeliveryTypeNo(), "배송형태", "")));
        when(statusCodeRepository.findByStatusCodeName(SalesStatus.SALE.getValue()))
                .thenReturn(Optional.of(new StatusCode("판매중", 2, "판매상태", "")));
        when(tagRepository.findById(createRequest.getTagNos().get(0)))
                .thenReturn(Optional.of(new Tag(1, "태그")));

        productService.addProduct(List.of(multipartFile), createRequest);

        verify(categoryRepository).findById(createRequest.getCategoryNo());
        verify(statusCodeRepository).findById(createRequest.getDeliveryTypeNo());
        verify(statusCodeRepository).findByStatusCodeName(SalesStatus.SALE.getValue());
    }

    @DisplayName("상품 수정 성공")
    @Test
    void modifyProduct() throws IOException {
        ProductModifyRequestDto modifyRequest = ProductDummy.modifyRequestDummy();
        Product product = ProductDummy.dummy();
        ReflectionTestUtils.setField(product, "no", modifyRequest.getNo());

        when(productRepository.findById(modifyRequest.getNo()))
                .thenReturn(Optional.of(product));
        when(categoryRepository.findById(modifyRequest.getCategoryNo()))
                .thenReturn(Optional.of(CategoryDummy.dummy()));
        when(statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo()))
                .thenReturn(Optional.of(new StatusCode("설치", modifyRequest.getDeliveryTypeNo(), "배송형태", "")));

        productService.modifyProduct(List.of(multipartFile), modifyRequest);

        verify(productRepository).findById(modifyRequest.getNo());
        verify(categoryRepository).findById(modifyRequest.getCategoryNo());
        verify(statusCodeRepository).findById(modifyRequest.getDeliveryTypeNo());
    }

    @DisplayName("상품 수정 실패 - 해당 상품 찾기 불가")
    @Test
    void modifyProductFail_NotFoundProduct() {
        ProductModifyRequestDto modifyRequest = ProductDummy.modifyRequestDummy();
        Integer productNo = modifyRequest.getNo();

        when(productRepository.findById(productNo))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.modifyProduct(List.of(multipartFile), modifyRequest))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(productNo);
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

        when(productRepository.findById(salesStatusModifyRequest.getProductNo()))
                .thenReturn(Optional.of(product));
        when(statusCodeRepository.findByStatusCodeName(salesStatusModifyRequest.getStatusCodeName()))
                .thenReturn(Optional.of(statusCode));

        productService.modifyProductSalesStatus(salesStatusModifyRequest);

        verify(productRepository)
                .findById(salesStatusModifyRequest.getProductNo());
        verify(statusCodeRepository)
                .findByStatusCodeName(salesStatusModifyRequest.getStatusCodeName());
    }
}