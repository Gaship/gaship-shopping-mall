package shop.gaship.gashipshoppingmall.repairSchedule.service;

import java.time.LocalDate;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dummy.CreateScheduleRequestDtoDummy;
import shop.gaship.gashipshoppingmall.repairSchedule.dummy.GetRepairScheduleResponseDtoDummy;
import shop.gaship.gashipshoppingmall.repairSchedule.dummy.ModifyScheduleRequestDtoDummy;
import shop.gaship.gashipshoppingmall.repairSchedule.dummy.RepairScheduleDummy;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.exception.AlreadyExistSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.exception.NotExistSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.repository.RepairScheduleRepository;
import shop.gaship.gashipshoppingmall.repairSchedule.service.impl.RepairScheduleServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.service
 * fileName       : RepairScheduleServiceTest
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
@ExtendWith(SpringExtension.class)
@Import(RepairScheduleServiceImpl.class)
class RepairScheduleServiceTest {

    @Autowired
    RepairScheduleService service;

    @MockBean
    RepairScheduleRepository repository;

    @MockBean
    DayLaborRepository dayLaborRepository;

    CreateScheduleRequestDto dto;
    RepairSchedule schedule;
    DayLabor dayLabor;
    ArgumentCaptor<RepairSchedule> captor;
    ModifyScheduleRequestDto modify;
    GetRepairScheduleResponseDto responseDto1;
    GetRepairScheduleResponseDto responseDto2;
    List<GetRepairScheduleResponseDto> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        responseDto1 = GetRepairScheduleResponseDtoDummy.dummy1();
        responseDto2 = GetRepairScheduleResponseDtoDummy.dummy2();
        list.add(responseDto1);
        list.add(responseDto2);
        modify = ModifyScheduleRequestDtoDummy.dummy();
        captor = ArgumentCaptor.forClass(RepairSchedule.class);
        dto = CreateScheduleRequestDtoDummy.dummy();
        schedule = RepairScheduleDummy.dummy();
        dayLabor = DayLaboyDummy.dummy1();
    }

    @DisplayName("이미 존재하는 스케줄이있어서 예외가 생성되는상황")
    @Test
    void wrongAddressException_ScheduleTest() {
        //given
        given(repository.findByPk(dto.getLocalNo(), dto.getDate()))
            .willReturn(Optional.of(schedule));

        //when & then
        assertThatThrownBy(() -> service.addRepairSchedule(dto))
            .isInstanceOf(AlreadyExistSchedule.class);
    }

    @DisplayName("이미 존재하는 스케줄은 없는데 관련 지역물량이없을경우")
    @Test
    void wrongDayLaborException_ScheduleTest() {
        //given
        given(repository.findByPk(dto.getLocalNo(), dto.getDate()))
            .willReturn(Optional.empty());

        given(dayLaborRepository.findById(dto.getLocalNo()))
            .willReturn(Optional.empty());
        //when & then
        assertThatThrownBy(() -> service.addRepairSchedule(dto))
            .isInstanceOf(NotExistDayLabor.class);
    }

    @DisplayName("스케줄을 생성하는 테스트")
    @Test
    void success_ScheduleTest() {
        //given
        given(repository.findByPk(dto.getLocalNo(), dto.getDate()))
            .willReturn(Optional.empty());
        given(dayLaborRepository.findById(dto.getLabor()))
            .willReturn(Optional.of(dayLabor));

        //when
        service.addRepairSchedule(dto);

        //then
        verify(repository, timeout(1))
            .save(captor.capture());

        RepairSchedule test = captor.getValue();
        assertThat(test.pk.getDate()).isEqualTo(dto.getDate());
        assertThat(test.pk.getAddressNo()).isEqualTo(dto.getLocalNo());
        assertThat(test.getLabor()).isEqualTo(dto.getLabor());
        assertThat(test.getDayLabor()).isEqualTo(dayLabor);
    }

    @DisplayName("스케줄을 수정 예외 : 케줄을 찾을수없어서 실패")
    @Test
    void modify_ScheduleFail() {
        //given
        given(repository.findByPk(modify.getLocalNo(), modify.getDate()))
            .willReturn(Optional.empty());
        //when & then
        assertThatThrownBy(() -> service.modifyRepairSchedule(modify))
            .isInstanceOf(NotExistSchedule.class);
    }

    @DisplayName("스케줄을 수정 성공 테스트")
    @Test
    void modify_ScheduleSuccess() {
        //given
        given(repository.findByPk(modify.getLocalNo(), modify.getDate()))
            .willReturn(Optional.of(schedule));
        //when
        service.modifyRepairSchedule(modify);

        //then
        verify(repository, times(1))
            .findByPk(any(), any());
    }

    @DisplayName("스케줄 일자별 조회 테스트")
    @Test
    void searchSchedule_date() {
        //given
        LocalDate now = LocalDate.now();

        given(repository.findAllByDate(now))
            .willReturn(list);

        //when
        List<GetRepairScheduleResponseDto> test = service.findSchedulesByDate(now);

        //then
        verify(repository, times(1))
            .findAllByDate(now);

        assertThat(test.get(0)).isEqualTo(responseDto1);
        assertThat(test.get(1)).isEqualTo(responseDto2);
    }

    @DisplayName("스케줄 일자별 조회 page")
    @Test
    void searchSchedule_page() {
        //give
        PageRequest req = PageRequest.of(1, 10);
        Page<GetRepairScheduleResponseDto> pages = new PageImpl<>(list, req,
            list.size());

        given(repository.findAllSortDate(any()))
            .willReturn(pages);

        //when
        Page<GetRepairScheduleResponseDto> test = service.findRepairSchedules(req.getPageNumber(), req.getPageSize());

        //then
        assertThat(test.getSize()).isEqualTo(pages.getSize());
        assertThat(test.getPageable()).isEqualTo(req);
        assertThat(test.getSize()).isEqualTo(req.getPageSize());
        assertThat(req.getPageNumber()).isEqualTo(req.getPageNumber());
    }
}