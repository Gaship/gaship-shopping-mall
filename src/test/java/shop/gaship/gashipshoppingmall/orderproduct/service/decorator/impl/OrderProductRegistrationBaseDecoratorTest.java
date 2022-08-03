package shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.decorator.OrderProductRegistrationDecorator;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.NoMoreProductException;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderProductRegistrationBaseDecorator.class)
class OrderProductRegistrationBaseDecoratorTest {
    @Autowired
    private OrderProductRegistrationDecorator baseDecorator;

    @MockBean
    private OrderProductRepository orderProductRepository;

    @Test
    @DisplayName("주문 상세를 저장하는 기본 데코레이터 테스트")
    void saveTest() {
        OrderProduct dummy = OrderProductDummy.dummy();
        given(orderProductRepository.save(any()))
            .willReturn(dummy);

        OrderProduct orderProduct = baseDecorator.save(dummy);

        assertThat(orderProduct).isEqualTo(dummy);
    }

    @Test
    @DisplayName("주문 제품 양만큼 재고 수량 정리하는 기본 데코레이터 테스트 : 정상 통과")
    void cleanUpStockCaseSuccess() {
        Product dummy = ProductDummy.dummy();

        baseDecorator.cleanUpStock(dummy);

        assertThat(dummy.getStockQuantity()).isEqualTo(9);
    }

    @Test
    @DisplayName("주문 제품 양만큼 재고 수량 정리하는 기본 데코레이터 테스트 : 수량이 부족할 시")
    void cleanUpStockCaseFailure() {
        Product dummy = ProductDummy.dummy();
        ProductDummy.productDummyStockMakeZero(dummy);

        assertThatThrownBy(() -> baseDecorator.cleanUpStock(dummy))
            .isInstanceOf(NoMoreProductException.class)
            .hasMessage("재고가 더 이상 없습니다.");
    }
}
