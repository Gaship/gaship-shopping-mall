package shop.gaship.gashipshoppingmall.product.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class InquiryDummy {
    public static Inquiry customerDummy(StatusCode statusCodeHolder) {
        return Inquiry.builder()
            .title("1번째 고객문의제목")
            .inquiryContent("1번째 고객문의내용")
            .processStatusCode(statusCodeHolder)
            .isProduct(false)
            .registerDatetime(LocalDateTime.now())
            .build();
    }

    public static Inquiry productDummy(StatusCode statusCodeHolder) {
        return Inquiry.builder()
            .title("2번째 상품문의제목")
            .inquiryContent("2번째 상품문의내용")
            .processStatusCode(statusCodeHolder)
            .isProduct(true)
            .registerDatetime(LocalDateTime.now())
            .build();
    }

    public static StatusCode statusCodeHolderDummy() {
        return StatusCode.builder()
            .statusCodeName("답변대기")
            .priority(1)
            .groupCodeName("처리상태")
            .build();
    }

    public static StatusCode statusCodeCompleteDummy() {
        return StatusCode.builder()
            .statusCodeName("답변완료")
            .priority(2)
            .groupCodeName("처리상태")
            .build();
    }
}
