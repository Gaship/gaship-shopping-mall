package shop.gaship.gashipshoppingmall.addresslist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.exception.NotFoundAddressListException;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;

import java.util.function.Function;

/**
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AddressListServiceImpl implements AddressListService {
    private final AddressListRepository addressListRepository;
    private final AddressLocalRepository addressLocalRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void addAddressList(AddressListAddRequestDto request) {
        AddressLocal addressLocal = addressLocalRepository.findById(request.getAddressLocalNo()).orElseThrow(NotExistAddressLocal::new);
        Member member = memberRepository.findById(request.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        StatusCode defaultStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.USE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressListRepository.save(dtoToEntity(request,addressLocal,member,defaultStatus));
    }
    @Transactional
    @Override
    public void addAddressList(AddressListModifyRequestDto request) {
        AddressLocal addressLocal = addressLocalRepository.findById(request.getAddressLocalNo()).orElseThrow(NotExistAddressLocal::new);
        Member member = memberRepository.findById(request.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        StatusCode defaultStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.USE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressListRepository.save(dtoToEntity(request,addressLocal,member,defaultStatus));
    }
    @Transactional
    @Override
    public void modifyAddressList(AddressListModifyRequestDto request) {
        AddressList addressList = addressListRepository.findById(request.getAddressListNo()).orElseThrow(NotFoundAddressListException::new);
        StatusCode deleteStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.DELETE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressList.modifyStatusToDelete(deleteStatus);
        addressListRepository.save(addressList);
    }

    @Transactional
    @Override
    public void deleteAddressList(Integer addressListId) {
        AddressList addressList = addressListRepository.findById(addressListId).orElseThrow(NotFoundAddressListException::new);
        StatusCode deleteStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.DELETE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressList.modifyStatusToDelete(deleteStatus);
        addressListRepository.save(addressList);
    }

    @Override
    public AddressListResponseDto findAddressList(Integer addressListId) {
        return entityToDto(addressListRepository.findById(addressListId).orElseThrow(NotFoundAddressListException::new));
    }

    @Override
    public AddressListPageResponseDto<AddressListResponseDto, AddressList> findAddressLists(Pageable pageable) {
        Page<AddressList> page = addressListRepository.findAll(pageable);
        Function<AddressList,AddressListResponseDto> fn = this::entityToDto;

        return new AddressListPageResponseDto<>(page,fn);
    }
}
