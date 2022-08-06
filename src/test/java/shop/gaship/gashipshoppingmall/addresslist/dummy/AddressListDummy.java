package shop.gaship.gashipshoppingmall.addresslist.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;

/**
 * @author 최정우
 * @since 1.0
 */
public class AddressListDummy {
    public static AddressListAddRequestDto addressListAddRequestDtoDummy() {
        AddressListAddRequestDto dummy = new AddressListAddRequestDto();
        ReflectionTestUtils.setField(dummy, "addressLocalNo", 1);
        ReflectionTestUtils.setField(dummy, "memberNo", 1);
        ReflectionTestUtils.setField(dummy, "address", "경기도 안양시 비산동");
        ReflectionTestUtils.setField(dummy, "addressDetail", "현대아파트 65층 화장실");
        ReflectionTestUtils.setField(dummy, "zipCode", "12344");

        return dummy;
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
        return AddressListPageResponseDto.<AddressListResponseDto, AddressList>builder()
            .dtoList(List.of(AddressListDummy.addressListResponseDto1(), AddressListDummy.addressListResponseDto2()))
            .totalPage(1)
            .page(1)
            .size(10)
            .start(1)
            .end(1)
            .prev(false)
            .next(false)
            .pageList(List.of(1, 2, 3, 4, 5))
            .build();
    }

    public static AddressList addressListEntity() {
        return AddressList.builder()
            .addressListNo(1)
            .addressLocal(AddressLocalDummy.dummy1())
            .member(MemberDummy.dummy())
            .statusCode(StatusCodeDummy.dummy())
            .address("경기도 안양시 비산동")
            .addressDetail("현대아파트 65층 화장실")
            .zipCode("12344")
            .build();
    }

    public static List<AddressList> addressListEntityList() {
        List<AddressList> list = new ArrayList<>();

        IntStream.rangeClosed(1, 103).forEach(i -> list.add(AddressList.builder()
            .addressListNo(i)
            .addressLocal(AddressLocalDummy.dummy1())
            .member(MemberDummy.dummy())
            .statusCode(StatusCodeDummy.dummy())
            .address("경기도 안양시 비산동")
            .addressDetail("현대아파트 65층 화장실")
            .zipCode("12344")
            .build()));
        return list;
    }
}
