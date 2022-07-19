package shop.gaship.gashipshoppingmall.gradehistory.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDtoDummy;
import shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepository;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    private GradeHistoryAddRequestDto requestDummy;

    @BeforeEach
    void setUp() {
        requestDummy = GradeHistoryDtoDummy.requestDto();
    }

    @DisplayName("등급이력 등록 테스트")
    @Test
    void addGradeHistory() {
        Member testMember = MemberDummy.dummy();

        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(testMember));

        assertThatNoException().isThrownBy(() -> gradeHistoryService
                .addGradeHistory(requestDummy));

        verify(memberRepository).findById(any());
        verify(gradeHistoryRepository).save(any());
    }

    @DisplayName("등급이력 등록시" +
            "멤버를 찾을 수 없는 경우")
    @Test
    void addGradeHistory_whenMemberNotFound_throwExp(){
        when(memberRepository.findById(any()))
                .thenThrow(MemberNotFoundException.class);

        assertThatThrownBy(() -> gradeHistoryService.addGradeHistory(requestDummy))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberRepository).findById(any());
        verify(gradeHistoryRepository, never()).save(any());
    }
}