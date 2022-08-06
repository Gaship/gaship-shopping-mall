package shop.gaship.gashipshoppingmall.inquiry.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 문의를 검색할때 넘어오는 파라미터들을 담기위한 dto 객체입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class InquiryListSearch {

    private final Boolean isProduct;
    private final Integer statusCodeNo;
    private final Integer memberNo;
    private final Integer productNo;
}
