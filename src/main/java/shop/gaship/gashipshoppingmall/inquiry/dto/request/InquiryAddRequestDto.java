package shop.gaship.gashipfront.inquiry.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 문의와 관련된 요청이나 반환시 정보를 담는 dto입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class InquiryAddRequestDto {

    @Min(1)
    @NotNull(message = "memberNo 는 필수 입력값입니다.")
    private Integer memberNo;

    @Min(1)
    private Integer productNo;

    @NotBlank(message = "title 은 필수 입력값입니다.")
    private String title;

    @NotNull(message = "memberNo 는 필수 입력값입니다.")
    private String inquiryContent;

    @NotNull(message = "isProduct 는 필수 입력값입니다.")
    private Boolean isProduct;
}
