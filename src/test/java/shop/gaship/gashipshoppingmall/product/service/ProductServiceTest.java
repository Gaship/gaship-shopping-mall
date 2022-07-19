package shop.gaship.gashipshoppingmall.product.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
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
@Slf4j
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


    @DisplayName("상품 등록 성공")
    @Test
    void addProduct() throws IOException {
        ProductCreateRequestDto createRequest = ProductDummy.createRequestDummy();
        File file = new File("src/test/resources/sample.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));

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
}