package shop.gaship.gashipshoppingmall.addresslist.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslist.dto.*;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * @author 최정우
 * @since 1.0
 */
public interface AddressListService {
    void addAddressList(AddressListAddRequestDto request);

    void addAddressList(AddressListModifyRequestDto request);

    void modifyAddressList(AddressListModifyRequestDto request);

    void removeAddressList(Integer addressListId);

    AddressListResponseDto findAddressList(Integer addressListId);

    AddressListPageResponseDto<AddressListResponseDto,AddressList> findAddressLists(Pageable pageable);

    default AddressList dtoToEntity(AddressListAddRequestDto request, AddressLocal addressLocal, Member member, StatusCode defaultStatus){
        return AddressList.builder()
                .addressLocal(addressLocal)
                .member(member)
                .statusCode(defaultStatus)
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .zipCode(request.getZipCode())
                .build();
    }

    default AddressList dtoToEntity(AddressListModifyRequestDto request, AddressLocal addressLocal, Member member, StatusCode defaultStatus){
        return AddressList.builder()
                .addressLocal(addressLocal)
                .member(member)
                .statusCode(defaultStatus)
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .zipCode(request.getZipCode())
                .build();
    }

    default AddressListResponseDto entityToDto(AddressList addressList){
        return AddressListResponseDto.builder()
                .addressListNo(addressList.getAddressListNo())
                .addressLocal(addressList.getAddressLocal())
                .address(addressList.getAddress())
                .addressDetail(addressList.getAddressDetail())
                .zipCode(addressList.getZipCode())
                .build();
    }
}
