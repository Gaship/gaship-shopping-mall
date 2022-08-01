package shop.gaship.gashipshoppingmall.error;

/**
 * 파일 삭제 실패 시 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class FileDeleteException extends RuntimeException{
    public static final String MESSAGE = "파일 삭제에 실패했습니다.";

    public FileDeleteException() {
        super(MESSAGE);
    }
}
