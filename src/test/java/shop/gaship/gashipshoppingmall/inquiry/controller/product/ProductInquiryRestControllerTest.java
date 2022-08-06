package shop.gaship.gashipshoppingmall.inquiry.controller.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.inquiry.controller.customer.CustomerInquiryRestController;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.statuscode.status.ProcessStatus;

/**
 * @author : 최겸준
 * @since 1.0
 */
@WebMvcTest(ProductInquiryRestController.class)
class ProductInquiryRestControllerTest {
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


    @DisplayName("상품문의 목록을 요청받았을시에 PageResponse 객체를 body에 담아서 ResponseEntity를 반환한다. status : 200")
    @Test
    void productInquiryList() throws Exception {
        List<InquiryListResponseDto> list = new ArrayList<>();
        InquiryListResponseDto customerInquiryPasswordDto = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "memberNickname", "홍길동");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "processStatus", "답변대기");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "title", "비밀번호를 까먹었어요..");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "registerDatetime", LocalDateTime.now());

        InquiryListResponseDto customerInquiryBeautiful = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryBeautiful, "inquiryNo", 2);
        ReflectionTestUtils.setField(customerInquiryBeautiful, "memberNickname", "이순신");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "processStatus", "답변완료");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "title", "이 사이트는 왜이렇게 이쁘나요?");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "registerDatetime", LocalDateTime.now());

        list.add(customerInquiryPasswordDto);
        list.add(customerInquiryBeautiful);

        Page page = new PageImpl(list, PageRequest.of(0, 5), 10);
        given(inquiryService.findInquiries(any(Pageable.class), anyBoolean()))
            .willReturn(page);

        mvc.perform(get("/api/inquiries/product-inquiries")
                .queryParam("page", "0")
                .queryParam("size", "5")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].inquiryNo").value(customerInquiryPasswordDto.getInquiryNo()))
            .andExpect(jsonPath("$.content[0].memberNickname").value(customerInquiryPasswordDto.getMemberNickname()))
            .andExpect(jsonPath("$.content[0].processStatus").value(customerInquiryPasswordDto.getProcessStatus()))
            .andExpect(jsonPath("$.content[0].title").value(customerInquiryPasswordDto.getTitle()))
            .andExpect(jsonPath("$.content[1].inquiryNo").value(customerInquiryBeautiful.getInquiryNo()))
            .andExpect(jsonPath("$.content[1].memberNickname").value(customerInquiryBeautiful.getMemberNickname()))
            .andExpect(jsonPath("$.content[1].processStatus").value(customerInquiryBeautiful.getProcessStatus()))
            .andExpect(jsonPath("$.content[1].title").value(customerInquiryBeautiful.getTitle()))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(5));

        verify(inquiryService).findInquiries(any(Pageable.class), eq(true));
    }

    @DisplayName("상품문의중 답변대기상태의 목록을 요청받았을시에 대기상태의 value값을 전달인자로 추가하여 서비스에 위임하며 PageResponse 객체를 body에 담아서 ResponseEntity를 반환한다. status : 200")
    @Test
    void productInquiryStatusHoldList() throws Exception {
        List<InquiryListResponseDto> list = new ArrayList<>();
        InquiryListResponseDto customerInquiryPasswordDto = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "memberNickname", "홍길동");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "processStatus", "답변대기");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "title", "비밀번호를 까먹었어요..");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "registerDatetime", LocalDateTime.now());

        InquiryListResponseDto customerInquiryBeautiful = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryBeautiful, "inquiryNo", 2);
        ReflectionTestUtils.setField(customerInquiryBeautiful, "memberNickname", "이순신");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "processStatus", "답변완료");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "title", "이 사이트는 왜이렇게 이쁘나요?");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "registerDatetime", LocalDateTime.now());

        list.add(customerInquiryPasswordDto);
        list.add(customerInquiryBeautiful);

        Page page = new PageImpl(list, PageRequest.of(0, 5), 10);
        given(inquiryService.findInquiriesByStatusCodeNo(any(Pageable.class), anyBoolean(), anyString()))
            .willReturn(page);

        mvc.perform(get("/api/inquiries/product-inquiries/status-hold")
                .queryParam("page", "0")
                .queryParam("size", "5")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].inquiryNo").value(customerInquiryPasswordDto.getInquiryNo()))
            .andExpect(jsonPath("$.content[0].memberNickname").value(customerInquiryPasswordDto.getMemberNickname()))
            .andExpect(jsonPath("$.content[0].processStatus").value(customerInquiryPasswordDto.getProcessStatus()))
            .andExpect(jsonPath("$.content[0].title").value(customerInquiryPasswordDto.getTitle()))
            .andExpect(jsonPath("$.content[1].inquiryNo").value(customerInquiryBeautiful.getInquiryNo()))
            .andExpect(jsonPath("$.content[1].memberNickname").value(customerInquiryBeautiful.getMemberNickname()))
            .andExpect(jsonPath("$.content[1].processStatus").value(customerInquiryBeautiful.getProcessStatus()))
            .andExpect(jsonPath("$.content[1].title").value(customerInquiryBeautiful.getTitle()))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(5));

        verify(inquiryService).findInquiriesByStatusCodeNo(any(Pageable.class), eq(true),
            eq(ProcessStatus.WAITING.getValue()));
    }


    @DisplayName("상품문의중 특정회원의 목록을 요청받았을시에 memberNo 파라미터를 추가하여 서비스에 위임하며 PageResponse 객체를 body에 담아서 ResponseEntity를 반환한다. status : 200")
    @Test
    void customerInquiryMemberList() throws Exception {
        List<InquiryListResponseDto> list = new ArrayList<>();
        InquiryListResponseDto customerInquiryPasswordDto = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "memberNickname", "홍길동");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "processStatus", "답변대기");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "title", "비밀번호를 까먹었어요..");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "registerDatetime", LocalDateTime.now());

        InquiryListResponseDto customerInquiryBeautiful = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryBeautiful, "inquiryNo", 2);
        ReflectionTestUtils.setField(customerInquiryBeautiful, "memberNickname", "이순신");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "processStatus", "답변완료");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "title", "이 사이트는 왜이렇게 이쁘나요?");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "registerDatetime", LocalDateTime.now());

        list.add(customerInquiryPasswordDto);
        list.add(customerInquiryBeautiful);

        Page page = new PageImpl(list, PageRequest.of(0, 5), 10);
        given(inquiryService.findInquiriesByMemberNo(any(Pageable.class), anyBoolean(), anyInt()))
            .willReturn(page);

        mvc.perform(get("/api/inquiries/member/{memberNo}/product-inquiries", 1)
                .queryParam("page", "0")
                .queryParam("size", "5")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].inquiryNo").value(customerInquiryPasswordDto.getInquiryNo()))
            .andExpect(jsonPath("$.content[0].memberNickname").value(customerInquiryPasswordDto.getMemberNickname()))
            .andExpect(jsonPath("$.content[0].processStatus").value(customerInquiryPasswordDto.getProcessStatus()))
            .andExpect(jsonPath("$.content[0].title").value(customerInquiryPasswordDto.getTitle()))
            .andExpect(jsonPath("$.content[1].inquiryNo").value(customerInquiryBeautiful.getInquiryNo()))
            .andExpect(jsonPath("$.content[1].memberNickname").value(customerInquiryBeautiful.getMemberNickname()))
            .andExpect(jsonPath("$.content[1].processStatus").value(customerInquiryBeautiful.getProcessStatus()))
            .andExpect(jsonPath("$.content[1].title").value(customerInquiryBeautiful.getTitle()))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(5));

        verify(inquiryService).findInquiriesByMemberNo(any(Pageable.class), eq(Boolean.TRUE),
            eq(1));
    }


    @DisplayName("상품문의중 답변완료상태의 목록을 요청받았을시에 대기상태의 value값을 전달인자로 추가하여 서비스에 위임하며 PageResponse 객체를 body에 담아서 ResponseEntity를 반환한다. status : 200")
    @Test
    void productInquiryStatusCompleteList() throws Exception {
        List<InquiryListResponseDto> list = new ArrayList<>();
        InquiryListResponseDto customerInquiryPasswordDto = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "memberNickname", "홍길동");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "processStatus", "답변대기");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "title", "비밀번호를 까먹었어요..");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "registerDatetime", LocalDateTime.now());

        InquiryListResponseDto customerInquiryBeautiful = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryBeautiful, "inquiryNo", 2);
        ReflectionTestUtils.setField(customerInquiryBeautiful, "memberNickname", "이순신");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "processStatus", "답변완료");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "title", "이 사이트는 왜이렇게 이쁘나요?");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "registerDatetime", LocalDateTime.now());

        list.add(customerInquiryPasswordDto);
        list.add(customerInquiryBeautiful);

        Page page = new PageImpl(list, PageRequest.of(0, 5), 10);
        given(inquiryService.findInquiriesByStatusCodeNo(any(Pageable.class), anyBoolean(), anyString()))
            .willReturn(page);

        mvc.perform(get("/api/inquiries/product-inquiries/status-complete")
                .queryParam("page", "0")
                .queryParam("size", "5")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].inquiryNo").value(customerInquiryPasswordDto.getInquiryNo()))
            .andExpect(jsonPath("$.content[0].memberNickname").value(customerInquiryPasswordDto.getMemberNickname()))
            .andExpect(jsonPath("$.content[0].processStatus").value(customerInquiryPasswordDto.getProcessStatus()))
            .andExpect(jsonPath("$.content[0].title").value(customerInquiryPasswordDto.getTitle()))
            .andExpect(jsonPath("$.content[1].inquiryNo").value(customerInquiryBeautiful.getInquiryNo()))
            .andExpect(jsonPath("$.content[1].memberNickname").value(customerInquiryBeautiful.getMemberNickname()))
            .andExpect(jsonPath("$.content[1].processStatus").value(customerInquiryBeautiful.getProcessStatus()))
            .andExpect(jsonPath("$.content[1].title").value(customerInquiryBeautiful.getTitle()))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(5));

        verify(inquiryService).findInquiriesByStatusCodeNo(any(Pageable.class), eq(true),
            eq(ProcessStatus.COMPLETE.getValue()));
    }


    @DisplayName("상품문의중 특정상품의 목록을 요청받았을시에 productNo 파라미터를 추가하여 서비스에 위임하며 PageResponse 객체를 body에 담아서 ResponseEntity를 반환한다. status : 200")
    @Test
    void productInquiryProductList() throws Exception {
        List<InquiryListResponseDto> list = new ArrayList<>();
        InquiryListResponseDto customerInquiryPasswordDto = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "memberNickname", "홍길동");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "processStatus", "답변대기");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "title", "비밀번호를 까먹었어요..");
        ReflectionTestUtils.setField(customerInquiryPasswordDto, "registerDatetime", LocalDateTime.now());

        InquiryListResponseDto customerInquiryBeautiful = new InquiryListResponseDto();
        ReflectionTestUtils.setField(customerInquiryBeautiful, "inquiryNo", 2);
        ReflectionTestUtils.setField(customerInquiryBeautiful, "memberNickname", "이순신");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "processStatus", "답변완료");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "title", "이 사이트는 왜이렇게 이쁘나요?");
        ReflectionTestUtils.setField(customerInquiryBeautiful, "registerDatetime", LocalDateTime.now());

        list.add(customerInquiryPasswordDto);
        list.add(customerInquiryBeautiful);

        Page page = new PageImpl(list, PageRequest.of(0, 5), 10);
        given(inquiryService.findInquiriesByMemberNo(any(Pageable.class), anyBoolean(), anyInt()))
            .willReturn(page);

        mvc.perform(get("/api/inquiries/product/{productNo}", 1)
                .queryParam("page", "0")
                .queryParam("size", "5")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].inquiryNo").value(customerInquiryPasswordDto.getInquiryNo()))
            .andExpect(jsonPath("$.content[0].memberNickname").value(customerInquiryPasswordDto.getMemberNickname()))
            .andExpect(jsonPath("$.content[0].processStatus").value(customerInquiryPasswordDto.getProcessStatus()))
            .andExpect(jsonPath("$.content[0].title").value(customerInquiryPasswordDto.getTitle()))
            .andExpect(jsonPath("$.content[1].inquiryNo").value(customerInquiryBeautiful.getInquiryNo()))
            .andExpect(jsonPath("$.content[1].memberNickname").value(customerInquiryBeautiful.getMemberNickname()))
            .andExpect(jsonPath("$.content[1].processStatus").value(customerInquiryBeautiful.getProcessStatus()))
            .andExpect(jsonPath("$.content[1].title").value(customerInquiryBeautiful.getTitle()))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(5));

        verify(inquiryService).findInquiriesByMemberNo(any(Pageable.class), eq(true),
            eq(1));
    }
}