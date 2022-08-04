package shop.gaship.gashipshoppingmall.inquiry.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.employee.dummy.EmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dummy.InquiryDummy;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentInquiryException;
import shop.gaship.gashipshoppingmall.inquiry.repository.InquiryRepository;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * @author : 최겸준
 * @since 1.0
 */
@DataJpaTest
class InquiryTest {

    @DisplayName("답변등록시 요청한 문의번호와 등록하려는 엔티티의 문의번호가 다를경우 DifferentInquiryException 이 발생한다.")
    @Test
    void addAnswer_fail_DifferentInquiryException() {
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo", 2);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "4번문의 답변입니다.");

        Employee employeeDummy = EmployeeDummy.dummy();
        StatusCode statusCode = InquiryDummy.statusCodeCompleteDummy();
        assertThatThrownBy(() -> inquiry.addAnswer(inquiryAnswerRequestDto, employeeDummy,
            statusCode))
            .isInstanceOf(DifferentInquiryException.class)
            .hasMessageContaining(DifferentInquiryException.MESSAGE);
    }

    @DisplayName("답변수정시 요청한 문의번호와 수정하려는 엔티티의 문의번호가 다를경우 DifferentInquiryException 이 발생한다.")
    @Test
    void modifyAnswer_fail_DifferentInquiryException() {
        Inquiry inquiry = InquiryDummy.customerDummy(InquiryDummy.statusCodeHolderDummy());
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "4번문의 답변입니다.");

        inquiry.addAnswer(inquiryAnswerRequestDto, EmployeeDummy.dummy(), InquiryDummy.statusCodeCompleteDummy());

        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo", 223);
        assertThatThrownBy(() -> inquiry.modifyAnswer(inquiryAnswerRequestDto))
            .isInstanceOf(DifferentInquiryException.class)
            .hasMessageContaining(DifferentInquiryException.MESSAGE);
    }


    @DisplayName("답변삭제시 요청한 문의번호와 삭제하려는 엔티티의 문의번호가 다를경우 DifferentInquiryException 이 발생한다.")
    @Test
    void deleteAnswer_fail_DifferentInquiryException() {
        StatusCode processStatusCode = InquiryDummy.statusCodeCompleteDummy();
        Inquiry inquiry = InquiryDummy.customerDummy(processStatusCode);
        ReflectionTestUtils.setField(inquiry, "inquiryNo", 1);

        assertThatThrownBy(() -> inquiry.deleteAnswer(processStatusCode, 20))
            .isInstanceOf(DifferentInquiryException.class)
            .hasMessageContaining(DifferentInquiryException.MESSAGE);
    }
}