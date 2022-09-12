package shop.gaship.gashipshoppingmall.inquiry.service.impl;

import static shop.gaship.gashipshoppingmall.inquiry.repository.custom.impl.InquiryRepositoryImpl.ANSWER_COMPLETE_NO;
import static shop.gaship.gashipshoppingmall.tablecount.nameenum.TableName.CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME;
import static shop.gaship.gashipshoppingmall.tablecount.nameenum.TableName.CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.aspact.exception.InvalidIdException;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.error.MemberForbiddenException;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.WrongInquiryApproachException;
import shop.gaship.gashipshoppingmall.inquiry.inquiryenum.InquiryType;
import shop.gaship.gashipshoppingmall.inquiry.repository.InquiryRepository;
import shop.gaship.gashipshoppingmall.inquiry.search.InquiryListSearch;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.ProcessStatus;
import shop.gaship.gashipshoppingmall.tablecount.entity.TableCount;
import shop.gaship.gashipshoppingmall.tablecount.service.TableCountService;

/**
 * InquiryService 인터페이스의 구현체입니다.
 *
 * @author 최겸준
 * @see InquiryService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;

    private final TableCountService tableCountService;

    private static final Long COUNT_UP_AND_DOWN_NUMBER = 1L;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void addInquiry(InquiryAddRequestDto inquiryAddRequestDto) {
        StatusCode statusCode =
            statusCodeRepository.findByStatusCodeName(ProcessStatus.WAITING.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);
        Inquiry inquiry =
            this.inquiryAddRequestDtoToInquiryEntityWhenCreation(inquiryAddRequestDto, statusCode);

        setMember(inquiryAddRequestDto, inquiry);
        saveOrSetProduct(inquiryAddRequestDto, inquiry);

        if (Boolean.TRUE.equals(inquiry.getIsProduct())) {
            return;
        }
        plusCustomerInquiryAllCount();
    }

    /**
     * member를 조회하여 inquiry에 연관관계를 추가하는 기능입니다.
     * 기능 추가한 뒤에 상품문의인지 아닌지 확인하여 상품문의가 아니라면 해당 문의를 바로 저장합니다.
     *
     * @param inquiryAddRequestDto 상품문의 등록에 필요한 정보를 담고있는 DTO 객체입니다.
     * @param inquiry              아직 영속화 되기 전의 상태인 Inquiry entity 입니다.
     * @author 최겸준
     */
    private void setMember(InquiryAddRequestDto inquiryAddRequestDto, Inquiry inquiry) {
        Member member = memberRepository.findById(inquiryAddRequestDto.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);
        inquiry.addMember(member);
    }

    /**
     * product를 조회하여 inquiry에 연관관계를 추가한뒤 해당 문의를 바로 저장하는 기능입니다.
     *
     * @param inquiryAddRequestDto 상품문의 등록에 필요한 정보를 담고있는 DTO 객체입니다.
     * @param inquiry              아직 영속화 되기 전의 상태인 Inquiry entity 입니다.
     * @author 최겸준
     */
    private void saveOrSetProduct(InquiryAddRequestDto inquiryAddRequestDto, Inquiry inquiry) {
        if (!inquiryAddRequestDto.getIsProduct()) {
            inquiryRepository.save(inquiry);
            return;
        }

        Product product = productRepository.findById(inquiryAddRequestDto.getProductNo())
            .orElseThrow(ProductNotFoundException::new);
        inquiry.addProduct(product);

        inquiryRepository.save(inquiry);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void addInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryAnswerRequestDto.getInquiryNo())
            .orElseThrow(InquiryNotFoundException::new);

        Integer employeeNo = inquiryAnswerRequestDto.getEmployeeNo();
        Employee employee =
            employeeRepository.findById(employeeNo).orElseThrow(EmployeeNotFoundException::new);

        StatusCode processStatusCode =
            statusCodeRepository.findByStatusCodeName(ProcessStatus.COMPLETE.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        inquiry.addAnswer(inquiryAnswerRequestDto, employee, processStatusCode);

        if (Boolean.TRUE.equals(inquiry.getIsProduct())) {
            return;
        }
        plusCustomerInquiryAnswerCount();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void modifyInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryAnswerRequestDto.getInquiryNo())
            .orElseThrow(InquiryNotFoundException::new);

        Employee employee = employeeRepository.findById(inquiryAnswerRequestDto.getEmployeeNo()).orElseThrow(EmployeeNotFoundException::new);
        inquiry.modifyAnswer(inquiryAnswerRequestDto, employee);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteInquiry(Integer inquiryNo, Integer memberNo) {
        InquiryDetailsResponseDto inquiry = inquiryRepository.findDetailsById(inquiryNo).orElseThrow(InquiryNotFoundException::new);

        if (!Objects.equals(inquiry.getMemberNo(), memberNo)) {
            throw new MemberForbiddenException();
        }

        inquiryRepository.deleteById(inquiryNo);

        minusTableCount(inquiry);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteInquiryManager(Integer inquiryNo) {
        if (!inquiryRepository.existsById(inquiryNo)) {
            throw new InquiryNotFoundException();
        }

        InquiryDetailsResponseDto inquiry = inquiryRepository.findDetailsById(inquiryNo).orElseThrow(InquiryNotFoundException::new);
        inquiryRepository.deleteById(inquiryNo);

        minusTableCount(inquiry);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteInquiryAnswer(Integer inquiryNo) {
        Inquiry inquiry =
            inquiryRepository.findById(inquiryNo).orElseThrow(InquiryNotFoundException::new);

        StatusCode processStatusCode =
            statusCodeRepository.findByStatusCodeName(ProcessStatus.WAITING.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        inquiry.deleteAnswer(processStatusCode, inquiryNo);

        if (Boolean.TRUE.equals(inquiry.getIsProduct())) {
            return;
        }
        minusCustomerInquiryAnswerCount();
    }

    private void plusCustomerInquiryAllCount() {
        TableCount tableCount = tableCountService.findByName(
            CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME.getValue());
        if (Objects.equals(tableCount.getCount(), 0L)) {
            long totalCount = inquiryRepository.getCustomerInquiryCount(new InquiryListSearch(false, null, null, null));
            tableCount.setCount(totalCount + COUNT_UP_AND_DOWN_NUMBER);
            return;
        }
        tableCount.setCount(tableCount.getCount() + COUNT_UP_AND_DOWN_NUMBER);
    }

    private void plusCustomerInquiryAnswerCount() {
        TableCount tableCount = tableCountService.findByName(
            CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME.getValue());
        if (Objects.equals(tableCount.getCount(), 0L)) {
            long totalCount = inquiryRepository.getCustomerInquiryCount(new InquiryListSearch(false, 15, null, null));
            tableCount.setCount(totalCount + COUNT_UP_AND_DOWN_NUMBER);
            return;
        }
        tableCount.setCount(tableCount.getCount() + COUNT_UP_AND_DOWN_NUMBER);
    }

    private void minusCustomerInquiryAllCount() {
        TableCount tableCount = tableCountService.findByName(
            CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME.getValue());
        if (Objects.equals(tableCount.getCount(), 0L)) {
            long totalCount = inquiryRepository.getCustomerInquiryCount(new InquiryListSearch(false, null, null, null));
            tableCount.setCount(totalCount - COUNT_UP_AND_DOWN_NUMBER);
            return;
        }
        tableCount.setCount(tableCount.getCount() - COUNT_UP_AND_DOWN_NUMBER);
    }

    private void minusCustomerInquiryAnswerCount() {
        TableCount tableCount = tableCountService.findByName(
            CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME.getValue());
        if (Objects.equals(tableCount.getCount(), 0L)) {
            long totalCount = inquiryRepository.getCustomerInquiryCount(new InquiryListSearch(false, 15, null, null));
            tableCount.setCount(totalCount - COUNT_UP_AND_DOWN_NUMBER);
            return;
        }
        tableCount.setCount(tableCount.getCount() - COUNT_UP_AND_DOWN_NUMBER);
    }

    private void minusTableCount(InquiryDetailsResponseDto inquiry) {
        if (Objects.nonNull(inquiry.getProductNo())) {
            return;
        }

        minusCustomerInquiryAllCount();
        if (Objects.isNull(inquiry.getEmployeeName())) {
            return;
        }

        minusCustomerInquiryAnswerCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<InquiryListResponseDto> findProductInquiriesAll(Pageable pageable, Boolean isProduct) {
        InquiryListSearch inquirySearchRequestDto =
            new InquiryListSearch(Boolean.TRUE, null, null, null);

        return inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto,
            null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<InquiryListResponseDto> findInquiriesByStatusCodeNo(Pageable pageable,
                                                                    Boolean isProduct,
                                                                    String statusCodeName) {
        StatusCode statusCode = statusCodeRepository.findByStatusCodeName(statusCodeName)
            .orElseThrow(StatusCodeNotFoundException::new);

        InquiryListSearch inquirySearchRequestDto =
            new InquiryListSearch(isProduct, statusCode.getStatusCodeNo(), null, null);

        if (Objects.equals(inquirySearchRequestDto.getIsProduct(), Boolean.FALSE)
            && Objects.equals(inquirySearchRequestDto.getStatusCodeNo(), ANSWER_COMPLETE_NO)) {

            TableCount tableCount = tableCountService.findByName(CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME.getValue());
            return inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto, tableCount.getCount());
        }

        return inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto, null);
    }

    @Override
    public Page<InquiryListResponseDto> findCustomerInquiriesAllOrStatusComplete(Pageable pageable,
                                                                                 InquiryListSearch inquiryListSearch) {
        TableCount tableCount = getTableCount(inquiryListSearch);

        return inquiryRepository.customerInquiryAllOrStatusCompleteFindAll(pageable, tableCount.getCount(), inquiryListSearch);
    }


    @Override
    public Page<InquiryListResponseDto> findCustomerInquiriesAllOrStatusCompletePrevPage(
        Pageable pageable, Integer inquiryNo,
        InquiryListSearch inquiryListSearch) {
        TableCount tableCount = getTableCount(inquiryListSearch);

        return inquiryRepository.customerInquiryAllOrStatusCompletePrevFindAll(pageable, inquiryNo, tableCount.getCount(), inquiryListSearch);
    }

    @Override
    public Page<InquiryListResponseDto> findCustomerInquiriesAllOrStatusCompleteNextPage(
        Pageable pageable, Integer inquiryNo, InquiryListSearch inquiryListSearch) {
        TableCount tableCount = getTableCount(inquiryListSearch);

        return inquiryRepository.customerInquiryAllOrStatusCompleteNextFindAll(pageable, inquiryNo, tableCount.getCount(), inquiryListSearch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<InquiryListResponseDto> findInquiriesByMemberNo(Pageable pageable,
                                                                Boolean isProduct,
                                                                Integer memberNo) {
        if (!memberRepository.existsById(memberNo)) {
            throw new MemberNotFoundException();
        }

        InquiryListSearch inquirySearchRequestDto =
            new InquiryListSearch(isProduct, null, memberNo, null);

        return inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto,
            null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<InquiryListResponseDto> findInquiriesByProductNo(Pageable pageable,
                                                                 Integer productNo) {
        if (!productRepository.existsById(productNo)) {
            throw new ProductNotFoundException();
        }

        InquiryListSearch inquirySearchRequestDto =
            new InquiryListSearch(InquiryType.PRODUCT_INQUIRY.getValue(), null, null,
                productNo);

        return inquiryRepository.findAllThroughSearchDto(pageable, inquirySearchRequestDto,
            null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InquiryDetailsResponseDto findInquiry(int inquiryNo) {

        return inquiryRepository.findDetailsById(inquiryNo)
            .orElseThrow(InquiryNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InquiryDetailsResponseDto findProductInquiryMemberSelf(Integer inquiryNo) {

        InquiryDetailsResponseDto details =
            inquiryRepository.findDetailsById(inquiryNo)
                .orElseThrow(InquiryNotFoundException::new);

        if (Objects.isNull(details.getProductNo())) {
            throw new WrongInquiryApproachException();
        }
        return details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InquiryDetailsResponseDto findCustomerInquiryMemberSelf(Integer inquiryNo, Integer memberNo) {

        InquiryDetailsResponseDto details =
            inquiryRepository.findDetailsById(inquiryNo)
                .orElseThrow(InquiryNotFoundException::new);

        if (Objects.nonNull(details.getProductNo())) {
            throw new WrongInquiryApproachException();
        }

        if (!Objects.equals(details.getMemberNo(), memberNo)) {
            throw new InvalidIdException();
        }
        return details;
    }

    private TableCount getTableCount(InquiryListSearch inquiryListSearch) {
        TableCount tableCount;
        if (Objects.isNull(inquiryListSearch.getStatusCodeNo())) {
            tableCount = tableCountService.findByName(CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME.getValue());
        } else {
            tableCount = tableCountService.findByName(CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME.getValue());
        }
        return tableCount;
    }
}
