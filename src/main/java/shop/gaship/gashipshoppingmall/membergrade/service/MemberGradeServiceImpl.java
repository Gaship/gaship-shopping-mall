package shop.gaship.gashipshoppingmall.membergrade.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeInUseException;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
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

    @Transactional
    @Override
    public void addMemberGrade(MemberGradeRequestDto request) {
        StatusCode renewalPeriod = statusCodeRepository
                .findById(1)
                .orElseThrow(StatusCodeNotFoundException::new);

        MemberGrade memberGrade = MemberGrade.builder()
                .renewalPeriod(renewalPeriod)
                .name(request.getName())
                .accumulateAmount(request.getAccumulateAmount())
                .build();

        memberGradeRepository.save(memberGrade);
    }

    @Transactional
    @Override
    public void modifyMemberGrade(Integer memberGradeNo, MemberGradeRequestDto request) {
        MemberGrade memberGrade = memberGradeRepository
                .findById(memberGradeNo)
                .orElseThrow(MemberGradeNotFoundException::new);

        memberGrade.modify(request);

        memberGradeRepository.save(memberGrade);
    }

    @Transactional
    @Override
    public void removeMemberGrade(Integer memberGradeNo) {
        MemberGrade memberGrade = memberGradeRepository
                .findById(memberGradeNo)
                .orElseThrow(MemberGradeNotFoundException::new);

        if (!memberRepository.findByMemberGrades(memberGrade).isEmpty()) {
            throw new MemberGradeInUseException();
        }

        memberGradeRepository.delete(memberGrade);
    }

    @Override
    public MemberGradeDto findMemberGrade(Integer memberGradeNo) {
        return memberGradeRepository.getMemberGradeBy(memberGradeNo)
                .orElseThrow(MemberGradeNotFoundException::new);
    }

    @Override
    public List<MemberGradeDto> findMemberGrades(Pageable page) {
        return memberGradeRepository.getMemberGrades(page);
    }
}
