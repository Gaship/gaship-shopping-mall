package shop.gaship.gashipshoppingmall.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 회원가입의 요청이 들어올떄 회원가입을 할 데이터들이 담겨진 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Data
@Builder
public class MemberCreationRequest {
    @Min(value = 0, message = "추천인에 대한 정보가 올바르지 않습니다.")
    private Integer recommendMemberNo;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Length(max = 255, message = "이메일의 길이는 255자를 넘길 수 없습니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(max = 100, message = "비밀번호가 너무 깁니다.")
    @Pattern(
        // 첫글자는 대문자, 소문자, 숫자, @$!%*#?& 특수문자만 허용하여 8자리 이상 조합
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
        message = "영문, 숫자, 특수문자를 포함하여 8자리 이상이어야합니다."
    )
    private String password;

    @NotBlank(message = "휴대전화번호를 입력해주세요.")
    @Length(max = 100, message = "전화번호가 너무 깁니다.")
    private String phoneNumber;

    @NotBlank(message = "이름은 필수로 작성하여야합니다.")
    @Length(max = 100, message = "이름이 너무 깁니다.")
    @Pattern(regexp = "^[가-힣]+", message = "이름을 정확히 입력하여주십시오.")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Length(max = 20, message = "닉네임이 너무 깁니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z\\d]{0,20}",
        message = "닉네임은 한글 또는 영문, 숫자만 입력이 가능합니다."
    )
    private String nickName;

    @NotBlank
    @Length(max = 1)
    @Pattern(
            regexp = "^[남|녀]$",
            message = "성별은 남, 녀만 입력이 가능합니다."
    )
    private String gender;

    @NotNull(message = "이메일 인증을 완료해주세요.")
    private Boolean isVerifiedEmail;

    @NotNull(message = "이메일 중복확인을 진행 후 회원가입해주세요.")
    private Boolean isUniqueEmail;

    @NotNull
    private String encodedEmailForSearch;
}
