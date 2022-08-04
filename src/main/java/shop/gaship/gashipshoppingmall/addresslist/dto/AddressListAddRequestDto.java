package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Getter;

/**
 * 등록하고자하는 배송지 목록의 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListAddRequestDto {
    private Integer addressLocalNo;

    private Integer memberNo;

    private String address;

    private String addressDetail;

    private String zipCode;
}
