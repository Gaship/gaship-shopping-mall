package shop.gaship.gashipshoppingmall.member.dto;

import shop.gaship.gashipshoppingmall.dataprotection.protection.Aes;
import shop.gaship.gashipshoppingmall.member.entity.Member;

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
    default Member creationRequestToMemberEntity(MemberCreationRequest memberCreationRequest){
        return Member.builder()
            .recommendMemberNo(memberCreationRequest.getRecommendMemberNo())
            .email(memberCreationRequest.getEmail())
            .nickName(memberCreationRequest.getNickName())
            .name(memberCreationRequest.getName())
            .password(memberCreationRequest.getPassword())
            .phoneNumber(memberCreationRequest.getPhoneNumber())
            .birthDate(memberCreationRequest.getBirthDate())
            .gender(memberCreationRequest.getGender())
            .statusNo(0) // FIXME : 해당 기능 생성시 수정 예정
            .gradeNo(0) // FIXME : 해당 기능 생성시 수정 예정
            .totalPurchaseAmount(0L)
            .build();
    }
}
