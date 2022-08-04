package shop.gaship.gashipshoppingmall.addresslist.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 배송지목록의 service interface 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface AddressListService {
    /**
     * 배송지목록의 등록을 하기 위한 메서드입니다.
     *
     * @param request 등록에 필요한 정보를 담는 dto 입니다.
     * @author 최정우
     */
    void addAddressList(AddressListAddRequestDto request);

    /**
     * 배송지목록의 등록을 하기 위한 메서드입니다.
     *
     * @param request 등록에 필요한 정보를 담는 dto 입니다.
     * @author 최정우
     */
    void addAddressList(AddressListModifyRequestDto request);

    /**
     * 배송지목록의 등록을 하기 위한 메서드입니다.
     *
     * @param request 등록에 필요한 정보를 담는 dto 입니다. 타입은 modifyRequestDto 입니다.
     * @author 최정우
     */
    void modifyAddressList(AddressListModifyRequestDto request);


    /**
     * 배송지목록의 삭제를 하기 위한 메서드입니다.
     *
     * @param addressListId 삭제하길 원하는 배송지 목록의 id 값을 담고 있습니다.
     * @author 최정우
     */
    void removeAddressList(Integer addressListId);

    /**
     * 배송지목록의 단건조회 하기 위한 메서드입니다. (배송지 정보 수정과 배송지목록 상세 보기에 사용됨)
     *
     * @param addressListId 조회하길 원하는 배송지목록의 id 값을 담고있습니다.
     * @return 조회하길 원하는 배송지목록의 정보를 담고있습니다.
     * @author 최정우
     */
    AddressListResponseDto findAddressList(Integer addressListId);

    /**
     * 배송지목록의 다건조회 하기 위한 메서드입니다. (배송지 정보 수정과 배송지목록 상세 보기에 사용됨)
     *
     * @param pageable 조회하길 원하는 배송지목록 페이지의 페이지 번호,사이즈,정렬조건 값을 담고있습니다.
     * @return 조회하길 원하는 배송지목록 페이지의 정보들을 담고있습니다.
     * @author 최정우
     */
    AddressListPageResponseDto<AddressListResponseDto, AddressList> findAddressLists(
        Integer memberId, Pageable pageable);

    /**
     * 새로운 테이블을 등록시 필요한 정보로 데이터를 변환해주는 메서드입니다.
     *
     * @param request       등록에 필요한 정보를 담고있는 dto 입니다.
     * @param addressLocal  dto 에 있는 주소 id 를 변환한 값입니다.
     * @param member        dto 에 있는 회원 id 를 변환한 값입니다.
     * @param defaultStatus dto 에 있는 상태 id 를 변환한 값입니다.
     * @return 등록에 필요한 entity 를 반환해줍니다.(id 값은 등록이 되면 설정되므로 따로 설정하지 않습니다.)
     * @author 최정우
     */
    default AddressList dtoToEntity(AddressListAddRequestDto request, AddressLocal addressLocal,
                                    Member member, StatusCode defaultStatus) {
        return AddressList.builder().addressLocal(addressLocal).member(member)
            .statusCode(defaultStatus).address(request.getAddress())
            .addressDetail(request.getAddressDetail()).zipCode(request.getZipCode()).build();
    }

    /**
     * 배송지 목록의 상태를 변경과 동시에 새로운 테이블을 등록시 필요한 정보로 데이터를 변환해주는 메서드입니다.
     *
     * @param request       수정, 등록에 필요한 정보를 담고있는 dto 입니다.
     * @param addressLocal  dto 에 있는 주소 id 를 변환한 값입니다.
     * @param member        dto 에 있는 회원 id 를 변환한 값입니다.
     * @param defaultStatus dto 에 있는 상태 id 를 변환한 값입니다.
     * @return 등록에 필요한 entity 를 반환해줍니다.(id 값은 등록이 되면 설정되므로 따로 설정하지 않습니다.)
     * @author 최정우
     */
    default AddressList dtoToEntity(AddressListModifyRequestDto request, AddressLocal addressLocal,
                                    Member member, StatusCode defaultStatus) {
        return AddressList.builder().addressLocal(addressLocal).member(member)
            .statusCode(defaultStatus).address(request.getAddress())
            .addressDetail(request.getAddressDetail()).zipCode(request.getZipCode()).build();
    }

    /**
     * 등록된 배송지의 정보를 보여주기 위해 데이터를 변환하는 메서드입니다.
     *
     * @param addressList 변환하기 원하는 엔티티의 정보를 담고 있는 객체입니다.
     * @return 변환된 객체의 값을 반환합니다.
     * @author 최정우
     */
    default AddressListResponseDto entityToDto(AddressList addressList) {
        return AddressListResponseDto.builder().addressListNo(addressList.getAddressListsNo())
            .addressLocal(addressList.getAddressLocal()).address(addressList.getAddress())
            .addressDetail(addressList.getAddressDetail()).zipCode(addressList.getZipCode())
            .build();
    }
}
