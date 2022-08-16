package shop.gaship.gashipshoppingmall.commonfile.exception;

/**
 * Resource 로드 실패 시 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class ResourceLoadFailureException extends RuntimeException {
    public static final String MESSAGE = "리소스를 불러오는데 실패했습니다.";

    public ResourceLoadFailureException() {
        super(MESSAGE);
    }
}
