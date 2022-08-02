package shop.gaship.gashipshoppingmall.inquiry.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.employee.dummy.EmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.AlreadyCompleteInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentEmployeeWriterAboutInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.NoRegisteredAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.repository.InquiryRepository;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.inquiry.dummy.InquiryDummy;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.ProcessStatus;

/**
 * InquiryServiceImpl test
 *
 * @author : 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(InquiryServiceImpl.class)
class InquiryServiceImplTest {

    @Autowired
    private InquiryService inquiryService;

    @MockBean
    private InquiryRepository inquiryRepository;

    @MockBean
    private StatusCodeRepository statusCodeRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    private InquiryAddRequestDto inquiryAddRequestDtoWhenCustomer;

    private InquiryAddRequestDto inquiryAddRequestDtoWhenProduct;

    private InquiryAnswerRequestDto inquiryAnswerRequestDto;

    @BeforeEach
    void setUp() {
        inquiryAddRequestDtoWhenCustomer
            = new InquiryAddRequestDto();

        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "memberNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "title", "첫번재 고객문의 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "inquiryContent","첫번째 고객문의 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenCustomer, "isProduct", Boolean.FALSE);

        inquiryAddRequestDtoWhenProduct
            = new InquiryAddRequestDto();

        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "memberNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "productNo", 1);
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "title", "두번재 상품문의 제목");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "inquiryContent","두번째 고객문의 내용");
        ReflectionTestUtils.setField(inquiryAddRequestDtoWhenProduct, "isProduct", Boolean.TRUE);

        inquiryAnswerRequestDto
            = new InquiryAnswerRequestDto();

        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "첫번째 답변입니다.");
    }

    @DisplayName("고객문의를 정해진 로직대로 저장요청한다.")
    @Test
    void addInquiry_customer() {
        // given
        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(MemberDummy.dummy()));

        // when then
        assertThatNoException().isThrownBy(() -> inquiryService.addInquiry(
            inquiryAddRequestDtoWhenCustomer));
        verify(inquiryRepository).save(any(Inquiry.class));
    }

    @DisplayName("상품문의를 정해진 로직대로 저장요청한다.")
    @Test
    void addInquiry_product() {
        // given
        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(MemberDummy.dummy()));

        given(productRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(ProductDummy.dummy()));
        // when then
        assertThatNoException().isThrownBy(() -> inquiryService.addInquiry(
            inquiryAddRequestDtoWhenProduct));
        verify(inquiryRepository).save(any(Inquiry.class));
    }

    @DisplayName("존재하지 않는 멤버로 문의등록했을때 MemberNotFoundException이 발생한다.")
    @Test
    void addInquiry_MemberNotFoundException() {
        // given
        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> inquiryService.addInquiry(inquiryAddRequestDtoWhenProduct))
            .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining(MemberNotFoundException.MESSAGE);
    }

    @DisplayName("존재하지 않는 상품의 상품문의가 왔을때 ProductNotFoundException이 발생한다.")
    @Test
    void addInquiry_ProductNotFoundException() {
        // given
        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(MemberDummy.dummy()));

        given(productRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> inquiryService.addInquiry(inquiryAddRequestDtoWhenProduct))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessageContaining(ProductNotFoundException.MESSAGE);
    }

    @DisplayName("답변을 등록하는 경우에 실제 답변이 등록되어 있지않았고(답변대기) 다른 전제조건들이 문제없이 주어졌을때 답변을 추가하는 요청이 잘 이루어진다.")
    @Test
    void addInquiryAnswer_success() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(employeeRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(EmployeeDummy.dummy()));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        // when then
        assertThatNoException().isThrownBy(() -> inquiryService.addInquiryAnswer(inquiryAnswerRequestDto));
    }

    @DisplayName("답변을 등록하고 실제 답변이 등록되어 있지않았지만(답변대기) 문의번호가 잘못온경우 InquiryNotFoundException이 발생한다.")
    @Test
    void addInquiryAnswer_fail_InquiryNotFoundException() {
        // given
        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> inquiryService.addInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(InquiryNotFoundException.class)
            .hasMessageContaining(InquiryNotFoundException.MESSAGE);
    }

    @DisplayName("답변을 등록하는 경우지만 실제 답변이 등록되어 있을시(답변완료)에 AlreadyCompleteInquiryAnswerException이 발생한다.")
    @Test
    void addInquiryAnswer_fail_AlreadyCompleteInquiryAnswerException() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeCompleteDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(employeeRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(EmployeeDummy.dummy()));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));


        // when then
        assertThatThrownBy(() -> inquiryService.addInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(AlreadyCompleteInquiryAnswerException.class)
            .hasMessageContaining(AlreadyCompleteInquiryAnswerException.MESSAGE);
    }

    @DisplayName("답변을 등록하고 실제 답변이 등록되어 있지않았지만(답변대기) 직원번호가 잘못온경우 EmployeeNotFoundException이 발생한다.")
    @Test
    void addInquiryAnswer_fail_EmployeeNotFoundException() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(employeeRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> inquiryService.addInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(EmployeeNotFoundException.class)
            .hasMessageContaining(EmployeeNotFoundException.MESSAGE);
    }

    @DisplayName("답변을 등록하고 실제 답변이 등록되어 있고(답변대기) 직원번호가 잘왔지만 상태코드를 못찾은 경우 StatusCodeNotFoundException이 발생한다.")
    @Test
    void addInquiryAnswer_fail_StatusCodeNotFoundException이() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(employeeRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(EmployeeDummy.dummy()));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> inquiryService.addInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(StatusCodeNotFoundException.class)
            .hasMessageContaining(StatusCodeNotFoundException.MESSAGE);
    }

    @DisplayName("답변을 수정할때 실제 답변이 등록되어 있고(답변완료) 다른 조건들도 다 충족되었을시에 답변 수정이 잘 된다.")
    @Test
    void modifyInquiryAnswer_success() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeCompleteDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        Employee employee = EmployeeDummy.dummy();
        ReflectionTestUtils.setField(employee, "employeeNo", 1);
        inquiry.setEmployee(employee);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        // when then
        assertThat(inquiry.getAnswerContent())
            .isNull();

        assertThatNoException().isThrownBy(() -> inquiryService.modifyInquiryAnswer(inquiryAnswerRequestDto));

        assertThat(inquiry.getAnswerContent())
            .isEqualTo("첫번째 답변입니다.");
    }

    @DisplayName("답변을 수정하는 경우에 요청값으로 문의번호가 존재하지 않는 문의번호를 보냈을때 InquiryNotFoundException이 발생한다.")
    @Test
    void modifyInquiryAnswer_fail_InquiryNotFoundException() {
        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> inquiryService.modifyInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(InquiryNotFoundException.class)
            .hasMessageContaining(InquiryNotFoundException.MESSAGE);
    }

    @DisplayName("답변을 수정하는 경우에 실제 답변이 등록되어 있지않을시에(답변대기) NoRegisteredAnswerException이 발생한다.")
    @Test
    void modifyInquiryAnswer_fail_NoRegisteredAnswerException() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        Employee employee = EmployeeDummy.dummy();
        ReflectionTestUtils.setField(employee, "employeeNo", 1);
        inquiry.setEmployee(employee);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        // when then
        assertThatThrownBy(() -> inquiryService.modifyInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(NoRegisteredAnswerException.class)
                .hasMessageContaining(NoRegisteredAnswerException.MESSAGE);
    }

    @DisplayName("답변을 수정하는 경우에 실제 답변이 등록되어 있지만(답변완료) 실제 직원 작성자와 번호가 다른 직원으로 요청이 왔다면 보안방어를 위해 DifferentEmployeeWriterAboutInquiryAnswerException이 발생한다.")
    @Test
    void modifyInquiryAnswer_fail_DifferentEmployeeWriterAboutInquiryAnswerException() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        Employee employee = EmployeeDummy.dummy();
        ReflectionTestUtils.setField(employee, "employeeNo", 252123);
        inquiry.setEmployee(employee);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        // when then
        assertThatThrownBy(() -> inquiryService.modifyInquiryAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(DifferentEmployeeWriterAboutInquiryAnswerException.class)
            .hasMessageContaining(DifferentEmployeeWriterAboutInquiryAnswerException.MESSAGE);
    }

    @DisplayName("문의삭제 요청이 잘 이루어진다.")
    @Test
    void deleteInquiry() {
        assertThatNoException().isThrownBy(() -> inquiryService.deleteInquiry(1));
    }

    @DisplayName("답변삭제시 답변이 등록되어 있을시에(답변완료) 답변삭제 요청이 잘 이루어진다.")
    @Test
    void deleteAnswerInquiry_success() {
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeCompleteDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        assertThat(inquiry.getProcessStatusCode())
            .isEqualTo(InquiryDummy.statusCodeCompleteDummy());

        assertThatNoException().isThrownBy(() -> inquiryService.deleteInquiryAnswer(1));

        assertThat(inquiry.getProcessStatusCode())
            .isEqualTo(InquiryDummy.statusCodeHolderDummy());

        assertThat(inquiry.getAnswerContent())
            .isEmpty();
        assertThat(inquiry.getEmployee())
            .isNull();
        assertThat(inquiry.getAnswerModifyDatetime())
            .isNull();
        assertThat(inquiry.getAnswerRegisterDatetime())
            .isNull();
    }

    @DisplayName("답변삭제시 답변이 등록되되어 있지않을시에(답변대기) NoRegisteredAnswerException이 발생한다.")
    @Test
    void deleteAnswerInquiry_fail_NoRegisteredAnswerException() {
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.ofNullable(InquiryDummy.statusCodeHolderDummy()));

        assertThatThrownBy(() -> inquiryService.deleteInquiryAnswer(1))
            .isInstanceOf(NoRegisteredAnswerException.class)
            .hasMessageContaining(NoRegisteredAnswerException.MESSAGE);
    }

    @DisplayName("답변삭제시 잘못된 문의 번호가 요청되었을시에 InquiryNotFoundException이 발생한다.")
    @Test
    void deleteAnswerInquiry_fail_InquiryNotFoundException() {
        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> inquiryService.deleteInquiryAnswer(1))
            .isInstanceOf(InquiryNotFoundException.class)
            .hasMessageContaining(InquiryNotFoundException.MESSAGE);
    }

    @DisplayName("답변삭제시 잘못된 상태코드가 요청되었을시에 StatusCodeNotFoundException이 발생한다.")
    @Test
    void deleteAnswerInquiry_fail_StatusCodeNotFoundException() {
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());

        given(inquiryRepository.findById(anyInt()))
            .willReturn(Optional.ofNullable(inquiry));

        given(statusCodeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> inquiryService.deleteInquiryAnswer(1))
            .isInstanceOf(StatusCodeNotFoundException.class)
            .hasMessageContaining(StatusCodeNotFoundException.MESSAGE);
    }

    @DisplayName("아무조건없는 문의 리스트를 repository에 요청하고 Page 객체를 반환한다.")
    @Test
    void findCustomerInquiries() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);
        given(inquiryRepository.findAllThroughSearchDto(any(PageRequest.class), any(InquirySearchRequestDto.class)))
            .willReturn(page);

        // when then
        assertThat(inquiryService.findInquiries(pageRequest, Boolean.TRUE))
            .isEqualTo(page);
    }

    @DisplayName("요청되는 상태명에 맞게 고객문의 및 상품문의 리스트를 repository에 요청하고 Page 객체를 반환한다.")
    @Test
    void findCustomerInquiriesByStatusCodeNo() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);

        StatusCode statusCode = mock(StatusCode.class);
        when(statusCode.getStatusCodeNo())
            .thenReturn(1);

        given(statusCodeRepository.findByStatusCodeName(ProcessStatus.WAITING.getValue()))
            .willReturn(Optional.ofNullable(statusCode));
        given(inquiryRepository.findAllThroughSearchDto(any(PageRequest.class), any(InquirySearchRequestDto.class)))
            .willReturn(page);

        // when then
        assertThat(inquiryService.findInquiriesByStatusCodeNo(pageRequest, Boolean.TRUE, ProcessStatus.WAITING.getValue()))
            .isEqualTo(page);
    }

    @DisplayName("요청되는 상태명이 잘못들어온경우 StatusCodeNotFoundException 을 발생시킨다.")
    @Test
    void findCustomerInquiriesByStatusCodeNo_fail() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);

        String value = ProcessStatus.WAITING.getValue();
        given(statusCodeRepository.findByStatusCodeName(value))
            .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(
            () -> inquiryService.findInquiriesByStatusCodeNo(
                pageRequest, Boolean.TRUE, value))
            .isInstanceOf(StatusCodeNotFoundException.class)
                .hasMessageContaining(StatusCodeNotFoundException.MESSAGE);
    }

    @DisplayName("존재하는 특정 회원번호를 받아서 상품 또는 고객문의 리스트를 repository에 요청하고 Page 객체를 반환한다.")
    @Test
    void findCustomerInquiriesByMemberNo() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);

        given(memberRepository.existsById(anyInt()))
            .willReturn(Boolean.TRUE);
        given(inquiryRepository.findAllThroughSearchDto(any(PageRequest.class), any(InquirySearchRequestDto.class)))
            .willReturn(page);

        // when then
        assertThat(inquiryService.findInquiriesByMemberNo(pageRequest, Boolean.TRUE, 1))
            .isEqualTo(page);
    }

    @DisplayName("존재하지 않는 특정 회원번호를 받으면 MemberNotFoundException 를 발생시킨다.")
    @Test
    void findCustomerInquiriesByMemberNo_fail() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);

        given(memberRepository.existsById(anyInt()))
            .willReturn(Boolean.FALSE);

        // when then
        assertThatThrownBy(
            () -> inquiryService.findInquiriesByMemberNo(pageRequest, Boolean.TRUE, 1))
            .isInstanceOf(MemberNotFoundException.class)
            .hasMessageContaining(MemberNotFoundException.MESSAGE);
    }

    @DisplayName("존재하는 특정 상품번호를 받아서 상품문의 리스트를 repository에 요청하고 Page 객체를 반환한다.")
    @Test
    void findCustomerInquiriesByProductNo() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);

        given(productRepository.existsById(anyInt()))
            .willReturn(Boolean.TRUE);
        given(inquiryRepository.findAllThroughSearchDto(any(PageRequest.class), any(InquirySearchRequestDto.class)))
            .willReturn(page);

        // when then
        assertThat(inquiryService.findInquiriesByProductNo(pageRequest, 1))
            .isEqualTo(page);
    }

    @DisplayName("존재하지 않는 상품번호로 요청을 할시에는 ProductNotFoundException 을 발생시킨다.")
    @Test
    void findCustomerInquiriesByProductNo_fail() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));
        Page<InquiryListResponseDto> page = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 10);

        given(productRepository.existsById(anyInt()))
            .willReturn(Boolean.FALSE);

        // when then
        assertThatThrownBy(
            () -> inquiryService.findInquiriesByProductNo(pageRequest, 1))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessageContaining(ProductNotFoundException.MESSAGE);
    }
}