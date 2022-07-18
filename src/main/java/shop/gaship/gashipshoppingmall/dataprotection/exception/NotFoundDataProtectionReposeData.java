package shop.gaship.gashipshoppingmall.dataprotection.exception;

/**
 * Secure Key Manager에서 응답이 제대로 받지 못하거나, 잘못 요청했을때 발생하는 예외입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public class NotFoundDataProtectionReposeData extends RuntimeException {
    public NotFoundDataProtectionReposeData(String s) {
        super(s);
    }
}
