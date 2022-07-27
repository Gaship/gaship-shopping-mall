package shop.gaship.gashipshoppingmall.gradehistory.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryFindRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDtoDummy;
import shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepository;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 등급이력 Service 구현체 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(GradeHistoryServiceImpl.class)
class GradeHistoryServiceImplTest {

    @Autowired
    private GradeHistoryService gradeHistoryService;

    @MockBean
    private GradeHistoryRepository gradeHistoryRepository;

    @MockBean
    private MemberRepository memberRepository;

    private GradeHistoryAddRequestDto addRequestDummy;

    @BeforeEach
    void setUp() {
        addRequestDummy = GradeHistoryDtoDummy.addRequestDummy();
    }

    @DisplayName("등급이력 등록 테스트")
    @Test
    void addGradeHistory() {
        Member memberDummy = MemberDummy.dummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(memberDummy));

        assertThatNoException().isThrownBy(() -> gradeHistoryService
                .addGradeHistory(addRequestDummy));

        verify(memberRepository).findById(any());
        verify(gradeHistoryRepository).save(any());
    }

    @DisplayName("등급이력 등록시" +
            "멤버를 찾을 수 없는 경우")
    @Test
    void addGradeHistory_whenMemberNotFound_throwExp(){
        when(memberRepository.findById(any()))
                .thenThrow(MemberNotFoundException.class);

        assertThatThrownBy(() -> gradeHistoryService.addGradeHistory(addRequestDummy))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberRepository).findById(any());
        verify(gradeHistoryRepository, never()).save(any());
    }

    @DisplayName("멤버 통한 등급이력 다건 조회 테스트")
    @Test
    void findGradeHistories(){
        int page = 0;
        int size = 1;
        Member memberDummy = MemberDummy.dummy();
        GradeHistoryResponseDto responseDtoDummy = GradeHistoryDtoDummy.responseDummy();
        GradeHistoryFindRequestDto requestDtoDummy = GradeHistoryDtoDummy.findRequestDummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(memberDummy));

        when(gradeHistoryRepository.getGradeHistoriesByMember(any(), any()))
                .thenReturn(new PageImpl<>(List.of(responseDtoDummy), PageRequest.of(page, size), 2));

        PageResponse<GradeHistoryResponseDto> result = gradeHistoryService
                .findGradeHistories(requestDtoDummy);

        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getContent()).hasSize(1);
    }

    @DisplayName("멤버 통한 등급이력 다건 조회시" +
            "해당 멤버가 존재하지 않을 경우")
    @Test
    void findGradeHistories_whenMemberIsNotFound_throwExp(){
        GradeHistoryFindRequestDto requestDtoDummy = GradeHistoryDtoDummy.findRequestDummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> gradeHistoryService.findGradeHistories(requestDtoDummy))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberRepository).findById(any());
        verify(gradeHistoryRepository, never()).getGradeHistoriesByMember(any(),any());
    }
}