package shop.gaship.gashipshoppingmall.addresslocal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.GetAddressLocalResponseDtoDummy;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.ModifyAddressRequestDtoDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addresslocal.service.impl.AddressLocalServiceImpl;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.service
 * fileName       : AddressLocalServiceTest
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
@ExtendWith(SpringExtension.class)
@Import(AddressLocalServiceImpl.class)
class AddressLocalServiceTest {

    @Autowired
    AddressLocalService service;
    ModifyAddressRequestDto modifyDto;
    AddressLocal addressLocal;
    GetAddressLocalResponseDto responseDto;
    String requestDto;
    ArgumentCaptor<AddressLocal> captor;
    @MockBean
    AddressLocalRepository addressLocalRepository;

    @BeforeEach
    void setUp() {
        requestDto = "마산턱별시";
        responseDto = GetAddressLocalResponseDtoDummy.dummy();
        captor = ArgumentCaptor.forClass(AddressLocal.class);
        addressLocal = AddressLocalDummy.dummy1();
        modifyDto = ModifyAddressRequestDtoDummy.dummy();
    }

    @DisplayName("배송여부 수정을 위한 테스트 주소를 찾지못한경우")
    @Test
    void modifyAddressLocal_delivery_Fail() {
        //given & when
        given(addressLocalRepository.findById(any()))
            .willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> service.modifyLocalDelivery(modifyDto))
            .isInstanceOf(NotExistAddressLocal.class);
    }

    @DisplayName("배송여부 수정을 위한 테스트 성공한 경우 테스트")
    @Test
    void modifyAddressLocal_delivery_Success() {
        //given
        given(addressLocalRepository.findById(any()))
            .willReturn(Optional.of(addressLocal));

        //when
        service.modifyLocalDelivery(modifyDto);

        //then
        verify(addressLocalRepository, timeout(1))
            .findById(any());
    }

    @DisplayName("배송지 검색테스트")
    @Test
    void searchAddressLocal() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<GetAddressLocalResponseDto> list = new ArrayList<>();
        list.add(responseDto);

        PageImpl<GetAddressLocalResponseDto> page = new PageImpl<>(list, pageRequest, pageRequest.getOffset());
        PageResponse<GetAddressLocalResponseDto> pages = new PageResponse<>(page);
        given(addressLocalRepository.findAllAddress(requestDto, pageRequest))
            .willReturn(pages);

        //when & then
        service.findAddressLocals(requestDto, pageRequest);
        //then
        verify(addressLocalRepository, times(1))
            .findAllAddress(requestDto, pageRequest);
    }

}