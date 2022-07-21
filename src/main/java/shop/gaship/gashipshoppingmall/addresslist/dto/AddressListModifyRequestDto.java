package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListModifyRequestDto {
    private final Integer AddressListNo;

    private final Integer addressLocalNo;

    private final Integer memberNo;

    private final String address;

    private final String addressDetail;

    private final String zipCode;

    @Builder
    public AddressListModifyRequestDto(Integer addressListNo, Integer addressLocalNo, Integer memberNo, String address, String addressDetail, String zipCode) {
        AddressListNo = addressListNo;
        this.addressLocalNo = addressLocalNo;
        this.memberNo = memberNo;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
