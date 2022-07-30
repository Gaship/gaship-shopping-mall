package shop.gaship.gashipshoppingmall.inquiry.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * InquiryRestController test
 *
 * @author : 최겸준
 * @since 1.0
 */
@WebMvcTest(InquiryRestController.class)
class InquiryRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InquiryService inquiryService;

    private InquiryAddRequestDto inquiryAddRequestDtoWhenCustomer;
    private InquiryAddRequestDto inquiryAddRequestDtoWhenProduct;
    @BeforeEach
    void setUp() {
        inquiryAddRequestDtoWhenCustomer = new InquiryAddRequestDto();
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "memberNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", "고객문의 1번 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "inquiryContent", "고객문의 1번 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "isProduct", Boolean.FALSE);

        inquiryAddRequestDtoWhenProduct = new InquiryAddRequestDto();
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "memberNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", "상품문의 2번 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "inquiryContent", "상품문의 2번 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "isProduct", Boolean.TRUE);
    }

    @DisplayName("고객문의를 추가하는 요청을 받고 service에 요청을 위임한뒤에 301값을 가지는 ResponseEntity를 반환한다.")
    @Test
    void inquiryAdd_success_customer() throws Exception {
        mvc.perform(post("/api/inquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(inquiryService).addInquiry(any(InquiryAddRequestDto.class));
    }

    @DisplayName("상품문의를 추가하는 요청을 받고 service에 요청을 위임한뒤에 301값을 가지는 ResponseEntity를 반환한다.")
    @Test
    void inquiryAdd_success_product() throws Exception {
        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenProduct))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        verify(inquiryService).addInquiry(any(InquiryAddRequestDto.class));
    }

    @DisplayName("요청하는 dto 값이 검증조건에 맞지 않으면 validation 400 예외가 발생한다.")
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

    @DisplayName("요청 dto값으로 memberNo만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_fail_validation_memberNo() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "memberNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("memberNo 는 필수 입력값입니다."));
    }

    @DisplayName("요청 dto값으로 title만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_fail_validation_title() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("title 은 필수 입력값입니다."));
    }

    @DisplayName("요청 dto값으로 inquiryContent만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_fail_validation_inquiryContent() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "inquiryContent", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("inquiryContent 는 필수 입력값입니다."));
    }

    @DisplayName("요청 dto값으로 isProduct만 null일시에 validation 400 예외와 message값이 발생한다.")
    @Test
    void inquiryAdd_success_validation_productNo() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "productNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @DisplayName("요청 dto값으로 productNo는 null이더라도 아무 예외 발생하지 않고 처리임무를 수행한다.")
    @Test
    void inquiryAdd_fail_validation_isProduct() throws Exception {
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "productNo", null);

        mvc.perform(post("/api/inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inquiryAddRequestDtoWhenCustomer))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message")
                .value("isProduct 는 필수 입력값입니다."));
    }


    @Test
    void inquiryAnswerAdd() {

    }

    @Test
    void inquiryAnswerModify() {
    }

    @Test
    void inquiryDelete() {
    }

    @Test
    void inquiryAnswerDelete() {
    }
}