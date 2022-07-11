package shop.gaship.gashipshoppingmall.member.dto;

import lombok.Builder;
import lombok.Data;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dto
 * fileName       : MemberResponseDto
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@Data
@Builder
public class MemberResponseDto {
    private Integer memberNo;
    private Integer recommendMemberNo;
    private Integer memberStatusNo;
    private Integer memberGradeNo;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private LocalDate birthDate;
    private String nickname;
    private String gender;
    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
    private Boolean isBlackMember;
}
