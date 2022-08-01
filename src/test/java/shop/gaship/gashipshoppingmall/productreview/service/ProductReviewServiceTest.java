package shop.gaship.gashipshoppingmall.productreview.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dummy.ProductReviewDummy;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.exception.ProductReviewNotFoundException;
import shop.gaship.gashipshoppingmall.productreview.repository.ProductReviewRepository;
import shop.gaship.gashipshoppingmall.productreview.service.impl.ProductReviewServiceImpl;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

/**
 * 상품평 서비스 테스트입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(ProductReviewServiceImpl.class)
class ProductReviewServiceTest {
    @Autowired
    ProductReviewService productReviewService;

    @MockBean
    ProductReviewRepository productReviewRepository;

    @MockBean
    OrderProductRepository orderProductRepository;

    @MockBean
    FileUploadUtil fileUploadUtil;

    ProductReview review;
    ProductReviewRequestDto createRequest;
    ProductReviewRequestDto modifyRequest;
    OrderProduct orderProduct;
    MockMultipartFile multipartFile;
    String uploadDir =  File.separator + "reviews";

    @BeforeEach
    void setUp() throws IOException {
        review = ProductReviewDummy.dummy();
        createRequest = ProductReviewDummy.createRequestDummy();
        modifyRequest = ProductReviewDummy.modifyRequestDummy();
        orderProduct = OrderProduct.builder()
                .warrantyExpirationDate(LocalDate.now())
                .amount(10000L)
                .build();

        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));
    }

    @DisplayName("상품평 등록 성공 테스트")
    @Test
    void addProductReviewSuccess() {
        ReflectionTestUtils.setField(review, "orderProductNo", createRequest.getOrderProductNo());
        ReflectionTestUtils.setField(review, "imagePath", multipartFile.getOriginalFilename());
        
        when(orderProductRepository.findById(createRequest.getOrderProductNo()))
                .thenReturn(Optional.of(orderProduct));
        when(fileUploadUtil.uploadFile(uploadDir, List.of(multipartFile)))
                .thenReturn(List.of(multipartFile.getOriginalFilename()));
        when(productReviewRepository.save(any(ProductReview.class)))
                .thenReturn(review);

        productReviewService.addProductReview(multipartFile, createRequest);

        assertProductReview(createRequest);

        verify(orderProductRepository).findById(createRequest.getOrderProductNo());
        verify(fileUploadUtil).uploadFile(uploadDir, List.of(multipartFile));
        verify(productReviewRepository).save(any(ProductReview.class));
    }

    @DisplayName("상품평 등록 실패 테스트 - 주문상품을 찾을 수 없음")
    @Test
    void addProductReviewFailure_notFoundOrderProduct() {
        when(orderProductRepository.findById(createRequest.getOrderProductNo()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.addProductReview(multipartFile, createRequest))
                .isInstanceOf(OrderProductNotFoundException.class);

        verify(orderProductRepository).findById(createRequest.getOrderProductNo());
    }

    @DisplayName("상품평 수정 성공 테스트")
    @Test
    void modifyProductReviewSuccess() {
        ReflectionTestUtils.setField(review, "orderProductNo", modifyRequest.getOrderProductNo());
        String deletedFilePath = review.getImagePath();

        when(productReviewRepository.findById(modifyRequest.getOrderProductNo()))
                .thenReturn(Optional.of(review));
        doNothing().when(fileUploadUtil).cleanUpFiles(List.of(deletedFilePath));
        when(fileUploadUtil.uploadFile(uploadDir, List.of(multipartFile)))
                .thenReturn(List.of(multipartFile.getOriginalFilename()));

        productReviewService.modifyProductReview(multipartFile, modifyRequest);

        assertProductReview(modifyRequest);

        verify(productReviewRepository).findById(modifyRequest.getOrderProductNo());
        verify(fileUploadUtil).cleanUpFiles(List.of(deletedFilePath));
        verify(fileUploadUtil).uploadFile(uploadDir, List.of(multipartFile));
    }

    @DisplayName("상품평 수정 실패 테스트 - 상품평을 찾을 수 없음")
    @Test
    void modifyProductReviewFailure_notFoundProductReview() {
        when(productReviewRepository.findById(modifyRequest.getOrderProductNo()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.modifyProductReview(multipartFile, modifyRequest))
                .isInstanceOf(ProductReviewNotFoundException.class);

        verify(productReviewRepository).findById(modifyRequest.getOrderProductNo());
    }

    @DisplayName("상품평 삭제 성공 테스트")
    @Test
    void removeProductReview() {
        ReflectionTestUtils.setField(review, "orderProductNo", modifyRequest.getOrderProductNo());
        Integer orderProductNo = review.getOrderProductNo();

        when(productReviewRepository.findById(modifyRequest.getOrderProductNo()))
                .thenReturn(Optional.of(review));
        doNothing().when(productReviewRepository).deleteById(orderProductNo);

        productReviewService.removeProductReview(orderProductNo);

        verify(productReviewRepository).findById(modifyRequest.getOrderProductNo());
        verify(productReviewRepository).deleteById(orderProductNo);
    }

    @DisplayName("상품평 삭제 실패 테스트 - 상품평을 찾을 수 없음")
    @Test
    void removeProductReviewFailure_notFoundProductReview() {
        Integer orderProductNo = 9999;

        when(productReviewRepository.findById(orderProductNo))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.removeProductReview(orderProductNo))
                .isInstanceOf(ProductReviewNotFoundException.class);

        verify(productReviewRepository).findById(orderProductNo);
    }

    private void assertProductReview(ProductReviewRequestDto reviewRequest) {
        assertThat(review.getOrderProductNo()).isEqualTo(reviewRequest.getOrderProductNo());
        assertThat(review.getTitle()).isEqualTo(reviewRequest.getTitle());
        assertThat(review.getContent()).isEqualTo(reviewRequest.getContent());
        assertThat(review.getImagePath()).isEqualTo(multipartFile.getOriginalFilename());
        assertThat(review.getStarScore()).isEqualTo(reviewRequest.getStarScore());
    }
}