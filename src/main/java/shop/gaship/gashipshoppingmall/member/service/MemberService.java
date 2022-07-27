package shop.gaship.gashipshoppingmall.member.service;

import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.request.ReissuePasswordRequest;
import shop.gaship.gashipshoppingmall.member.dto.response.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.ReissuePasswordQualificationResult;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
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
    void addMember(MemberCreationRequest memberCreationRequest);

    /**
     * 소셜회원을 등록하는 메서드입니다.
     *
     * @param memberCreationRequestOauth 회원가입을 위한 정보 객체입니다.
     */
    void addMemberByOauth(MemberCreationRequestOauth memberCreationRequestOauth);


    /**
     * 멤버의 정보를 변경하는 메서드 입니다.
     *
     * @param memberModifyRequestDto 회원이 수정하려는 회원정보가 담긴 객체입니다.
     */
    void modifyMember(MemberModifyRequestDto memberModifyRequestDto);


    /**
     * 멤버의 상태정보를 변경하는 매서드입니다.
     *
     * @param memberModifyByAdminDto 관리자가 수정하려는 멤버의 정보가 담긴 객체입니다.
     */
    void modifyMemberByAdmin(MemberModifyByAdminDto memberModifyByAdminDto);

    /**
     * 멤버를 삭제하는 메서드 입니다.
     *
     * @param memberNo 멤버 고유정보
     */
    void removeMember(Integer memberNo);

    /**
     * 멤버를 단건조회하는 메서드입니다.
     *
     * @param memberNo 멤버고유정보
     * @return 멤버의 상세정보가 담긴 객체를 반환합니다.
     */
    MemberResponseDto findMember(Integer memberNo);

    /**
     * 멤버를 다건조회하는 메서드입니다.
     *
     * @param pageable 조회하고자 하는 멤버정보들의 페이지와 사이즈를 가지고있는 객체입니다.
     * @return 멤버들을 정보를 페이징 단위로 가지고있는 객체입니다.
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
     * 닉네임이 존재하는가에 대한 확인을 하는 메서드입니다.
     *
     * @param nickname 닉네임 문자열
     * @return 닉네임 존재여부를 반환합니다.
     */
    boolean isAvailableNickname(String nickname);

    /**
     * 이메일을 통해서 회원을 찾는 메서드입니다.
     *
     * @param email 이메일 문자열
     * @return 멤버 객체를 반환합니다.
     */
    MemberResponseDto findMemberFromEmail(String email);

    /**
     * 회원을 닉네임으로 조회하는 메서드입니다.
     *
     * @param nickName 닉네임
     * @return 검색된 회원
     */
    Member findMemberFromNickname(String nickName);

    /**
     * 멤버 엔티티 객체를 MemberResponseDto로 변환해주는 메서드입니다.
     *
     * @param member 멤버 엔티티 객체입니다.
     * @return 변환된 MemberResponseDto객체입니다.
     */
    default MemberResponseDto entityToMemberResponseDto(Member member, Aes aes) {
        return MemberResponseDto.builder().memberNo(member.getMemberNo())
            .memberStatus(member.getMemberStatusCodes().toString())
            .email(aes.aesEcbDecode(member.getEmail())).authorities(
                member.getRoleSet().stream().map(Enum::toString).collect(Collectors.toList()))
            .password(member.getPassword()).nickname(member.getNickname()).name(member.getName())
            .gender(member.getGender()).phoneNumber(member.getPhoneNumber())
            .birthDate(member.getBirthDate())
            .accumulatePurchaseAmount(member.getAccumulatePurchaseAmount())
            .nextRenewalGradeDate(member.getNextRenewalGradeDate())
            .registerDatetime(member.getRegisterDatetime())
            .modifyDatetime(member.getModifiedDatetime()).social(member.isSocial()).build();
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
        return Member.builder().recommendMember(recommendMember).memberStatusCodes(defaultStatus)
            .memberGrades(defaultGrade).email(memberCreationRequest.getEmail())
            .nickname(memberCreationRequest.getNickName()).name(memberCreationRequest.getName())
            .password(memberCreationRequest.getPassword())
            .phoneNumber(memberCreationRequest.getPhoneNumber())
            .birthDate(memberCreationRequest.getBirthDate())
            .gender(memberCreationRequest.getGender()).accumulatePurchaseAmount(0L).isSocial(false)
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
        MemberCreationRequestOauth memberCreationRequestOauth, StatusCode defaultStatus,
        MemberGrade defaultGrade) {
        return Member.builder().memberStatusCodes(defaultStatus).memberGrades(defaultGrade)
            .email(memberCreationRequestOauth.getEmail())
            .nickname(memberCreationRequestOauth.getNickName())
            .name(memberCreationRequestOauth.getName())
            .password(memberCreationRequestOauth.getPassword())
            .phoneNumber(memberCreationRequestOauth.getPhoneNumber())
            .birthDate(memberCreationRequestOauth.getBirthDate())
            .gender(memberCreationRequestOauth.getGender()).accumulatePurchaseAmount(0L)
            .isSocial(true).build();
    }

    /**
     * 회원 상세 정보를 이메일을 통해서 조회하는 메서드입니다.
     *
     * @param email 이메일입니다.
     * @return 로그인을 시도하는 회원의 상세 정보 결과입니다.
     */
    SignInUserDetailsDto findSignInUserDetailFromEmail(String email);

    /**
     * 마지막 회원의 마지막 번호를 조회하는 메서드입니다.
     *
     * @return 마지막 회원번호입니다.
     */
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
