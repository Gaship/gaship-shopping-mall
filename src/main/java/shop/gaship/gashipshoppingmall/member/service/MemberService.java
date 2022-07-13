package shop.gaship.gashipshoppingmall.member.service;

import org.springframework.lang.Nullable;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 회원가입을 위해서 구현체에 필요한 메서드들을 정의한 인터페이스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public interface MemberService {

    /**
     * 회원을 등록하는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입을 위한 정보 객체입니다.
     */
    void registerMember(MemberCreationRequest memberCreationRequest);

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
    Member findMemberFromEmail(String email);


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
            .status(defaultStatus)
            .grade(defaultGrade)
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
     * 회원을 닉네임으로 조회하는 메서드입니다.
     *
     * @param nickName 닉네임
     * @return 검색된 회원
     */
    Member findMemberFromNickname(String nickName);

    /**
     * 회원 상세 정보를 이메일을 통해서 조회하는 메서드입니다.
     *
     * @param email 이메일입니다.
     * @return 로그인을 시도하는 회원의 상세 정보 결과입니다.
     */
    SignInUserDetailsDto findSignInUserDetailFromEmail(String email);
}
