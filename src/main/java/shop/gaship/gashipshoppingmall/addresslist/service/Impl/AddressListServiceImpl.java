package shop.gaship.gashipshoppingmall.addresslist.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.exception.NotFoundAddressListException;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.addresslist.service.AddressListService;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;

import java.util.function.Function;

/**
 * 배송지 목록의 service interface 의 구현체입니다.
 *
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

    /**
     * {@inheritDoc}
     * 
     * @throws  NotExistAddressLocal  주소지역 id 값으로 주소지역 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws  MemberNotFoundException 회원 id 값으로 회원 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws  StatusCodeNotFoundException 상태코드 값으로 상태코드 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void addAddressList(AddressListAddRequestDto request) {
        AddressLocal addressLocal = addressLocalRepository.findById(request.getAddressLocalNo()).orElseThrow(NotExistAddressLocal::new);
        Member member = memberRepository.findById(request.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        StatusCode defaultStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.USE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressListRepository.save(dtoToEntity(request, addressLocal, member, defaultStatus));
    }

    /**
     * {@inheritDoc}
     * 
     * @param request 등록에 필요한 정보를 담는 dto 입니다.
     * @throws NotExistAddressLocal  주소지역 id 값으로 주소지역 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws MemberNotFoundException 회원 id 값으로 회원 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws StatusCodeNotFoundException 상태코드 값으로 상태코드 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void addAddressList(AddressListModifyRequestDto request) {
        AddressLocal addressLocal = addressLocalRepository.findById(request.getAddressLocalNo()).orElseThrow(NotExistAddressLocal::new);
        Member member = memberRepository.findById(request.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        StatusCode defaultStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.USE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressListRepository.save(dtoToEntity(request, addressLocal, member, defaultStatus));
    }

    /**
     * {@inheritDoc}
     *
     * @param request 수정에 필요한 정보를 담는 dto 입니다.
     * @throws NotFoundAddressListException 배송지목록 id 값으로 배송지목록 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws StatusCodeNotFoundException 상태코드 값으로 상태코드 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void modifyAddressList(AddressListModifyRequestDto request) {
        AddressList addressList = addressListRepository.findById(request.getAddressListNo()).orElseThrow(NotFoundAddressListException::new);
        StatusCode deleteStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.DELETE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressList.modifyStatusToDelete(deleteStatus);
        addressListRepository.save(addressList);
    }

    /**
     * {@inheritDoc}
     *
     * @param addressListId 조회에 필요한 정보를 담는 값입니다.
     * @throws NotFoundAddressListException 배송지목록 id 값으로 배송지목록 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws StatusCodeNotFoundException 상태코드 값으로 상태코드 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void removeAddressList(Integer addressListId) {
        AddressList addressList = addressListRepository.findById(addressListId).orElseThrow(NotFoundAddressListException::new);
        StatusCode deleteStatus = statusCodeRepository.findByStatusCodeName(AddressStatus.DELETE.getValue()).orElseThrow(StatusCodeNotFoundException::new);
        addressList.modifyStatusToDelete(deleteStatus);
        addressListRepository.save(addressList);
    }

    /**
     * {@inheritDoc}
     *
     * @param addressListId 조회에 필요한 정보를 담는 dto 입니다.
     * @throws NotFoundAddressListException 배송지목록 id 값으로 배송지목록 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     */
    @Override
    public AddressListResponseDto findAddressList(Integer addressListId) {
        return entityToDto(addressListRepository.findById(addressListId).orElseThrow(NotFoundAddressListException::new));
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 배송지목록의 다건 조회에 필요한 정보를 담는 pageable 입니다.
     */
    @Override
    public AddressListPageResponseDto<AddressListResponseDto, AddressList> findAddressLists(Integer memberId, Pageable pageable) {
        Page<AddressList> page = addressListRepository.findByMember_MemberNoAndStatusCode_StatusCodeName(memberId, AddressStatus.USE.getValue(), pageable);

        return new AddressListPageResponseDto<>(page, this::entityToDto);
    }
}
