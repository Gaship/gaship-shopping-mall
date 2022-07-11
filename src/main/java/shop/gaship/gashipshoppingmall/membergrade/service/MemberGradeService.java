package shop.gaship.gashipshoppingmall.membergrade.service;

import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.request.MemberGradeRequest;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.service
 * fileName       : MemberGradeService
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
public interface MemberGradeService {
    /**
     * .
     * methodName : addMemberGrade
     * author : Semi Kim
     * description :
     *
     * @param request MemberGradeRequest
     */
    void addMemberGrade(MemberGradeRequest request);

    /**
     * .
     * methodName : modifyMemberGrade
     * author : Semi Kim
     * description :
     *
     * @param memberGradeNo Integer
     * @param request       MemberGradeRequest
     */
    void modifyMemberGrade(Integer memberGradeNo, MemberGradeRequest request);

    /**
     * .
     * methodName : removeMemberGrade
     * author : Semi Kim
     * description :
     *
     * @param memberGradeNo Integer
     */
    void removeMemberGrade(Integer memberGradeNo);

    MemberGradeDto findMemberGrade(Integer memberGradeNo);
}
