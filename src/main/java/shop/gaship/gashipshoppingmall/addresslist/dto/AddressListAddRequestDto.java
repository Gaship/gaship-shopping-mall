package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListAddRequestDto {
    private final Integer addressLocalNo;

    private final Integer memberNo;

    private final String address;

    private final String addressDetail;

    private final String zipCode;

    @Builder
    public AddressListAddRequestDto(Integer addressLocalNo, Integer memberNo, String address, String addressDetail, String zipCode) {
        this.addressLocalNo = addressLocalNo;
        this.memberNo = memberNo;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
