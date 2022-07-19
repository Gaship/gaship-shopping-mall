package shop.gaship.gashipshoppingmall.addressLocal.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.GetAddressLocalResponseDtoDummy;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.ModifyAddressRequestDtoDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addressLocal.service.impl.AddressLocalServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        requestDto = new String("마산턱별시");
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
        given(addressLocalRepository.findAllAddress(any()))
                .willReturn(List.of(responseDto));

        //when & then
        service.findAddressLocals(requestDto);
        //then
        verify(addressLocalRepository, times(1))
                .findAllAddress(any());
    }

}