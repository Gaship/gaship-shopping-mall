package shop.gaship.gashipshoppingmall.membergrade.service;

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
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.PageResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.*;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * ???????????? service ?????????
 *
 * @author : ?????????
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(MemberGradeServiceImpl.class)
class MemberGradeServiceImplTest {

    @Autowired
    private MemberGradeService memberGradeService;

    @MockBean
    private MemberGradeRepository memberGradeRepository;
    @MockBean
    private StatusCodeRepository statusCodeRepository;
    @MockBean
    private MemberRepository memberRepository;
    private MemberGradeAddRequestDto memberGradeAddRequestDto;
    private Integer testMemberGradeNo;

    @BeforeEach
    void setUp() {
        memberGradeAddRequestDto = MemberGradeDtoDummy.requestDummy("??????", 0L);
        testMemberGradeNo = 1;
    }

    @DisplayName("default ?????? ????????? " +
            "??????????????? ?????? statusCode ???????????? " +
            "??????????????? ????????? ??????????????? ???????????? ?????? " +
            "????????? default ????????? ???????????? ?????? ??????")
    @Test
    void addMemberGrade_whenRenewalPeriodIsPresent() {
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.defaultDummy(memberGradeAddRequestDto, renewalPeriod);

        when(statusCodeRepository.findByGroupCodeName(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.existsByIsDefaultIsTrue())
                .thenReturn(false);
        when(memberGradeRepository.save(any()))
                .thenReturn(memberGrade);

        memberGradeService.addMemberGrade(memberGradeAddRequestDto);

        verify(statusCodeRepository).findByGroupCodeName(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).existsByIsDefaultIsTrue();
        verify(memberGradeRepository).save(any());
    }

    @DisplayName("default ?????? ????????? " +
            "??????????????? ?????? StatusCode ??? ????????????" +
            "??????????????? ????????? ??????????????? ???????????? ??????")
    @Test
    void addMemberGrade_whenAccumulateAmountOverlapMemberGradeIsExist_throwExp(){
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();

        when(statusCodeRepository.findByGroupCodeName(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(true);

        assertThatThrownBy(() -> memberGradeService.addMemberGrade(memberGradeAddRequestDto))
                .isInstanceOf(AccumulateAmountIsOverlap.class)
                .hasMessageContaining("????????? ??????????????????");

        verify(statusCodeRepository).findByGroupCodeName(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository, never()).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }

    @DisplayName("default ?????? ????????? " +
            "??????????????? ?????? StatusCode ??? ???????????? " +
            "??????????????? ????????? ??????????????? ???????????? ?????? " +
            "????????? default ????????? ???????????? ??????")
    @Test
    void addMemberGrade_whenDefaultMemberGradeIsExist_throwExp(){
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();

        when(statusCodeRepository.findByGroupCodeName(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.existsByIsDefaultIsTrue())
                .thenReturn(true);

        assertThatThrownBy(() -> memberGradeService.addMemberGrade(memberGradeAddRequestDto))
                .isInstanceOf(DefaultMemberGradeIsExist.class)
                .hasMessageContaining("?????? ????????????");

        verify(statusCodeRepository).findByGroupCodeName(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }


    @DisplayName("default ????????? ????????? ????????? " +
            "??????????????? ?????? StatusCode ??? ???????????? " +
            "??????????????? ????????? ??????????????? ???????????? ?????? ??????")
    @Test
    void addMemberGrade_whenNotDefaultMemberGrade(){
        memberGradeAddRequestDto.setIsDefault(false);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(statusCodeRepository.findByGroupCodeName(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.save(any()))
                .thenReturn(memberGrade);

        memberGradeService.addMemberGrade(memberGradeAddRequestDto);

        verify(statusCodeRepository).findByGroupCodeName(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).save(any());
        verify(memberGradeRepository, never()).existsByIsDefaultIsTrue();
    }

    @DisplayName("?????? ????????? ??????????????? ?????? StatusCode ??? ???????????? ?????? ??????")
    @Test
    void addMemberGrade_whenRenewalPeriodIsEmpty_throwStatusCodeNotFoundException() {
        when(statusCodeRepository.findByGroupCodeName(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberGradeService
                .addMemberGrade(memberGradeAddRequestDto))
                .isInstanceOf(StatusCodeNotFoundException.class);

        verify(statusCodeRepository).findByGroupCodeName(any());
        verify(memberGradeRepository, never()).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository, never()).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }

    @DisplayName("???????????? ????????? " +
            "??????????????? ???????????? " +
            "??????????????? ????????????????????? ?????? ?????? ????????? ??????")
    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent() {
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "??????", 0L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        memberGradeAddRequestDto.setName("??????");
        MemberGrade modifyMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(memberGrade));
        when(memberGradeRepository.save(any()))
                .thenReturn(modifyMemberGrade);

        memberGradeService.modifyMemberGrade(modifyRequestDummy);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository, never()).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).save(any());
    }

    @DisplayName("???????????? ????????? " +
            "??????????????? ???????????? " +
            "??????????????? ????????????????????? ?????? ?????? ?????? ??????")
    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent_modifyAccumulate(){
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "??????", 1L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        MemberGrade modifyMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(memberGrade));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.save(any()))
                .thenReturn(modifyMemberGrade);

        memberGradeService.modifyMemberGrade(modifyRequestDummy);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).save(any());
    }

    @DisplayName("???????????? ????????? " +
            "?????? ??????????????? ???????????? ?????? ??????")
    @Test
    void modifyMemberGrade_whenMemberGradeIsEmpty_throwMemberGradeNotFoundException() {
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "??????", 0L);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberGradeService
                .modifyMemberGrade(modifyRequestDummy))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository, never()).save(any());
    }

    @DisplayName("???????????? ?????????" +
            "?????? ??????????????? ????????????" +
            "?????? ?????? ????????? ?????????" +
            "??????????????? ?????? ??????????????? ??????")
    @Test
    void removeMemberGrade_notDefault_whenMemberGradeIsPresent_memberGradeIsNotUsed() {
        memberGradeAddRequestDto.setIsDefault(false);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));
        when(memberRepository.findByMemberGrades(any()))
                .thenReturn(List.of());

        assertThatNoException().isThrownBy(() -> memberGradeService.removeMemberGrade(any()));

        verify(memberGradeRepository).findById(any());
        verify(memberRepository).findByMemberGrades(any());
        verify(memberGradeRepository).delete(any());
    }

    @DisplayName("???????????? ?????????" +
            "??????????????? ????????????" +
            "?????? ??????????????? ?????????" +
            "???????????? ????????? ??????")
    @Test
    void removeMemberGrade_whenMemberGradeIsPresent_memberGradeIsUsed() {
        memberGradeAddRequestDto.setIsDefault(false);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));
        when(memberRepository.findByMemberGrades(any()))
                .thenReturn(List.of(MemberDummy.dummy()));

        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(1))
                .isInstanceOf(MemberGradeInUseException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberRepository).findByMemberGrades(any());
        verify(memberGradeRepository, never()).delete(any());
    }

    @DisplayName("???????????? ?????????" +
            "????????? ???????????? ?????? ??????")
    @Test
    void removeMemberGrade_whenMemberGradeIsEmpty_throwExp() {
        when(memberGradeRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(testMemberGradeNo))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberRepository, never()).findByMemberGrades(any());
        verify(memberGradeRepository, never()).delete(any());
    }

    @DisplayName("???????????? ?????????" +
            "????????? ????????????" +
            "?????? ??????????????? ??????")
    @Test
    void removeMemberGrade_whenDefaultMemberGrade_throwExp(){
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.defaultDummy(memberGradeAddRequestDto, renewalPeriod);

        ReflectionTestUtils.setField(testMemberGrade, "no", testMemberGradeNo);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));

        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(testMemberGradeNo))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(CannotDeleteDefaultMemberGrade.class)
                .hasMessageContaining("?????? ????????????");

        verify(memberRepository, never()).findByMemberGrades(any());
    }


    @Test
    void findMemberGrade_whenMemberGradeIsPresent() {
        MemberGradeResponseDto testMemberGradeResponseDto = MemberGradeDtoDummy.responseDummy("??????", 0L, "12??????");

        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.of(testMemberGradeResponseDto));

        MemberGradeResponseDto result = memberGradeService.findMemberGrade(testMemberGradeNo);

        assertThat(result.getName()).isEqualTo(testMemberGradeResponseDto.getName());
        assertThat(result.getAccumulateAmount()).isEqualTo(testMemberGradeResponseDto.getAccumulateAmount());

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrade_whenMemberGradeIsEmpty() {
        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberGradeService.findMemberGrade(testMemberGradeNo))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrades() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("??????",
                0L,
                "12??????");

        when(memberGradeRepository.getMemberGrades(pageable))
                .thenReturn(new PageImpl<>(List.of(dummyMemberGradeResponseDto), pageable, 1));

        PageResponseDto<MemberGradeResponseDto> result = memberGradeService.findMemberGrades(pageable);

        assertThat(result.getPage()).isEqualTo(page + 1);
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getDtoList()).hasSize(1);

        verify(memberGradeRepository).getMemberGrades(any());
    }
}