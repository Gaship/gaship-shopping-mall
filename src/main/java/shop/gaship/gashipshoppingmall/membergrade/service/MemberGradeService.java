package shop.gaship.gashipshoppingmall.membergrade.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.PageResponseDto;


/**
 * 회원등급 Service Interface.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberGradeService {

    /**
     * 회원등급 등록을 위한 메서드
     * 모든 회원등급의 갱신기간은 동일하게 적용된다.
     * 요청에 담겨있는 기준누적금액에 해당하는 기존의 다른 회원등급이 존재하면 등록 불가.
     * 해당 회원등급 등록 요청의 isDefault 값이 true 일때 (기본회원등급 등록을 할때)
     * 기존에 기본회원등급이 존재한다면 등록 불가.
     * request 의 isDefault 값에 따라 기본회원등급 등록 / 이외의 회원등급 등록 으로 나뉜다.
     *
     * @param request MemberGradeAddRequestDto.
     * @author 김세미
     */
    void addMemberGrade(MemberGradeAddRequestDto request);

    /**
     * 회원등급 수정을 위한 메서드
     * 기준누적금액을 수정하려고 하는 경우 해당 기준누적금액이 기존과 다를때
     * 기준누적금액이 회원등급끼리 중복되는 부분을 방지하기 위해
     * 해당 기준누적금액과 동일한 기준누적금액을 가지고 있는 다른 회원등급이 존재하면 수정 불가.
     *
     * @param request MemberGradeModifyRequestDto
     * @author 김세미
     */
    void modifyMemberGrade(MemberGradeModifyRequestDto request);

    /**
     * 회원등급 삭제를 위한 메서드
     * 기본 회원 등급은 삭제할 수 없으며 해당 회원등급을 사용중인 member 가 존재하면 삭제할 수 없다.
     *
     * @param memberGradeNo Integer
     * @author 김세미
     */
    void removeMemberGrade(Integer memberGradeNo);

    /**
     * 회원등급 단건 조회를 위한 메서드.
     *
     * @param memberGradeNo 조회하려는 회원등급의 식별 번호 (Integer)
     * @return memberGradeResponseDto
     * @author 김세미
     */
    MemberGradeResponseDto findMemberGrade(Integer memberGradeNo);

    /**
     * pagination 이 적용된 다건 조회를 위한 메서드.
     *
     * @param pageable Pageable.
     * @return PageResponseDto
     * @author 김세미
     */
    PageResponseDto<MemberGradeResponseDto> findMemberGrades(Pageable pageable);
}
