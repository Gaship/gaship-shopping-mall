package shop.gaship.gashipshoppingmall.dayLabor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.CreateDayLaborRequestDtoDummy;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.FixDayLaborRequestDtoDummy;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.GetDayLaborResponseDtoDummy;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.dayLabor.service.impl.DayLaborServiceImpl;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.service
 * fileName       :
 * DayLaborServiceTest
 * author         : yuhocheol
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR         NOTE
 * -----------------------------------------------------------
 * 2022/07/12        yuhocheol      ?????? ??????
 */

@ExtendWith(SpringExtension.class)
@Import(DayLaborServiceImpl.class)
class DayLaborServiceTest {

    @MockBean
    DayLaborRepository dayLaborRepository;

    @MockBean
    AddressLocalRepository localRepository;

    @Autowired
    DayLaborService service;

    CreateDayLaborRequestDto dto;

    AddressLocal addressLocal;

    FixDayLaborRequestDto fixDto;

    DayLabor dayLabor;

    ArgumentCaptor<DayLabor> captor;

    GetDayLaborResponseDto response1;

    GetDayLaborResponseDto response2;

    @BeforeEach
    void setUp() {
        dto = CreateDayLaborRequestDtoDummy.dummy();
        addressLocal = AddressLocalDummy.dummy1();
        fixDto = FixDayLaborRequestDtoDummy.dummy();
        dayLabor = new DayLabor();
        dayLabor.registerDayLabor(dto);
        captor = ArgumentCaptor.forClass(DayLabor.class);
        response1 = GetDayLaborResponseDtoDummy.dummy1();
        response2 = GetDayLaborResponseDtoDummy.dummy2();
    }

    @DisplayName("????????? ?????? ???????????? ???????????? ??????????????? ??????")
    @Test
    void wrongAddressException_createDayLaborTest() {
        //given & when
        given(localRepository.findById(dto.getLocalNo()))
                .willReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> service.addDayLabor(dto))
                .isInstanceOf(WrongAddressException.class);
    }

    @DisplayName("????????? ????????? ??????????????? ????????? ??????")
    @Test
    void success_createDayLaborTest() {
        //given
        given(localRepository.findById(dto.getLocalNo()))
                .willReturn(Optional.of(addressLocal));
        given(dayLaborRepository.save(any()))
                .willReturn(dayLabor);

        //when
        service.addDayLabor(dto);

        //then
        verify(dayLaborRepository, timeout(1))
                .save(captor.capture());

        //then
        DayLabor test = captor.getValue();
        System.out.println(dto.getLocalNo());
        assertThat(test.getMaxLabor()).isEqualTo(dto.getMaxLabor());
        assertThat(test.getAddressNo()).isEqualTo(dto.getLocalNo());
    }

    @DisplayName("??????????????? ?????????????????? ?????????????????????????????????")
    @Test
    void modifyDayLabor_exception() {
        //given
        given(dayLaborRepository.findById(any()))
                .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> service.modifyDayLabor(fixDto))
                .isInstanceOf(NotExistDayLabor.class);
    }

    @DisplayName("????????? ?????? ?????? ??????????????????")
    @Test
    void success_modifyDayLabor() {
        //given
        given(dayLaborRepository.findById(any()))
                .willReturn(Optional.of(dayLabor));
        //when
        service.modifyDayLabor(fixDto);

        //then
        verify(dayLaborRepository, times(1))
                .findById(any());
    }

    @DisplayName("????????? ?????? ??????????????????")
    @Test
    void findAll() {
        //given
        DayLabor laborDummy = DayLaboyDummy.dummy1();
        List<DayLabor> list = new ArrayList<>();
        List<GetDayLaborResponseDto> getlist = new ArrayList<>();

        getlist.add(response1);
        getlist.add(response2);

        list.add(dayLabor);
        list.add(laborDummy);

        given(dayLaborRepository.findAll())
                .willReturn(list);
        given(dayLaborRepository.findAllDayLabor())
                .willReturn(getlist);

        //when
        List<GetDayLaborResponseDto> allDayLabors = service.findDayLabors();

        //then
        verify(dayLaborRepository, times(1))
                .findAllDayLabor();

        assertThat(allDayLabors.get(0).getMaxLabor()).isEqualTo(response1.getMaxLabor());
        assertThat(allDayLabors.get(0).getLocal()).isEqualTo(response1.getLocal());
        assertThat(allDayLabors.get(1).getMaxLabor()).isEqualTo(response2.getMaxLabor());
        assertThat(allDayLabors.get(1).getMaxLabor()).isEqualTo(response2.getMaxLabor());
    }
}