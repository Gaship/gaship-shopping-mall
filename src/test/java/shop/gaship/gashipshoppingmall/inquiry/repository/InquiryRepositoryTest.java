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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.employee.dummy.EmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.search.InquiryListSearch;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.dummy.InquiryDummy;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquirySearchBadRequestException;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
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
        statusCodeHolder = InquiryDummy.statusCodeHolderDummy();
        statusCodeComplete = InquiryDummy.statusCodeCompleteDummy();

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
        customerInquiry = InquiryDummy.customerDummy(statusCodeHolder);
        productInquiry = InquiryDummy.productDummy(statusCodeHolder);

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

        ReflectionTestUtils.setField(result, "title", "변경된제목");
        ReflectionTestUtils.setField(result, "processStatusCode", statusCodeComplete);

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

    @DisplayName("고객문의리스트 조건없는 전체조회에 대해서 List<InquiryResponseDto>를(내부값 3개, 답변대기 2, 답변완료 1) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_customer() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(statusCodeHolder);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(false, null, null, null);

        Inquiry inquiry2 = InquiryDummy.customerDummy(statusCodeHolder);
        inquiry2.addMember(member);
        inquiryRepository.save(inquiry2);

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo", inquiry2.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "4번문의 답변입니다.");

        Employee employee = EmployeeDummy.dummy();

        AddressLocal addressLocal = AddressLocalDummy.dummy1();

        StatusCode code = StatusCodeDummy.dummy();

        DayLabor labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);

        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        testEntityManager.persist(labor);
        testEntityManager.persist(addressLocal);
        testEntityManager.persist(code);
        testEntityManager.persist(employee);

        inquiry2.addAnswer(inquiryAnswerRequestDto, employee, statusCodeComplete);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(3);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content).hasSize(3);
        assertThat(content.get(0).getMemberNickname())
            .isEqualTo("examplenickname");
        assertThat(content.get(0).getProcessStatus())
            .isEqualTo("답변완료");

        assertThat(content.get(0).getTitle())
            .isEqualTo("1번째 고객문의제목");

        for (int i = 1; i < 3; i++) {
            assertThat(content.get(i).getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(content.get(i).getProcessStatus())
                .isEqualTo("답변대기");
            assertThat(content.get(i).getTitle())
                .isEqualTo("1번째 고객문의제목");
        }
    }

    @DisplayName("고객문의리스트 답변완료조회에 대해서 List<InquiryResponseDto>를(내부값 2개) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_customer_complete() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(statusCodeHolder);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo",
            customerInquiry.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "1번문의 답변입니다.");

        InquiryAnswerRequestDto inquiryAnswerRequestDto2 = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto2, "inquiryNo", inquiry.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto2, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto2, "answerContent", "1번문의 답변입니다.");

        Employee employee = EmployeeDummy.dummy();

        AddressLocal addressLocal = AddressLocalDummy.dummy1();

        StatusCode code = StatusCodeDummy.dummy();

        DayLabor labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);

        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        testEntityManager.persist(labor);
        testEntityManager.persist(addressLocal);
        testEntityManager.persist(code);
        testEntityManager.persist(employee);

        customerInquiry = inquiryRepository.findById(customerInquiry.getInquiryNo()).orElseThrow();
        customerInquiry.addAnswer(inquiryAnswerRequestDto, employee, statusCodeComplete);
        inquiry.addAnswer(inquiryAnswerRequestDto2, employee, statusCodeComplete);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch inquirySearchRequestDto =
            getInquirySearchRequestDto(false, statusCodeComplete.getStatusCodeNo(), null, null);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(2);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(2);

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getProcessStatus())
                .isEqualTo("답변완료");
            assertThat(inquiryDto.getTitle())
                .isEqualTo("1번째 고객문의제목");
        });
    }

    @DisplayName("고객문의리스트 답변대기조회에 대해서 List<InquiryResponseDto>를(내부값 3개) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_customer_holder() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(statusCodeHolder);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        Inquiry inquiry2 = InquiryDummy.customerDummy(statusCodeHolder);
        inquiry2.addMember(member);
        inquiryRepository.save(inquiry2);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(false, statusCodeHolder.getStatusCodeNo(), null, null);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(3);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(3);

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getProcessStatus())
                .isEqualTo("답변대기");

            assertThat(inquiryDto.getTitle())
                .isEqualTo("1번째 고객문의제목");
        });
    }

    @DisplayName("고객문의리스트 특정회원에 대해서 List<InquiryResponseDto>를(내부값 2개, 답변대기, 답변완료) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 2, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_customer_member() {
        // given
        Inquiry inquiry = InquiryDummy.customerDummy(statusCodeComplete);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(false,
                null, member.getMemberNo(), null);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(2);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(2);


        assertThat(content.get(0).getProcessStatus())
            .isEqualTo("답변완료");
        assertThat(content.get(1).getProcessStatus())
            .isEqualTo("답변대기");

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getTitle())
                .isEqualTo("1번째 고객문의제목");
        });
    }

    @DisplayName("상품문의리스트 조건없는 전체조회에 대해서 List<InquiryResponseDto>를(내부값 3개, 답변대기 2, 답변완료 1) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_product() {
        // given
        Inquiry inquiry = InquiryDummy.productDummy(statusCodeHolder);
        inquiry.addMember(member);
        inquiry.addProduct(product);
        inquiryRepository.save(inquiry);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(true, null, null, null);

        Inquiry inquiry2 = InquiryDummy.productDummy(statusCodeHolder);
        inquiry2.addMember(member);
        inquiry2.addProduct(product);
        inquiryRepository.save(inquiry2);

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo", inquiry2.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "4번문의 답변입니다.");

        Employee employee = EmployeeDummy.dummy();

        AddressLocal addressLocal = AddressLocalDummy.dummy1();

        StatusCode code = StatusCodeDummy.dummy();

        DayLabor labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);

        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        testEntityManager.persist(labor);
        testEntityManager.persist(addressLocal);
        testEntityManager.persist(code);
        testEntityManager.persist(employee);

        inquiry2.addAnswer(inquiryAnswerRequestDto, employee, statusCodeComplete);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(3);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(3);
        assertThat(content.get(0).getMemberNickname())
            .isEqualTo("examplenickname");
        assertThat(content.get(0).getProcessStatus())
            .isEqualTo("답변완료");
        assertThat(content.get(0).getTitle())
            .isEqualTo("2번째 상품문의제목");

        for (int i = 1; i < 3; i++) {
            assertThat(content.get(i).getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(content.get(i).getProcessStatus())
                .isEqualTo("답변대기");
            assertThat(content.get(i).getTitle())
                .isEqualTo("2번째 상품문의제목");
        }
    }

    @DisplayName("상품문의리스트 답변완료조회에 대해서 List<InquiryResponseDto>를(내부값 2개) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_product_complete() {
        // given
        Inquiry inquiry = InquiryDummy.productDummy(statusCodeHolder);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo",
            productInquiry.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "1번문의 답변입니다.");

        InquiryAnswerRequestDto inquiryAnswerRequestDto2 = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto2, "inquiryNo", inquiry.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto2, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto2, "answerContent", "1번문의 답변입니다.");

        Employee employee = EmployeeDummy.dummy();

        AddressLocal addressLocal = AddressLocalDummy.dummy1();

        StatusCode code = StatusCodeDummy.dummy();

        DayLabor labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);

        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        testEntityManager.persist(labor);
        testEntityManager.persist(addressLocal);
        testEntityManager.persist(code);
        testEntityManager.persist(employee);

        productInquiry = inquiryRepository.findById(productInquiry.getInquiryNo()).orElseThrow();
        productInquiry.addAnswer(inquiryAnswerRequestDto, employee, statusCodeComplete);
        inquiry.addAnswer(inquiryAnswerRequestDto2, employee, statusCodeComplete);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch inquirySearchRequestDto =
            getInquirySearchRequestDto(true, statusCodeComplete.getStatusCodeNo(), null, null);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(2);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(2);

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getProcessStatus())
                .isEqualTo("답변완료");
            assertThat(inquiryDto.getTitle())
                .isEqualTo("2번째 상품문의제목");
        });
    }

    @DisplayName("상품문의리스트 답변대기조회에 대해서 List<InquiryResponseDto>를(내부값 3개) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_product_holder() {
        // given
        Inquiry inquiry = InquiryDummy.productDummy(statusCodeHolder);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        Inquiry inquiry2 = InquiryDummy.productDummy(statusCodeHolder);
        inquiry2.addMember(member);
        inquiryRepository.save(inquiry2);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(true, statusCodeHolder.getStatusCodeNo(), null, null);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(3);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(3);

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getProcessStatus())
                .isEqualTo("답변대기");

            assertThat(inquiryDto.getTitle())
                .isEqualTo("2번째 상품문의제목");
        });
    }

    @DisplayName("상품문의리스트 특정회원에 대해서 List<InquiryResponseDto>를(내부값 2개, 답변대기, 답변완료) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 2, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_product_member() {
        // given
        Inquiry inquiry = InquiryDummy.productDummy(statusCodeComplete);
        inquiry.addMember(member);
        inquiryRepository.save(inquiry);

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(true,
                null, member.getMemberNo(), null);

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(2);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(2);


        assertThat(content.get(0).getProcessStatus())
            .isEqualTo("답변완료");
        assertThat(content.get(1).getProcessStatus())
            .isEqualTo("답변대기");

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getTitle())
                .isEqualTo("2번째 상품문의제목");
        });
    }

    @DisplayName("상품문의리스트 특정상품에 대해서 List<InquiryResponseDto>를(내부값 2개) content로 가지는 PageImpl 객체가 잘 넘어온다.(limit 3, offset 0, 최신순)")
    @Test
    void findAllThroughSearch_product_specificProduct() {
        // given
        Inquiry inquiry = InquiryDummy.productDummy(statusCodeComplete);
        inquiry.addMember(member);
        inquiry.addProduct(product);
        inquiryRepository.save(inquiry);

        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(true,
                null, null, product.getNo());

        // when
        Page<InquiryListResponseDto> page =
            inquiryRepository.findAllThroughSearchDto(pageable, dto);
        List<InquiryListResponseDto> content = page.getContent();

        long totalElement = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        // then
        assertThat(totalElement)
            .isEqualTo(2);
        assertThat(totalPages)
            .isEqualTo(1);
        assertThat(pageNumber)
            .isEqualTo(pageable.getPageNumber());
        assertThat(pageSize)
            .isEqualTo(pageable.getPageSize());

        assertThat(content)
            .hasSize(2);


        assertThat(content.get(0).getProcessStatus())
            .isEqualTo("답변완료");
        assertThat(content.get(1).getProcessStatus())
            .isEqualTo("답변대기");

        content.stream().forEach(inquiryDto -> {
            assertThat(inquiryDto.getMemberNickname())
                .isEqualTo("examplenickname");
            assertThat(inquiryDto.getTitle())
                .isEqualTo("2번째 상품문의제목");
        });
    }

    @DisplayName("고객문의이면서 상품번호가 있도록 dto가 메서드에 전달된경우 InquiryNotFoundException() 예외를 발생시킨다.")
    @Test
    void findAllThroughSearch_customerAndProduct_fail() {
        // given
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "inquiryNo"));

        InquiryListSearch dto =
            getInquirySearchRequestDto(false,
                null, null, product.getNo());

        // when then
        assertThatThrownBy(() -> inquiryRepository.findAllThroughSearchDto(pageable, dto))
            .isInstanceOf(InquirySearchBadRequestException.class)
            .hasMessageContaining(InquirySearchBadRequestException.MESSAGE);
    }

    private InquiryListSearch getInquirySearchRequestDto(boolean isProduct,
                                                         Integer statusCodeNo,
                                                         Integer memberNo,
                                                         Integer productNo) {
        InquiryListSearch dto =
            new InquiryListSearch(isProduct, statusCodeNo, memberNo, productNo);
        return dto;
    }

    @DisplayName("등록된 답변전 문의를 조회하면 연관된 member와 status 값들이 모두 들어온다.")
    @Test
    void findDetailsById_customer_holder() {
        InquiryDetailsResponseDto
            inquiry =
            inquiryRepository.findDetailsById(customerInquiry.getInquiryNo()).orElseThrow();

        assertThat(inquiry.getInquiryContent())
            .isEqualTo("1번째 고객문의내용");
        assertThat(inquiry.getTitle())
            .isEqualTo("1번째 고객문의제목");
        assertThat(inquiry.getMemberNickname())
            .isEqualTo(member.getNickname());
        assertThat(inquiry.getProcessStatus())
            .isEqualTo(statusCodeHolder.getStatusCodeName());
    }

    @DisplayName("등록된 답변후 상품문의를 조회하면 연관된 member와 status, employee, product 값들이 모두 들어온다.")
    @Test
    void findDetailsById_product_complete() {
        Inquiry inquiryBasic =
            inquiryRepository.findById(productInquiry.getInquiryNo()).orElseThrow();

        InquiryAnswerRequestDto inquiryAnswerRequestDto = new InquiryAnswerRequestDto();
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "inquiryNo",
            productInquiry.getInquiryNo());
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(inquiryAnswerRequestDto, "answerContent", "1번문의 답변입니다.");

        Employee employee = EmployeeDummy.dummy();

        AddressLocal addressLocal = AddressLocalDummy.dummy1();

        StatusCode code = StatusCodeDummy.dummy();

        DayLabor labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);

        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        testEntityManager.persist(labor);
        testEntityManager.persist(addressLocal);
        testEntityManager.persist(code);
        testEntityManager.persist(employee);

        inquiryBasic.addAnswer(inquiryAnswerRequestDto, employee, statusCodeComplete);
        InquiryDetailsResponseDto inquiry =
            inquiryRepository.findDetailsById(productInquiry.getInquiryNo()).orElseThrow();


        assertThat(inquiry.getInquiryContent())
            .isEqualTo("2번째 상품문의내용");
        assertThat(inquiry.getTitle())
            .isEqualTo("2번째 상품문의제목");
        assertThat(inquiry.getMemberNickname())
            .isEqualTo(member.getNickname());
        assertThat(inquiry.getProcessStatus())
            .isEqualTo(statusCodeComplete.getStatusCodeName());
        assertThat(inquiry.getEmployeeName())
            .isEqualTo(employee.getName());
        assertThat(inquiry.getProductNo())
            .isEqualTo(product.getNo());
        assertThat(inquiry.getProductName())
            .isEqualTo(product.getName());
    }
}