package shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.membercoupon.dummy.MemberCouponDummy;
import shop.gaship.gashipshoppingmall.membercoupon.excpetion.MemberCouponNotFoundException;
import shop.gaship.gashipshoppingmall.membercoupon.repository.MemberCouponRepository;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderProductRegistrationCouponDecorator.class)
class OrderProductRegistrationCouponDecoratorTest {
    @Autowired
    private OrderProductRegistrationCouponDecorator registrationDecorator;

    @MockBean
    private OrderProductRepository orderProductRepository;

    @MockBean
    private MemberCouponRepository  memberCouponRepository;

    @MockBean
    private StatusCodeRepository statusCodeRepository;

    @Test
    void applyMemberCouponCaseSuccess() {
        given(memberCouponRepository.findById(anyInt()))
            .willReturn(Optional.of(MemberCouponDummy.dummy()));

        registrationDecorator.applyMemberCoupon(1);

        verify(memberCouponRepository, times(1)).findById(anyInt());
    }

    @Test
    void applyMemberCouponCaseFailure() {
        given(memberCouponRepository.findById(anyInt()))
            .willThrow(new MemberCouponNotFoundException());

        assertThatThrownBy(() -> registrationDecorator.applyMemberCoupon(1))
            .isInstanceOf(MemberCouponNotFoundException.class)
            .hasMessage("회원의 쿠폰이 존재하지 않습니다.");

        verify(memberCouponRepository, times(1)).findById(anyInt());
    }

    @Test
    void save() {
        OrderProduct dummy = OrderProductDummy.dummy();

        given(memberCouponRepository.findById(anyInt()))
            .willReturn(Optional.of(MemberCouponDummy.dummy()));
        given(orderProductRepository.save(any()))
            .willReturn(dummy);

        registrationDecorator.applyMemberCoupon(1);
        OrderProduct orderProduct = registrationDecorator.save(dummy);
        assertThat(orderProduct).isEqualTo(dummy);

        verify(memberCouponRepository, times(1)).findById(anyInt());
        verify(orderProductRepository, times(1)).save(any());
    }

    @Test
    void cleanUpStockCaseSuccess() {
        Product product = ProductDummy.dummy();
        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(memberCouponRepository.findById(anyInt()))
            .willReturn(Optional.of(MemberCouponDummy.dummy()));

        registrationDecorator.applyMemberCoupon(1);

        registrationDecorator.cleanUpStock(product);

        verify(statusCodeRepository, times(1)).findByStatusCodeName(anyString());
        verify(memberCouponRepository, times(1)).findById(anyInt());
    }

    @Test
    void cleanUpStockCaseFailure() {
        Product product = ProductDummy.dummy();
        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willThrow(new StatusCodeNotFoundException());

        assertThatThrownBy(() -> registrationDecorator.cleanUpStock(product))
            .isInstanceOf(StatusCodeNotFoundException.class)
            .hasMessage("해당 상태 코드를 찾을 수 없습니다.");
    }
}
