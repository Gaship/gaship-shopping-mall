package shop.gaship.gashipshoppingmall.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.dummy.SignInUserDetailDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils;
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

        given(memberRepository.findById(anyInt())).willReturn(
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

    @Test
    @DisplayName("이메일을 통해 현존하는 회원의 존재여부 확인 : 존재하는 경우")
    void isAvailableEmailCaseFounded() {
        given(memberRepository.findByEmail(anyString())).willReturn(
            Optional.of(MemberDummy.dummy()));

        boolean isAvailableMember = memberService.isAvailableEmail("example@nhn.com");

        assertThat(isAvailableMember).isTrue();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원의 존재여부 확인 : 없는 경우")
    void isAvailableEmailCaseNotFounded() {
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        boolean isAvailableMember = memberService.isAvailableEmail("example@nhn.com");

        assertThat(isAvailableMember).isFalse();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하는 경우")
    void findMemberFromEmailCaseFounded() {
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(MemberDummy.dummy()));

        Member member = memberService.findMemberFromEmail("example@nhn.com");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하지 않는 경우")
    void findMemberFromEmailCaseNotFounded() {
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMemberFromEmail("example@nhn.com"))
            .hasMessage("찿고있는 회원의 정보가 존재하지않습니다.");
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하는 경우")
    void findMemberFromNicknameCaseFounded() {
        given(memberRepository.findByNickname(anyString()))
            .willReturn(Optional.of(MemberDummy.dummy()));

        Member member = memberService.findMemberFromNickname("example nickName");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하지 않는 경우")
    void findMemberFromNicknameCaseNotFounded() {
        String expectErrorMessage = "찿고있는 회원의 정보가 존재하지않습니다.";
        given(memberRepository.findByNickname(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMemberFromNickname("example nickName"))
            .hasMessage(expectErrorMessage);
    }

    @Test
    @DisplayName("이메일을 통해서 로그인을 시도하는 회원의 정보를 조회합니다. : 존재하는 경우")
    void findSignInUserDetailCaseFounded() {
        Member dummy = MemberDummy.dummy();
        given(memberRepository.findSignInUserDetail(anyString()))
            .willReturn(Optional.of(SignInUserDetailDummy.dummy()));

        SignInUserDetailsDto userDetailsDto =
            memberService.findSignInUserDetailFromEmail("example@nhn.com");


        assertThat(userDetailsDto.getEmail()).isEqualTo(dummy.getEmail());
        assertThat(userDetailsDto.getHashedPassword()).isEqualTo(dummy.getPassword());
        assertThat(userDetailsDto.getIdentifyNo()).isEqualTo(dummy.getMemberNo());
        assertThat(userDetailsDto.getAuthorities()).isEqualTo(List.of(dummy.getGrade().getName()));
        assertThat(userDetailsDto).isInstanceOf(SignInUserDetailsDto.class);
    }

    @Test
    @DisplayName("이메일을 통해서 로그인을 시도하는 회원의 정보를 조회합니다. : 존재하지 않는 경우")
    void findSignInUserDetailCaseNotFounded() {
        String expectErrorMessage = "찿고있는 회원의 정보가 존재하지않습니다.";
        given(memberRepository.findSignInUserDetail(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findSignInUserDetailFromEmail("exmaple@nhn.com"))
            .hasMessage(expectErrorMessage);
    }
}
