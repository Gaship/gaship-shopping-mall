package shop.gaship.gashipshoppingmall.adresslist.service;

import shop.gaship.gashipshoppingmall.adresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.adresslist.dto.AddressListRequestDto;
import shop.gaship.gashipshoppingmall.adresslist.dto.AddressListResponseDto;

/**
 * @author 최정우
 * @since 1.0
 */
public interface AddressListService {
    void addAddressList(AddressListRequestDto request);

    void modifyAddressList(AddressListRequestDto request);

    void deleteAddressList(Integer request);

    AddressListResponseDto findAddressList(Integer addressListId);

    AddressListPageResponseDto findAddressLists();
}
