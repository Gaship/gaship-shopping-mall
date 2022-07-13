package shop.gaship.gashipshoppingmall.membergrade.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;


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
    void addMemberGrade(MemberGradeRequestDto request);

    /**
     * .
     * methodName : modifyMemberGrade
     * author : Semi Kim
     * description :
     *
     * @param memberGradeNo Integer
     * @param request       MemberGradeRequest
     */
    void modifyMemberGrade(Integer memberGradeNo, MemberGradeRequestDto request);

    /**
     * .
     * methodName : removeMemberGrade
     * author : Semi Kim
     * description :
     *
     * @param memberGradeNo Integer
     */
    void removeMemberGrade(Integer memberGradeNo);

    MemberGradeResponseDto findMemberGrade(Integer memberGradeNo);

    List<MemberGradeResponseDto> findMemberGrades(Pageable pageable);
}
