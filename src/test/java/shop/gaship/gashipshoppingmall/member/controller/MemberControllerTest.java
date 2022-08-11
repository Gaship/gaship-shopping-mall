package shop.gaship.gashipshoppingmall.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.gaship.gashipshoppingmall.member.dto.request.FindMemberEmailRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.response.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.member.dto.request.ReissuePasswordRequest;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.dummy.SignInUserDetailDummy;
import shop.gaship.gashipshoppingmall.member.exception.InvalidReissueQualificationException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberBaseDummy;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.controller <br/>
 * fileName       : MemberControllerTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@WebMvcTest(MemberController.class)
@Slf4j
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUpMemberTest() throws Exception {
        String contentBody = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(MemberCreationRequestDummy.dummy());

        doNothing().when(memberService).addMember(MemberCreationRequestDummy.dummy());

        mockMvc.perform(post("/api/members/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentBody))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 : 이메일 미인증 또는 이메일 중복확인을 하지않는 경우")
    void signUpMemberFailureTest() throws Exception {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();
        dummy.setIsUniqueEmail(false);

        log.trace("dummy isUniqueEmail : {}", dummy.getIsUniqueEmail());

        String contentBody = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(dummy);


        mockMvc.perform(post("/api/members/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBody))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("이메일 중복확인 또는 이메일 검증이 필요합니다."));
    }

    @Test
    @DisplayName("이메일을 통한 회원 중복 결과 존재 : 있음 ")
    void retrieveMemberFromEmailCaseSuccess() throws Exception {
        given(memberService.isAvailableEmail(anyString()))
            .willReturn(true);

        mockMvc.perform(get("/api/members/check-email")
                .param("email", "abc@nhn.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.hasEmail").value(true));
    }

    @Test
    @DisplayName("이메일을 통한 회원 중복 결과 존재 : 없음 ")
    void retrieveMemberFromEmailCaseFailure() throws Exception {
        given(memberService.isAvailableEmail(anyString()))
            .willReturn(false);

        mockMvc.perform(get("/api/members/check-email")
                .param("email", "abc@nhn.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.hasEmail").value(false));
    }

    @Test
    @DisplayName("닉네임을 통한 회원 중복 결과 존재 : 있음")
    void checkDuplicateNicknameTestCaseFounded() throws Exception {
        given(memberService.isAvailableNickname(anyString()))
            .willReturn(true);

        mockMvc.perform(get("/api/members/check-nickname")
                .param("nickname", "abc"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.hasNickname").value(true));
    }

    @Test
    @DisplayName("닉네임을 통한 회원 중복 결과 존재 : 없음 ")
    void checkDuplicateNicknameTestCaseNotFounded() throws Exception {
        given(memberService.isAvailableNickname(anyString()))
            .willReturn(false);

        mockMvc.perform(get("/api/members/check-nickname")
                .param("nickname", "abc"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.hasNickname").value(false));
    }

    @Test
    @DisplayName("닉네임을 통한 추천할 회원의 고유번호 조회 : 성공 ")
    void retrieveMemberFromNicknameCaseSuccess() throws Exception {
        given(memberService.findMemberFromNickname(anyString()))
            .willReturn(MemberDummy.dummy());

        mockMvc.perform(get("/api/members/recommend-member")
                .param("nickname", "example nickname"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.memberNo").value(1));
    }

    @Test
    @DisplayName("닉네임을 통한 추천할 회원의 고유번호 조회 : 실패")
    void retrieveMemberFromNicknameCaseFailure() throws Exception {
        given(memberService.findMemberFromNickname(anyString()))
            .willThrow(new MemberNotFoundException());

        mockMvc.perform(get("/api/members/recommend-member")
                .param("nickname", "example nickname"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("해당 멤버를 찾을 수 없습니다"));
    }

    @Test
    @DisplayName("이메일을 통한 로그인 대상 회원 조회 : 성공")
    void retrieveSignInUserDetailCaseSuccess() throws Exception {
        SignInUserDetailsDto dummy = SignInUserDetailDummy.dummy();
        given(memberService.findSignInUserDetailFromEmail(anyString()))
            .willReturn(SignInUserDetailDummy.dummy());

        mockMvc.perform(get("/api/members/user-detail")
                .param("email", "example@nhn.com"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.memberNo").value(dummy.getMemberNo()))
            .andExpect(jsonPath("$.email").value(dummy.getEmail()))
            .andExpect(jsonPath("$.hashedPassword").value(dummy.getHashedPassword()))
            .andExpect(jsonPath("$.isSocial").value(false))
            .andExpect(jsonPath("$.authorities[0]")
                .value(dummy.getAuthorities().toArray()[0]));
    }

    @Test
    @DisplayName("이메일을 통한 로그인 대상 회원 조회 : 실패")
    void retrieveSignInUserDetailCaseFailure() throws Exception {
        given(memberService.findSignInUserDetailFromEmail(anyString()))
            .willThrow(new MemberNotFoundException());

        mockMvc.perform(get("/api/members/user-detail")
                .param("email", "example@nhn.com"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("해당 멤버를 찾을 수 없습니다"));
    }

    @Test
    @DisplayName("이메일 찾기 : 성공")
    void memberEmailFromNicknameFindCaseSuccess() throws Exception {
        String obscuredEmail = "exam****@nhn.com";
        given(memberService.findMemberEmailFromNickname(anyString())).willReturn(
            new FindMemberEmailResponse(obscuredEmail));

        FindMemberEmailRequest request = new FindMemberEmailRequest();
        ReflectionTestUtils.setField(request, "nickname", "example");

        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(
                post("/api/members/find-email")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.obscuredEmail").value(obscuredEmail));
    }

    @Test
    @DisplayName("이메일 찾기 : 실패")
    void memberEmailFromNicknameFindCaseFailure() throws Exception {
        given(memberService.findMemberEmailFromNickname(anyString())).willThrow(
            new MemberNotFoundException());

        FindMemberEmailRequest request = new FindMemberEmailRequest();
        ReflectionTestUtils.setField(request, "nickname", "example");

        String content = new ObjectMapper().writeValueAsString(request);


        mockMvc.perform(
                post("/api/members/find-email")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
            .andDo(print()).andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("해당 멤버를 찾을 수 없습니다"));
    }

    @Test
    @DisplayName("비밀번호 찾기 자격확인 : 성공")
    void reissuePasswordCheckCaseSuccess() throws Exception {
        given(memberService.reissuePassword(any(ReissuePasswordRequest.class)))
            .willReturn(true);

        ReissuePasswordRequest request = new ReissuePasswordRequest("example@nhn.com", "홍홍홍");
        mockMvc.perform(post("/api/members/find-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.changed").value(true))
            .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 찾기 자격확인 : 이름이 달라 실패")
    void reissuePasswordCheckCaseFailure1() throws Exception {
        given(memberService.reissuePassword(any(ReissuePasswordRequest.class)))
            .willThrow(new InvalidReissueQualificationException());

        ReissuePasswordRequest request = new ReissuePasswordRequest("example@nhn.com", "홍홍홍");
        mockMvc.perform(post("/api/members/find-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message")
                .value("유효하지 않은 접근으로 인해 요청을 취하합니다."))
            .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 찾기 자격확인 : 존재하는 사용자가 없어 실패")
    void reissuePasswordCheckCaseFailure2() throws Exception {
        given(memberService.reissuePassword(any(ReissuePasswordRequest.class)))
            .willThrow(new MemberNotFoundException());

        ReissuePasswordRequest request = new ReissuePasswordRequest("example@nhn.com", "홍홍홍");

        mockMvc.perform(post("/api/members/find-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message")
                .value("해당 멤버를 찾을 수 없습니다"))
            .andDo(print());
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void modifyMemberTest() throws Exception {
        String body = objectMapper.writeValueAsString(MemberBaseDummy.memberModifyRequestDtoDummy());
        doNothing().when(memberService).modifyMember(MemberBaseDummy.memberModifyRequestDtoDummy());

        mockMvc.perform(put("/api/members/{memberNo}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberService, times(1)).modifyMember(any());
    }

    @DisplayName("회원 삭제 테스트(실삭제(db 에서 삭제))")
    @Test
    void deleteMemberTest() throws Exception {
        doNothing().when(memberService).removeMember(any(Integer.class));
        mockMvc.perform(delete("/api/members/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(memberService).removeMember(any());
    }


    @DisplayName("회원 단건 조회 테스트")
    @Test
    void getMemberTest() throws Exception {
        when(memberService.findMember(anyInt())).thenReturn(MemberBaseDummy.memberResponseDtoDummy());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/members/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMember(any());
    }
}
