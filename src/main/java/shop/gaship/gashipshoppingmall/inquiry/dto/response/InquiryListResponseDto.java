package shop.gaship.gashipshoppingmall.inquiry.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 조회시 필요한 정보만 프로젝션하기위한 dto이며 해당 dto가 클라이언트에게 반환됩니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public class InquiryListResponseDto {

    private Integer inquiryNo;
    private String memberNickname;
    private String processStatus;
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registerDatetime;
}
