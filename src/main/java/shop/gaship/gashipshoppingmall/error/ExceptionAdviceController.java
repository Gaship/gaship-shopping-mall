package shop.gaship.gashipshoppingmall.error;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.addresslist.exception.NotFoundAddressListException;
import shop.gaship.gashipshoppingmall.addresslocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.aspact.exception.InvalidAuthorityException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainLowerCategoryException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainProductException;
import shop.gaship.gashipshoppingmall.commonfile.exception.CommonFileNotFoundException;
import shop.gaship.gashipshoppingmall.commonfile.exception.ResourceLoadFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.DecodeFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.EncodeFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.NotFoundDataProtectionReposeData;
import shop.gaship.gashipshoppingmall.daylabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.employee.exception.EmailAlreadyExistException;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.exception.NotMatchRequestData;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongStatusCodeException;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;
import shop.gaship.gashipshoppingmall.inquiry.exception.AlreadyCompleteInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.CustomerInquiryHasProductNoException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentEmployeeWriterAboutInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentInquiryException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquirySearchBadRequestException;
import shop.gaship.gashipshoppingmall.inquiry.exception.NoRegisteredAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.ProductInquiryHasNullProductNoException;
import shop.gaship.gashipshoppingmall.inquiry.exception.WrongInquiryApproachException;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.InvalidReissueQualificationException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.membergrade.exception.AccumulateAmountIsOverlap;
import shop.gaship.gashipshoppingmall.membergrade.exception.CannotDeleteDefaultMemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.DefaultMemberGradeIsExist;
import shop.gaship.gashipshoppingmall.membergrade.exception.InvalidAccumulateAmountException;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeInUseException;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membertag.exception.IllegalTagSelectionException;
import shop.gaship.gashipshoppingmall.order.exception.OrderNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.exception.CouponProcessException;
import shop.gaship.gashipshoppingmall.orderproduct.exception.InvalidOrderCancellationHistoryNo;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductDetailNoFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductEmptyException;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.exception.NoMoreProductException;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.productreview.exception.ProductReviewNotFoundException;
import shop.gaship.gashipshoppingmall.repairschedule.exception.AlreadyExistSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.exception.NotExistSchedule;
import shop.gaship.gashipshoppingmall.tablecount.exception.TableCountNotFoundException;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.totalsale.exception.LocalDateMaxYearException;

/**
 * 예외를 잡기위한 Advice 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdviceController {
    private final LogAndCrashAdapter logAndCrashAdapter;
    /**
     * gaship 개발자들이 선언하고, 예상되는 예외들을 핸들링하기 위해서 제작한 에러 핸들 메서드입니다.
     *
     * @param exception 개발자들이 예상하는 예외 객체입니다.
     * @return 예상되는 예외들의 각 메세지를 담아 응답 객체를 반환합니다.
     */
    @ExceptionHandler(value = {SignUpDenyException.class,
        InvalidReissueQualificationException.class, DuplicatedNicknameException.class,
        AlreadyCompleteInquiryAnswerException.class,
        DifferentEmployeeWriterAboutInquiryAnswerException.class, NoRegisteredAnswerException.class,
        DifferentInquiryException.class, InquirySearchBadRequestException.class,
        ProductInquiryHasNullProductNoException.class, CustomerInquiryHasProductNoException.class,
        CategoryRemainLowerCategoryException.class, CategoryRemainProductException.class,
        ResourceLoadFailureException.class, EmailAlreadyExistException.class,
        WrongAddressException.class, WrongStatusCodeException.class,
        AccumulateAmountIsOverlap.class, CannotDeleteDefaultMemberGrade.class,
        DefaultMemberGradeIsExist.class, InvalidAccumulateAmountException.class,
        MemberGradeInUseException.class, NotMatchRequestData.class,
        IllegalTagSelectionException.class, InvalidOrderCancellationHistoryNo.class,
        NoMoreProductException.class, AlreadyExistSchedule.class,
        DuplicatedTagTitleException.class, LocalDateMaxYearException.class,
        WrongInquiryApproachException.class
        })
    public ResponseEntity<ErrorResponse> declaredExceptionAdvice(RuntimeException exception) {
        log.error("Custom Exception (사용자 정의 예외 처리) : {}", ExceptionUtils.getStackTrace(exception));
        if(Objects.nonNull(exception.getMessage())) {
            logAndCrashAdapter.requestSendReissuePassword(exception, exception.getMessage());
        }

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({MemberNotFoundException.class, InquiryNotFoundException.class,
        NotFoundAddressListException.class, CategoryNotFoundException.class,
        CommonFileNotFoundException.class, NotFoundDataProtectionReposeData.class,
        EmployeeNotFoundException.class, ApplicationEventPublisherNotFoundException.class,
        MemberGradeNotFoundException.class, NotExistAddressLocal.class, NotExistDayLabor.class,
        OrderNotFoundException.class, OrderProductDetailNoFoundException.class,
        OrderProductEmptyException.class, OrderProductNotFoundException.class,
        ProductNotFoundException.class, ProductReviewNotFoundException.class,
        NotExistSchedule.class, TableCountNotFoundException.class, TagNotFoundException.class,
    })
    public ResponseEntity<ErrorResponse> notfoundExceptionHadle(RuntimeException exception) {
        log.error("Custom Exception (사용자 정의 예외 처리) : {}", ExceptionUtils.getStackTrace(exception));
        if(Objects.nonNull(exception.getMessage())) {
            logAndCrashAdapter.requestSendReissuePassword(exception, exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
            .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({MemberForbiddenException.class})
    public ResponseEntity<ErrorResponse> notAccessibleExceptionHandle(RuntimeException exception) {
        log.error("Access Deny Exception (접근 권한 제한예외) : {}", ExceptionUtils.getStackTrace(exception));
        if(Objects.nonNull(exception.getMessage())) {
            logAndCrashAdapter.requestSendReissuePassword(exception, exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
            .body(new ErrorResponse(exception.getMessage()));
    }


    /**
     * 컨트롤러에서 발생한 유효성문제를 잡기위한 클래스입니다.
     *
     * @param ex 적합한 값이 아닐경우 들어오게됩니다.
     * @return response entity
     * @author 유호철
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .reduce("", (accumulateMsg, nextMessage) -> accumulateMsg + "\n" + nextMessage).trim();
        log.warn("Validation Exception : {}", ExceptionUtils.getStackTrace(ex));

        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    @ExceptionHandler({CouponProcessException.class, InvalidAuthorityException.class})
    public ResponseEntity<ErrorResponse> orderRequestProcessExceptionAdvice(Exception exception) {
        log.error("Authorize Exception (권한 예외 처리) : {}", ExceptionUtils.getStackTrace(exception));
        if(Objects.nonNull(exception.getMessage())) {
            logAndCrashAdapter.requestSendReissuePassword(exception, exception.getMessage());
        }

        return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * 예상치 못한 예외가 발생시 핸들링해주는 에러핸들 메서드입니다.
     *
     * @param exception 예기치 못한 예외 객체입니다.
     * @return 예기치 못한 예외의 내용을 응답합니다.
     */
    @ExceptionHandler({FileUploadFailureException.class, RequestFailureThrow.class,
        FileDeleteFailureException.class, NoResponseDataException.class, DecodeFailureException.class,
        EncodeFailureException.class, Exception.class})
    public ResponseEntity<ErrorResponse> otherExceptionAdvice(Exception exception) {
        log.error("Custom Exception (사용자 정의 예외 처리) : {}", ExceptionUtils.getStackTrace(exception));
        if(Objects.nonNull(exception.getMessage())) {
            logAndCrashAdapter.requestSendReissuePassword(exception, exception.getMessage());
        }

        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }


}
