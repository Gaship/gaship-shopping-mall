package shop.gaship.gashipshoppingmall.membergrade.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.*;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;
import shop.gaship.gashipshoppingmall.membergrade.service.impl.MemberGradeServiceImpl;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 회원등급 service 테스트
 *
 * @author : 김세미
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
        memberGradeAddRequestDto = MemberGradeDtoDummy.requestDummy("일반", 0L);
        testMemberGradeNo = 1;
    }

    @DisplayName("default 등급 추가시 " +
            "갱신기간에 대한 statusCode 존재하고 " +
            "누적금액이 동일한 회원등급이 존재하지 않고 " +
            "기존에 default 등급도 존재하지 않는 경우")
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

    @DisplayName("default 등급 추가시 " +
            "갱신기간에 대한 StatusCode 가 존재하고" +
            "누적금액이 동일한 회원등급이 존재하는 경우")
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
                .hasMessageContaining("동일한 기준누적금액");

        verify(statusCodeRepository).findByGroupCodeName(any());
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
                .hasMessageContaining("기본 회원등급");

        verify(statusCodeRepository).findByGroupCodeName(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
        verify(memberGradeRepository).existsByIsDefaultIsTrue();
        verify(memberGradeRepository, never()).save(any());
    }


    @DisplayName("default 이외의 등급을 추가시 " +
            "갱신기간에 대한 StatusCode 가 존재하고 " +
            "누적금액이 동일한 회원등급이 존재하지 않는 경우")
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

    @DisplayName("등급 추가시 갱신가긴에 대한 StatusCode 가 존재하지 않는 경우")
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

    @DisplayName("회원등급 수정시 " +
            "회원등급이 존재하고 " +
            "수정하려는 기준누적금액이 기존 값과 동일할 경우")
    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent() {
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 0L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        memberGradeAddRequestDto.setName("새싹");
        MemberGrade modifyMemberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(memberGrade));
        when(memberGradeRepository.save(any()))
                .thenReturn(modifyMemberGrade);

        memberGradeService.modifyMemberGrade(modifyRequestDummy);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository, never()).existsByAccumulateAmountEquals(any());
    }

    @DisplayName("회원등급 수정시 " +
            "회원등급이 존재하고 " +
            "수정하려는 기준누적금액이 기존 값과 다를 경우")
    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent_modifyAccumulate(){
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 1L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(memberGrade));
        when(memberGradeRepository.existsByAccumulateAmountEquals(any()))
                .thenReturn(false);

        memberGradeService.modifyMemberGrade(modifyRequestDummy);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository).existsByAccumulateAmountEquals(any());
    }

    @DisplayName("회원등급 수정시 " +
            "해당 회원등급이 존재하지 않을 경우")
    @Test
    void modifyMemberGrade_whenMemberGradeIsEmpty_throwMemberGradeNotFoundException() {
        MemberGradeModifyRequestDto modifyRequestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 0L);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberGradeService
                .modifyMemberGrade(modifyRequestDummy))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
    }

    @DisplayName("회원등급 삭제시" +
            "해당 회원등급이 존재하고" +
            "기본 회원 등급이 아니고" +
            "사용중이지 않은 회원등급인 경우")
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

    @DisplayName("회원등급 삭제시" +
            "회원등급이 존재하고" +
            "기본 회원등급이 아니고" +
            "사용중인 등급인 경우")
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

    @DisplayName("회원등급 삭제시" +
            "등급이 존재하지 않는 경우")
    @Test
    void removeMemberGrade_whenMemberGradeIsEmpty_throwExp() {
        when(memberGradeRepository.findById(any())).thenReturn(Optional.empty());

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
        memberGradeAddRequestDto.setIsDefault(true);
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.defaultDummy(memberGradeAddRequestDto, renewalPeriod);

        ReflectionTestUtils.setField(testMemberGrade, "no", testMemberGradeNo);

        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(testMemberGrade));

        assertThatThrownBy(() -> memberGradeService.removeMemberGrade(testMemberGradeNo))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(CannotDeleteDefaultMemberGrade.class)
                .hasMessageContaining("기본 회원등급");

        verify(memberRepository, never()).findByMemberGrades(any());
    }


    @Test
    void findMemberGrade_whenMemberGradeIsPresent() {
        MemberGradeResponseDto testMemberGradeResponseDto = MemberGradeDtoDummy.responseDummy("일반", 0L, "12개월");

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

    @DisplayName("pagination 이 적용된 회원등급 다건 조회")
    @Test
    void findMemberGrades_withPageable() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("일반",
                0L,
                "12개월");

        when(memberGradeRepository.getMemberGrades(pageable))
                .thenReturn(new PageImpl<>(List.of(dummyMemberGradeResponseDto), pageable, 1));

        PageResponse<MemberGradeResponseDto> result = memberGradeService.findMemberGrades(pageable);

        assertThat(result.getPage()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getContent()).hasSize(1);

        verify(memberGradeRepository).getMemberGrades(any());
    }

    @DisplayName("전체 회원등급 다건 조회")
    @Test
    void findMemberGrades_all(){
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("일반",
                        0L,
                        "12개월");

        when(memberGradeRepository.getAll())
                .thenReturn(List.of(dummyMemberGradeResponseDto));

        List<MemberGradeResponseDto> result = memberGradeService.findMemberGrades();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("일반");
        assertThat(result.get(0).getAccumulateAmount()).isZero();
        assertThat(result.get(0).getRenewalPeriodStatusCode()).isEqualTo("12개월");
    }
}
