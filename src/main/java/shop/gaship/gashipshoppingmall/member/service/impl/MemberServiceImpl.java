package shop.gaship.gashipshoppingmall.member.service.impl;

import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.UserAuthority;

/**
 * MemberService를 구현하는 클래스입니다.
 *
 * @author 김민수
 * @author 최겸준
 * @author 최정우
 * @see MemberService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final Aes aes;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void addMember(MemberCreationRequest memberCreationRequest) {
        Member recommendMember =
            memberRepository.findById(memberCreationRequest.getRecommendMemberNo()).orElse(null);
        StatusCode defaultStatus =
            statusCodeRepository.findByStatusCodeName(MemberStatus.DORMANCY.name())
                .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode defaultAuthority =
            statusCodeRepository.findByStatusCodeName(UserAuthority.MEMBER.name())
                .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findByDefaultGrade();

        Member savedMember =
            creationRequestToMemberEntity(encodePrivacyUserInformation(memberCreationRequest),
                recommendMember, defaultStatus, defaultAuthority, defaultGrade);

        memberRepository.saveAndFlush(savedMember);
    }

    @Override
    @Transactional
    public void addMember(MemberCreationRequestOauth memberCreationRequestOauth) {
        StatusCode defaultStatus =
            statusCodeRepository.findByStatusCodeName(MemberStatus.DORMANCY.name())
                .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode defaultAuthority =
            statusCodeRepository.findByStatusCodeName(UserAuthority.MEMBER.name())
                .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findByDefaultGrade();

        Member savedMember =
            creationRequestToMemberEntity(encodePrivacyUserInformation(memberCreationRequestOauth),
                defaultStatus, defaultAuthority, defaultGrade);

        memberRepository.saveAndFlush(savedMember);
    }

    /**
     * 회원 정보 중 중요한 정보를 암호화하여 저장하는 메서드입니다.
     *
     * @param memberCreationRequest 회원 가입할 정보가 담긴 객체
     * @return 중요 정보가 암호화 된 회원정보 객체
     */
    private MemberCreationRequest encodePrivacyUserInformation(
        MemberCreationRequest memberCreationRequest) {
        memberCreationRequest.setEmail(aes.aesECBEncode(memberCreationRequest.getEmail()));
        memberCreationRequest.setName(aes.aesECBEncode(memberCreationRequest.getName()));
        memberCreationRequest.setPhoneNumber(
            aes.aesECBEncode(memberCreationRequest.getPhoneNumber()));
        memberCreationRequest.setPassword(
            passwordEncoder.encode(memberCreationRequest.getPassword()));

        return memberCreationRequest;
    }

    /**
     * 소셜회원가입시 회원 정보 중 중요한 정보를 암호화하여 저장하는 메서드입니다.
     *
     * @param memberCreationRequestOauth 회원 가입할 정보가 담긴 객체
     * @return 중요 정보가 암호화 된 회원정보 객체
     */
    private MemberCreationRequestOauth encodePrivacyUserInformation(
        MemberCreationRequestOauth memberCreationRequestOauth) {
        memberCreationRequestOauth.setEmail(
            aes.aesECBEncode(memberCreationRequestOauth.getEmail()));
        memberCreationRequestOauth.setName(aes.aesECBEncode(memberCreationRequestOauth.getName()));

        if (!Objects.isNull(memberCreationRequestOauth.getPhoneNumber())) {
            memberCreationRequestOauth.setPhoneNumber(
                aes.aesECBEncode(memberCreationRequestOauth.getPhoneNumber()));
        }

        memberCreationRequestOauth.setPassword(
            passwordEncoder.encode(memberCreationRequestOauth.getPassword()));

        return memberCreationRequestOauth;
    }

    @Override
    public boolean isAvailableEmail(String email) {
        try {
            findMemberFromEmail(email);
        } catch (MemberNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public MemberResponseDto findMemberFromEmail(String email) {
        Member member =
            memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        return entityToMemberResponseDto(member);
    }

    @Override
    public Member findMemberFromNickname(String nickName) {
        return memberRepository.findByNickname(nickName).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new DuplicatedNicknameException();
        }
        Member member = memberRepository.findById(request.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);
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
        return entityToMemberResponseDto(
            memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new));
    }

    @Override
    public MemberPageResponseDto findMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Function<Member, MemberResponseDto> fn = (this::entityToMemberResponseDto);
        return new MemberPageResponseDto<>(page, fn);
    }

    @Override
    public Integer findLastNo() {
        return memberRepository.findLastNo();
    }

    @Override
    public SignInUserDetailsDto findSignInUserDetailFromEmail(String email) {
        return memberRepository.findSignInUserDetail(email)
            .orElseThrow(MemberNotFoundException::new);
    }
}
