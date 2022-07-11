package shop.gaship.gashipshoppingmall.member.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dto <br/>
 * fileName       : MemberCreationRequest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
@Data
@Builder
public class MemberCreationRequest implements MemberDto {
    private Long recommendMemberNo;

    private String email;

    private String password;

    private String phoneNumber;

    private String name;

    private LocalDate birthDate;

    private String nickName;

    private String gender;
}
