package shop.gaship.gashipshoppingmall.addresslist.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * @author 최정우
 * @since 1.0
 */
public class AddressListDummy {
    public static AddressListAddRequestDto addressListAddRequestDtoDummy(Integer addressLocalNo,Integer memberNo, String address, String addressDetail,String zipCode) {
        AddressListAddRequestDto dto = new AddressListAddRequestDto();
        ReflectionTestUtils.setField(dto, "addressLocalNo", addressLocalNo);
        ReflectionTestUtils.setField(dto, "memberNo", memberNo);
        ReflectionTestUtils.setField(dto, "address", address);
        ReflectionTestUtils.setField(dto, "addressDetail", addressDetail);
        ReflectionTestUtils.setField(dto, "zipCode", zipCode);

        return dto;
    }

    public static AddressListModifyRequestDto addressListModifyRequestDtoDummy(Integer addressListNo, Integer addressLocalNo,Integer memberNo, String address, String addressDetail,String zipCode) {
        AddressListModifyRequestDto dto = new AddressListModifyRequestDto();
        ReflectionTestUtils.setField(dto, "addressListNo", addressListNo);
        ReflectionTestUtils.setField(dto, "addressLocalNo", addressLocalNo);
        ReflectionTestUtils.setField(dto, "memberNo", memberNo);
        ReflectionTestUtils.setField(dto, "address", address);
        ReflectionTestUtils.setField(dto, "addressDetail", addressDetail);
        ReflectionTestUtils.setField(dto, "zipCode", zipCode);
        return dto;
    }

    public static AddressListResponseDto addressListResponseDtoDummy(Integer addressListNo, String addressName, boolean allowDelivery, String address, String addressDetail,String zipCode) {
        return new AddressListResponseDto(addressListNo,addressName,allowDelivery,address,addressDetail,zipCode);
    }

    public static PageResponse<AddressListResponseDto> addressListPageResponseDtoDummy() {
        AddressListResponseDto dto1 = addressListResponseDtoDummy(1, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto2 = addressListResponseDtoDummy(2, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto3 = addressListResponseDtoDummy(3, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto4 = addressListResponseDtoDummy(4, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto5 = addressListResponseDtoDummy(5, "1", false, "경기", "안양", "12345");
        AddressListResponseDto dto6 = addressListResponseDtoDummy(6, "1", true, "경기", "안양", "12345");
        List<AddressListResponseDto> dtoList = List.of(dto1, dto2, dto3, dto4, dto5, dto6);
        Pageable pageable = PageRequest.of(1, 5);
        Page<AddressListResponseDto> page = new PageImpl<>(dtoList,pageable,dtoList.size());
        PageResponse<AddressListResponseDto> response = new PageResponse<>(page);
        return response;
    }

    public static Page<AddressListResponseDto> addressListsPageDummy() {
        AddressListResponseDto dto1 = addressListResponseDtoDummy(1, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto2 = addressListResponseDtoDummy(2, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto3 = addressListResponseDtoDummy(3, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto4 = addressListResponseDtoDummy(4, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto5 = addressListResponseDtoDummy(5, "1", false, "경기", "안양", "12345");
        AddressListResponseDto dto6 = addressListResponseDtoDummy(6, "1", true, "경기", "안양", "12345");
        List<AddressListResponseDto> dtoList = List.of(dto1, dto2, dto3, dto4, dto5, dto6);
        Pageable pageable = PageRequest.of(1, 5);
        Page<AddressListResponseDto> page = new PageImpl<>(dtoList,pageable,dtoList.size());
        return page;
    }

    public static List<AddressListResponseDto> addressListsListDummy() {
        AddressListResponseDto dto1 = addressListResponseDtoDummy(1, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto2 = addressListResponseDtoDummy(2, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto3 = addressListResponseDtoDummy(3, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto4 = addressListResponseDtoDummy(4, "1", true, "경기", "안양", "12345");
        AddressListResponseDto dto5 = addressListResponseDtoDummy(5, "1", false, "경기", "안양", "12345");
        AddressListResponseDto dto6 = addressListResponseDtoDummy(6, "1", true, "경기", "안양", "12345");
        return List.of(dto1, dto2, dto3, dto4, dto5, dto6);
    }

    public static AddressList addressListEntity() {
        return AddressList.builder()
            .addressLocal(AddressLocalDummy.dummy1())
            .member(MemberDummy.dummy())
            .statusCode(StatusCodeDummy.dummy())
            .address("경기도 안양시 비산동")
            .addressDetail("현대아파트 65층 화장실")
            .zipCode("12344")
            .build();
    }
    public static AddressListAddRequestDto addressListAddRequestDtoDummyFilled(){
        AddressListAddRequestDto dto = new AddressListAddRequestDto();
        ReflectionTestUtils.setField(dto, "addressLocalNo", 1);
        ReflectionTestUtils.setField(dto, "memberNo", 1);
        ReflectionTestUtils.setField(dto, "address", "경기");
        ReflectionTestUtils.setField(dto, "addressDetail", "안양");
        ReflectionTestUtils.setField(dto, "zipCode", "12345");

        return dto;
    }

    public static AddressListModifyRequestDto addressListModifyRequestDtoDummyFilled(){
        AddressListModifyRequestDto dto = new AddressListModifyRequestDto();
        ReflectionTestUtils.setField(dto, "addressListNo", 1);
        ReflectionTestUtils.setField(dto, "addressLocalNo", 1);
        ReflectionTestUtils.setField(dto, "memberNo", 1);
        ReflectionTestUtils.setField(dto, "address", "경기");
        ReflectionTestUtils.setField(dto, "addressDetail", "안양");
        ReflectionTestUtils.setField(dto, "zipCode", "12345");

        return dto;
    }

    public static List<AddressList> addressListEntityList() {
        List<AddressList> list = new ArrayList<>();

        IntStream.rangeClosed(1, 103).forEach(i -> list.add(AddressList.builder()
            .addressListsNo(i)
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
