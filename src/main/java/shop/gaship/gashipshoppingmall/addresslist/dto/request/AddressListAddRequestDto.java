package shop.gaship.gashipshoppingmall.addresslist.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 등록하고자하는 배송지 목록의 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListAddRequestDto {
    @NotNull(message = "addressLocalNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "addressLocalNo 는 0 이하일 수 없습니다.")
    private Integer addressLocalNo;

    @NotNull(message = "memberNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "addressLocalNo 는 0 이하일 수 없습니다.")
    private Integer memberNo;

    @NotNull(message = "주소를 입력해주세요.")
    private String address;

    @Length(min = 1, max = 300, message = "address 의 길이는 최소1 최대 300입니다.")
    @NotNull(message = "상세 주소를 입력해주세요.")
    private String addressDetail;

    @NotBlank(message = "우편번호를 입력해주세요.")
    private String zipCode;
}
