package shop.gaship.gashipshoppingmall.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.member.dto.MemberAddRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.StatusCodeNotFoundException;
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
 * tagservice를 구현하는 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
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
        if (memberRepository.existsByNickName(request.getNickname())){
            throw new DuplicatedNicknameException();
        }
        Member recommendMember = memberRepository.findByNickname(request.getRecommendMemberNickname()).orElseThrow(MemberNotFoundException::new);
        MemberGrade memberGrade = memberGradeRepository.findById(0).orElseThrow(MemberGradeNotFoundException::new);
        StatusCode statusCode = statusCodeRepository.findByStatusCodeName(MemberStatus.ACTIVATION.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        Member member = dtoToEntity(request, recommendMember, statusCode, memberGrade, false);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        if (memberRepository.existsByNickName(request.getNickname())){
            throw new DuplicatedNicknameException();
        }
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
    public MemberPageResponseDto findMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Function<Member,MemberResponseDto> fn = (this::entityToDto);
        return new MemberPageResponseDto<>(page,fn);
    }
}
