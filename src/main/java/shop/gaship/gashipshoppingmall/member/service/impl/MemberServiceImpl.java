package shop.gaship.gashipshoppingmall.member.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.member.adapter.MemberAdapter;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.request.ReissuePasswordRequest;
import shop.gaship.gashipshoppingmall.member.dto.response.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDtoByAdmin;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.event.domain.SignedUpEvent;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.InvalidReissueQualificationException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;
import shop.gaship.gashipshoppingmall.util.Events;
import shop.gaship.gashipshoppingmall.util.PageResponse;

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
    private final MemberRepository memberRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final MemberAdapter memberAdapter;
    private final Aes aes;
    private final Sha512 sha512;
    private final PasswordEncoder passwordEncoder;


    /**
     * {@inheritDoc}
     *
     * @param memberCreationRequest 회원가입을 위한 정보 객체입니다.
     * @throws StatusCodeNotFoundException 상태정보를 찾지 못하였을 때 예외를 던집니다.
     */
    @Override
    @Transactional
    public void addMember(MemberCreationRequest memberCreationRequest) {
        Member recommendMember =
            memberRepository.findById(
                memberCreationRequest.getRecommendMemberNo()).orElse(null);
        StatusCode defaultStatus =
            statusCodeRepository.findByStatusCodeName(MemberStatus.ACTIVATION.name())
                .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findByDefaultGrade();

        if (memberRepository.existsByNickname(memberCreationRequest.getNickName())) {
            throw new DuplicatedNicknameException();
        }

        Member savedMember =
            creationRequestToMemberEntity(encodePrivacyUserInformation(memberCreationRequest),
                recommendMember, defaultStatus, defaultGrade);

        memberRepository.saveAndFlush(savedMember);

        if (Objects.isNull(recommendMember)) {
            return;
        }
        Events.raise(new SignedUpEvent(recommendMember.getMemberNo()));
    }

    /**
     * {@inheritDoc}
     *
     * @param memberCreationRequestOauth 회원가입을 위한 정보 객체입니다.
     * @throws StatusCodeNotFoundException 상태정보를 찾지 못하였을 때 예외를 던집니다.
     */
    @Override
    @Transactional
    public void addMemberByOauth(MemberCreationRequestOauth memberCreationRequestOauth) {
        StatusCode defaultStatus =
            statusCodeRepository.findByStatusCodeName(MemberStatus.ACTIVATION.name())
                .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findByDefaultGrade();

        Member savedMember =
            creationRequestToMemberEntity(
                encodePrivacyUserInformation(memberCreationRequestOauth),
                defaultStatus, defaultGrade);

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
        String email = memberCreationRequest.getEmail();
        memberCreationRequest.setEmail(aes.aesEcbEncode(memberCreationRequest.getEmail()));
        memberCreationRequest.setName(aes.aesEcbEncode(memberCreationRequest.getName()));
        memberCreationRequest.setPhoneNumber(
            aes.aesEcbEncode(memberCreationRequest.getPhoneNumber()));
        memberCreationRequest.setPassword(memberCreationRequest.getPassword());
        memberCreationRequest.setEncodedEmailForSearch(sha512.encryptPlainText(email));

        return memberCreationRequest;
    }

    /**
     * 회원 정보 중 중요한 정보를 암호화하여 저장하는 메서드입니다.
     *
     * @param request 수정된 회원 정보가 담기는 객체
     * @return 중요 정보가 암호화 된 회원정보 객체
     */
    private MemberModifyRequestDto encodePrivacyUserInformation(
        MemberModifyRequestDto request) {
        request.changePassword(aes.aesEcbEncode(request.getPassword()));
        request.changeName(aes.aesEcbEncode(request.getPassword()));
        request.changePhoneNumber(aes.aesEcbEncode(request.getPassword()));

        return request;
    }

    /**
     * 소셜회원가입시 회원 정보 중 중요한 정보를 암호화하여 저장하는 메서드입니다.
     *
     * @param memberCreationRequestOauth 회원 가입할 정보가 담긴 객체
     * @return 중요 정보가 암호화 된 회원정보 객체
     */
    private MemberCreationRequestOauth encodePrivacyUserInformation(
        MemberCreationRequestOauth memberCreationRequestOauth) {
        String email = memberCreationRequestOauth.getEmail();
        memberCreationRequestOauth.setEmail(aes.aesEcbEncode(email));
        memberCreationRequestOauth.setName(aes.aesEcbEncode(memberCreationRequestOauth.getName()));
        memberCreationRequestOauth.setEncodedEmailForSearch(sha512.encryptPlainText(email));

        if (!isNullOrEmpty(memberCreationRequestOauth.getPhoneNumber())) {
            memberCreationRequestOauth.setPhoneNumber(
                aes.aesEcbEncode(memberCreationRequestOauth.getPhoneNumber()));
        }

        memberCreationRequestOauth.setPassword(memberCreationRequestOauth.getPassword());

        return memberCreationRequestOauth;
    }

    /**
     * 빈 값이거나 null인지 확인하기 위해 체크하는 메서드입니다.
     *
     * @param text 비거나, null인지 확인 할 대상의 문자열
     * @return 검사 결과값을 반환합니다.
     */
    private boolean isNullOrEmpty(String text) {
        return Objects.isNull(text) || text.isEmpty();
    }

    /**
     * {@inheritDoc}
     *
     * @param email 이메일 문자열
     * @return 회원의 존재여부입니다.
     */
    @Override
    public boolean isAvailableEmail(String email) {
        try {
            findMemberFromEmail(email);
        } catch (MemberNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param nickname 닉네임 문자열
     * @return 회원의 존재여부입니다.
     */
    @Override
    public boolean isAvailableNickname(String nickname) {
        try {
            findMemberFromNickname(nickname);
        } catch (MemberNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param email 이메일 문자열
     * @return 멤버
     * @throws MemberNotFoundException 멤버 정보를 못찾았을 때 던집니다.
     */
    @Override
    public MemberResponseDto findMemberFromEmail(String email) {

        Member member = memberRepository.findByEncodedEmailForSearch(sha512.encryptPlainText(email))
            .orElseThrow(MemberNotFoundException::new);
        return entityToMemberResponseDto(member, aes);
    }

    /**
     * {@inheritDoc}
     *
     * @param nickName 닉네임
     * @return 멤버엔티티 정보
     * @throws MemberNotFoundException 멤버 정보를 못찾았을 때 던집니다.
     */
    @Override
    public Member findMemberFromNickname(String nickName) {
        return memberRepository.findByNickname(nickName).orElseThrow(MemberNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     *
     * @param request 회원이 수정하려는 회원정보가 담긴 객체입니다.
     * @throws MemberNotFoundException 멤버 정보를 못찾았을 때 던집니다.
     * @author 최정우
     */
    @Transactional
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new DuplicatedNicknameException();
        }

        Member member = memberRepository.findById(request.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);
        MemberModifyRequestDto encodedRequest = encodePrivacyUserInformation(request);
        member.modifyMember(encodedRequest);
    }

    /**
     * {@inheritDoc}
     *
     * @param memberModifyByAdminDto 관리자가 수정하려는 멤버의 정보가 담긴 객체입니다.
     * @throws MemberNotFoundException     멤버 정보를 못찾았을 때 던집니다.
     * @throws StatusCodeNotFoundException 상태정보를 찾지 못하였을 때 예외를 던집니다.
     */
    @Transactional
    @Override
    public void modifyMemberByAdmin(MemberModifyByAdminDto memberModifyByAdminDto) {
        Member member = memberRepository.findById(memberModifyByAdminDto.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);
        StatusCode statusCode =
            statusCodeRepository.findByStatusCodeName(memberModifyByAdminDto.getStatus())
                .orElseThrow(StatusCodeNotFoundException::new);

        member.modifyMemberByAdmin(member.getNickname(), statusCode);
    }

    /**
     * {@inheritDoc}
     *
     * @param memberNo 멤버 고유정보
     */
    @Transactional
    @Override
    public void removeMember(Integer memberNo) {
        memberRepository.deleteById(memberNo);
    }

    /**
     * {@inheritDoc}
     *
     * @param memberNo 멤버고유정보
     * @return 검색된 회원
     * @throws MemberNotFoundException 멤버정보를 찾지 못하였을 때 던집니다.
     */
    @Override
    public MemberResponseDto findMember(Integer memberNo) {
        return entityToMemberResponseDto(
            memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new), aes);
    }


    @Override
    public MemberResponseDtoByAdmin findMemberByAdmin(Integer memberNo) {
        return entityToMemberResponseDtoByAdmin(
            memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new), aes);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 조회하고자 하는 멤버정보들의 페이지와 사이즈를 가지고있는 객체입니다.
     * @return 멤버들을 정보를 페이징 단위로 가지고있는 객체입니다.
     */
    @Override
    public PageResponse<MemberResponseDtoByAdmin> findMembers(Pageable pageable) {
        Page<MemberResponseDtoByAdmin> page = memberRepository.findMembers(pageable);
        PageResponse<MemberResponseDtoByAdmin> response = new PageResponse<>(page);
        List<MemberResponseDtoByAdmin> decodedMembers = page.getContent().stream()
                .map(member -> entityToMemberResponseDtoByAdmin(member, aes))
                .collect(Collectors.toList());

        response.decodeContent(decodedMembers);
        return response;
    }

    /**
     * {@inheritDoc}
     *
     * @return 멤버 고유번호
     */
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
        String memberEmail =
            memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new)
                .getEmail();

        String emailIdPart = memberEmail.substring(0, memberEmail.indexOf('@'));
        String emailDomainPart = memberEmail.substring(memberEmail.indexOf('@'));

        double idPartHalfLength = emailIdPart.length() / 2.0;
        String obscuredEmail = emailIdPart.substring(0, (int) Math.ceil(idPartHalfLength))
            + "*".repeat((int) Math.floor(idPartHalfLength)) + emailDomainPart;

        return new FindMemberEmailResponse(obscuredEmail);
    }

    /**
     * {@inheritDoc}
     * </p>
     * 비밀번호가 정상적으로 변경되면 true를 리턴하고, 아니면 예외를 던집니다.
     *
     * @param reissuePasswordRequest 비밀번호를 재발급 받기 위해 이름, 이메일 정보가 담긴 객체입니다.
     * @return 정상적으로 변경이 완료되었을 시 true를 반환합니다.
     * @throws MemberNotFoundException              이메일을 통해서 조회시 멤버 정보가 없을 때 해당 예외를 던집니다.
     * @throws InvalidReissueQualificationException 실명이 다를 때 예외를 발생기킵니다.
     */
    @Override
    @Transactional
    public Boolean reissuePassword(ReissuePasswordRequest reissuePasswordRequest) {
        String email = reissuePasswordRequest.getEmail();
        Member member = memberRepository.findByEncodedEmailForSearch(sha512.encryptPlainText(email))
            .orElseThrow(MemberNotFoundException::new);
        String decodedMemberName = aes.aesEcbDecode(member.getName());
        String requestMemberName = reissuePasswordRequest.getName();
        if (!Objects.equals(decodedMemberName, requestMemberName)) {
            throw new InvalidReissueQualificationException();
        }

        String renewalPassword = Objects.requireNonNull(memberAdapter.requestSendReissuePassword(
                    new ReissuePasswordReceiveEmailDto(
                        member.getEmail()))
                .getBody())
            .getReissuedPassword();
        String hashedPassword = passwordEncoder.encode(renewalPassword);

        member.modifyMemberPassword(hashedPassword);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param email 이메일입니다.
     * @return 로그인을 시도하는 회원의 상세 정보 결과입니다.
     * @throws MemberNotFoundException 멤버정보를 찾지 못하였을 때 던집니다.
     */
    @Override
    public SignInUserDetailsDto findSignInUserDetailFromEmail(String email) {
        return memberRepository.findSignInUserDetail(email)
            .orElseThrow(MemberNotFoundException::new);
    }
}
