package shop.gaship.gashipshoppingmall.addresslist.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.exception.NotFoundAddressListException;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.addresslist.service.impl.AddressListServiceImpl;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author 최정우
 * @since 1.0
 */
@ExtendWith(value = SpringExtension.class)
@Import(AddressListServiceImpl.class)
class AddressListServiceImplTest {
    @MockBean
    AddressListRepository addressListRepository;

    @MockBean
    AddressLocalRepository addressLocalRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    AddressListService addressListService;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("AddressListService 매개변수가 AddressListAddRequest 인 addAddressList 테스트")
    @Test
    void AddAddressListWithAddressListAddRequest() {
        AddressLocal addressLocal = AddressLocalDummy.dummy1();
        when(addressLocalRepository.findById(any())).thenReturn(Optional.of(addressLocal));
        when(memberRepository.findById(any())).thenReturn(Optional.of(MemberDummy.dummy()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.of(StatusCodeDummy.AddressListUseStateDummy()));
        when(addressListRepository.save(any())).thenReturn(AddressListDummy.addressListEntity());

        addressListService.addAddressList(AddressListDummy.addressListAddRequestDtoDummy());

        verify(addressLocalRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(statusCodeRepository, times(1)).findByStatusCodeName(any());
        verify(addressListRepository, times(1)).save(any());
    }

    @DisplayName("AddAddressList Fail 테스트(주소지역 조회값 = null)")
    @Test
    void AddAddressListFailTest1() {
        when(addressLocalRepository.findById(any())).thenReturn(Optional.empty());
        AddressListAddRequestDto dummy = AddressListDummy.addressListAddRequestDtoDummy();
        assertThatThrownBy(() -> addressListService.addAddressList(dummy))
            .isInstanceOf(NotExistAddressLocal.class);

        verify(addressLocalRepository, times(1)).findById(any());
        verify(memberRepository, never()).findById(any());
        verify(statusCodeRepository, never()).findByStatusCodeName(any());
        verify(addressListRepository, never()).save(any());
    }

    @DisplayName("AddAddressList Fail 테스트(배송목록 조회값 = null)")
    @Test
    void AddAddressListFailTest2() {
        AddressLocal addressLocal = AddressLocalDummy.dummy1();
        when(addressLocalRepository.findById(any())).thenReturn(Optional.of(addressLocal));
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        AddressListAddRequestDto dummy = AddressListDummy.addressListAddRequestDtoDummy();
        assertThatThrownBy(() -> addressListService.addAddressList(dummy))
            .isInstanceOf(MemberNotFoundException.class);

        verify(addressLocalRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(statusCodeRepository, never()).findByStatusCodeName(any());
        verify(addressListRepository, never()).save(any());
    }

    @DisplayName("AddAddressList Fail 테스트(상태값 조회값 = null)")
    @Test
    void AddAddressListFailTest3() {
        AddressLocal addressLocal = AddressLocalDummy.dummy1();
        when(addressLocalRepository.findById(any())).thenReturn(Optional.of(addressLocal));
        when(memberRepository.findById(any())).thenReturn(Optional.of(MemberDummy.dummy()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.empty());
        
        AddressListAddRequestDto dummy = AddressListDummy.addressListAddRequestDtoDummy();
        assertThatThrownBy(() -> addressListService.addAddressList(dummy))
            .isInstanceOf(StatusCodeNotFoundException.class);

        verify(addressLocalRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(statusCodeRepository, times(1)).findByStatusCodeName(any());
        verify(addressListRepository, never()).save(any());
    }

    @DisplayName("AddressListService 매개변수가 AddressListModifyRequest 인 addAddressList 테스트")
    @Test
    void AddAddressListWithAddressListModifyRequest() {
        AddressLocal addressLocal = AddressLocalDummy.dummy1();
        when(addressLocalRepository.findById(any())).thenReturn(Optional.of(addressLocal));
        when(memberRepository.findById(any())).thenReturn(Optional.of(MemberDummy.dummy()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.of(StatusCodeDummy.AddressListUseStateDummy()));
        when(addressListRepository.save(any())).thenReturn(AddressListDummy.addressListEntity());

        addressListService.addAddressList(AddressListDummy.addressListModifyRequestDtoDummy());

        verify(addressLocalRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(statusCodeRepository, times(1)).findByStatusCodeName(any());
        verify(addressListRepository, times(1)).save(any());
    }

    @DisplayName("AddressListService modifyAddressList 테스트")
    @Test
    void modifyAddressList() {
        when(addressListRepository.findById(any())).thenReturn(Optional.of(AddressListDummy.addressListEntity()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.of(StatusCodeDummy.AddressListDeleteStateDummy()));
        when(addressListRepository.save(any())).thenReturn(AddressListDummy.addressListEntity());

        addressListService.modifyAddressList(AddressListDummy.addressListModifyRequestDtoDummy());

        verify(addressListRepository, times(1)).findById(any());
        verify(statusCodeRepository, times(1)).findByStatusCodeName(any());
        verify(addressListRepository, times(1)).save(any());
    }

    @DisplayName("removeAddressList Success 테스트")
    @Test
    void removeAddressList() {
        when(addressListRepository.findById(any())).thenReturn(Optional.of(AddressListDummy.addressListEntity()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.of(StatusCodeDummy.AddressListDeleteStateDummy()));
        when(addressListRepository.save(any())).thenReturn(AddressListDummy.addressListEntity());

        addressListService.removeAddressList(1);

        verify(addressListRepository, times(1)).findById(any());
        verify(statusCodeRepository, times(1)).findByStatusCodeName(any());
        verify(addressListRepository, times(1)).save(any());
    }

    @DisplayName("removeAddressList Fail 테스트(배송지목록 조회값 = null)")
    @Test
    void removeAddressListFailTest1() {
        when(addressListRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressListService.removeAddressList(1))
            .isInstanceOf(NotFoundAddressListException.class);

        verify(addressListRepository, times(1)).findById(1);
        verify(statusCodeRepository, never()).findByStatusCodeName(any());
        verify(addressListRepository, never()).save(any());
    }

    @DisplayName("removeAddressList Fail 테스트(배송지목록 조회값 = null)")
    @Test
    void removeAddressListFailTest2() {
        when(addressListRepository.findById(any())).thenReturn(Optional.of(AddressListDummy.addressListEntity()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressListService.removeAddressList(1))
            .isInstanceOf(StatusCodeNotFoundException.class);

        verify(addressListRepository, times(1)).findById(any());
        verify(statusCodeRepository, times(1)).findByStatusCodeName(any());
        verify(addressListRepository, never()).save(any());
    }

    @DisplayName("AddressListService findAddressList 테스트")
    @Test
    void findAddressList() {
        when(addressListRepository.findById(any())).thenReturn(Optional.of(AddressListDummy.addressListEntity()));

        addressListService.findAddressList(1);

        verify(addressListRepository, times(1)).findById(any());

    }

    @DisplayName("AddressListService findAddressLists 테스트")
    @Test
    void findAddressLists() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<AddressList> list = AddressListDummy.addressListEntityList();
        Page<AddressList> resultPage = new PageImpl<>(list, pageable, 103);
        when(addressListRepository.findByMember_MemberNoAndStatusCode_StatusCodeName(any(), any(), any()))
            .thenReturn(resultPage);

        AddressListPageResponseDto<AddressListResponseDto, AddressList> result = addressListService.findAddressLists(1, pageable);

        assertThat(result.getPage()).isEqualTo(page + 1);
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getDtoList()).hasSize(103);
        verify(addressListRepository, times(1)).findByMember_MemberNoAndStatusCode_StatusCodeName(any(), any(), any(Pageable.class));
    }
}
