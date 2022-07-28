package shop.gaship.gashipshoppingmall.membergrade.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.AdvancementMemberRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.service.AdvancementService;

/**
 * 회원 승급 Service interface 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see AdvancementService
 */
@Service
@RequiredArgsConstructor
public class AdvancementServiceImpl implements AdvancementService {
    private final MemberRepository memberRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final GradeHistoryService gradeHistoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AdvancementTargetResponseDto>
        findTargetsByRenewalGradeDate(String nextRenewalGradeDate) {
        return memberRepository
                .findMembersByNextRenewalGradeDate(LocalDate.parse(nextRenewalGradeDate));
    }

    /**
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     * @throws MemberGradeNotFoundException 회원등급을 찾을 수 없습니다.
     */
    @Override
    @Transactional
    public void renewalMemberGrade(RenewalMemberGradeRequestDto requestDto) {
        updateGradeOfMember(requestDto.getAdvancementMemberRequestDto());
        gradeHistoryService.addGradeHistory(requestDto.getGradeHistoryAddRequestDto());
    }

    /**
     * 회원의 등급정보를 갱신하는 메서드.
     *
     * @param requestDto 등급 갱신 대상에 대한 정보를 담은 dto.
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     * @throws MemberGradeNotFoundException 회원등급을 찾을 수 없습니다.
     */
    @Transactional
    public void updateGradeOfMember(AdvancementMemberRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberNo())
                .orElseThrow(MemberNotFoundException::new);

        MemberGrade memberGrade = memberGradeRepository.findById(requestDto.getMemberGradeNo())
                .orElseThrow(MemberGradeNotFoundException::new);

        member.modifyGrade(memberGrade, requestDto.getNextRenewalGradeDate());
    }
}
