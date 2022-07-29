package shop.gaship.gashipshoppingmall.inquiry.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.repository.InquiryRepository;
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

/**
 * InquiryService 인터페이스의 구현체입니다.
 *
 * @author 최겸준
 * @see InquiryService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInquiry(InquiryAddRequestDto inquiryAddRequestDto) {
        StatusCode statusCode =
            statusCodeRepository.findByStatusCodeName(ProcessStatus.WAITING.getValue()).orElseThrow(
                StatusCodeNotFoundException::new);
        Inquiry inquiry = Inquiry.dtoToEntityWhenCreation(inquiryAddRequestDto, statusCode);

        setMember(inquiryAddRequestDto, inquiry);
        saveOrSetProduct(inquiryAddRequestDto, inquiry);
    }

    /**
     * member를 조회하여 inquiry에 연관관계를 추가하는 기능입니다.
     * 기능 추가한 뒤에 상품문의인지 아닌지 확인하여 상품문의가 아니라면 해당 문의를 바로 저장합니다.
     *
     * @param inquiryAddRequestDto 상품문의 등록에 필요한 정보를 담고있는 DTO 객체입니다.
     * @param inquiry              아직 영속화 되기 전의 상태인 Inquiry entity 입니다.
     * @author 최겸준
     */
    private void setMember(InquiryAddRequestDto inquiryAddRequestDto,
                           Inquiry inquiry) {
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
    @Override
    public void addOrModifyInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto,
                                         Boolean isAddAnswer) {
        Inquiry inquiry =
            inquiryRepository.findById(inquiryAnswerRequestDto.getInquiryNo()).orElseThrow(
                InquiryNotFoundException::new);

        if (Boolean.FALSE.equals(isAddAnswer)) {
            inquiry.modifyAnswer(inquiryAnswerRequestDto);
            return;
        }

        addInquiryAnswer(inquiryAnswerRequestDto, inquiry);
    }

    /**
     * 상품문의답변을 처음으로 등록할때 사용되는 기능입니다.
     * 등록되면 상품의 처리상태가 답변완료로 변경됩니다.
     *
     * @param inquiryAnswerRequestDto 상품문의 답변 등록에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @param inquiry                 아직 영속화 되기 전의 상태인 Inquiry entity 입니다.
     * @author 최겸준
     */
    private void addInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto,
                                  Inquiry inquiry) {
        Integer employeeNo = inquiryAnswerRequestDto.getEmployeeNo();
        Employee employee =
            employeeRepository.findById(employeeNo).orElseThrow(EmployeeNotFoundException::new);

        StatusCode processStatusCode =
            statusCodeRepository.findByStatusCodeName(ProcessStatus.COMPLETE.getValue())
                .orElseThrow(
                    StatusCodeNotFoundException::new);

        inquiry.addAnswer(inquiryAnswerRequestDto, employee, processStatusCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteInquiry(Integer inquiryNo) {
        inquiryRepository.deleteById(inquiryNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAnswerInquiry(Integer inquiryNo) {
        Inquiry inquiry =
            inquiryRepository.findById(inquiryNo).orElseThrow(InquiryNotFoundException::new);

        StatusCode processStatusCode =
            statusCodeRepository.findByStatusCodeName(ProcessStatus.WAITING.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        inquiry.deleteAnswer(processStatusCode);
    }
}
