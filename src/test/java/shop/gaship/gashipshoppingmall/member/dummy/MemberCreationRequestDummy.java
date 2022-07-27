package shop.gaship.gashipshoppingmall.member.dummy;

import java.time.LocalDate;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dummy <br/>
 * fileName       : MemberCreationRequestDummy <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
public class MemberCreationRequestDummy {
    private MemberCreationRequestDummy() {
    }

    public static MemberCreationRequest dummy() {
        return MemberCreationRequest.builder()
            .email("example@nhn.com")
            .birthDate(LocalDate.now())
            .gender("남")
            .name("김땡땡")
            .nickName("exampleNickName")
            .recommendMemberNo(1)
            .password("password1234!")
            .phoneNumber("01012341234")
            .isUniqueEmail(true)
            .isVerifiedEmail(true)
            .encodedEmailForSearch("example@nhn.com")
            .build();
    }
}
