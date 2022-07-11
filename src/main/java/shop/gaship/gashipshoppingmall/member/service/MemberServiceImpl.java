package shop.gaship.gashipshoppingmall.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberRegisterRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

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

    @Transactional
    @Override
    public void register(MemberRegisterRequestDto memberRegisterRequestDto) {
        Member recommendMember = memberRepository.findByNickname(memberRegisterRequestDto.getRecommendMemberNickname())
                .orElseThrow(RuntimeException::new);

        // fixme : 멤버 회원가입 미완성
    }

    @Transactional
    @Override
    public void modify(MemberModifyRequestDto memberModifyRequestDto) {
        Member member = memberRepository.findById(memberModifyRequestDto.getMemberNo()).orElseThrow(RuntimeException::new);
        member.modifyMember(memberModifyRequestDto);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void delete(Integer memberNo) {
        memberRepository.deleteById(memberNo);
    }

    @Override
    public MemberResponseDto get(Integer memberNo) {
        return entityToDto(memberRepository.findById(memberNo).orElseThrow(RuntimeException::new));
    }

    @Override
    public List<MemberResponseDto> getList(Pageable pageable) {
        Function<Member, MemberResponseDto> converter = (this::entityToDto);
        return memberRepository.findAll(pageable).stream().map(converter).collect(Collectors.toList());
    }
}
