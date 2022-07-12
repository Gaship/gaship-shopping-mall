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
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberDummy;
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

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils.*;

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

    @BeforeEach
    void setUp() {
        memberGradeRequestDto = createTestMemberGradeRequest("일반", 0L);
    }

    @Test
    void addMemberGrade_whenRenewalPeriodIsPresent() {
        // given
        StatusCode renewalPeriod = createTestStatusCode();
        MemberGrade memberGrade = createTestMemberGrade(memberGradeRequestDto, renewalPeriod);

        // mocking
        when(statusCodeRepository.findById(any())).thenReturn(Optional.of(renewalPeriod));
        when(memberGradeRepository.save(any())).thenReturn(memberGrade);

        // when
        memberGradeServiceImpl.addMemberGrade(memberGradeRequestDto);

        // then
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
        Integer testMemberGradeNo = 1;
        MemberGradeRequestDto modifyMemberGradeRequestDto = createTestMemberGradeRequest("새싹", 0L);

        StatusCode renewalPeriod = createTestStatusCode();
        MemberGrade memberGrade = createTestMemberGrade(memberGradeRequestDto, renewalPeriod);

        MemberGrade modifyMemberGrade = createTestMemberGrade(modifyMemberGradeRequestDto, renewalPeriod);

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
        Integer testMemberGradeNo = 1;
        MemberGradeRequestDto modifyMemberGradeRequestDto = createTestMemberGradeRequest("새싹", 0L);

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
        StatusCode renewalPeriod = createTestStatusCode();
        MemberGrade testMemberGrade = createTestMemberGrade(memberGradeRequestDto, renewalPeriod);

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
        Integer testMemberGradeNo = 1;
        StatusCode renewalPeriod = createTestStatusCode();
        MemberGrade testMemberGrade = createTestMemberGrade(memberGradeRequestDto, renewalPeriod);

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
        // given
        Integer testMemberGradeNo = 1;

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
        Integer testMemberGradeNo = 1;
        MemberGradeDto testMemberGradeDto = createTestMemberGradeDto("일반", 0L, "12개월");

        // mocking
        when(memberGradeRepository.getMemberGradeBy(any()))
                .thenReturn(Optional.of(testMemberGradeDto));

        // when&then
        assertThatNoException()
                .isThrownBy(() -> memberGradeServiceImpl.findMemberGrade(testMemberGradeNo));

        verify(memberGradeRepository).getMemberGradeBy(any());
    }

    @Test
    void findMemberGrade_whenMemberGradeIsEmpty() {
        // given
        Integer testMemberGradeNo = 1;

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
                .thenReturn(List.of(createTestMemberGradeDto("일반",
                        0L,
                        "12개월")));

        // when&then
        assertThatNoException()
                .isThrownBy(() -> memberGradeServiceImpl
                        .findMemberGrades(pageable));

        verify(memberGradeRepository).getMemberGrades(any());
    }
}