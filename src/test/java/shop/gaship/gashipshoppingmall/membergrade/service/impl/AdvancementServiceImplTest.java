package shop.gaship.gashipshoppingmall.membergrade.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.*;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.service.AdvancementService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 승급 관련 서비스 테스트 코드.
 *
 * @author : 김세미
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(AdvancementServiceImpl.class)
class AdvancementServiceImplTest {
    @Autowired
    private AdvancementService advancementService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberGradeRepository memberGradeRepository;

    @MockBean
    private GradeHistoryService gradeHistoryService;

    @DisplayName("회원 등급 갱신 대상 회원 다건 조회 테스트")
    @Test
    void findTargetsByRenewalGradeDate() {
        AdvancementTargetResponseDto dummyResponseDto = AdvancementTargetDummy.dummy();

        when(memberRepository.findMembersByNextRenewalGradeDate(any()))
                .thenReturn(List.of(dummyResponseDto));

        List<AdvancementTargetResponseDto> result = advancementService
                .findTargetsByRenewalGradeDate(LocalDate.now().toString());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMemberNo()).isOne();

        verify(memberRepository).findMembersByNextRenewalGradeDate(any());
    }

    @DisplayName("회원 등급 갱신 테스트")
    @Test
    void renewalMemberGrade() {
        RenewalMemberGradeRequestDto dummyRequestDto =
                RenewalMemberGradeRequestDummy.dummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(MemberDummy.dummy()));
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.of(MemberGradeDummy
                        .dummy(MemberGradeDtoDummy.requestDummy("일반", 0L),
                                StatusCodeDummy.dummy())));
        doNothing().when(gradeHistoryService)
                .addGradeHistory(any());

        assertThatNoException().isThrownBy(() -> advancementService.renewalMemberGrade(dummyRequestDto));

        verify(memberRepository).findById(any());
        verify(memberGradeRepository).findById(any());
        verify(gradeHistoryService).addGradeHistory(any());
    }

    @DisplayName("회원 등급 갱신시 " +
            "등급을 갱신하려는 회원이 없는 경우")
    @Test
    void renewalMemberGrade_whenMemberNotFound(){
        RenewalMemberGradeRequestDto dummyRequestDto =
                RenewalMemberGradeRequestDummy.dummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->  advancementService.renewalMemberGrade(dummyRequestDto))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberRepository).findById(any());
        verify(memberGradeRepository, never()).findById(any());
        verify(gradeHistoryService, never()).addGradeHistory(any());
    }

    @DisplayName("회원 등급 갱신시 " +
            "갱신하려는 등급이 없는 경우")
    @Test
    void renewalMemberGrade_whenMemberGradeNotFound(){
        RenewalMemberGradeRequestDto dummyRequestDto =
                RenewalMemberGradeRequestDummy.dummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(MemberDummy.dummy()));
        when(memberGradeRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->  advancementService.renewalMemberGrade(dummyRequestDto))
                .isInstanceOf(MemberGradeNotFoundException.class);

        verify(memberRepository).findById(any());
        verify(memberGradeRepository).findById(any());
        verify(gradeHistoryService, never()).addGradeHistory(any());
    }
}