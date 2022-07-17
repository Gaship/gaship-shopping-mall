package shop.gaship.gashipshoppingmall.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.member.dto.MemberAddRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.service
 * fileName       : MemberServiceImpl
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberGradeRepository memberGradeRepository;

    @Transactional
    @Override
    public void addMember(MemberAddRequestDto request) {
        Member recommendMember = memberRepository.findByNickname(request.getRecommendMemberNickname()).orElseThrow(MemberNotFoundException::new);
        MemberGrade memberGrade = memberGradeRepository.findById(0).orElseThrow(MemberGradeNotFoundException::new);
        StatusCode statusCode = statusCodeRepository.findByStatusCodeName("활성").orElseThrow(RuntimeException::new);
        Member member = dtoToEntity(request, recommendMember, statusCode, memberGrade, false);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        Member member = memberRepository.findById(request.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        member.modifyMember(request);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void removeMember(Integer memberNo) {
        memberRepository.deleteById(memberNo);
    }

    @Override
    public MemberResponseDto findMember(Integer memberNo) {
        return entityToDto(memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new));
    }

    @Override
    public List<MemberResponseDto> findMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
