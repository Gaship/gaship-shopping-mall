package shop.gaship.gashipshoppingmall.orderproduct.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.orderproduct.adapter.OrderProductAdapter;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CouponUseEventHandler.class)
class CouponUseEventHandlerTest {
    @Autowired
    private CouponUseEventHandler couponUseEventHandler;

    @MockBean
    private OrderProductAdapter orderProductAdapter;

    @Test
    void handle() {
        willDoNothing()
            .given(orderProductAdapter)
            .useCouponRequest(anyList());

        couponUseEventHandler.handle(new CouponUseEvent(List.of(1)));

        then(orderProductAdapter)
            .should(times(1))
            .useCouponRequest(List.of(1));
    }
}
