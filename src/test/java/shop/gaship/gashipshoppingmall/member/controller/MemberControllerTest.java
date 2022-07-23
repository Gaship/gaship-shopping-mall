package shop.gaship.gashipshoppingmall.member.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.dummy.SignInUserDetailDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
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

        doNothing().when(memberService)
            .addMember(MemberCreationRequestDummy.dummy());

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
            .andExpect(jsonPath("$.identifyNo").value(dummy.getIdentifyNo()))
            .andExpect(jsonPath("$.email").value(dummy.getEmail()))
            .andExpect(jsonPath("$.hashedPassword").value(dummy.getHashedPassword()))
            .andExpect(jsonPath("$.isSocial").value(false))
            .andExpect(jsonPath("$.authorities[0]").value(dummy.getAuthorities().get(0)));
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
}
