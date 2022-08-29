package shop.gaship.gashipshoppingmall.inquiry.controller.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;

/**
 * @author : 최겸준
 * @since 1.0
 */
@WebMvcTest(CommonInquiryRestController.class)
class CommonInquiryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

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


    @DisplayName("문의 추가를 요청하는 dto 값이 검증조건에 맞지 않으면 validation 400 예외가 발생한다.")
    @Test
    void inquiryAdd_fail_validation_all() throws Exception {
        InquiryAddRequestDto failDto = new InquiryAddRequestDto();

        MvcResult result = mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(failDto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .exists())
            .andReturn();

        String body = result.getResponse().getContentAsString();

        assertThat(body)
            .contains("memberNo")
            .contains("title")
            .contains("inquiryContent")
            .contains("isProduct");
    }

    @DisplayName("고객문의를 추가하는 요청을 받고 service에 요청을 위임한뒤에 201값을 가지는 ResponseEntity를 반환한다.")
    @Test
    void inquiryAdd_customer_success_customer() throws Exception {
        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(inquiryService).addInquiry(any(InquiryAddRequestDto.class));
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 memberNo만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_customer_fail_validation_memberNo() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "memberNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("memberNo 는 필수 입력값입니다."));
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 title만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_customer_fail_validation_title_null() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 title이 빈문자열일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_customer_fail_validation_title_empty() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", "");

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 title이 공백일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_customer_fail_validation_title_space() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", " ");

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 inquiryContent만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_customer_fail_validation_inquiryContent() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "inquiryContent", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("inquiryContent 는 필수 입력값입니다."));
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 isProduct만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_customer_success_validation_productNo() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "productNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @DisplayName("고객문의 추가를 요청 dto값으로 productNo는 null이더라도 아무 예외 발생하지 않고 처리임무를 수행한다.")
    @Test
    void inquiryAdd_customer_fail_validation_isProduct() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "productNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @DisplayName("문의의 답변을 추가하는 요청이 오고 정상적인 전제조건이 주어졌을때 service에 기능요청을 한뒤 status 201값을 반환한다.")
    @Test
    void inquiryAnswerAdd_success() throws Exception {
        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isCreated());

        verify(inquiryService).addInquiryAnswer(any(InquiryAnswerRequestDto.class));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 오고 validation 조건을 모두 불충족하였을때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_all() throws Exception {
        InquiryAnswerRequestDto failDto = new InquiryAnswerRequestDto();

        MvcResult result = mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(failDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .exists())
            .andReturn();

        String body = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(body)
            .contains("inquiryNo")
            .contains("employeeNo")
            .contains("answerContent");
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 inquiryNo가 null일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_inquiryNo_null() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "inquiryNo", null);

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("inquiryNo 는 필수값입니다."));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 inquiryNo가 1미만일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_inquiryNo_min() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "inquiryNo", 0);

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("inquiryNo 는 최소값이 1입니다."));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 employeeNo이 null일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_employeeNo_null() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "employeeNo", null);

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("employeeNo 는 필수값입니다."));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 employeeNo가 1미만일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_employeeNo_min() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "employeeNo", 0);

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("employeeNo 는 최소값이 1입니다."));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 answerContent가 null일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_answerContent_null() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "answerContent", null);

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("answerContent 는 필수값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 answerContent가 빈문자열일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_answerContent_empty() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "answerContent", "");

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("answerContent 는 필수값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("문의의 답변을 추가하는 요청이 왔을때 answerContent가 공백일때 400 예외와 message가 발생한다")
    @Test
    void inquiryAnswerAdd_fail_answerContent_space() throws Exception {
        ReflectionTestUtils.setField(inquiryAnswerRequestDtoWhenAdd, "answerContent", " ");

        mvc.perform(post("/api/inquiries/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenAdd)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("answerContent 는 필수값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("문의답변 수정에 대한 정상적인 요청이 왔을시에 service에 처리를 위임하고 200을 반환한다.")
    @Test
    void inquiryAnswerModify() throws Exception {
        mvc.perform(put("/api/inquiries/1/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAnswerRequestDtoWhenModify)))
            .andExpect(status().isOk());

        verify(inquiryService).modifyInquiryAnswer(any(InquiryAnswerRequestDto.class));
    }

    @DisplayName("문의삭제시 service에 처리를 위임하고 200을 반환한다.")
    @Test
    void inquiryDelete() throws Exception {
        mvc.perform(delete("/api/inquiries/1/manager")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(inquiryService).deleteInquiryManager(anyInt());
    }

    @DisplayName("문의답변삭제시 service에 처리를 위임하고 200을 반환한다.")
    @Test
    void inquiryAnswerDelete() throws Exception {
        mvc.perform(delete("/api/inquiries/1/inquiry-answer")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(inquiryService).deleteInquiryAnswer(anyInt());
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 memberNo만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_memberNo_null() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "memberNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("memberNo 는 필수 입력값입니다."));
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 productNo가 1미만 일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_productNo_min() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", 0);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("productNo 는 최소값이 1입니다."));
    }

    @DisplayName("상품문의를 추가하는 요청을 받고 service에 요청을 위임한뒤에 201값을 가지는 ResponseEntity를 반환한다.")
    @Test
    void inquiryAdd_product_success_product() throws Exception {
        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(inquiryService).addInquiry(any(InquiryAddRequestDto.class));
    }



    @DisplayName("상품문의 추가를 요청 dto값으로 memberNo가 1미만 일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_memberNo_min() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "memberNo", 0);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("memberNo 는 최소값이 1입니다."));
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 title만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_title_null() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 title이 빈문자열일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_title_empty() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", "");

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 title이 공백일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_title_space() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", " ");

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값이며 공백이나 빈문자열이면 안됩니다."));
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 inquiryContent만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_fail_validation_inquiryContent() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "inquiryContent", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("inquiryContent 는 필수 입력값입니다."));
    }

    @DisplayName("상품문의 추가를 요청 dto값으로 isProduct만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_product_success_validation_productNo() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }



    @DisplayName("상품문의 추가를 요청 dto값으로 productNo는 null이더라도 아무 예외 발생하지 않고 처리임무를 수행한다.")
    @Test
    void inquiryAdd_product_fail_validation_isProduct() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @DisplayName("문의 단건조회요청을 잘 받고 service에 위임하여 반환된 InquiryDetailsResponseDto를 ResponseEntity의 body에 넣어서 반환한다. 상태코드 200")
    @Test
    void inquiryDetails() throws Exception {
        InquiryDetailsResponseDto mockDto = new InquiryDetailsResponseDto(
            1, 1, 1,"memberNickName", "employeeName", "processStatus",
            "productName", "title", "inquiryContent", LocalDateTime.now(), "answerContent",LocalDateTime.now(), null);

        given(inquiryService.findInquiry(anyInt()))
            .willReturn(mockDto);

        MvcResult result = mvc.perform(get("/api/inquiries/{inquiryNo}", 1)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.inquiryNo").value(mockDto.getInquiryNo()))
            .andExpect(jsonPath("$.inquiryContent").value(mockDto.getInquiryContent()))
            .andExpect(jsonPath("$.answerContent").value(mockDto.getAnswerContent()))
            .andExpect(jsonPath("$.title").value(mockDto.getTitle()))
            .andExpect(jsonPath("$.employeeName").value(mockDto.getEmployeeName()))
            .andExpect(jsonPath("$.memberNickname").value(mockDto.getMemberNickname()))
            .andExpect(jsonPath("$.productName").value(mockDto.getProductName()))
            .andExpect(jsonPath("$.productNo").value(mockDto.getProductNo()))
            .andExpect(jsonPath("$.processStatus").value(mockDto.getProcessStatus()))
            .andReturn();

        verify(inquiryService).findInquiry(1);
    }

}