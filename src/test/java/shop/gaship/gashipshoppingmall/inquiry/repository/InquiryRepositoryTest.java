package shop.gaship.gashipshoppingmall.inquiry.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * repository test
 *
 * @author : 최겸준
 * @since 1.0
 */

@DataJpaTest
class InquiryRepositoryTest {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private StatusCode statusCodeHolder;

    private StatusCode statusCodeComplete;

    private Inquiry customerInquiry;

    private Inquiry productInquiry;

    private Member member;

    private Product product;


    @BeforeEach
    void setUp() {
        setStatusCode();
        setMember();
        setProduct();
        setInquiry();
    }

    private void setStatusCode() {
        statusCodeHolder = StatusCode.builder()
            .statusCodeName("답변대기")
            .priority(1)
            .groupCodeName("처리상태")
            .build();

        statusCodeComplete = StatusCode.builder()
            .statusCodeName("답변완료")
            .priority(2)
            .groupCodeName("처리상태")
            .build();

        testEntityManager.persist(statusCodeHolder);
        testEntityManager.persist(statusCodeComplete);
    }

    private void setMember() {
        member = MemberDummy.dummy();
        ReflectionTestUtils.setField(member, "memberNo", null);
        testEntityManager.persist(member.getMemberGrades().getRenewalPeriodStatusCode());
        testEntityManager.persist(member.getMemberStatusCodes());
        testEntityManager.persist(member.getMemberGrades());
        testEntityManager.persist(member);
    }

    private void setProduct() {
        product = ProductDummy.dummy();
        StatusCode deliveryType = new StatusCode("설치", 1, "배송 형태", "");
        StatusCode salesStatus = new StatusCode("판매중", 2, "판매 상태", "");
        ReflectionTestUtils.setField(product, "deliveryType", deliveryType);
        ReflectionTestUtils.setField(product, "salesStatus", salesStatus);
        testEntityManager.persist(product);
    }

    private void setInquiry() {
        customerInquiry = Inquiry.builder()
            .title("1번째 고객문의제목")
            .inquiryContent("1번째 고객문의내용")
            .processStatusCode(statusCodeHolder)
            .isProduct(false)
            .registerDatetime(LocalDateTime.now())
            .build();

        productInquiry = Inquiry.builder()
            .title("2번째 상품문의제목")
            .inquiryContent("2번째 상품문의내용")
            .processStatusCode(statusCodeHolder)
            .isProduct(true)
            .registerDatetime(LocalDateTime.now())
            .build();

        customerInquiry.addMember(member);
        productInquiry.addMember(member);
        productInquiry.addProduct(product);

        testEntityManager.persist(customerInquiry);
        testEntityManager.persist(productInquiry);
        testEntityManager.clear();
    }

    @DisplayName("기존 등록된 번호에 맞게 고객문의를 잘 찾아온다.")
    @Test
    void findById_customer() {
        Inquiry inquiry = inquiryRepository.findById(customerInquiry.getInquiryNo()).orElseThrow();

        assertThat(inquiry)
            .isNotNull();
        assertThat(inquiry.getInquiryNo())
            .isEqualTo(customerInquiry.getInquiryNo());
        assertThat(inquiry.getInquiryContent())
            .isEqualTo(customerInquiry.getInquiryContent());
        assertThat(inquiry.getProcessStatusCode())
            .isEqualTo(customerInquiry.getProcessStatusCode());
    }

    @DisplayName("기존 등록된 번호에 맞게 상품문의를 잘 찾아온다.")
    @Test
    void findById_product() {
        Inquiry inquiry = inquiryRepository.findById(productInquiry.getInquiryNo()).orElseThrow();

        assertThat(inquiry)
            .isNotNull();
        assertThat(inquiry.getInquiryNo())
            .isEqualTo(productInquiry.getInquiryNo());
        assertThat(inquiry.getInquiryContent())
            .isEqualTo(productInquiry.getInquiryContent());
        assertThat(inquiry.getProcessStatusCode())
            .isEqualTo(productInquiry.getProcessStatusCode());
        assertThat(inquiry.getProduct().getNo())
            .isEqualTo(productInquiry.getProduct().getNo());
    }

    @DisplayName("모든 문의를 잘 찾아온다.")
    @Test
    void findAll() {
        List<Inquiry> inquiryList = inquiryRepository.findAll();

        Inquiry[] codes = {customerInquiry, productInquiry};

        for (int i = 0; i < codes.length; i++) {
            assertThat(inquiryList.get(i))
                .isNotNull();
            assertThat(inquiryList.get(i).getInquiryNo())
                .isEqualTo(codes[i].getInquiryNo());
            assertThat(inquiryList.get(i).getInquiryContent())
                .isEqualTo(codes[i].getInquiryContent());
            assertThat(inquiryList.get(i).getProcessStatusCode())
                .isEqualTo(codes[i].getProcessStatusCode());
        }
    }

    @DisplayName("고객문의가 원하는대로 저장된다.")
    @Test
    void save() {

        Inquiry inquiry = Inquiry.builder()
            .title("1번째 고객문의제목")
            .inquiryContent("1번째 고객문의내용")
            .processStatusCode(statusCodeHolder)
            .isProduct(false)
            .registerDatetime(LocalDateTime.now())
            .build();

        inquiry.addMember(member);

        Inquiry result = inquiryRepository.save(inquiry);

        assertThat(result)
            .isNotNull();
        assertThat(result.getInquiryNo())
            .isEqualTo(inquiry.getInquiryNo());
        assertThat(result.getInquiryContent())
            .isEqualTo(inquiry.getInquiryContent());
        assertThat(result.getProcessStatusCode())
            .isEqualTo(inquiry.getProcessStatusCode());
    }

    @DisplayName("상품문의가 원하는대로 저장된다.")
    @Test
    void save_product() {

        Inquiry inquiry = Inquiry.builder()
            .title("1번째 고객문의제목")
            .inquiryContent("1번째 고객문의내용")
            .processStatusCode(statusCodeHolder)
            .isProduct(true)
            .registerDatetime(LocalDateTime.now())
            .build();

        inquiry.addMember(member);
        inquiry.addProduct(product);

        Inquiry result = inquiryRepository.save(inquiry);

        assertThat(result)
            .isNotNull();
        assertThat(result.getInquiryNo())
            .isEqualTo(inquiry.getInquiryNo());
        assertThat(result.getInquiryContent())
            .isEqualTo(inquiry.getInquiryContent());
        assertThat(result.getProcessStatusCode())
            .isEqualTo(inquiry.getProcessStatusCode());
        assertThat(result.getProduct().getNo())
            .isEqualTo(inquiry.getProduct().getNo());
    }

    @DisplayName("제목과 처리상태(답변대기, 답변완료)처럼 내부 값이 잘 변경된다.")
    @Test
    void update() {
        Inquiry result = inquiryRepository.findById(customerInquiry.getInquiryNo()).orElseThrow();

        assertThat(result.getTitle())
            .isEqualTo(customerInquiry.getTitle());

        assertThat(result.getProcessStatusCode())
            .isEqualTo(statusCodeHolder);

        result.setTitle("변경된제목");
        result.setProcessStatusCode(statusCodeComplete);

        Inquiry test = inquiryRepository.findById(customerInquiry.getInquiryNo()).orElseThrow();
        assertThat(test.getTitle())
            .isEqualTo("변경된제목");

        assertThat(test.getProcessStatusCode())
            .isEqualTo(statusCodeComplete);
    }

    @DisplayName("저장된 문의가 잘 삭제된다.")
    @Test
    void delete() {
        Inquiry result = inquiryRepository.findById(customerInquiry.getInquiryNo()).orElseThrow();

        assertThat(result)
            .isNotNull();

        inquiryRepository.deleteById(result.getInquiryNo());

        Optional<Inquiry> byId = inquiryRepository.findById(customerInquiry.getInquiryNo());
        assertThatThrownBy(() -> {
            byId.orElseThrow(
                InquiryNotFoundException::new);
        })
            .isInstanceOf(InquiryNotFoundException.class)
                .hasMessageContaining(InquiryNotFoundException.MESSAGE);
    }
}