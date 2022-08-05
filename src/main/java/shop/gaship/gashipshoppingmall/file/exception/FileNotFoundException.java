package shop.gaship.gashipshoppingmall.file.exception;

/**
 * db에서 파일 데이터를 찾을 수 없을 때 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class FileNotFoundException extends RuntimeException{
    public static final String MESSAGE = "해당 파일을 찾을 수 없습니다.";

    public FileNotFoundException() {
        super(MESSAGE);
    }
}
