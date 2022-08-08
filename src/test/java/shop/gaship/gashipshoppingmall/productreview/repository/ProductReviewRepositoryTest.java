package shop.gaship.gashipshoppingmall.productreview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewViewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.dummy.ProductReviewDummy;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 상품평 레퍼지토리 테스트
 *
 * @author : 김보민
 * @since 1.0
 */
@DataJpaTest
class ProductReviewRepositoryTest {
    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AddressListRepository addressListRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    TestEntityManager entityManager;

    List<ProductReview> reviews = new ArrayList<>();

    @BeforeEach
    void setUp() {
        StatusCode statusCode = entityManager.persist(StatusCodeDummy.dummy());
        Product productDummy = ProductDummy.dummy();
        ReflectionTestUtils.setField(productDummy, "deliveryType", statusCode);
        ReflectionTestUtils.setField(productDummy, "salesStatus", statusCode);

        Member memberDummy = MemberDummy.dummy();
        entityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        entityManager.persist(memberDummy.getMemberStatusCodes());
        entityManager.persist(memberDummy.getMemberGrades());

        Member member = memberRepository.save(memberDummy);

        AddressLocal addressLocal = AddressLocalDummy.dummy1();
        entityManager.persist(addressLocal);

        AddressList addressList = addressListRepository.save(AddressList.builder()
                .addressLocal(addressLocal)
                .member(member)
                .statusCode(statusCode)
                .address("주소")
                .addressDetail("상세주소")
                .zipCode("11111")
                .build());

        Product product = entityManager.persist(productDummy);
        Order order = entityManager.persist(
                Order.builder()
                        .member(member)
                        .addressList(addressList)
                        .receiptName("수령인 이름")
                        .receiptPhoneNumber("01011111111")
                        .build()
        );

        IntStream.range(0, 10).forEach(i -> {
            reviews.add(ProductReviewDummy.dummy(orderProductRepository.save(OrderProduct.builder()
                    .product(product)
                    .order(order)
                    .orderStatusCode(statusCode)
                    .warrantyExpirationDate(LocalDate.now())
                    .amount(10000L)
                    .build())));
        });
    }

    @DisplayName("상품평 레퍼지토리 저장 테스트")
    @Test
    void saveTest() {
        ProductReview review = reviews.get(0);
        ReflectionTestUtils.setField(review, "modifyDatetime", LocalDateTime.now());

        ProductReview savedReview = productReviewRepository.save(review);

        assertThat(savedReview.getOrderProductNo()).isEqualTo(review.getOrderProductNo());
        assertThat(savedReview.getTitle()).isEqualTo(review.getTitle());
        assertThat(savedReview.getContent()).isEqualTo(review.getContent());
        assertThat(savedReview.getImagePath()).isEqualTo(review.getImagePath());
        assertThat(savedReview.getStarScore()).isEqualTo(review.getStarScore());
        assertThat(savedReview.getRegisterDatetime()).isNotNull();
        assertThat(savedReview.getModifyDatetime()).isNotNull();
    }

    @DisplayName("상품평 레퍼지토리 삭제 테스트")
    @Test
    void deleteByIdTest() {
        ProductReview review = reviews.get(0);
        ProductReview savedReview = productReviewRepository.save(review);

        productReviewRepository.deleteById(savedReview.getOrderProductNo());

        assertThat(productReviewRepository.findById(savedReview.getOrderProductNo())).isEmpty();
    }

    @DisplayName("상품평 레퍼지토리 조회 테스트")
    @Test
    void findByIdTest() {
        ProductReview savedReview = productReviewRepository.save(reviews.get(0));

        Optional<ProductReview> found =
                productReviewRepository.findById(savedReview.getOrderProductNo());

        assertThat(found).isPresent();
        assertThat(found.get().getOrderProductNo()).isEqualTo(savedReview.getOrderProductNo());
        assertThat(found.get().getTitle()).isEqualTo(savedReview.getTitle());
        assertThat(found.get().getContent()).isEqualTo(savedReview.getContent());
        assertThat(found.get().getImagePath()).isEqualTo(savedReview.getImagePath());
        assertThat(found.get().getStarScore()).isEqualTo(savedReview.getStarScore());
    }

    @DisplayName("상품평 단건 조회 테스트")
    @Test
    void findProductReview() {
        ProductReview savedReview = productReviewRepository.save(reviews.get(0));
        ProductReviewViewRequestDto viewRequest = ProductReviewViewRequestDto.builder()
                .orderProductNo(savedReview.getOrderProductNo()).build();

        Page<ProductReviewResponseDto> result =
                productReviewRepository.findProductReviews(viewRequest);

        assertThat(result).hasSize(1);
        assertProductReviewResponse(result.getContent());
    }

    @DisplayName("상품평 전체 조회 테스트")
    @Test
    void findProductReviews() {
        productReviewRepository.saveAll(reviews);
        ProductReviewViewRequestDto viewRequest = ProductReviewViewRequestDto.builder()
                .build();

        Page<ProductReviewResponseDto> result =
                productReviewRepository.findProductReviews(viewRequest);

        assertThat(result).hasSize(viewRequest.getPageable().getPageSize());
        assertProductReviewResponse(result.getContent());
    }

    @DisplayName("상품번호로 상품평 다건 조회 테스트")
    @Test
    void findProductReviewsByProductNo() {
        List<ProductReview> savedReviews = productReviewRepository.saveAll(reviews);
        ProductReviewViewRequestDto viewRequest = ProductReviewViewRequestDto.builder()
                .productNo(savedReviews.get(0).getOrderProduct().getProduct().getNo()).build();

        Page<ProductReviewResponseDto> result =
                productReviewRepository.findProductReviews(viewRequest);

        assertThat(result).hasSize(viewRequest.getPageable().getPageSize());
        assertProductReviewResponse(result.getContent());
    }

    @DisplayName("회원번호로 상품평 다건 조회 테스트")
    @Test
    void findProductReviewsByMemberNo() {
        List<ProductReview> savedReviews = productReviewRepository.saveAll(reviews);
        ProductReviewViewRequestDto viewRequest = ProductReviewViewRequestDto.builder()
                .memberNo(savedReviews.get(0).getOrderProduct().getOrder().getMember().getMemberNo()).build();

        Page<ProductReviewResponseDto> result =
                productReviewRepository.findProductReviews(viewRequest);

        assertThat(result).hasSize(viewRequest.getPageable().getPageSize());
        assertProductReviewResponse(result.getContent());
    }

    private void assertProductReviewResponse(List<ProductReviewResponseDto> responseDtos) {
        IntStream.range(0, responseDtos.size()).forEach(i -> {
            ProductReview review = reviews.get(i);
            assertThat(responseDtos.get(i).getOrderProductNo()).isEqualTo(review.getOrderProduct().getNo());
            assertThat(responseDtos.get(i).getWriterNickname()).isEqualTo(review.getOrderProduct().getOrder().getMember().getNickname());
            assertThat(responseDtos.get(i).getProductName()).isEqualTo(review.getOrderProduct().getProduct().getName());
            assertThat(responseDtos.get(i).getTitle()).isEqualTo(review.getTitle());
            assertThat(responseDtos.get(i).getContent()).isEqualTo(review.getContent());
            assertThat(responseDtos.get(i).getStarScore()).isEqualTo(review.getStarScore());
            assertThat(responseDtos.get(i).getImagePath()).isEqualTo(review.getImagePath());
            assertThat(responseDtos.get(i).getRegisterDateTime()).isNotNull();
            assertThat(responseDtos.get(i).getModifyDateTime()).isEqualTo(review.getModifyDatetime());
        });
    }
}