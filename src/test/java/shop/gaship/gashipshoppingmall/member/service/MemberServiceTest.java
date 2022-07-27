package shop.gaship.gashipshoppingmall.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.config.DataSourceConfig;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.member.dto.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
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
import shop.gaship.gashipshoppingmall.member.service.impl.MemberServiceImpl;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.UserAuthority;

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

    @Autowired
    Aes aes;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MemberGradeRepository memberGradeRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @Test
    @DisplayName("새로운 회원 저장")
    void registerMember() throws NoSuchAlgorithmException {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();

        String plainEmailDummy = dummy.getEmail();

        given(statusCodeRepository.findByStatusCodeName(MemberStatus.DORMANCY.name()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));
        given(memberGradeRepository.findByDefaultGrade()).willReturn(
            MemberGradeDummy.defaultDummy(
                MemberGradeDtoDummy.requestDummy("일반", 0L),
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
    @DisplayName("이메일을 통해 현존하는 회원의 존재여부 확인 : 존재하는 경우")
    void isAvailableEmailCaseFounded() {
        given(memberRepository.findByEncodedEmailForSearch(anyString())).willReturn(
            Optional.of(MemberDummy.dummy()));

        boolean isAvailableMember = memberService.isAvailableEmail(aes.aesECBEncode("example@nhn.com"));

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
    void findMemberFromEmailCaseFounded() throws NoSuchAlgorithmException {
        given(memberRepository.findByEncodedEmailForSearch(anyString()))
            .willReturn(Optional.of(MemberDummy.dummy()));

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
            .willReturn(Optional.of(MemberDummy.dummy()));

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
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestDummy.member1()));

        memberService.modifyMember(MemberTestDummy.memberModifyRequestDto());

        verify(memberRepository, times(1))
            .findById(any());
        verify(memberRepository, times(1))
            .save(any(Member.class));
    }

    @DisplayName("memberRepository modify fail Test(해당 아이디가 없음)")
    @Test
    void modifyFailTest() {
        MemberModifyRequestDto memberModifyRequestDto = MemberTestDummy.memberModifyRequestDto();
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
        MemberModifyRequestDto memberModifyRequestDto = MemberTestDummy.memberModifyRequestDto();

        when(memberRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.existsByNickname(any())).thenReturn(true);
        assertThatThrownBy(() -> memberService.modifyMember(memberModifyRequestDto))
            .isInstanceOf(DuplicatedNicknameException.class)
            .hasMessage("중복된 닉네임입니다");

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
        assertThat(userDetailsDto.getIsSocial()).isEqualTo(false);
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
            .willReturn(Optional.of(MemberDummy.dummy()));

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
//        TODO : 엔티티 병합 후 진행예정
    }

    @Test
    @DisplayName("이메일과 이름을 통해서 비밀번호 발급 자격을 확인합니다 : 실패")
    void checkReissuePasswordQualificationCaseFailure() {
//        TODO : 엔티티 병합 후 진행예정
    }
}
