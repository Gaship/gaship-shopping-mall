package shop.gaship.gashipshoppingmall.inquiry.controller.advice;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.inquiry.controller.common.CommonInquiryRestController;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.exception.AlreadyCompleteInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentEmployeeWriterAboutInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentInquiryException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquirySearchBadRequestException;
import shop.gaship.gashipshoppingmall.inquiry.exception.NoRegisteredAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;

/**
 * advice controller까지 예외가 올라간 경우에 반환값을 테스트합니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@WebMvcTest(CommonInquiryRestController.class)
class InquiryRestControllerAdviceTest {
    @Autowired
    private CommonInquiryRestController commonInquiryRestController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InquiryService inquiryService;

    private InquiryAddRequestDto inquiryAddRequestDtoWhenCustomer;

    private InquiryAddRequestDto inquiryAddRequestDtoWhenProduct;

    private InquiryAnswerRequestDto inquiryAnswerRequestDtoWhenAdd;

    private InquiryAnswerRequestDto inquiryAnswerRequestDtoWhenModify;

    @BeforeEach
    void setUp() {
        setInquiryAddRequestDtoWhenCustomer();
        setInquiryAddRequestDtoWhenProduct();
        setInquiryAnswerRequestDtoWhenAdd();
        setInquiryAnswerRequestDtoWhenModify();
    }

    private void setInquiryAddRequestDtoWhenCustomer() {
        inquiryAddRequestDtoWhenCustomer = new InquiryAddRequestDto();
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "memberNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", "고객문의 1번 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "inquiryContent",
            "고객문의 1번 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "isProduct", Boolean.FALSE);
    }

    private void setInquiryAddRequestDtoWhenProduct() {
        inquiryAddRequestDtoWhenProduct = new InquiryAddRequestDto();
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "memberNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", "상품문의 2번 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "inquiryContent",
            "상품문의 2번 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "isProduct", Boolean.TRUE);
    }

    private void setInquiryAnswerRequestDtoWhenAdd() {
        inquiryAnswerRequestDtoWhenAdd = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "inquiryNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "answerContent",
            "문의 1번 내용에 대한 답변 추가");
    }

    private void setInquiryAnswerRequestDtoWhenModify() {
        inquiryAnswerRequestDtoWhenModify = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenModify, "inquiryNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenModify, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenModify, "answerContent",
            "문의 1번 내용에 대한 답변 수정");
    }

    @DisplayName("AlreadyCompleteInquiryAnswerException 이 발생했을시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 400이다.")
    @Test
    void AlreadyCompleteInquiryAnswerException() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new AlreadyCompleteInquiryAnswerException());

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(AlreadyCompleteInquiryAnswerException.MESSAGE));
    }

    @DisplayName("DifferentEmployeeWriterAboutInquiryAnswerException 이 발생했을시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 400이다.")
    @Test
    void DifferentEmployeeWriterAboutInquiryAnswerException() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new DifferentEmployeeWriterAboutInquiryAnswerException());

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value(
                DifferentEmployeeWriterAboutInquiryAnswerException.MESSAGE));
    }

    @DisplayName("InquiryNotFoundException 이 발생했을시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 400이다.")
    @Test
    void InquiryNotFoundException() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new InquiryNotFoundException());

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(InquiryNotFoundException.MESSAGE));
    }

    @DisplayName("NoRegisteredAnswerException 이 발생했을시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 400이다.")
    @Test
    void NoRegisteredAnswerException() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new NoRegisteredAnswerException());

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(NoRegisteredAnswerException.MESSAGE));
    }

    @DisplayName("DifferentInquiryException 이 발생했을시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 400이다.")
    @Test
    void DifferentInquiryException() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new DifferentInquiryException());

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(DifferentInquiryException.MESSAGE));
    }

    @DisplayName("InquirySearchBadRequestException 이 발생했을시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 400이다.")
    @Test
    void InquirySearchBadRequestException() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new InquirySearchBadRequestException());

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value(InquirySearchBadRequestException.MESSAGE));
    }

    @DisplayName("어딘가에서 모종의 이유로 exception 이 발생할시에 ErrorResponse에 해당정보가 들어간뒤 responseEntity의 바디값으로 들어간다. 이때 status코드는 500이다.")
    @Test
    void exception() throws Exception {
        given(commonInquiryRestController.inquiryDelete(anyInt(), anyInt())).willThrow(
            new RuntimeException("알수 없는 에러"));

        mvc.perform(delete("/api/inquiries/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value("알수 없는 에러"));
    }
}

