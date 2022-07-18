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
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
 * MemberService를 구현하는 클래스입니다.
 *
 * @see MemberService
 * @author 김민수
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    /**
     * 신규회훤의 기본 상태 번호입니다.
     */
    private static final int MEMBER_STATUS_ID = 2;
    /**
     * 신규 회원의 기본등급 번호입니다.
     */
    private static final int MEMBER_GRADE_ID = 1;

    private final MemberRepository memberRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final Aes aes;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void addMember(MemberCreationRequest memberCreationRequest) {
        Member recommendMember = memberRepository
            .findById(memberCreationRequest.getRecommendMemberNo())
            .orElse(null);
        StatusCode defaultStatus = statusCodeRepository.findById(MEMBER_STATUS_ID)
            .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findById(MEMBER_GRADE_ID)
            .orElseThrow(MemberGradeNotFoundException::new);

        Member savedMember = creationRequestToMemberEntity(
            encodePrivacyUserInformation(memberCreationRequest),
            recommendMember,
            defaultStatus,
            defaultGrade
        );

        memberRepository.saveAndFlush(savedMember);
    }

    @Override
    @Transactional
    public void addMember(MemberCreationRequestOauth memberCreationRequestOauth) {
        StatusCode defaultStatus = statusCodeRepository.findById(MEMBER_STATUS_ID)
            .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findById(MEMBER_GRADE_ID)
            .orElseThrow(MemberGradeNotFoundException::new);

        Member savedMember = creationRequestToMemberEntity(
            encodePrivacyUserInformation(memberCreationRequestOauth),
            defaultStatus,
            defaultGrade
        );

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

        if (!Objects.isNull(memberCreationRequest.getPhoneNumber())) {
            memberCreationRequest.setPhoneNumber(
            aes.aesECBEncode(memberCreationRequest.getPhoneNumber()));
        }

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
        memberCreationRequestOauth.setEmail(aes.aesECBEncode(memberCreationRequestOauth.getEmail()));
        memberCreationRequestOauth.setName(aes.aesECBEncode(memberCreationRequestOauth.getName()));
        memberCreationRequestOauth.setPhoneNumber(
            aes.aesECBEncode(memberCreationRequestOauth.getPhoneNumber()));
        memberCreationRequestOauth.setPassword(
            passwordEncoder.encode(memberCreationRequestOauth.getPassword()));

        return memberCreationRequestOauth;
    }

    @Override
    public boolean isAvailableEmail(String email) {
        try{
            findMemberFromEmail(email);
        } catch (MemberNotFoundException e){
            return false;
        }
        return true;
    }

    /**
     * email로 member를 조회하고 memberResponseDto를 반환받기위한 기능입니다.
     *
     * @param email 회원을 찾을 매개체
     * @return MemberResponseDto 조회한 member를 dto로 변경하여 반환합니다.
     */
    @Override
    public MemberResponseDto findMemberFromEmail(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
        return entityToDto(member);
    }

    @Override
    public Member findMemberFromNickname(String nickName) {
        return memberRepository.findByNickname(nickName)
            .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        if (memberRepository.existsByNickname(request.getNickname())){
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
