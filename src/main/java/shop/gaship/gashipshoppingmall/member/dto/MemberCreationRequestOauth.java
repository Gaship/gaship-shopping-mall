package shop.gaship.gashipshoppingmall.member.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
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
