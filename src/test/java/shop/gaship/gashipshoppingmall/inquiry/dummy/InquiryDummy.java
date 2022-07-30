package shop.gaship.gashipshoppingmall.inquiry.dummy;

import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * test 진행을 위한 문의 관련 dummy입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class InquiryDummy {
    public static Inquiry customerDummy(StatusCode statusCode) {
        return Inquiry.builder()
            .title("1번째 고객문의제목")
            .inquiryContent("1번째 고객문의내용")
            .processStatusCode(statusCode)
            .isProduct(false)
            .registerDatetime(LocalDateTime.now())
            .build();
    }

    public static Inquiry productDummy(StatusCode statusCode) {
        return Inquiry.builder()
            .title("2번째 상품문의제목")
            .inquiryContent("2번째 상품문의내용")
            .processStatusCode(statusCode)
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
