package shop.gaship.gashipshoppingmall.addresslist.dummy;

import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;

import java.util.List;

/**
 * @author 최정우
 * @since 1.0
 */
public class AddressListDummy {
    public static AddressListAddRequestDto addressListAddRequestDtoDummy() {
        return AddressListAddRequestDto.builder()
                .addressLocalNo(1)
                .memberNo(1)
                .address("경기도 안양시 비산동")
                .addressDetail("현대아파트 65층 화장실")
                .zipCode("12344")
                .build();
    }

    public static AddressListModifyRequestDto addressListModifyRequestDtoDummy() {
        return AddressListModifyRequestDto.builder()
                .addressListNo(1)
                .addressLocalNo(1)
                .memberNo(1)
                .address("경기도 안양시 비산동")
                .addressDetail("현대아파트 65층 창문 앞")
                .zipCode("12344")
                .build();
    }

    public static AddressListResponseDto addressListResponseDto1() {
        return AddressListResponseDto.builder()
                .addressListNo(1)
                .address("경기도 안양시 비산동")
                .addressDetail("현대아파트 65층 창문 앞")
                .zipCode("12344")
                .build();
    }

    public static AddressListResponseDto addressListResponseDto2() {
        return AddressListResponseDto.builder()
                .addressListNo(2)
                .address("경기도 안양시 비산동")
                .addressDetail("현대아파트 65층 창문 앞")
                .zipCode("12344")
                .build();
    }

    public static AddressListPageResponseDto<AddressListResponseDto, AddressList> addressListPageResponseDto() {
        return AddressListPageResponseDto.<AddressListResponseDto,AddressList>builder()
                .dtoList(List.of(AddressListDummy.addressListResponseDto1(),AddressListDummy.addressListResponseDto2()))
                .totalPage(1)
                .page(1)
                .size(10)
                .start(1)
                .end(1)
                .prev(false)
                .next(false)
                .pageList(List.of(1,2,3,4,5))
                .build();
    }
}
