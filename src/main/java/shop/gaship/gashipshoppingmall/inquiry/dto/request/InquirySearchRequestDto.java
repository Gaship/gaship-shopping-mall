package shop.gaship.gashipshoppingmall.inquiry.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 문의를 검색할때 넘어오는 파라미터들을 담기위한 dto 객체입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
public class InquirySearchRequestDto {

    @NotNull
    private Boolean isProduct;
    private String status;
    private Integer memberNo;
    private Integer productNo;
}
