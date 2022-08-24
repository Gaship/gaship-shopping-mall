package shop.gaship.gashipshoppingmall.member.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.member.adapter.MemberAdapter;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipshoppingmall.member.dto.SuccessReissueResponse;
import shop.gaship.gashipshoppingmall.member.dto.VerifiedCheckDto;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.request.ReissuePasswordRequest;

import shop.gaship.gashipshoppingmall.member.dto.response.*;
import shop.gaship.gashipshoppingmall.member.dummy.SignInUserDetailDummy;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.InvalidReissueQualificationException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberBaseDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.impl.MemberServiceImpl;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.RenewalPeriod;
import shop.gaship.gashipshoppingmall.util.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.UserAuthority;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = DataProtectionConfig.class)
@Import({DataSourceConfig.class, MemberServiceImpl.class})
@TestPropertySource(
        value = {"classpath:application.properties", "classpath:application-dev.properties"})
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @MockBean
    Aes aes;

    @MockBean
    Sha512 sha512;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MemberGradeRepository memberGradeRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @MockBean
    MemberAdapter memberAdapter;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("새로운 회원 저장")
    void registerMember() {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();

        String plainEmailDummy = dummy.getEmail();

        given(statusCodeRepository.findByStatusCodeName(MemberStatus.ACTIVATION.getValue()))
                .willReturn(Optional.of(StatusCodeDummy.dummy()));

        StatusCode periodStatusCodeDummy = StatusCodeDummy.dummy();
        ReflectionTestUtils.setField(periodStatusCodeDummy, "explanation", "12");
        given(statusCodeRepository.findByStatusCodeName(RenewalPeriod.PERIOD.getValue()))
            .willReturn(Optional.of(periodStatusCodeDummy));
        given(memberGradeRepository.findByDefaultGrade()).willReturn(
                MemberGradeDummy.defaultDummy(
                        MemberGradeDtoDummy.requestDummy("일반", 0L),
                        StatusCodeDummy.dummy()
                ));
        given(statusCodeRepository.findByStatusCodeName(UserAuthority.MEMBER.getValue()))
                .willReturn(Optional.of(StatusCodeDummy.dummy()));

        VerifiedCheckDto verifiedCheck = new VerifiedCheckDto(true);
        given(memberAdapter.checkVerifiedEmail(any()))
            .willReturn(verifiedCheck);

        memberService.addMember(dummy);

        assertThat(dummy.getEmail()).isNotEqualTo(plainEmailDummy);

        verify(memberRepository, times(1))
                .saveAndFlush(any(Member.class));
    }


    @Test
    @DisplayName("이메일을 통해 현존하는 회원의 존재여부 확인 : 존재하는 경우")
    void isAvailableEmailCaseFounded() {
        given(sha512.encryptPlainText(anyString())).willReturn("a".repeat(10));
        given(memberRepository.findByEncodedEmailForSearch(anyString())).willReturn(
                Optional.of(shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy()));

        boolean isAvailableMember = memberService.isAvailableEmail("example@nhn.com");

        assertThat(isAvailableMember).isTrue();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원의 존재여부 확인 : 없는 경우")
    void isAvailableEmailCaseNotFounded() {
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
                .willReturn(Optional.empty());

        boolean isAvailableMember = memberService.isAvailableEmail("example@nhn.com");

        assertThat(isAvailableMember).isFalse();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하는 경우")
    void findMemberFromEmailCaseFounded() {
        given(sha512.encryptPlainText(anyString())).willReturn("a".repeat(10));
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
                .willReturn(Optional.of(shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy()));

        MemberResponseDto member = memberService.findMemberFromEmail("example@nhn.com");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하지 않는 경우")
    void findMemberFromEmailCaseNotFounded() {
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMemberFromEmail("example@nhn.com"))
                .hasMessage("해당 멤버를 찾을 수 없습니다");
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하는 경우")
    void findMemberFromNicknameCaseFounded() {
        given(memberRepository.findByNickname(anyString()))
                .willReturn(Optional.of(shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy()));

        Member member = memberService.findMemberFromNickname("example nickName");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("이메일을 통해 현존하는 회원 검색 : 존재하지 않는 경우")
    void findMemberFromNicknameCaseNotFounded() {
        String expectErrorMessage = "해당 멤버를 찾을 수 없습니다";
        given(memberRepository.findByNickname(anyString()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMemberFromNickname("example nickName"))
                .hasMessage(expectErrorMessage);
    }

    @DisplayName("memberRepository modify Test")
    @Test
    void modifyTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberBaseDummy.member1()));

        memberService.modifyMember(MemberBaseDummy.memberModifyRequestDtoDummy());

        verify(memberRepository, times(1))
                .findById(any());
    }

    @DisplayName("memberRepository modify by admin Test")
    @Test
    void modifyByAdminTestSuccess() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberBaseDummy.member1()));
        when(statusCodeRepository.findByStatusCodeName(any())).thenReturn(Optional.ofNullable(StatusCodeDummy.dummy()));

        memberService.modifyMemberByAdmin(MemberBaseDummy.memberModifyByAdminDto());

        verify(memberRepository, times(1))
                .findById(any());
        verify(statusCodeRepository, times(1))
                .findByStatusCodeName(any());
    }

    @DisplayName("memberRepository modify fail Test(해당 아이디가 없음)")
    @Test
    void modifyFailTest() {
        MemberModifyRequestDto memberModifyRequestDto = MemberBaseDummy.memberModifyRequestDtoDummy();
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.modifyMember(memberModifyRequestDto))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("해당 멤버를 찾을 수 없습니다");

        verify(memberRepository, times(1))
                .findById(any());
    }

    @DisplayName("memberRepository modify fail Test(바꾸려는 닉네임이 이미 존재)")
    @Test
    void modifyFailWithDuplicatedNicknameTest() {
        MemberModifyRequestDto memberModifyRequestDto = MemberBaseDummy.memberModifyRequestDtoDummy();

        when(memberRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.existsByNickname(any())).thenReturn(true);
        assertThatThrownBy(() -> memberService.modifyMember(memberModifyRequestDto))
                .isInstanceOf(DuplicatedNicknameException.class)
                .hasMessage("중복된 닉네임입니다");

        verify(memberRepository, times(1)).existsByNickname(any());
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
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberBaseDummy.member1()));

        memberService.findMember(1);

        verify(memberRepository).findById(1);
    }

    @DisplayName("memberRepository getByAdmin Test")
    @Test
    void getByAdminTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberBaseDummy.member1()));

        memberService.findMemberByAdmin(1);

        verify(memberRepository).findById(1);
    }

    @DisplayName("memberRepository getByAdmin Test2")
    @Test
    void getByAdminTest2() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberBaseDummy.member2()));

        memberService.findMemberByAdmin(1);

        verify(memberRepository).findById(1);
    }

    @DisplayName("memberRepository getList Test")
    @Test
    void getListTest() {
        Pageable pageable = PageRequest.of(3, 10);
        List<MemberResponseDtoByAdmin> memberList = MemberBaseDummy.MemberResponseDtoByAdminDummy();
        Page<MemberResponseDtoByAdmin> dtoPage = new PageImpl<>(memberList, pageable, memberList.size());
        when(memberRepository.findMembers(any(Pageable.class))).thenReturn(dtoPage);

        PageResponse<MemberResponseDtoByAdmin> list = memberService.findMembers(pageable);
        assertThat(list.getTotalPages()).isEqualTo(10);
        assertThat(list.getNumber()).isEqualTo(3);
        verify(memberRepository).findMembers(pageable);
    }

    @DisplayName("memberRepository modify Test")
    @Test
    void modifyByAdminTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberBaseDummy.member1()));

        memberService.modifyMember(MemberBaseDummy.memberModifyRequestDtoDummy());

        verify(memberRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("이메일을 통해서 로그인을 시도하는 회원의 정보를 조회합니다. : 존재하는 경우")
    void findSignInUserDetailCaseFounded() {
        Member dummy = shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy();
        String email = "example@nhn.com";

        given(sha512.encryptPlainText(anyString())).willReturn(email);
        given(memberRepository.findSignInUserDetail(anyString()))
                .willReturn(Optional.of(SignInUserDetailDummy.dummy()));
        given(aes.aesEcbDecode(anyString()))
            .willReturn(email);

        SignInUserDetailsDto userDetailsDto =
                memberService.findSignInUserDetailFromEmail(email);


        assertThat(userDetailsDto.getEmail()).isEqualTo(dummy.getEmail());
        assertThat(userDetailsDto.getHashedPassword()).isEqualTo(dummy.getPassword());
        assertThat(userDetailsDto.getMemberNo()).isEqualTo(dummy.getMemberNo());
        assertThat(userDetailsDto.getAuthorities()).isEqualTo(
                List.of(dummy.getMemberGrades().getName()));
        assertThat(userDetailsDto.getAuthorities()).isEqualTo(List.of(dummy.getMemberGrades().getName()));
        assertThat(userDetailsDto).isInstanceOf(SignInUserDetailsDto.class);
    }

    @Test
    @DisplayName("이메일을 통해서 로그인을 시도하는 회원의 정보를 조회합니다. : 존재하지 않는 경우")
    void findSignInUserDetailCaseNotFounded() {
        String expectErrorMessage = "해당 멤버를 찾을 수 없습니다";
        given(memberRepository.findSignInUserDetail(anyString()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findSignInUserDetailFromEmail("exmaple@nhn.com"))
                .hasMessage(expectErrorMessage);
    }

    @Test
    @DisplayName("닉네임을 통해 이메일 찾기를 실행하였을 때 이메일 일부가 감춰진 데이터를 반환합니다. : 성공")
    void findMemberEmailFromNicknameCaseSuccess() {
        given(memberRepository.findByNickname(anyString()))
                .willReturn(Optional.of(shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy()));

        FindMemberEmailResponse findMemberEmailResponse =
                memberService.findMemberEmailFromNickname("example nickname");

        assertThat(findMemberEmailResponse.getObscuredEmail())
                .isEqualTo("exam***@nhn.com");
    }

    @Test
    @DisplayName("닉네임을 통해 이메일 찾기를 실행하였을 때 이메일 일부가 감춰진 데이터를 반환합니다. : 실패")
    void findMemberEmailFromNicknameCaseFailure() {
        given(memberRepository.findByNickname(anyString()))
                .willThrow(new MemberNotFoundException());

        assertThatThrownBy(() ->
                memberService.findMemberEmailFromNickname("example nickname"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("이메일과 이름을 통해서 비밀번호 발급 자격을 확인합니다 : 성공")
    void checkReissuePasswordQualificationCaseSuccess() {
        given(sha512.encryptPlainText(anyString()))
                .willReturn("a".repeat(10));
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
                .willReturn(Optional.of(shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy()));
        given(aes.aesEcbDecode(anyString()))
                .willReturn("example");
        given(memberAdapter.requestSendReissuePassword(any(ReissuePasswordReceiveEmailDto.class)))
                .willReturn(ResponseEntity.ok(new SuccessReissueResponse("example@nhn.com", "password")));
        given(passwordEncoder.encode(anyString()))
                .willReturn("drowssap");

        ReissuePasswordRequest reissuePasswordRequest =
                new ReissuePasswordRequest("example@nhn.com", "example");

        Boolean result = memberService.reissuePassword(reissuePasswordRequest);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일과 이름을 통해서 비밀번호 발급 자격을 확인합니다 : 이름이 달라 실패")
    void checkReissuePasswordQualificationCaseFailure1() {
        given(sha512.encryptPlainText(anyString())).willReturn("a".repeat(10));
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
                .willReturn(Optional.of(shop.gaship.gashipshoppingmall.member.dummy.MemberDummy.dummy()));
        given(aes.aesEcbDecode(anyString())).willReturn("홍홍힝");


        ReissuePasswordRequest reissuePasswordRequest =
                new ReissuePasswordRequest("example@nhn.com", "홍홍홍");


        assertThatThrownBy(() ->
                memberService.reissuePassword(reissuePasswordRequest))
                .isInstanceOf(InvalidReissueQualificationException.class)
                .hasMessage("유효하지 않은 접근으로 인해 요청을 취하합니다.");
    }

    @Test
    @DisplayName("이메일과 이름을 통해서 비밀번호 발급 자격을 확인합니다 : 이메일이 없어 실패")
    void checkReissuePasswordQualificationCaseFailure2() {
        given(sha512.encryptPlainText(anyString())).willReturn("a".repeat(10));
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
                .willReturn(Optional.empty());

        ReissuePasswordRequest reissuePasswordRequest =
                new ReissuePasswordRequest("example@nhn.com", "홍홍홍");


        assertThatThrownBy(() ->
                memberService.reissuePassword(reissuePasswordRequest))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("해당 멤버를 찾을 수 없습니다");
    }
}
