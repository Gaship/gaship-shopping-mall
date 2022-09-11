package shop.gaship.gashipshoppingmall.error;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;
import shop.gaship.gashipshoppingmall.inquiry.controller.common.CommonInquiryRestController;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.member.controller.MemberController;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.member.service.impl.MemberServiceImpl;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@WebMvcTest({ExceptionAdviceController.class, MemberController.class,
    CommonInquiryRestController.class, LogAndCrashAdapter.class})
class ExceptionAdviceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    @MockBean
    private InquiryService inquiryService;

    @Test
    void declaredExceptionAdviceTest() throws Exception {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();

        willThrow(SignUpDenyException.class)
            .given(memberService)
                .addMember(dummy);

        mockMvc.perform(post("/api/members/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(dummy)))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message").isEmpty());
    }

    @Test
    void notfoundExceptionHadle() throws Exception {
        String email = "example@nhn.com";

        willThrow(MemberNotFoundException.class).given(memberService).isAvailableEmail(email);

        mockMvc.perform(get("/api/members/check-email")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").isEmpty());
    }

    @Test
    void notAccessibleExceptionHandle() throws Exception {
        MemberForbiddenException exception = new MemberForbiddenException();
        willThrow(MemberForbiddenException.class).given(inquiryService).deleteInquiry(anyInt(), anyInt());

        mockMvc.perform(delete("/api/inquiries/{inquiryNo}", "100")
            .param("memberNo", "100"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").isEmpty());

    }

    @Test
    void validException() throws Exception {
        MemberCreationRequest request = MemberCreationRequestDummy.dummy();
        ReflectionTestUtils.setField(request, "email", "");

        mockMvc.perform(post("/api/members/sign-up")
                .content(new ObjectMapper().registerModule(new JavaTimeModule())
                    .writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("이메일을 입력해주세요."));
    }

    @Test
    void otherExceptionAdvice() throws Exception {
        willThrow(RuntimeException.class).given(inquiryService).deleteInquiry(anyInt(), anyInt());

        mockMvc.perform(delete("/api/inquiries/{inquiryNo}", "100")
                .param("memberNo", "100"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").isEmpty());
    }
}
