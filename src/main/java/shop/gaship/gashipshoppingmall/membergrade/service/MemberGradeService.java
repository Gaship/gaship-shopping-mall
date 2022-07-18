package shop.gaship.gashipshoppingmall.membergrade.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.PageResponseDto;


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

    void addMemberGrade(MemberGradeAddRequestDto request);

    void modifyMemberGrade(MemberGradeModifyRequestDto request);

    void removeMemberGrade(Integer memberGradeNo);

    MemberGradeResponseDto findMemberGrade(Integer memberGradeNo);

    PageResponseDto<MemberGradeResponseDto> findMemberGrades(Pageable pageable);
}
