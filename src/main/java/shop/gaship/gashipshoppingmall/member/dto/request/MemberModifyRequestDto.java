package shop.gaship.gashipshoppingmall.member.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 멤버 정보 수정을 위한 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberModifyRequestDto {
    @Min(0)
    private Integer memberNo;

    @NotBlank
    @Length(max = 100)
    @Pattern(
        // 첫글자는 대문자, 소문자, 숫자, @$!%*#?& 특수문자만 허용하여 8자리 이상 조합
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "영문, 숫자, 특수문자를 포함하여 8자리 이상이어야합니다.")
    private String password;

    @NotBlank
    @Length(max = 100)
    private String phoneNumber;

    @NotBlank
    @Length(max = 100)
    @Pattern(regexp = "^[가-힣]+", message = "이름을 정확히 입력하여주십시오.")
    private String name;

    @NotBlank
    @Length(max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z\\d]{0,20}", message = "닉네임은 한글 또는 영문, 숫자만 입력이 가능합니다.")
    private String nickname;

    @NotBlank
    @Length(max = 1)
    @Pattern(regexp = "^[남|녀]$", message = "성별을 정확히 작성해여주십시오.")
    private String gender;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
