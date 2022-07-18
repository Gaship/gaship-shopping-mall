package shop.gaship.gashipshoppingmall.member.service;

import org.springframework.lang.Nullable;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * member crud를 담당하는 service 입니다.
 *
 * @author 최정우
 * @author 최겸준
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
     * @param memberCreationRequest 회원가입을 위한 정보 객체입니다.
     */
    void addMember(MemberCreationRequestOauth memberCreationRequestOauth);


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
    MemberPageResponseDto<MemberResponseDto,Member> findMembers(Pageable pageable);

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
    MemberResponseDto findMemberFromEmail(String email);

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
    default MemberResponseDto entityToDto(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .accumulatePurchaseAmount(member.getAccumulatePurchaseAmount())
                .birthDate(member.getNextRenewalGradeDate())
                .registerDatetime(member.getRegisterDatetime())
                .modifyDatetime(member.getModifiedDatetime())
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
            .build();
    }

    /**
     * 필수정보를 받아 새로운 소셜 회원을 반환하는 메서드입니다.
     *
     * @param memberCreationRequestOauth 회원가입에 필요한 최소한의 요구 데이터.
     * @param defaultStatus         신규회원의 초기 상태값
     * @param defaultGrade          초기 등급
     * @return 신규 회원가입된 회원 객체를 반환합니다.
     */
    default Member creationRequestToMemberEntity(MemberCreationRequestOauth memberCreationRequestOauth,
                                                 StatusCode defaultStatus,
                                                 MemberGrade defaultGrade) {
        return Member.builder()
            .memberStatusCodes(defaultStatus)
            .memberGrades(defaultGrade)
            .email(memberCreationRequestOauth.getEmail())
            .nickname(memberCreationRequestOauth.getNickName())
            .name(memberCreationRequestOauth.getName())
            .password(memberCreationRequestOauth.getPassword())
            .phoneNumber(memberCreationRequestOauth.getPhoneNumber())
            .birthDate(memberCreationRequestOauth.getBirthDate())
            .gender(memberCreationRequestOauth.getGender())
            .accumulatePurchaseAmount(0L)
            .build();
    }
}
