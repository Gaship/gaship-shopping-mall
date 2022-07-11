package shop.gaship.gashipshoppingmall.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

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
    @Min(0)
    private Long recommendMemberNo;

    @NotBlank
    @Length(max = 255)
    private String email;

    @NotBlank
    @Length(max = 100)
    private String password;

    @NotBlank
    @Length(max = 100)
    private String phoneNumber;

    @NotBlank
    @Length(max = 100)
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    @Length(max = 20)
    private String nickName;

    @NotBlank
    @Length(max = 1)
    private String gender;
}
