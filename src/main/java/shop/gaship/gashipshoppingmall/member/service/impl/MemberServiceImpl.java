package shop.gaship.gashipshoppingmall.member.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.member.dto.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordQualificationResult;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordRequest;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
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
 * @author 김민수
 * @author 최겸준
 * @author 최정우
 * @author 조재철
 * @see MemberService
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
    private final Sha512 sha512;


    @Override
    @Transactional
    public void addMember(MemberCreationRequest memberCreationRequest)
        throws NoSuchAlgorithmException {
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
    public void addMember(MemberCreationRequestOauth memberCreationRequestOauth)
        throws NoSuchAlgorithmException {
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
        MemberCreationRequest memberCreationRequest) throws NoSuchAlgorithmException {
        String email = memberCreationRequest.getEmail();
        memberCreationRequest.setEmail(aes.aesECBEncode(memberCreationRequest.getEmail()));
        memberCreationRequest.setName(aes.aesECBEncode(memberCreationRequest.getName()));
        memberCreationRequest.setPhoneNumber(
            aes.aesECBEncode(memberCreationRequest.getPhoneNumber()));
        memberCreationRequest.setPassword(memberCreationRequest.getPassword());
        memberCreationRequest.setEncodedEmailForSearch(sha512.encrypt(email));

        return memberCreationRequest;
    }

    /**
     * 소셜회원가입시 회원 정보 중 중요한 정보를 암호화하여 저장하는 메서드입니다.
     *
     * @param email
     * @param memberCreationRequestOauth 회원 가입할 정보가 담긴 객체
     * @return 중요 정보가 암호화 된 회원정보 객체
     */
    private MemberCreationRequestOauth encodePrivacyUserInformation(
        MemberCreationRequestOauth memberCreationRequestOauth) throws NoSuchAlgorithmException {
        String email = memberCreationRequestOauth.getEmail();
        memberCreationRequestOauth.setEmail(aes.aesECBEncode(email));
        memberCreationRequestOauth.setName(aes.aesECBEncode(memberCreationRequestOauth.getName()));
        memberCreationRequestOauth.setEncodedEmailForSearch(sha512.encrypt(email));

        if (!Objects.isNull(memberCreationRequestOauth.getPhoneNumber())) {
            memberCreationRequestOauth.setPhoneNumber(
                aes.aesECBEncode(memberCreationRequestOauth.getPhoneNumber()));
        }

        memberCreationRequestOauth.setPassword(memberCreationRequestOauth.getPassword());

        return memberCreationRequestOauth;
    }

    @Override
    public boolean isAvailableEmail(String email) {
        try {
            findMemberFromEmail(email);
        } catch (MemberNotFoundException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public MemberResponseDto findMemberFromEmail(String email) throws NoSuchAlgorithmException {

        Member member = memberRepository.findByEncodedEmailForSearch(sha512.encrypt(email))
            .orElseThrow(MemberNotFoundException::new);
        return entityToMemberResponseDto(member, aes);
    }

    @Override
    public Member findMemberFromNickname(String nickName) {
        return memberRepository.findByNickname(nickName)
            .orElseThrow(MemberNotFoundException::new);
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
            memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new), aes);
    }

    @Override
    public MemberPageResponseDto findMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Function<Member, MemberResponseDto> fn = (member -> entityToMemberResponseDto(member, aes));
        return new MemberPageResponseDto<>(page, fn);
    }

    @Override
    public Integer findLastNo() {
        return memberRepository.findLastNo();
    }

    /**
     * {@inheritDoc}
     *
     * @param nickname 찾고자하는 이메일의 멤버 닉네임입니다.
     * @return 이메일의 일부가 가려진 데이터를 담고있는 객체를 반환합니다.
     * @throws MemberNotFoundException 닉네임을 통해서 멤버를 찾지 못하였을 때 던집니다.
     */
    @Override
    public FindMemberEmailResponse findMemberEmailFromNickname(String nickname) {
        String memberEmail = memberRepository.findByNickname(nickname)
            .orElseThrow(MemberNotFoundException::new)
            .getEmail();

        String emailIdPart = memberEmail.substring(0, memberEmail.indexOf("@"));
        String emailDomainPart = memberEmail.substring(
            memberEmail.indexOf("@"));

        double idPartHalfLength = emailIdPart.length() / 2.0;
        String obscuredEmail =
            emailIdPart.substring(0, (int) Math.ceil(idPartHalfLength)) +
                "*".repeat((int) Math.floor(idPartHalfLength)) +
                emailDomainPart;

        return new FindMemberEmailResponse(obscuredEmail);
    }

    @Override
    public ReissuePasswordQualificationResult checkReissuePasswordQualification(
        ReissuePasswordRequest reissuePasswordRequest) {
//        TODO : 엔티티 병합 후 진행예정
        return null;
    }

    @Override
    public SignInUserDetailsDto findSignInUserDetailFromEmail(String email) {
        return memberRepository.findSignInUserDetail(email)
            .orElseThrow(MemberNotFoundException::new);
    }
}
