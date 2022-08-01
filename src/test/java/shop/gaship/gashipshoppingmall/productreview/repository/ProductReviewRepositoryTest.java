package shop.gaship.gashipshoppingmall.productreview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.dummy.NotNullDummy;
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

    ProductReview review;
    OrderProduct orderProduct;

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

        orderProduct = orderProductRepository.save(
                OrderProduct.builder()
                        .product(product)
                        .order(order)
                        .orderStatusCode(statusCode)
                        .warrantyExpirationDate(LocalDate.now())
                        .amount(10000L)
                        .build()
        );

        review = ProductReviewDummy.dummy();
        ReflectionTestUtils.setField(review, "orderProduct", orderProduct);
    }

    @DisplayName("상품평 레퍼지토리 저장 테스트")
    @Test
    void saveTest() {
        ProductReview savedReview = productReviewRepository.save(review);

        assertThat(savedReview.getOrderProductNo()).isEqualTo(review.getOrderProductNo());
        assertThat(savedReview.getTitle()).isEqualTo(review.getTitle());
        assertThat(savedReview.getContent()).isEqualTo(review.getContent());
        assertThat(savedReview.getImagePath()).isEqualTo(review.getImagePath());
        assertThat(savedReview.getStarScore()).isEqualTo(review.getStarScore());
    }

    @DisplayName("상품평 레퍼지토리 삭제 테스트")
    @Test
    void deleteByIdTest() {
        ProductReview savedReview = productReviewRepository.save(review);

        productReviewRepository.deleteById(savedReview.getOrderProductNo());

        assertThat(productReviewRepository.findById(savedReview.getOrderProductNo())).isEmpty();
    }

    @DisplayName("상품평 레퍼지토리 조회 테스트")
    @Test
    void findByIdTest() {
        ProductReview savedReview = productReviewRepository.save(review);

        Optional<ProductReview> found =
                productReviewRepository.findById(savedReview.getOrderProductNo());

        assertThat(found).isPresent();
        assertThat(found.get().getOrderProductNo()).isEqualTo(savedReview.getOrderProductNo());
        assertThat(found.get().getTitle()).isEqualTo(savedReview.getTitle());
        assertThat(found.get().getContent()).isEqualTo(savedReview.getContent());
        assertThat(found.get().getImagePath()).isEqualTo(savedReview.getImagePath());
        assertThat(found.get().getStarScore()).isEqualTo(savedReview.getStarScore());
    }
}