package shop.gaship.gashipshoppingmall.membergrade.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.PageResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.*;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;


/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.service
 * fileName       : MemberGradeServiceImpl
 * author         : semi
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        semi       최초 생성
 */
@RequiredArgsConstructor
@Service
public class MemberGradeServiceImpl implements MemberGradeService {
    private final MemberGradeRepository memberGradeRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberRepository memberRepository;

    /**
     * .
     * methodName : addMemberGrade
     * author : Semi Kim
     * description : 회원등급 등록을 위한 메서드
     * 모든 회원등급의 갱신기간은 동일하게 적용된다.
     * 요청에 담겨있는 기준누적금액에 해당하는 기존의 다른 회원등급이 존재하면 등록 불가.
     * 해당 회원등급 등록 요청의 isDefault 값이 true 일때 (기본회원등급 등록을 할때)
     * 기존에 기본회원등급이 존재한다면 등록 불가.
     * request 의 isDefault 값에 따라 기본회원등급 등록 / 이외의 회원등급 등록 으로 나뉜다.
     *
     * @param request MemberGradeRequest
     */
    @Transactional
    @Override
    public void addMemberGrade(MemberGradeAddRequestDto request) {
        StatusCode renewalPeriod = statusCodeRepository
                .findById(1)
                .orElseThrow(StatusCodeNotFoundException::new);

        checkOverlapAccumulateAmount(request.getAccumulateAmount());

        if (request.getIsDefault()) {
            checkExistDefaultMemberGrade();
            memberGradeRepository.save(MemberGrade.createDefault(renewalPeriod, request));
        } else {
            memberGradeRepository.save(MemberGrade.create(renewalPeriod, request));
        }
    }

    /**
     * .
     * methodName : modifyMemberGrade
     * author : Semi Kim
     * description : 회원등급 수정을 위한 메서드
     * 기준누적금액을 수정하려고 하는 경우 해당 기준누적금액이 기존과 다를때
     * 기준누적금액이 회원등급끼리 중복되는 부분을 방지하기 위해
     * 해당 기준누적금액과 동일한 기준누적금액을 가지고 있는 다른 회원등급이 존재하면 수정 불가
     *
     * @param request       수정된 내용이 담긴 requestDto (MemberGradeModifyRequestDto)
     */
    @Transactional
    @Override
    public void modifyMemberGrade(MemberGradeModifyRequestDto request) {
        MemberGrade memberGrade = memberGradeRepository
                .findById(request.getNo())
                .orElseThrow(MemberGradeNotFoundException::new);

        if (!memberGrade.getAccumulateAmount().equals(request.getAccumulateAmount())) {
            checkOverlapAccumulateAmount(request.getAccumulateAmount());
        }

        memberGrade.modifyDetails(request);

        memberGradeRepository.save(memberGrade);
    }

    /**
     * .
     * methodName : removeMemberGrade
     * author : Semi Kim
     * description : 회원등급 삭제를 위한 메서드
     * 기본 회원 등급은 삭제할 수 없으며 해당 회원등급을 사용중인 member 가 존재하면 삭제할 수 없다.
     *
     * @param memberGradeNo 삭제하려는 회원등급의 식별 번호 (Integer)
     */
    @Transactional
    @Override
    public void removeMemberGrade(Integer memberGradeNo) {
        MemberGrade memberGrade = memberGradeRepository
                .findById(memberGradeNo)
                .orElseThrow(MemberGradeNotFoundException::new);

        if (memberGrade.isDefault()) {
            throw new CannotDeleteDefaultMemberGrade();
        }

        if (!memberRepository.findByMemberGrades(memberGrade).isEmpty()) {
            throw new MemberGradeInUseException();
        }

        memberGradeRepository.delete(memberGrade);
    }

    /**.
     * methodName : findMemberGrade
     * author : Semi Kim
     * description : 회원등급 단건 조회를 위한 메서드
     *
     * @param memberGradeNo 조회하려는 회원등급의 식별 번호 (Integer)
     * @return memberGradeResponseDto
     */
    @Override
    public MemberGradeResponseDto findMemberGrade(Integer memberGradeNo) {
        return memberGradeRepository.getMemberGradeBy(memberGradeNo)
                .orElseThrow(MemberGradeNotFoundException::new);
    }

    /**.
     * methodName : findMemberGrades
     * author : Semi Kim
     * description : pagination 이 적용된 다건 조회를 위한 메서드
     *
     * @param pageable Pageable
     * @return list
     */
    @Override
    public PageResponseDto<MemberGradeResponseDto> findMemberGrades(Pageable pageable) {
        return new PageResponseDto<>(memberGradeRepository
                .getMemberGrades(pageable));
    }

    /**.
     * methodName : checkOverlapAccumulateAmount
     * author : Semi Kim
     * description : 회원등급의 기준누적금액 중복을 방지하기 위해 check 하는 메서드
     *
     * @param accumulateAmount 회원등급 승급의 기준이 되는 기준누적금액 (Long)
     */
    private void checkOverlapAccumulateAmount(Long accumulateAmount) {
        if (memberGradeRepository.existsByAccumulateAmountEquals(accumulateAmount)) {
            throw new AccumulateAmountIsOverlap(accumulateAmount);
        }
    }

    /**.
     * methodName : checkExistDefaultMemberGrade
     * author : Semi Kim
     * description : 기본회원등급 재등록을 방지하기 위해 check 하는 메서드
     */
    private void checkExistDefaultMemberGrade() {
        if (memberGradeRepository.existsByIsDefaultIsTrue()) {
            throw new DefaultMemberGradeIsExist();
        }
    }
}
