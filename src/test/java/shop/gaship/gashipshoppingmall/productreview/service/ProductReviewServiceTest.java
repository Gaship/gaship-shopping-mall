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
import java.util.Collections;
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
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewViewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.dummy.ProductReviewDummy;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.exception.ProductReviewNotFoundException;
import shop.gaship.gashipshoppingmall.productreview.repository.ProductReviewRepository;
import shop.gaship.gashipshoppingmall.productreview.service.impl.ProductReviewServiceImpl;

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
    ProductRepository productRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    CommonFileRepository fileRepository;

    @MockBean
    CommonFileService fileService;

    ProductReview review;
    ProductReviewResponseDto responseDummy;
    ProductReviewRequestDto createRequest;
    ProductReviewRequestDto modifyRequest;
    OrderProduct orderProduct;
    MockMultipartFile multipartFile;
    Pageable pageable;

    @BeforeEach
    void setUp() throws IOException {
        Order order = OrderDummy.createOrderDummy();
        orderProduct = OrderProduct.builder()
                .order(order)
                .warrantyExpirationDate(LocalDate.now())
                .amount(10000L)
                .build();
        review = ProductReviewDummy.dummy(orderProduct);
        responseDummy = ProductReviewDummy.responseDummy();
        createRequest = ProductReviewDummy.createRequestDummy();
        modifyRequest = ProductReviewDummy.modifyRequestDummy();

        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));
        pageable = PageRequest.of(0, 5);
    }

    @DisplayName("상품평 등록 성공 테스트")
    @Test
    void addProductReviewSuccess() {
        ReflectionTestUtils.setField(review, "orderProductNo", createRequest.getOrderProductNo());

        when(orderProductRepository.findById(createRequest.getOrderProductNo()))
                .thenReturn(Optional.of(orderProduct));
        when(fileService.createCommonFile(any())).thenReturn(new CommonFile());
        when(productReviewRepository.save(any(ProductReview.class)))
                .thenReturn(review);

        productReviewService.addProductReview(multipartFile, createRequest, null);

        assertProductReview(createRequest);

        verify(orderProductRepository).findById(createRequest.getOrderProductNo());
        verify(fileService).createCommonFile(any());
        verify(productReviewRepository).save(any(ProductReview.class));
    }

    @DisplayName("상품평 등록 실패 테스트 - 주문상품을 찾을 수 없음")
    @Test
    void addProductReviewFailure_notFoundOrderProduct() {
        when(orderProductRepository.findById(createRequest.getOrderProductNo()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.addProductReview(multipartFile, createRequest, null))
                .isInstanceOf(OrderProductNotFoundException.class);

        verify(orderProductRepository).findById(createRequest.getOrderProductNo());
    }

    @DisplayName("상품평 수정 성공 테스트")
    @Test
    void modifyProductReviewSuccess() {
        ReflectionTestUtils.setField(review, "orderProductNo", modifyRequest.getOrderProductNo());

        when(productReviewRepository.findById(modifyRequest.getOrderProductNo()))
                .thenReturn(Optional.of(review));
        when(fileService.createCommonFile(any())).thenReturn(new CommonFile());
        productReviewService.modifyProductReview(multipartFile, modifyRequest, null);

        assertProductReview(modifyRequest);

        verify(productReviewRepository).findById(modifyRequest.getOrderProductNo());
        verify(fileService).createCommonFile(any());
    }

    @DisplayName("상품평 수정 실패 테스트 - 상품평을 찾을 수 없음")
    @Test
    void modifyProductReviewFailure_notFoundProductReview() {
        when(productReviewRepository.findById(modifyRequest.getOrderProductNo()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.modifyProductReview(multipartFile, modifyRequest, null))
                .isInstanceOf(ProductReviewNotFoundException.class);

        verify(productReviewRepository).findById(modifyRequest.getOrderProductNo());
    }

    @DisplayName("상품평 삭제 성공 테스트")
    @Test
    void removeProductReviewSuccess() {
        ReflectionTestUtils.setField(review, "orderProductNo", modifyRequest.getOrderProductNo());
        Integer orderProductNo = review.getOrderProductNo();

        when(productReviewRepository.findById(modifyRequest.getOrderProductNo()))
                .thenReturn(Optional.of(review));
        doNothing().when(productReviewRepository).deleteById(orderProductNo);

        productReviewService.removeProductReview(orderProductNo, null, null);

        verify(productReviewRepository).findById(modifyRequest.getOrderProductNo());
        verify(productReviewRepository).deleteById(orderProductNo);
    }

    @DisplayName("상품평 삭제 실패 테스트 - 상품평을 찾을 수 없음")
    @Test
    void removeProductReviewFailure_notFoundProductReview() {
        Integer orderProductNo = 9999;

        when(productReviewRepository.findById(orderProductNo))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.removeProductReview(orderProductNo, null, null))
                .isInstanceOf(ProductReviewNotFoundException.class);

        verify(productReviewRepository).findById(orderProductNo);
    }

    @DisplayName("상품평 단건 조회 성공 테스트")
    @Test
    void findReviewSuccess() {
        Integer orderProductNo = 1;

        when(orderProductRepository.findById(orderProductNo)).thenReturn(Optional.of(orderProduct));
        when(productReviewRepository.findProductReviews(any(ProductReviewViewRequestDto.class)))
                .thenReturn(new PageImpl<>(List.of(responseDummy)));

        ProductReviewResponseDto responseDto = productReviewService.findReview(orderProductNo);
        assertProductReviewResponseDto(responseDto);

        verify(orderProductRepository).findById(orderProductNo);
        verify(productReviewRepository).findProductReviews(any(ProductReviewViewRequestDto.class));
    }

    @DisplayName("상품평 단건 조회 실패 테스트 - 주문상품이 없는 경우")
    @Test
    void findReviewFailure_notFoundOrderProduct() {
        Integer orderProductNo = 1;

        when(orderProductRepository.findById(orderProductNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.findReview(orderProductNo))
                .isInstanceOf(OrderProductNotFoundException.class);

        verify(orderProductRepository).findById(orderProductNo);
    }

    @DisplayName("상품평 단건 조회 실패 테스트 - 상품평이 없는 경우")
    @Test
    void findReviewFailure_notFoundProductReview() {
        Integer orderProductNo = 1;

        when(orderProductRepository.findById(orderProductNo)).thenReturn(Optional.of(orderProduct));
        when(productReviewRepository.findProductReviews(any(ProductReviewViewRequestDto.class)))
                .thenReturn(new PageImpl<>(List.of()));

        assertThatThrownBy(() -> productReviewService.findReview(orderProductNo))
                .isInstanceOf(ProductReviewNotFoundException.class);

        verify(orderProductRepository).findById(orderProductNo);
        verify(productReviewRepository).findProductReviews(any(ProductReviewViewRequestDto.class));
    }

    @DisplayName("상품평 전체 조회 성공 테스트")
    @Test
    void findReviewsTestSuccess() {
        when(productReviewRepository.findProductReviews(any(ProductReviewViewRequestDto.class)))
                .thenReturn(new PageImpl<>(List.of(responseDummy)));

        Page<ProductReviewResponseDto> responseDtos = productReviewService.findReviews(pageable);
        assertThat(responseDtos).hasSize(1);
        assertProductReviewResponseDto(responseDtos.getContent().get(0));

        verify(productReviewRepository).findProductReviews(any(ProductReviewViewRequestDto.class));
    }

    @DisplayName("상품번호로 상품평 조회 성공 테스트")
    @Test
    void findReviewsByProductNoSuccess() {
        Integer productNo = 1;

        when(productRepository.findById(productNo)).thenReturn(Optional.of(ProductDummy.dummy()));
        when(productReviewRepository.findProductReviews(any(ProductReviewViewRequestDto.class)))
                .thenReturn(new PageImpl<>(List.of(responseDummy)));

        Page<ProductReviewResponseDto> responseDtos = productReviewService.findReviewsByProductNo(productNo, pageable);
        assertThat(responseDtos).hasSize(1);
        assertProductReviewResponseDto(responseDtos.getContent().get(0));

        verify(productRepository).findById(productNo);
        verify(productReviewRepository).findProductReviews(any(ProductReviewViewRequestDto.class));
    }

    @DisplayName("상품번호로 상품평 조회 실패 테스트")
    @Test
    void findReviewsByProductNoFailure_notFoundProduct() {
        Integer productNo = 1;

        when(productRepository.findById(productNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.findReviewsByProductNo(productNo, pageable))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(productNo);
    }

    @DisplayName("회원번호로 상품평 조회 성공 테스트")
    @Test
    void findReviewsByMemberNoSuccess() {
        Integer memberNo = 1;

        when(memberRepository.findById(memberNo)).thenReturn(Optional.of(MemberDummy.dummy()));
        when(productReviewRepository.findProductReviews(any(ProductReviewViewRequestDto.class)))
                .thenReturn(new PageImpl<>(List.of(responseDummy)));

        Page<ProductReviewResponseDto> responseDtos = productReviewService.findReviewsByMemberNo(memberNo, pageable);
        assertThat(responseDtos).hasSize(1);
        assertProductReviewResponseDto(responseDtos.getContent().get(0));

        verify(memberRepository).findById(memberNo);
        verify(productReviewRepository).findProductReviews(any(ProductReviewViewRequestDto.class));
    }

    @DisplayName("회원번호로 상품평 조회 실패 테스트")
    @Test
    void findReviewsByMemberNoFailure_notFoundMember() {
        Integer memberNo = 1;

        when(memberRepository.findById(memberNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReviewService.findReviewsByMemberNo(memberNo, pageable))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberRepository).findById(memberNo);
    }

    private void assertProductReview(ProductReviewRequestDto reviewRequest) {
        assertThat(review.getOrderProductNo()).isEqualTo(reviewRequest.getOrderProductNo());
        assertThat(review.getTitle()).isEqualTo(reviewRequest.getTitle());
        assertThat(review.getContent()).isEqualTo(reviewRequest.getContent());
        assertThat(review.getStarScore()).isEqualTo(reviewRequest.getStarScore());
    }

    private void assertProductReviewResponseDto(ProductReviewResponseDto responseDto) {
        assertThat(responseDto.getOrderProductNo()).isEqualTo(responseDummy.getOrderProductNo());
        assertThat(responseDto.getWriterNickname()).isEqualTo(responseDummy.getWriterNickname());
        assertThat(responseDto.getProductName()).isEqualTo(responseDummy.getProductName());
        assertThat(responseDto.getTitle()).isEqualTo(responseDummy.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(responseDummy.getContent());
        assertThat(responseDto.getStarScore()).isEqualTo(responseDummy.getStarScore());
        assertThat(responseDto.getRegisterDateTime()).isNotNull();
        assertThat(responseDto.getModifyDateTime()).isNotNull();
    }
}