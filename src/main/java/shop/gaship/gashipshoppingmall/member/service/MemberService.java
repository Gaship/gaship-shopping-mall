package shop.gaship.gashipshoppingmall.member.service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.member.dto.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordQualificationResult;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordRequest;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.MembersRole;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 회원가입, member crud를 위해서 구현체에 필요한 메서드들을 정의한 인터페이스입니다.
 *
 * @author 최정우
 * @author 최겸준
 * @author 조재철
 * @since 1.0
 */
public interface MemberService {

    /**
     * 회원을 등록하는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입을 위한 정보 객체입니다.
     */
    void addMember(MemberCreationRequest memberCreationRequest) throws NoSuchAlgorithmException;

    /**
     * 소셜회원을 등록하는 메서드입니다.
     *
     * @param memberCreationRequestOauth 회원가입을 위한 정보 객체입니다.
     */
    void addMember(MemberCreationRequestOauth memberCreationRequestOauth)
        throws NoSuchAlgorithmException;


    /**
     * 멤버의 정보를 변경하는 메서드 입니다.
     *
     * @param memberModifyRequestDto the member modify request dto
     */
    void modifyMember(MemberModifyRequestDto memberModifyRequestDto);

    /**
     * 멤버를 삭제하는 메서드 입니다.
     *
     * @param memberNo the member no
     */
    void removeMember(Integer memberNo);

    /**
     * 멤버를 단건조회하는 메서드입니다.
     *
     * @param memberNo the member no
     * @return the member response dto
     * @throws MemberNotFoundException the member not found exception
     */
    MemberResponseDto findMember(Integer memberNo);

    /**
     * 멤버를 다건조회하는 메서드입니다.
     *
     * @param pageable the pageable
     * @return the list
     */
    MemberPageResponseDto<MemberResponseDto, Member> findMembers(Pageable pageable);

    /**
     * 이메일이 존재하는가에 대한 확인을 하는 메서드입니다.
     *
     * @param email 이메일 문자열
     * @return 이메일 존재여부를 반환합니다.
     */
    boolean isAvailableEmail(String email);

    /**
     * 이메일을 통해서 회원을 찾는 메서드입니다.
     *
     * @param email 이메일 문자열
     * @return 멤버 객체를 반환합니다.
     */
    MemberResponseDto findMemberFromEmail(String email) throws NoSuchAlgorithmException;

    /**
     * 회원을 닉네임으로 조회하는 메서드입니다.
     *
     * @param nickName 닉네임
     * @return 검색된 회원
     */
    Member findMemberFromNickname(String nickName);

    /**
     * Entity to dto member response dto.
     *
     * @param member the member
     * @return the member response dto
     */

    default MemberResponseDto entityToMemberResponseDto(Member member, Aes aes) {
        return MemberResponseDto.builder()
            .memberNo(member.getMemberNo())
            .memberStatus(member.getMemberStatusCodes().toString())
            .email(aes.aesEcbDecode(member.getEmail()))
            .authorities(member.getRoleSet().stream()
                .map(Enum::toString)
                .collect(Collectors.toList()))
            .password(member.getPassword())
            .nickname(member.getNickname())
            .name(member.getName())
            .gender(member.getGender())
            .phoneNumber(member.getPhoneNumber())
            .birthDate(member.getBirthDate())
            .accumulatePurchaseAmount(member.getAccumulatePurchaseAmount())
            .nextRenewalGradeDate(member.getNextRenewalGradeDate())
            .registerDatetime(member.getRegisterDatetime())
            .modifyDatetime(member.getModifiedDatetime())
            .social(member.isSocial())
            .build();
    }


    /**
     * 필수정보를 받아 새로운 회원을 반환하는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입에 필요한 최소한의 요구 데이터.
     * @param recommendMember       신규가입하는 회원이 추천하는 회원.
     * @param defaultStatus         신규회원의 초기 상태값
     * @param defaultGrade          초기 등급
     * @return 신규 회원가입된 회원 객체를 반환합니다.
     */
    default Member creationRequestToMemberEntity(MemberCreationRequest memberCreationRequest,
                                                 @Nullable Member recommendMember,
                                                 StatusCode defaultStatus,
                                                 MemberGrade defaultGrade) {

        return Member.builder()
            .recommendMember(recommendMember)
            .memberStatusCodes(defaultStatus)
            .memberGrades(defaultGrade)
            .email(memberCreationRequest.getEmail())
            .nickname(memberCreationRequest.getNickName())
            .name(memberCreationRequest.getName())
            .password(memberCreationRequest.getPassword())
            .phoneNumber(memberCreationRequest.getPhoneNumber())
            .birthDate(memberCreationRequest.getBirthDate())
            .gender(memberCreationRequest.getGender())
            .accumulatePurchaseAmount(0L)
            .roleSet(Set.of(MembersRole.ROLE_USER))
            .encodedEmailForSearch(memberCreationRequest.getEncodedEmailForSearch())
            .build();
    }

    /**
     * 필수정보를 받아 새로운 소셜 회원을 반환하는 메서드입니다.
     *
     * @param memberCreationRequestOauth 회원가입에 필요한 최소한의 요구 데이터.
     * @param defaultStatus              신규회원의 초기 상태값
     * @param defaultGrade               초기 등급
     * @return 신규 회원가입된 회원 객체를 반환합니다.
     */
    default Member creationRequestToMemberEntity(
        MemberCreationRequestOauth memberCreationRequestOauth,
        StatusCode defaultStatus,
        MemberGrade defaultGrade) {

        return Member.builder()
            .memberStatusCodes(defaultStatus)
            .memberGrades(defaultGrade)
            .email(memberCreationRequestOauth.getEmail())
            .password(memberCreationRequestOauth.getPassword())
            .name(memberCreationRequestOauth.getName())
            .nickname(memberCreationRequestOauth.getNickName())
            .accumulatePurchaseAmount(0L)
            .nextRenewalGradeDate(LocalDate.now())
            .phoneNumber(memberCreationRequestOauth.getPhoneNumber())
            .birthDate(memberCreationRequestOauth.getBirthDate())
            .gender(memberCreationRequestOauth.getGender())
            .isSocial(true)
            .roleSet(Set.of(MembersRole.ROLE_USER))
            .encodedEmailForSearch(memberCreationRequestOauth.getEncodedEmailForSearch())
            .build();
    }

    /**
     * 회원 상세 정보를 이메일을 통해서 조회하는 메서드입니다.
     *
     * @param email 이메일입니다.
     * @return 로그인을 시도하는 회원의 상세 정보 결과입니다.
     */
    SignInUserDetailsDto findSignInUserDetailFromEmail(String email);

    Integer findLastNo();


    /**
     * 닉네임을 통해서 데이터의 일부가 가려진 이메일을 얻어옵니다.
     *
     * @param nickname 멤버의 닉네임 입니다.
     * @return 이메일 데이터 일부가 가려진 결과를 가진 객체를 반환합니다.
     */
    FindMemberEmailResponse findMemberEmailFromNickname(String nickname);

    /**
     * 비밀번호 재발급을 위해 멤버의 정보 일치한지 확인합니다.
     *
     * @param reissuePasswordRequest 비밀번호를 재발급 받기 위해 이름, 이메일 정보가 담긴 객체입니다.
     * @return 재발급 자격 여부를 반환합니다.
     */
    ReissuePasswordQualificationResult checkReissuePasswordQualification(
        ReissuePasswordRequest reissuePasswordRequest) throws NoSuchAlgorithmException;
}
