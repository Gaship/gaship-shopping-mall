package shop.gaship.gashipshoppingmall.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.service <br/>
 * fileName       : MemberServiceTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
@SpringBootTest
@Transactional
@TestPropertySource("classpath:application-test.properties")
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MemberGradeRepository memberGradeRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @Test
    @DisplayName("새로운 회원 저장")
    void registerMember() {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();

        String plainEmailDummy = dummy.getEmail();

        given(memberRepository.findById(0L)).willReturn(
            Optional.of(MemberDummy.dummy()));
        given(memberGradeRepository.findById(1)).willReturn(
            Optional.of(CreateTestUtils.createDummyMemberGrade()));
        given(statusCodeRepository.findById(2)).willReturn(
            Optional.of(CreateTestUtils.createTestStatusCode()));

        memberService.registerMember(dummy);

        assertThat(dummy.getEmail()).isNotEqualTo(plainEmailDummy);

        verify(memberRepository, times(1))
            .saveAndFlush(any(Member.class));
    }
}
