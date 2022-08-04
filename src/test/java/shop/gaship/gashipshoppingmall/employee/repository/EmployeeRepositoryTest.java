package shop.gaship.gashipshoppingmall.employee.repository;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.employee.dto.response.InstallOrderResponseDto;
import shop.gaship.gashipshoppingmall.employee.dummy.EmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.repository
 * fileName       : EmployeeRepositoryTest
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/10        유호철       최초
 * 생성
 */
@DataJpaTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    EmployeeRepository repository;
    @Autowired
    AddressLocalRepository localRepository;
    @Autowired
    StatusCodeRepository codeRepository;
    @Autowired
    DayLaborRepository laborRepository;
    private Employee employee;
    private AddressLocal addressLocal;
    private StatusCode code;
    private DayLabor labor;

    @BeforeEach
    void setUp() {
        employee = EmployeeDummy.dummy();

        labor = DayLaboyDummy.dummy1();

        addressLocal = AddressLocalDummy.dummy1();

        code = StatusCodeDummy.dummy();

        labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);
    }

    @DisplayName("이메일 중복검증을 위한 테스트")
    @Test
    @org.junit.jupiter.api.Order(4)
    void findByEmail() {
        //given
        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        laborRepository.save(labor);
        localRepository.save(addressLocal);
        codeRepository.save(code);
        repository.save(employee);
        //when & then
        Employee employee1 = repository.findByEmail("test@naver.com").orElse(null);

        assertThat(Objects.requireNonNull(employee1).getEmployeeNo()).isEqualTo(employee.getEmployeeNo());
        assertThat(employee1.getAddressLocal()).isEqualTo(addressLocal);
        assertThat(employee1.getStatusCode()).isEqualTo(code);
    }

    @Test
    @DisplayName("직원의 이메일을 통해서 직원 계정 정보를 가져옵니다. : 성공")
    @org.junit.jupiter.api.Order(2)
    void findSignInEmployeeUserDetailCaseSuccess() {
        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        laborRepository.save(labor);
        localRepository.save(addressLocal);
        codeRepository.save(code);
        repository.save(employee);

        SignInUserDetailsDto loginEmployee =
            repository.findSignInEmployeeUserDetail("test@naver.com").orElse(null);

        assertThat(Objects.requireNonNull(loginEmployee).getMemberNo()).isEqualTo(employee.getEmployeeNo());
        assertThat(loginEmployee.getEmail()).isEqualTo("test@naver.com");
        assertThat(loginEmployee.getAuthorities()).isEqualTo(List.of(code.getStatusCodeName()));
        assertThat(loginEmployee.getHashedPassword()).isEqualTo(employee.getPassword());
    }

    @Test
    @DisplayName("직원의 이메일을 통해서 직원 계정 정보를 가져옵니다. : 실패")
    @org.junit.jupiter.api.Order(3)
    void findSignInEmployeeUserDetailCaseFailure() {
        SignInUserDetailsDto loginEmployee =
            repository.findSignInEmployeeUserDetail("test@naver.com").orElse(null);

        assertThat(loginEmployee).isNull();
    }

    @Test
    @DisplayName("직원이 자신의 지역에서 설치해야 할 주문들을 조회합니다.")
    @org.junit.jupiter.api.Order(1)
    void findOrderBasedOnMyLocationTest(@Autowired AddressListRepository addressListRepository,
                                        @Autowired MemberRepository memberRepository,
                                        @Autowired OrderRepository orderRepository,
                                        @Autowired OrderProductRepository orderProductRepository){
        // 주문 등록
        Member memberDummy = MemberDummy.dummy();
        entityManager.persist(memberDummy.getMemberStatusCodes());
        entityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        entityManager.persist(memberDummy.getMemberGrades());
        Member savedMember = memberRepository.save(memberDummy);

        AddressLocal addressLocalLevel2 = AddressListDummy.addressListEntity().getAddressLocal();
        AddressLocal addressLocalLevel1 = AddressLocalDummy.dummy1();
        addressLocalLevel2.registerUpperLocal(addressLocalLevel1);
        entityManager.persist(addressLocalLevel1);
        AddressLocal savedLocalLevel2 = entityManager.persist(addressLocalLevel2);

        AddressList addressListDummy = AddressList.builder()
            .addressLocal(AddressLocalDummy.dummy2())
            .member(MemberDummy.dummy())
            .statusCode(shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy.dummy())
            .address("경기도 안양시 비산동")
            .addressDetail("현대아파트 65층 화장실")
            .zipCode("12344")
            .build();
        ReflectionTestUtils.setField(addressListDummy, "addressLocal", savedLocalLevel2);
        ReflectionTestUtils.setField(addressListDummy, "member", savedMember);
        entityManager.persist(addressListDummy.getAddressLocal());
        AddressList addressList = addressListRepository.save(addressListDummy);

        Order orderDummy = OrderDummy.createOrderDummy();
        ReflectionTestUtils.setField(orderDummy, "addressList", addressList);
        ReflectionTestUtils.setField(orderDummy, "member", savedMember);
        Order savedOrderDummy = orderRepository.save(orderDummy);

        // 주문 상품 저장
        Product productDummy = ProductDummy.dummy();
        entityManager.persist(productDummy.getCategory());
        entityManager.persist(productDummy.getSalesStatus());
        entityManager.persist(productDummy.getDeliveryType());
        entityManager.persist(productDummy);

        OrderProduct orderProduct1 = OrderProductDummy.dummy();
        OrderProduct orderProduct2 = OrderProductDummy.dummy();
        OrderProduct orderProduct3 = OrderProductDummy.dummy();
        StatusCode orderStatus = entityManager.persist(orderProduct1.getOrderStatusCode());
        ReflectionTestUtils.setField(orderProduct1, "orderStatusCode", orderStatus);
        ReflectionTestUtils.setField(orderProduct1, "product", productDummy);
        ReflectionTestUtils.setField(orderProduct1, "order", savedOrderDummy);
        ReflectionTestUtils.setField(orderProduct2, "orderStatusCode", orderStatus);
        ReflectionTestUtils.setField(orderProduct2, "product", productDummy);
        ReflectionTestUtils.setField(orderProduct2, "order", savedOrderDummy);
        ReflectionTestUtils.setField(orderProduct2, "no", 2);
        ReflectionTestUtils.setField(orderProduct3, "orderStatusCode", orderStatus);
        ReflectionTestUtils.setField(orderProduct3, "product", productDummy);
        ReflectionTestUtils.setField(orderProduct3, "order", savedOrderDummy);
        ReflectionTestUtils.setField(orderProduct3, "no", 3);

        orderProductRepository.save(orderProduct1);
        orderProductRepository.save(orderProduct2);
        orderProductRepository.save(orderProduct3);


        //직원 저장
        employee.fixLocation(savedLocalLevel2);
        int id = (Integer) entityManager.persistAndGetId(employee);

        Pageable pageable = PageRequest.of(0, 10);

        PageResponse<Order> result = repository.findOrderBasedOnEmployeeLocation(pageable, id);

        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getPage()).isZero();
        assertThat(result.isNext()).isTrue();
        assertThat(result.isPrev()).isFalse();
        assertThat(result.getContent()).hasSize(1);
        System.out.println(result.getContent());
    }
}


