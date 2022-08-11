package shop.gaship.gashipshoppingmall.addresslist.dto.request;

import lombok.Getter;

/**
 * 배송지목록 중 상태값을 변경하고자하는 배송지 목록의 id 값과 새로 등록하고자하는 배송지 목록의 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListModifyRequestDto {
    private Integer addressListNo;
    private Integer addressLocalNo;
    private Integer memberNo;
    private String address;
    private String addressDetail;
    private String zipCode;
}

