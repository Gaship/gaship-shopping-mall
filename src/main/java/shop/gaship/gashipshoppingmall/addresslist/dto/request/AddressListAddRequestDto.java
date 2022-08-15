package shop.gaship.gashipshoppingmall.addresslist.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 등록하고자하는 배송지 목록의 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListAddRequestDto {
    @NotNull
    @Min(value = 1, message = "주소지역 id 값은 1 이상이어야합니다.")
    private Integer addressLocalNo;

    @NotNull
    @Min(value = 1, message = "회원 id 값은 1 이상이어야합니다.")
    private Integer memberNo;

    @NotNull
    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotNull
    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String addressDetail;

    @NotNull
    @NotBlank(message = "우편번호를 입력해주세요.")
    private String zipCode;
}
