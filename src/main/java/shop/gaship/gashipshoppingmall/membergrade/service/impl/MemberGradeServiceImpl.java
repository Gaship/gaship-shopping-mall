package shop.gaship.gashipshoppingmall.membergrade.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.*;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;
import shop.gaship.gashipshoppingmall.util.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.RenewalPeriod;

/**
 * 회원등급 Service 구현체.
 *
 * @author : 김세미
 * @see shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class MemberGradeServiceImpl implements MemberGradeService {
    private final MemberGradeRepository memberGradeRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     *
     * @throws StatusCodeNotFoundException 상태코드를 찾을 수 없습니다.
     * @throws AccumulateAmountIsOverlap 기준누적금액이 중복됩니다.
     * @throws DefaultMemberGradeIsExist 기본회원등급이 이미 존재합니다.
     */
    @Transactional
    @Override
    public void addMemberGrade(MemberGradeAddRequestDto requestDto) {
        StatusCode renewalPeriod = statusCodeRepository
            .findByGroupCodeName(RenewalPeriod.GROUP)
            .orElseThrow(StatusCodeNotFoundException::new);

        checkOverlapAccumulateAmount(requestDto.getAccumulateAmount());

        if (requestDto.getIsDefault()) {
            checkExistDefaultMemberGrade();
            memberGradeRepository.save(MemberGrade.createDefault(renewalPeriod, requestDto));
        } else {
            memberGradeRepository.save(MemberGrade.create(renewalPeriod, requestDto));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws MemberGradeNotFoundException 회원등급을 찾을 수 없습니다.
     * @throws AccumulateAmountIsOverlap 기준누적금액이 중복됩니다.
     */
    @Transactional
    @Override
    public void modifyMemberGrade(Integer memberGradeNo,
                                  MemberGradeModifyRequestDto requestDto) {
        MemberGrade memberGrade = memberGradeRepository
            .findById(memberGradeNo)
            .orElseThrow(MemberGradeNotFoundException::new);

        if (memberGrade.isDefault() && requestDto.getAccumulateAmount() > 0) {
            throw new InvalidAccumulateAmountException();
        }

        if (!memberGrade.getAccumulateAmount().equals(requestDto.getAccumulateAmount())) {
            checkOverlapAccumulateAmount(requestDto.getAccumulateAmount());
        }

        memberGrade.modifyDetails(requestDto);
    }

    /**
     * {@inheritDoc}
     *
     * @throws MemberGradeNotFoundException 회원등급을 찾을 수 없습니다.
     * @throws CannotDeleteDefaultMemberGrade 기본회원등급은 삭제할 수 없습니다.
     * @throws MemberGradeInUseException 사용중인 회원등급입니다.
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

    /**
     * {@inheritDoc}
     *
     * @throws MemberGradeNotFoundException 회원등급을 찾을 수 없습니다.
     */
    @Override
    public MemberGradeResponseDto findMemberGrade(Integer memberGradeNo) {
        return memberGradeRepository.getMemberGradeBy(memberGradeNo)
            .orElseThrow(MemberGradeNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<MemberGradeResponseDto> findMemberGrades(Pageable pageable) {
        return new PageResponse<>(memberGradeRepository
            .getMemberGrades(pageable));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberGradeResponseDto> findMemberGrades() {
        return memberGradeRepository.getAll();
    }

    /**
     * 회원등급의 기준누적금액 중복을 방지하기 위해 check 하는 메서드.
     *
     * @param accumulateAmount 회원등급 승급의 기준이 되는 기준누적금액 (Long)
     * @throws AccumulateAmountIsOverlap 기준누적금액을 가지고 있는 회원등급이 이미 존재합니다.
     * @author 김세미
     */
    private void checkOverlapAccumulateAmount(Long accumulateAmount) {
        if (memberGradeRepository.existsByAccumulateAmountEquals(accumulateAmount)) {
            throw new AccumulateAmountIsOverlap(accumulateAmount);
        }
    }

    /**
     * 기본회원등급 재등록을 방지하기 위해 check 하는 메서드.
     *
     * @throws DefaultMemberGradeIsExist 이미 기본회원등급이 존재합니다.
     * @author 김세미
     */
    private void checkExistDefaultMemberGrade() {
        if (memberGradeRepository.existsByIsDefaultIsTrue()) {
            throw new DefaultMemberGradeIsExist();
        }
    }
}
