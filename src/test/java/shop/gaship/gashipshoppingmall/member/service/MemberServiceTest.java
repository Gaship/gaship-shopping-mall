package shop.gaship.gashipshoppingmall.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.SignInUserDetailDummy;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.UserAuthority;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.service <br/>
 * fileName       : MemberServiceTest <br/>
 * author         : ????????? <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           ?????????               ?????? ??????                         <br/>
 */
@SpringBootTest
@EnableConfigurationProperties(value = DataProtectionConfig.class)
@Import({DataSourceConfig.class})
@TestPropertySource(
    value = {"classpath:application.properties", "classpath:application-dev.properties"})
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
    @DisplayName("????????? ?????? ??????")
    void registerMember() {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();

        String plainEmailDummy = dummy.getEmail();

        given(statusCodeRepository.findByStatusCodeName(MemberStatus.DORMANCY.name()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(memberGradeRepository.findByDefaultGrade()).willReturn(
            MemberGradeDummy.defaultDummy(
                MemberGradeDtoDummy.requestDummy("??????", 0L),
                StatusCodeDummy.dummy()
            ));
        given(statusCodeRepository.findByStatusCodeName(UserAuthority.MEMBER.name()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));

        memberService.addMember(dummy);

        assertThat(dummy.getEmail()).isNotEqualTo(plainEmailDummy);

        verify(memberRepository, times(1))
            .saveAndFlush(any(Member.class));
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ????????? ???????????? ?????? : ???????????? ??????")
    void isAvailableEmailCaseFounded() {
        given(memberRepository.findByEmail(anyString())).willReturn(
            Optional.of(MemberDummy.dummy()));

        boolean isAvailableMember = memberService.isAvailableEmail("example@nhn.com");

        assertThat(isAvailableMember).isTrue();
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ????????? ???????????? ?????? : ?????? ??????")
    void isAvailableEmailCaseNotFounded() {
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        boolean isAvailableMember = memberService.isAvailableEmail("example@nhn.com");

        assertThat(isAvailableMember).isFalse();
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ?????? ?????? : ???????????? ??????")
    void findMemberFromEmailCaseFounded() {
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(MemberDummy.dummy()));

        MemberResponseDto member = memberService.findMemberFromEmail("example@nhn.com");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ?????? ?????? : ???????????? ?????? ??????")
    void findMemberFromEmailCaseNotFounded() {
        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMemberFromEmail("example@nhn.com"))
            .hasMessage("?????? ????????? ?????? ??? ????????????");
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ?????? ?????? : ???????????? ??????")
    void findMemberFromNicknameCaseFounded() {
        given(memberRepository.findByNickname(anyString()))
            .willReturn(Optional.of(MemberDummy.dummy()));

        Member member = memberService.findMemberFromNickname("example nickName");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ?????? ?????? : ???????????? ?????? ??????")
    void findMemberFromNicknameCaseNotFounded() {
        String expectErrorMessage = "?????? ????????? ?????? ??? ????????????";
        given(memberRepository.findByNickname(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMemberFromNickname("example nickName"))
            .hasMessage(expectErrorMessage);
    }

    @DisplayName("memberRepository modify Test")
    @Test
    void modifyTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestDummy.member1()));

        memberService.modifyMember(MemberTestDummy.memberModifyRequestDto());

        verify(memberRepository, times(1))
            .findById(any());
        verify(memberRepository, times(1))
            .save(any(Member.class));
    }

    @DisplayName("memberRepository modify fail Test(?????? ???????????? ??????)")
    @Test
    void modifyFailTest() {
        MemberModifyRequestDto memberModifyRequestDto = MemberTestDummy.memberModifyRequestDto();
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.modifyMember(memberModifyRequestDto))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessage("?????? ????????? ?????? ??? ????????????");

        verify(memberRepository, times(1))
            .findById(any());
    }

    @DisplayName("memberRepository modify fail Test(???????????? ???????????? ?????? ??????)")
    @Test
    void modifyFailWithDuplicatedNicknameTest() {
        MemberModifyRequestDto memberModifyRequestDto = MemberTestDummy.memberModifyRequestDto();

        when(memberRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.existsByNickname(any())).thenReturn(true);
        assertThatThrownBy(() -> memberService.modifyMember(memberModifyRequestDto))
            .isInstanceOf(DuplicatedNicknameException.class)
            .hasMessage("????????? ??????????????????");

        verify(memberRepository,times(1)).existsByNickname(any());
        verify(memberRepository, never())
            .findById(any());
    }

    @DisplayName("memberRepository delete Test")
    @Test
    void deleteTest() {
        memberService.removeMember(1);

        verify(memberRepository).deleteById(1);
    }

    @DisplayName("memberRepository get Test")
    @Test
    void getTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestDummy.member1()));

        memberService.findMember(1);

        verify(memberRepository).findById(1);
    }

    @DisplayName("memberRepository getList Test")
    @Test
    void getListTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Member> memberList = MemberTestDummy.CreateTestMemberEntityList();
        Page<Member> page = new PageImpl<>(memberList, pageable, 100);
        when(memberRepository.findAll(any(Pageable.class))).thenReturn(page);

        MemberPageResponseDto<MemberResponseDto, Member> list = memberService.findMembers(pageable);
        assertThat(list.getSize()).isEqualTo(10);
        assertThat(list.getTotalPage()).isEqualTo(10);
        assertThat(list.getPage()).isEqualTo(1);
        verify(memberRepository).findAll(pageable);
    }

    @DisplayName("memberRepository modify Test")
    @Test
    void modifyByAdminTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestDummy.member1()));

        memberService.modifyMember(MemberTestDummy.memberModifyRequestDto());

        verify(memberRepository, times(1))
            .findById(any());
        verify(memberRepository, times(1))
            .save(any(Member.class));
    }

    @Test
    @DisplayName("???????????? ????????? ???????????? ???????????? ????????? ????????? ???????????????. : ???????????? ??????")
    void findSignInUserDetailCaseFounded() {
        Member dummy = MemberDummy.dummy();
        given(memberRepository.findSignInUserDetail(anyString()))
            .willReturn(Optional.of(SignInUserDetailDummy.dummy()));

        SignInUserDetailsDto userDetailsDto =
            memberService.findSignInUserDetailFromEmail("example@nhn.com");


        assertThat(userDetailsDto.getEmail()).isEqualTo(dummy.getEmail());
        assertThat(userDetailsDto.getHashedPassword()).isEqualTo(dummy.getPassword());
        assertThat(userDetailsDto.getIdentifyNo()).isEqualTo(dummy.getMemberNo());
        assertThat(userDetailsDto.getAuthorities()).isEqualTo(List.of(dummy.getMemberGrades().getName()));
        assertThat(userDetailsDto).isInstanceOf(SignInUserDetailsDto.class);
    }

    @Test
    @DisplayName("???????????? ????????? ???????????? ???????????? ????????? ????????? ???????????????. : ???????????? ?????? ??????")
    void findSignInUserDetailCaseNotFounded() {
        String expectErrorMessage = "?????? ????????? ?????? ??? ????????????";
        given(memberRepository.findSignInUserDetail(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findSignInUserDetailFromEmail("exmaple@nhn.com"))
            .hasMessage(expectErrorMessage);
    }
}
