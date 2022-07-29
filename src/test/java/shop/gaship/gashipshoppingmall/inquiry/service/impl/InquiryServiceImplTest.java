package shop.gaship.gashipshoppingmall.inquiry.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.repository.InquiryRepository;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.product.dummy.InquiryDummy;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
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
    }

    @DisplayName("고객문의를 정해진 로직대로 저장요청한다. 상품문의이기때문에 ProductNotFoundException이 안난다.")
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

    @DisplayName("추가하는 경우에 isAddAnswer가 true이고 실제 답변이 등록되어 있지않을시(답변대기)에 답변을 추가하는 요청이 잘 이루어진다.")
    @Test
    void addOrModifyInquiryAnswer_add_success() {

    }
    @DisplayName("추가하는 경우에 isAddAnswer가 true이지만 실제 답변이 등록되어 있을시에(답변완료) AlreadyCompleteInquiryAnswerException이 발생한다.")
    @Test
    void addOrModifyInquiryAnswer_add_fail() {

    }
    @DisplayName("수정하는 경우에 isAddAnswer가 false이고 실제 답변이 등록되어 있을시에(답변완료) 답변을 수정하는 요청이 잘 이루어진다.")
    @Test
    void addOrModifyInquiryAnswer_modify_success() {

    }
    @DisplayName("수정하는 경우에 isAddAnswer가 false이지만 실제 답변이 등록되어 있지않을시에(답변대기) NoRegisteredAnswerException이 발생한다.")
    @Test
    void addOrModifyInquiryAnswer_modify_fail() {

    }

    @DisplayName("문의삭제 요청이 잘 이루어진다.")
    @Test
    void deleteInquiry() {
        assertThatNoException().isThrownBy(() -> inquiryService.deleteInquiry(1));
    }


    @DisplayName("답변삭제시 답변이 등록되어 있을시에(답변완료) 답변삭제 요청이 잘 이루어진다.")
    @Test
    void deleteAnswerInquiry_success() {

    }

    @DisplayName("답변삭제시 답변이 등록되되어 있지않을시에(답변대기) NoRegisteredAnswerException이 발생한다.")
    @Test
    void deleteAnswerInquiry_fail() {

    }
}