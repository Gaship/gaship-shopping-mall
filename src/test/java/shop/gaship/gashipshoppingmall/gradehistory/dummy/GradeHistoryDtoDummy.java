package shop.gaship.gashipshoppingmall.gradehistory.dummy;

import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryFindRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import java.time.LocalDate;

/**
 * 등급이력 dto dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class GradeHistoryDtoDummy {
    private GradeHistoryDtoDummy(){}

    public static GradeHistoryAddRequestDto addRequestDummy(){

        return new GradeHistoryAddRequestDto(1,
                1_000_000L,
                "일반");
    }

    public static GradeHistoryFindRequestDto findRequestDummy(){

        return new GradeHistoryFindRequestDto(1, 0, 1);
    }

    public static GradeHistoryResponseDto responseDummy(){
        GradeHistoryResponseDto dummy = new GradeHistoryResponseDto();
        dummy.setAt(LocalDate.of(2022, 7, 19));
        dummy.setTotalAmount(1_000_000L);
        dummy.setGradeName("일반");

        return dummy;
    }
}
