package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 배송지목록 중 상태값을 변경하고자하는 배송지 목록의 id 값과 새로 등록하고자하는 배송지 목록의 정보를 담은 dto 입니다.
 *
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
