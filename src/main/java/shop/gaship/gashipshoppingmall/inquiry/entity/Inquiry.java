package shop.gaship.gashipshoppingmall.inquiry.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.exception.AlreadyCompleteInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentEmployeeWriterAboutInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentInquiryException;
import shop.gaship.gashipshoppingmall.inquiry.exception.NoRegisteredAnswerException;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.ProcessStatus;

/**
 * db의 inquiries 테이블과 1:1매핑되는 entity 객체입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Entity
@Table(name = "inquiries")
@NoArgsConstructor
@Getter
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_no")
    private Integer inquiryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_status_no", nullable = false)
    @NotNull
    private StatusCode processStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @NotNull
    private String title;

    @Column(name = "inquiry_content")
    @NotNull
    private String inquiryContent;

    @Column(name = "register_datetime")
    @NotNull
    private LocalDateTime registerDatetime;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "answer_register_datetime")
    private LocalDateTime answerRegisterDatetime;

    @Column(name = "answer_modify_datetime")
    private LocalDateTime answerModifyDatetime;

    @Column(name = "is_product")
    @NotNull
    private Boolean isProduct;

    /**
     * Inquriy entity를 생성하기위한 생성자이며 Builder를 붙여서 편리하게 생성할수 있습니다.
     *
     * @param processStatusCode 처리상태정보를 담고있는 상태코드객체입니다.
     * @param title             문의 제목입니다.
     * @param inquiryContent    문의내용입니다.
     * @param registerDatetime  문의등록 시간입니다.
     * @param isProduct         상품문의인지 고객문의인지 구분하기위해 사용합니다. true이면 상품문의, false이면 고객문의입니다.
     * @author 최겸준
     */
    @Builder
    public Inquiry(StatusCode processStatusCode, String title, String inquiryContent,
                   LocalDateTime registerDatetime, Boolean isProduct) {
        this.processStatusCode = processStatusCode;
        this.title = title;
        this.inquiryContent = inquiryContent;
        this.registerDatetime = registerDatetime;
        this.isProduct = isProduct;
    }

    /**
     * 양방향에서의 연관관계 편의 메소드로서 member를 연관관계 추가하고 member의 방향에서도 inquiry를 저장해주는 기능입니다.
     *
     * @param member 영속화되어 넘어온 member entity 입니다.
     * @author 최겸준
     */
    public void addMember(Member member) {
        if (!Objects.isNull(this.member)) {
            this.member.getInquiries().remove(this);
        }

        this.member = member;
        member.getInquiries().add(this);
    }

    /**
     * 양방향에서의 연관관계 편의 메소드로서 product를 연관관계 추가하고 product의 방향에서도 inquiry를 저장해주는 기능입니다.
     *
     * @param product 영속화되어 넘어온 product entity 입니다.
     * @author 최겸준
     */
    public void addProduct(Product product) {
        if (!Objects.isNull(this.product)) {
            this.product.getInquiries().remove(this);
        }

        this.product = product;
        product.getInquiries().add(this);
    }

    /**
     * 문의 답변을 추가하는 기능입니다.
     *
     * @param inquiryAnswerRequestDto 문의 답변 추가에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @param employee                답변을 등록하려는 사원의 영속화된 entity입니다.
     * @param processStatusCode       처리상태정보를 담고있는 상태코드객체입니다.
     * @author 최겸준
     */
    public void addAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto, Employee employee,
                          StatusCode processStatusCode) {
        if (!inquiryAnswerRequestDto.getInquiryNo().equals(this.inquiryNo)) {
            throw new DifferentInquiryException();
        }

        if (Objects.equals(this.processStatusCode.getStatusCodeName(),
            ProcessStatus.COMPLETE.getValue())) {
            throw new AlreadyCompleteInquiryAnswerException();
        }

        this.employee = employee;
        this.answerContent = inquiryAnswerRequestDto.getAnswerContent();
        this.answerRegisterDatetime = LocalDateTime.now();
        this.processStatusCode = processStatusCode;
    }

    /**
     * 문의 답변을 수정하는 기능입니다.
     *
     * @param inquiryAnswerRequestDto 문의 답변 추가에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @author 최겸준
     */
    public void modifyAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto) {
        if (!inquiryAnswerRequestDto.getInquiryNo().equals(this.inquiryNo)) {
            throw new DifferentInquiryException();
        }

        if (Objects.equals(this.processStatusCode.getStatusCodeName(),
            ProcessStatus.WAITING.getValue())) {
            throw new NoRegisteredAnswerException();
        }

        if (!Objects.equals(inquiryAnswerRequestDto.getEmployeeNo(),
            this.employee.getEmployeeNo())) {
            throw new DifferentEmployeeWriterAboutInquiryAnswerException();
        }


        this.answerContent = inquiryAnswerRequestDto.getAnswerContent();
        this.answerModifyDatetime = LocalDateTime.now();
    }

    /**
     * 문의 답변을 삭제하는 기능입니다.
     * 정확하게는 테이블의 컬럼을 수정하는 용도입니다.
     *
     * @param processStatusCode 답변삭제시 답변완료상태에서 답변대기상태로 변경되어야하는데 해당 정보를 담고있는 상태코드입니다.
     * @param inquiryNo         답변삭제의 기준이 되는 문의번호입니다.
     * @author 최겸준
     */
    public void deleteAnswer(StatusCode processStatusCode, Integer inquiryNo) {
        if (!Objects.equals(inquiryNo, this.inquiryNo)) {
            throw new DifferentInquiryException();
        }

        if (Objects.equals(this.processStatusCode.getStatusCodeName(),
            ProcessStatus.WAITING.getValue())) {
            throw new NoRegisteredAnswerException();
        }

        this.answerContent = Strings.EMPTY;
        this.employee = null;
        this.answerRegisterDatetime = null;
        this.answerModifyDatetime = null;
        this.processStatusCode = processStatusCode;
    }
}
