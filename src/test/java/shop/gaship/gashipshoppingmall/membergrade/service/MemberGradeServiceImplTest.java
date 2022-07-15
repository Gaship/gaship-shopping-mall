package shop.gaship.gashipshoppingmall.membergrade.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
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
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.service
 * fileName       : MemberGradeServiceImplTest
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
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
        memberGradeAddRequestDto = MemberGradeDtoDummy.requestDummy("일반", 0L);
        testMemberGradeNo = 1;
    }

    @DisplayName("default 등급 추가시 " +
            "갱신기간에 대한 statusCode 존재하고 " +
            "누적금액이 동일한 회원등급이 존재하지 않고 " +
            "기존에 default 등급도 존재하지 않는 경우")
    @Test
    void addMemberGrade_whenRenewalPeriodIsPresent() {
        // given
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.defaultDummy(memberGradeAddRequestDto, renewalPeriod);

        // mocking
        when(statusCodeRepository.findById(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.existsByIsDefaultIsTrue())
                .thenReturn(false);
        when(memberGradeRepository.save(any()))
                .thenReturn(memberGrade);

        // when
        memberGradeService.addMemberGrade(memberGradeAddRequestDto);

        // then
        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).existsByIsDefaultIsTrue();
        verify(memberGradeRepository).save(any());
    }

    @DisplayName("default 등급 추가시 " +
            "갱신기간에 대한 StatusCode 가 존재하고" +
            "누적금액이 동일한 회원등급이 존재하는 경우")
    @Test
    void addMemberGrade_whenAccumulateAmountOverlapMemberGradeIsExist_throwExp(){
        // given
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();

        // mocking
        when(statusCodeRepository.findById(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(true);

        // when&then
        assertThatThrownBy(() -> memberGradeService.addMemberGrade(memberGradeAddRequestDto))
                .isInstanceOf(AccumulateAmountIsOverlap.class)
                .hasMessageContaining("동일한 기준누적금액");

        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository, never()).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }

    @DisplayName("default 등급 추가시 " +
            "갱신기간에 대한 StatusCode 가 존재하고 " +
            "누적금액이 동일한 회원등급이 존재하지 않고 " +
            "기존에 default 등급이 존재하는 경우")
    @Test
    void addMemberGrade_whenDefaultMemberGradeIsExist_throwExp(){
        // given
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();

        // mocking
        when(statusCodeRepository.findById(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.existsByIsDefaultIsTrue())
                .thenReturn(true);

        // when&then
        assertThatThrownBy(() -> memberGradeService.addMemberGrade(memberGradeAddRequestDto))
                .isInstanceOf(DefaultMemberGradeIsExist.class)
                .hasMessageContaining("기본 회원등급");

        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }


    @DisplayName("default 이외의 등급을 추가시 " +
            "갱신기간에 대한 StatusCode 가 존재하고 " +
            "누적금액이 동일한 회원등급이 존재하지 않는 경우")
    @Test
    void addMemberGrade_whenNotDefaultMemberGrade(){
        // given
        memberGradeAddRequestDto.setIsDefault(false);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        // mocking
        when(statusCodeRepository.findById(any()))
                .thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.save(any()))
                .thenReturn(memberGrade);

        // when
        memberGradeService.addMemberGrade(memberGradeAddRequestDto);

        // then
        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).save(any());
        verify(memberGradeRepository, never()).existsByIsDefaultIsTrue();
    }

    @DisplayName("등급 추가시 갱신가긴에 대한 StatusCode 가 존재하지 않는 경우")
    @Test
    void addMemberGrade_whenRenewalPeriodIsEmpty_throwStatusCodeNotFoundException() {
        // mocking
        when(statusCodeRepository.findById(any())).thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeService
                .addMemberGrade(memberGradeAddRequestDto))
                .isInstanceOf(StatusCodeNotFoundException.class);

        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository, never()).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository, never()).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }

    @DisplayName("회원등급 수정시 " +
            "회원등급이 존재하고 " +
            "수정하려는 기준누적금액이 기존 값과 동일할 경우")
    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent() {
        // given
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 0L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        memberGradeAddRequestDto.setName("새싹");
        MemberGrade modifyMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(memberGrade));
        when(memberGradeRepository.save(any()))
                .thenReturn(modifyMemberGrade);

        // when
        memberGradeService.modifyMemberGrade(modifyRequestDummy);

        // then
        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository, never()).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).save(any());
    }

    @DisplayName("회원등급 수정시 " +
            "회원등급이 존재하고 " +
            "수정하려는 기준누적금액이 기존 값과 다를 경우")
    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent_modifyAccumulate(){
        // given
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 1L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        MemberGrade modifyMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(memberGrade));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);
        when(memberGradeRepository.save(any()))
                .thenReturn(modifyMemberGrade);

        // when
        memberGradeService.modifyMemberGrade(modifyRequestDummy);

        // then
        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).save(any());
    }

    @DisplayName("회원등급 수정시 " +
            "해당 회원등급이 존재하지 않을 경우")
    @Test
    void modifyMemberGrade_whenMemberGradeIsEmpty_throwMemberGradeNotFoundException() {
        // given
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 0L);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeService
                .modifyMemberGrade(modifyRequestDummy))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository, never()).save(any());
    }

    @DisplayName("회원등급 삭제시" +
            "해당 회원등급이 존재하고" +
            "기본 회원 등급이 아니고" +
            "사용중이지 않은 회원등급인 경우")
    @Test
    void removeMemberGrade_notDefault_whenMemberGradeIsPresent_memberGradeIsNotUsed() {
        // given
        memberGradeAddRequestDto.setIsDefault(false);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));
        when(memberRepository.findByMemberGrades(any()))
                .thenReturn(List.of());

        // when&then
        assertThatNoException().isThrownBy(() -> memberGradeService.removeMemberGrade(any()));

        verify(memberGradeRepository).findById(any());
        verify(memberRepository).findByMemberGrades(any());
        verify(memberGradeRepository).delete(any());
    }

    @DisplayName("회원등급 삭제시" +
            "회원등급이 존재하고" +
            "기본 회원등급이 아니고" +
            "사용중인 등급인 경우")
    @Test
    void removeMemberGrade_whenMemberGradeIsPresent_memberGradeIsUsed() {
        // given
        memberGradeAddRequestDto.setIsDefault(false);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));
        when(memberRepository.findByMemberGrades(any()))
                .thenReturn(List.of(MemberDummy.dummy(renewalPeriod, testMemberGrade)));

        // when&then
        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(1))
                .isInstanceOf(MemberGradeInUseException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberRepository).findByMemberGrades(any());
        verify(memberGradeRepository, never()).delete(any());
    }

    @DisplayName("회원등급 삭제시" +
            "등급이 존재하지 않는 경우")
    @Test
    void removeMemberGrade_whenMemberGradeIsEmpty_throwExp() {
        // mocking
        when(memberGradeRepository.findById(any())).thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(testMemberGradeNo))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberRepository, never()).findByMemberGrades(any());
        verify(memberGradeRepository, never()).delete(any());
    }

    @DisplayName("회원등급 삭제시" +
            "등급이 존재하고" +
            "기본 회원등급인 경우")
    @Test
    void removeMemberGrade_whenDefaultMemberGrade_throwExp(){
        // given
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.defaultDummy(memberGradeAddRequestDto, renewalPeriod);

        ReflectionTestUtils.setField(testMemberGrade, "no", testMemberGradeNo);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));

        // when&then
        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(testMemberGradeNo))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(CannotDeleteDefaultMemberGrade.class)
                .hasMessageContaining("기본 회원등급");

        verify(memberRepository, never()).findByMemberGrades(any());
    }


    @Test
    void findMemberGrade_whenMemberGradeIsPresent() {
        // given
        MemberGradeResponseDto testMemberGradeResponseDto = MemberGradeDtoDummy.responseDummy("일반", 0L, "12개월");

        // mocking
        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.of(testMemberGradeResponseDto));

        // when
        MemberGradeResponseDto result = memberGradeService.findMemberGrade(testMemberGradeNo);

        // then
        assertThat(result.getName()).isEqualTo(testMemberGradeResponseDto.getName());
        assertThat(result.getAccumulateAmount()).isEqualTo(testMemberGradeResponseDto.getAccumulateAmount());

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrade_whenMemberGradeIsEmpty() {
        // mocking
        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeService.findMemberGrade(testMemberGradeNo))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrades() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("일반",
                0L,
                "12개월");

        // mocking
        when(memberGradeRepository.getMemberGrades(pageable))
                .thenReturn(List.of(dummyMemberGradeResponseDto));

        // when
        List<MemberGradeResponseDto> result = memberGradeService.findMemberGrades(pageable);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isEqualTo(dummyMemberGradeResponseDto);

        verify(memberGradeRepository).getMemberGrades(any());
    }
}