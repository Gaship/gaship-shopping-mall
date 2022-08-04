package shop.gaship.gashipshoppingmall.inquiry.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 문의 상세조회시 body에 담아야할 정보들을 가지는 객체입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
// TODO Getter 없어도 보내지는지 확인
@Getter
public class InquiryDetailsResponseDto {
    private final Integer inquiryNo;
    private final Integer productNo;

    private final String memberNickname;
    private final String employeeName;
    private final String processStatus;
    private final String productName;

    private final String title;
    private final String inquiryContent;
    private final LocalDateTime registerDatetime;

    private final String answerContent;
    private final LocalDateTime answerRegisterDatetime;
    private final LocalDateTime answerModifyDatetime;

}
