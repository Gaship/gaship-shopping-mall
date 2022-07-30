package shop.gaship.gashipshoppingmall.inquiry.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 문의에 대한 답변을 등록하기위해 사용하는 DTO 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
public class InquiryAnswerRequestDto {

    @Min(1)
    @NotNull(message = "inquiryNo 는 필수값입니다.")
    private Integer inquiryNo;

    @Min(1)
    @NotNull
    private Integer employeeNo;

    @NotBlank(message = "answerContent 는 필수값입니다.")
    private String answerContent;
}
