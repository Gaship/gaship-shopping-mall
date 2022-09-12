package shop.gaship.gashipshoppingmall.statuscode.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shop.gaship.gashipshoppingmall.error.ExceptionAdviceController;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;
import shop.gaship.gashipshoppingmall.statuscode.advisor.StatusCodeAdvisor;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 갱신기간 rest controller 테스트 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@WebMvcTest(RenewalPeriodRestController.class)
@Import({ExceptionAdviceController.class,})
class RenewalPeriodRestControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private StatusCodeService statusCodeService;

    @MockBean
    LogAndCrashAdapter logAndCrashAdapter;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RenewalPeriodRestController(statusCodeService))
                .setControllerAdvice(StatusCodeAdvisor.class)
                .build();
    }

    @DisplayName("회원등급 갱신기간 상태코드값 수정하는 경우")
    @Test
    void renewalPeriodModify() throws Exception {
        Integer period = 12;

        mockMvc.perform(put("/api/renewal-period")
                        .param("value", String.valueOf(period))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(statusCodeService).modifyRenewalPeriod(any());
    }

    @DisplayName("Exception Handler 테스트")
    @Test
    void exceptionHandler_whenThrowStatusCodeNotFoundException() throws Exception{
        Integer period = 12;
        StatusCodeNotFoundException statusCodeNotFoundException = new StatusCodeNotFoundException();
        String errorMessage = statusCodeNotFoundException.getMessage();

        willThrow(new StatusCodeNotFoundException()).given(statusCodeService).modifyRenewalPeriod(period);

        mockMvc.perform(put("/api/renewal-period")
                        .param("value", String.valueOf(period))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", equalTo(errorMessage)))
                .andDo(print());

        verify(statusCodeService).modifyRenewalPeriod(any());
    }
}
