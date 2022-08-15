package shop.gaship.gashipshoppingmall.error;

/**
 * ApplicationEventPublisher가 초기화 되지 않았을시에 발생시킬 예외입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class ApplicationEventPublisherNotFoundException extends RuntimeException {

    public static final String MESSAGE = "ApplicationEventPublisher가 초기화되지 않았습니다.";

    public ApplicationEventPublisherNotFoundException() {
        super(MESSAGE);
    }
}
