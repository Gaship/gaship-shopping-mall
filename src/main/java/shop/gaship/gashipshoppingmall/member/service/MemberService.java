package shop.gaship.gashipshoppingmall.member.service;

import org.springframework.lang.Nullable;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

public interface MemberService {

    void registerMember(MemberCreationRequest memberCreationRequest);

    boolean isAvailableEmail(String email);

    Member findMemberFromEmail(String email);

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

    Member findMemberFromNickname(String nickName);
}
