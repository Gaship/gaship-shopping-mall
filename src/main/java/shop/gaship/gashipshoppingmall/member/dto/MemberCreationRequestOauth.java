package shop.gaship.gashipshoppingmall.member.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * oauth로 회원가입요청 올시에 값을 바인딩할 dto 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class MemberCreationRequestOauth {
    private String email;
    private String password;
    private String nickName;
    private String name;
    private String gender;
    private String phoneNumber;
    private LocalDate birthDate;
}
