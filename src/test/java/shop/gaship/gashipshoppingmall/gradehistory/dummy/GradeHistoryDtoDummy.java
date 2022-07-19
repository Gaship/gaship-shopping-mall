package shop.gaship.gashipshoppingmall.gradehistory.dummy;

import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;

/**
 * 등급이력 dto dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class GradeHistoryDtoDummy {
    private GradeHistoryDtoDummy(){}

    public static GradeHistoryAddRequestDto requestDto(){

        return new GradeHistoryAddRequestDto(1,
                1_000_000L,
                "일반");
    }
}
