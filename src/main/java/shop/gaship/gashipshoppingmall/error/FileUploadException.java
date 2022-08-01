package shop.gaship.gashipshoppingmall.error;

/**
 * 파일 업로드 실패시 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class FileUploadException extends RuntimeException{
    public static final String MESSAGE = "파일 업로드에 실패하였습니다.";

    public FileUploadException() {
        super(MESSAGE);
    }
}
