package shop.gaship.gashipshoppingmall.orderproduct.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 주문 상품 repository 테스트.
 *
 * @author : 유호철, 조재철
 * @since 1.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DataProtectionConfig.class})
class OrderProductRepositoryTest {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressListRepository addressListRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StatusCodeRepository statusCodeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberGradeRepository memberGradeRepository;

    @Autowired
    AddressLocalRepository addressLocalRepository;

    @DisplayName("mysql 에서 특정범위의 매출량을 보기위한 테스트입니다.")
    @Test
    void findTotalSaleTestBetweenStartDateEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 30, 23, 59, 59);
        TotalSaleRequestDto requestDto = new TotalSaleRequestDto(startDate, endDate);
        List<TotalSaleResponseDto> totalSale = orderProductRepository.findTotalSale(requestDto);
        assertThat(totalSale).isNotEmpty();
    }

    @DisplayName("mysql 에서 상품주문번호 상세 조회")
    @Test
    void findOrderProduct() {
        Optional<OrderProductDetailResponseDto> productDetail = orderProductRepository.findOrderProductDetail(1);
        assertThat(productDetail).isPresent();
    }

    @DisplayName("mysql 에서 해당멤버의 주문상품 조회")
    @Test
    void findOrderSale() {
        Page<OrderProductResponseDto> list = orderProductRepository.findAllOrdersByMemberNo(1, PageRequest.of(0, 10));
        assertThat(list.getSize()).isEqualTo(10);
    }

    @Test
    void findOrderInfo() {

        StatusCode statusCode = StatusCode.builder()
            .groupCodeName(OrderStatus.GROUP)
            .statusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
            .priority(1)
            .build();

        StatusCode savedStatusCode = statusCodeRepository.save(statusCode);

        StatusCode saleStatusCode = shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy.dummy();

        StatusCode savedSaleStatusCode = statusCodeRepository.save(saleStatusCode);

        Product product = ProductDummy.dummy();
        ReflectionTestUtils.setField(product, "salesStatus", savedSaleStatusCode);
        ReflectionTestUtils.setField(product, "deliveryType", savedStatusCode);

        Product savedProduct = productRepository.save(product);

        StatusCode memberStatusCode = StatusCodeDummy.dummy();
        StatusCode savedMemberStatusCode = statusCodeRepository.save(memberStatusCode);

        StatusCode memberGradeStatus = StatusCodeDummy.dummy();

        StatusCode savedMemberGradeStatus = statusCodeRepository.save(memberGradeStatus);

        MemberGradeAddRequestDto memberGradeAddRequestDto = new MemberGradeAddRequestDto();
        memberGradeAddRequestDto.setName("dfs");
        memberGradeAddRequestDto.setAccumulateAmount(10000L);
        memberGradeAddRequestDto.setIsDefault(true);

        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, savedMemberGradeStatus);

        MemberGrade savedMemberGrade = memberGradeRepository.save(memberGrade);

        Member member = Member.builder()
            .memberNo(1)
            .name("유민철")
            .password("1234")
            .nickname("하하")
            .email("rhsnqk@daum.net")
            .accumulatePurchaseAmount(11111L)
            .nextRenewalGradeDate(LocalDate.now())
            .memberStatusCodes(savedMemberStatusCode)
            .encodedEmailForSearch("dasdfas@afasdf2f32edsffdsa")
            .memberGrades(savedMemberGrade)
            .build();

        Member savedMember = memberRepository.save(member);

        AddressLocal addressLocal = AddressLocalDummy.dummy1();

        AddressLocal savedAddressLocal = addressLocalRepository.save(addressLocal);

        StatusCode addressStatus = StatusCode.builder()
            .statusCodeName(AddressStatus.USE.getValue())
            .groupCodeName(AddressStatus.GROUP)
            .priority(1)
            .build();

        StatusCode savedAddressStatus = statusCodeRepository.save(addressStatus);


        AddressList addressList = AddressList.builder()
            .addressListsNo(1)
            .address("창원")
            .addressDetail("집")
            .addressLocal(savedAddressLocal)
            .member(savedMember)
            .statusCode(savedAddressStatus)
            .zipCode("123")
            .build();

        AddressList savedAddressList = addressListRepository.save(addressList);

        // given
        Order order = Order.builder()
            .receiptName("유민철")
            .receiptPhoneNumber("01000000000")
            .addressList(savedAddressList)
            .member(savedMember)
            .build();

        Order savedOrder = orderRepository.save(order);

        OrderProduct orderProduct = OrderProduct.builder()
            .order(savedOrder)
            .warrantyExpirationDate(LocalDate.now())
            .orderStatusCode(savedStatusCode)
            .product(savedProduct)
            .amount(1000L)
            .build();

        OrderProduct savedOrderProduct = orderProductRepository.save(orderProduct);

        // when
        Optional<DeliveryDto> orderInfo = orderProductRepository.findOrderInfo(savedOrderProduct.getNo());

        // then
        assertThat(orderInfo).isNotEmpty();
        assertThat(orderInfo.get().getOrderProductNo()).isEqualTo(savedOrderProduct.getNo());
        assertThat(orderInfo.get().getReceiverAddress()).isEqualTo(savedOrder.getAddressList().getAddress());
        assertThat(orderInfo.get().getReceiverName()).isEqualTo(savedOrder.getReceiptName());
        assertThat(orderInfo.get().getReceiverPhone()).isEqualTo(savedOrder.getReceiptPhoneNumber());

    }
}