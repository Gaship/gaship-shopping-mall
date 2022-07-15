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

    void addMemberGrade(MemberGradeRequestDto request);

    void modifyMemberGrade(MemberGradeRequestDto request);

    void removeMemberGrade(Integer memberGradeNo);

    MemberGradeResponseDto findMemberGrade(Integer memberGradeNo);

    List<MemberGradeResponseDto> findMemberGrades(Pageable pageable);
}
