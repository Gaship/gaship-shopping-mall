package shop.gaship.gashipshoppingmall.addresslist.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 단건 조회 시(수정, 조회)에 필요한 배송지목록 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListResponseDto {
    private final Integer addressListNo;
    private final String addressName;
    private final boolean allowDelivery;
    private final String address;
    private final String addressDetail;
    private final String zipCode;

    /**
     * 배송지목록 Response Builder 입니다.
     */
    @Builder
    public AddressListResponseDto(Integer addressListNo, String addressName,
                                  boolean allowDelivery, String address,
                                  String addressDetail, String zipCode) {
        this.addressListNo = addressListNo;
        this.addressName = addressName;
        this.allowDelivery = allowDelivery;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
