package shop.gaship.gashipshoppingmall.statuscode.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shop.gaship.gashipshoppingmall.statuscode.advisor.StatusCodeAdvisor;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeResponseDtoDummy;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 상태코드 rest controller 테스트
 *
 * @author : 김세미
 * @since 1.0
 */
@WebMvcTest(StatusCodeRestController.class)
class StatusCodeRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StatusCodeService statusCodeService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StatusCodeRestController(statusCodeService))
                .setControllerAdvice(StatusCodeAdvisor.class)
                .build();
    }

    @DisplayName("회원등급 갱신기간 상태코드값 수정하는 경우")
    @Test
    void renewalPeriodModify() throws Exception {
        String period = "12";

        mockMvc.perform(put("/statuscodes/renewal/period")
                        .param("period", period)
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                .andDo(print());

        verify(statusCodeService).modifyRenewalPeriod(any());
    }

    @DisplayName("상태코드 그룹명을 통해 상태코드 목록 조회하는 경우")
    @Test
    void statusCodeList() throws Exception{
        String groupCodeName = SalesStatus.GROUP;
        List<StatusCodeResponseDto> responseDummy = StatusCodeResponseDtoDummy.listDummy();

        when(statusCodeService.findStatusCodes(any()))
                .thenReturn(responseDummy);

        mockMvc.perform(get("/statuscodes/" + groupCodeName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()",equalTo(4)))
                .andExpect(jsonPath("$[0].statusCodeName", equalTo(SalesStatus.SALE.getValue())))
                .andExpect(jsonPath("$[0].priority", equalTo(1)));
    }

    @DisplayName("Exception Handler 테스트")
    @Test
    void exceptionHandler_whenThrowStatusCodeNotFoundException() throws Exception{
        String period = "12";
        StatusCodeNotFoundException statusCodeNotFoundException = new StatusCodeNotFoundException();
        String errorMessage = statusCodeNotFoundException.getMessage();

        willThrow(new StatusCodeNotFoundException()).given(statusCodeService).modifyRenewalPeriod(period);

        mockMvc.perform(put("/statuscodes/renewal/period")
                        .param("period", period)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", equalTo(errorMessage)))
                .andDo(print());

        verify(statusCodeService).modifyRenewalPeriod(any());
    }
}