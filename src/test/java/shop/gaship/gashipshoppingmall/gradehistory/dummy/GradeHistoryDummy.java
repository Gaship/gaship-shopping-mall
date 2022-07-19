package shop.gaship.gashipshoppingmall.gradehistory.dummy;

import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;

/**
 * 등급이력 dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class GradeHistoryDummy {
    private GradeHistoryDummy(){}

    public static GradeHistory dummy(){
        return GradeHistory.builder()
                .member(GradeHistoryMemberDummy.dummy())
                .request(GradeHistoryDtoDummy.requestDto())
                .build();
    }
}
