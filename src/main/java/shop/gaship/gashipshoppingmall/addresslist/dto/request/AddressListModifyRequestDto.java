package shop.gaship.gashipshoppingmall.addresslist.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 배송지목록 중 상태값을 변경하고자하는 배송지 목록의 id 값과 새로 등록하고자하는 배송지 목록의 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListModifyRequestDto {
    @NotNull(message = "addressListNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "addressListNo 은 0보다 작을 수 없습니다.")
    private Integer addressListNo;

    @NotNull(message = "addressLocalNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "addressLocalNo 는 0보다 작을 수 없습니다.")
    private Integer addressLocalNo;

    @NotNull(message = "memberNo 는 필수 입력값입니다.")
    @Min(value = 1, message = "memberNo 는 0보다 작을 수 없습니다.")
    private Integer memberNo;

    @NotBlank(message = "address 는 필수 입력값입니다.")
    private String address;

    @Length(min = 1, max = 300, message = "address 의 길이는 최소1 최대 300입니다.")
    @NotBlank(message = "addressDetail 는 필수 입력값입니다.")
    private String addressDetail;

    @NotBlank(message = "zipCode 는 필수 입력값입니다.")
    private String zipCode;
}

