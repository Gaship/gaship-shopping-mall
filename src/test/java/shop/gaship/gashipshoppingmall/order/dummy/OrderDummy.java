package shop.gaship.gashipshoppingmall.order.dummy;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class OrderDummy {
    public static Order createOrderDummy() {
        Order order = new Order();
        ReflectionTestUtils.setField(order, "no", 1);
        ReflectionTestUtils.setField(order, "member", MemberDummy.dummy());
        ReflectionTestUtils.setField(order, "addressList", AddressListDummy.addressListEntity());
        ReflectionTestUtils.setField(order, "orderProducts", Collections.emptyList());
        ReflectionTestUtils.setField(order, "orderDatetime", LocalDateTime.now());
        ReflectionTestUtils.setField(order, "receiptName", "홍홍홍");
        ReflectionTestUtils.setField(order, "receiptPhoneNumber", "01012345678");
        ReflectionTestUtils.setField(order, "receiptSubPhoneNumber", null);
        ReflectionTestUtils.setField(order, "deliveryRequest", null);

        return order;
    }

    public static OrderRegisterRequestDto createOrderRequestDtyDummy() {
        OrderRegisterRequestDto dummy = new OrderRegisterRequestDto();
        ReflectionTestUtils.setField(dummy, "addressListNo", 1);
        ReflectionTestUtils.setField(dummy, "memberNo", 1);
        ReflectionTestUtils.setField(dummy, "orderProductSpecific", List.of());
        ReflectionTestUtils.setField(dummy, "receiverName", "홍홍홍");
        ReflectionTestUtils.setField(dummy, "receiverPhoneNo", "01012345678");
        ReflectionTestUtils.setField(dummy, "receiverSubPhoneNo", null);
        ReflectionTestUtils.setField(dummy, "deliveryRequest", null);
        return dummy;
    }
}
