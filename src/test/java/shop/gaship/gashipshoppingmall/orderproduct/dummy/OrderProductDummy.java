package shop.gaship.gashipshoppingmall.orderproduct.dummy;

import java.time.LocalDate;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

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
            .orderStatusCode(dummyStatus())
            .amount(10000L)
            .product(ProductDummy.dummy())
            .hopeDate(LocalDate.now())
            .warrantyExpirationDate(LocalDate.now().plusYears(1L))
            .memberCouponNo(1)
            .build();
    }

    public static StatusCode dummyStatus(){
        return StatusCode.builder()
            .statusCodeName("배송준비중")
            .groupCodeName("주문")
            .explanation("제품의 배송타입입니다.")
            .priority(1)
            .build();
    }

}
