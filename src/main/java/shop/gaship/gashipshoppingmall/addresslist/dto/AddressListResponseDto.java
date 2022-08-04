package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Builder;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;

/**
 * 단건 조회 시(수정, 조회)에 필요한 배송지목록 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Builder
public class AddressListResponseDto {
    private final Integer addressListNo;

    private final AddressLocal addressLocal;

    private final String address;

    private final String addressDetail;

    private final String zipCode;
}
