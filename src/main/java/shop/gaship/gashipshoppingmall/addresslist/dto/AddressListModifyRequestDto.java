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
    private final Integer addressListNo;

    private final Integer addressLocalNo;

    private final Integer memberNo;

    private final String address;

    private final String addressDetail;

    private final String zipCode;

    /**
     * 배송지 목록을 수정하기위한 요청을 생성하는 생성자입니다.
     *
     * @param addressListNo 주소목록 고유번호입니다.
     * @param addressLocalNo 주소지역 고유번호입니다.
     * @param memberNo 회원의 고유번호입니다.
     * @param address 도로명주소입니다.
     * @param addressDetail 주소상세입니다.
     * @param zipCode 우편번호입니다.
     */
    @Builder
    public AddressListModifyRequestDto(Integer addressListNo, Integer addressLocalNo,
                                       Integer memberNo, String address, String addressDetail,
                                       String zipCode) {
        this.addressListNo = addressListNo;
        this.addressLocalNo = addressLocalNo;
        this.memberNo = memberNo;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
