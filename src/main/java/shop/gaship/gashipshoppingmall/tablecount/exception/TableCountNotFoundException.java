package shop.gaship.gashipshoppingmall.tablecount.exception;

/**
 * DB에 TableCount가 존재하지 않을시에 발생시키는 예외클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class TableCountNotFoundException extends RuntimeException {

    public static final String MESSAGE = "해당하는 TableCount가 없습니다.";

    public TableCountNotFoundException() {
        super(MESSAGE);
    }
}
