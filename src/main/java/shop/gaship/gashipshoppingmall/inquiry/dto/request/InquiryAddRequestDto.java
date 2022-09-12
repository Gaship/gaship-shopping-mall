package shop.gaship.gashipshoppingmall.inquiry.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.lang.Nullable;

/**
 * 문의와 관련된 요청이나 반환시 정보를 담는 dto입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class InquiryAddRequestDto {

    @Setter
    @Nullable
    private Integer memberNo;

    @Min(value = 1, message = "productNo 는 최소값이 1입니다.")
    private Integer productNo;

    @NotBlank(message = "title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다.")
    private String title;

    @NotNull(message = "inquiryContent 는 필수 입력값입니다.")
    private String inquiryContent;

    @NotNull(message = "isProduct 는 필수 입력값입니다.")
    private Boolean isProduct;


}
