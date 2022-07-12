package shop.gaship.gashipshoppingmall.membergrade.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeInUseException;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeRequestDto;
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
@SpringBootTest
class MemberGradeServiceImplTest {

    @InjectMocks
    private MemberGradeServiceImpl memberGradeServiceImpl;

    @Mock
    private MemberGradeRepository memberGradeRepository;
    @Mock
    private StatusCodeRepository statusCodeRepository;
    @Mock
    private MemberRepository memberRepository;
    private MemberGradeRequestDto memberGradeRequestDto;
    private Integer testMemberGradeNo;

    @BeforeEach
    void setUp() {
        memberGradeRequestDto = MemberGradeDtoDummy.requestDummy("일반", 0L);
        testMemberGradeNo = 1;
    }

    @Test
    void addMemberGrade_whenRenewalPeriodIsPresent() {
        // given
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeRequestDto, renewalPeriod);

        // mocking
        when(statusCodeRepository.findById(any())).thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.save(any())).thenReturn(memberGrade);

        // when
        memberGradeServiceImpl.addMemberGrade(memberGradeRequestDto);

        // then
        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository).save(any());
    }

    @Test
    void addMemberGrade_whenRenewalPeriodIsEmpty_throwStatusCodeNotFoundException() {
        // mocking
        when(statusCodeRepository.findById(any())).thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeServiceImpl
                .addMemberGrade(memberGradeRequestDto))
                .isInstanceOf(StatusCodeNotFoundException.class);

        verify(statusCodeRepository).findById(any());
        verify(memberGradeRepository, never()).save(any());
    }

    @Test
    void modifyMemberGrade_whenMemberGradeIsPresent() {
        // given
        MemberGradeRequestDto modifyMemberGradeRequestDto = MemberGradeDtoDummy.requestDummy("새싹", 0L);

        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade memberGrade = MemberGradeDummy.dummy(memberGradeRequestDto, renewalPeriod);

        MemberGrade modifyMemberGrade = MemberGradeDummy.dummy(modifyMemberGradeRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.ofNullable(memberGrade));
        when(memberGradeRepository.save(any()))
                .thenReturn(modifyMemberGrade);

        // when
        memberGradeServiceImpl.modifyMemberGrade(testMemberGradeNo, modifyMemberGradeRequestDto);

        // then
        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository).save(any());
    }

    @Test
    void modifyMemberGrade_whenMemberGradeIsEmpty_throwMemberGradeNotFoundException() {
        // given
        MemberGradeRequestDto modifyMemberGradeRequestDto = MemberGradeDtoDummy.requestDummy("새싹", 0L);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeServiceImpl
                .modifyMemberGrade(testMemberGradeNo, modifyMemberGradeRequestDto))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberGradeRepository, never()).save(any());
    }

    @Test
    void removeMemberGrade_whenMemberGradeIsPresent_memberGradeIsNotUsed() {
        // given
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.dummy(memberGradeRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.ofNullable(testMemberGrade));
        when(memberRepository.findByMemberGrades(any()))
                .thenReturn(List.of());

        // when&then
        assertThatNoException().isThrownBy(() -> memberGradeServiceImpl.removeMemberGrade(any()));

        verify(memberGradeRepository).findById(any());
        verify(memberRepository).findByMemberGrades(any());
        verify(memberGradeRepository).delete(any());
    }

    @Test
    void removeMemberGrade_whenMemberGradeIsPresent_memberGradeIsUsed() {
        // given
        StatusCode renewalPeriod = StatusCodeDummy.dummy();
        MemberGrade testMemberGrade = MemberGradeDummy.dummy(memberGradeRequestDto, renewalPeriod);

        // mocking
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.ofNullable(testMemberGrade));
        when(memberRepository.findByMemberGrades(any()))
                .thenReturn(List.of(MemberDummy.dummy(renewalPeriod, testMemberGrade)));

        // when&then
        assertThatThrownBy(() -> memberGradeServiceImpl.removeMemberGrade(1))
                .isInstanceOf(MemberGradeInUseException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberRepository).findByMemberGrades(any());
        verify(memberGradeRepository, never()).delete(any());
    }

    @Test
    void removeMemberGrade_whenMemberGradeIsEmpty_throwMemberGradeNotFoundException() {
        // mocking
        when(memberGradeRepository.findById(any())).thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeServiceImpl.removeMemberGrade(testMemberGradeNo))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).findById(any());
        verify(memberRepository, never()).findByMemberGrades(any());
        verify(memberGradeRepository, never()).delete(any());
    }

    @Test
    void findMemberGrade_whenMemberGradeIsPresent() {
        // given
        MemberGradeDto testMemberGradeDto = MemberGradeDtoDummy.responseDummy("일반", 0L, "12개월");

        // mocking
        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.of(testMemberGradeDto));

        // when
        MemberGradeDto result = memberGradeServiceImpl.findMemberGrade(testMemberGradeNo);

        // then
        assertThat(result.getName()).isEqualTo(testMemberGradeDto.getName());
        assertThat(result.getAccumulateAmount()).isEqualTo(testMemberGradeDto.getAccumulateAmount());

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrade_whenMemberGradeIsEmpty() {
        // mocking
        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.empty());

        // when&then
        assertThatThrownBy(() -> memberGradeServiceImpl.findMemberGrade(testMemberGradeNo))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrades() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // mocking
        when(memberGradeRepository.getMemberGrades(pageable))
                .thenReturn(List.of(MemberGradeDtoDummy.responseDummy("일반",
                        0L,
                        "12개월")));

        // when&then
        assertThatNoException()
                .isThrownBy(() -> memberGradeServiceImpl
                        .findMemberGrades(pageable));

        verify(memberGradeRepository).getMemberGrades(any());
    }
}