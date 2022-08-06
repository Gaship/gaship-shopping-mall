package shop.gaship.gashipshoppingmall.inquiry.inquiryenum;

import lombok.Getter;

/**
 * 컨트롤러나 서비스계층에서 가독성을 높이기 위해서 사용하는 enum 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public enum InquiryType {
    PRODUCT_INQUIRIES(Boolean.TRUE), CUSTOMER_INQUIRIES(Boolean.FALSE);

    private final Boolean value;

    InquiryType(Boolean value) {
        this.value = value;
    }
}
