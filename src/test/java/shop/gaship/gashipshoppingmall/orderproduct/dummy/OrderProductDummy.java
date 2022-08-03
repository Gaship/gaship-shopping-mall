package shop.gaship.gashipshoppingmall.orderproduct.dummy;

import java.time.LocalDate;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class OrderProductDummy {
    public static OrderProduct dummy() {
        return OrderProduct.builder()
            .order(OrderDummy.createOrderDummy())
            .orderStatusCode(StatusCodeDummy.dummy())
            .amount(10000L)
            .product(ProductDummy.dummy())
            .hopeDate(LocalDate.now())
            .warrantyExpirationDate(LocalDate.now().plusYears(1L))
            .build();
    }
}
