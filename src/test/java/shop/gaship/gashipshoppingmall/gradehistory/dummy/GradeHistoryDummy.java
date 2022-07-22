package shop.gaship.gashipshoppingmall.gradehistory.dummy;

import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * 등급이력 dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class GradeHistoryDummy {
    private GradeHistoryDummy(){}

    public static GradeHistory dummy(Member member){
        return GradeHistory.builder()
                .member(member)
                .request(GradeHistoryDtoDummy.addRequestDummy())
                .build();
    }
}
