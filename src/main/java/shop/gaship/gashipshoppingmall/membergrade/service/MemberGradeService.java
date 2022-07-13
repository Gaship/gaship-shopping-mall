package shop.gaship.gashipshoppingmall.membergrade.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;


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
    void modifyMemberGrade(Integer memberGradeNo, MemberGradeRequestDto request) throws MemberGradeNotFoundException;

    /**
     * .
     * methodName : removeMemberGrade
     * author : Semi Kim
     * description :
     *
     * @param memberGradeNo Integer
     */
    MemberGradeResponseDto findMemberGrade(Integer memberGradeNo) throws MemberGradeNotFoundException;

    List<MemberGradeResponseDto> findMemberGrades(Pageable pageable);
}
