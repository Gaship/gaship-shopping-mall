package shop.gaship.gashipshoppingmall.member.dto;

import org.springframework.lang.Nullable;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dto <br/>
 * fileName       : MemberDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
public interface MemberDto {
    default Member creationRequestToMemberEntity(MemberCreationRequest memberCreationRequest,
                                                 @Nullable Member recommendMember,
                                                 StatusCode defaultStatus, MemberGrade defaultGrade){
        return Member.builder()
            .recommendMember(recommendMember)
            .status(defaultStatus)
            .grade(defaultGrade)
            .email(memberCreationRequest.getEmail())
            .nickName(memberCreationRequest.getNickName())
            .name(memberCreationRequest.getName())
            .password(memberCreationRequest.getPassword())
            .phoneNumber(memberCreationRequest.getPhoneNumber())
            .birthDate(memberCreationRequest.getBirthDate())
            .gender(memberCreationRequest.getGender())
            .totalPurchaseAmount(0L)
            .build();
    }
}
