package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * 기준누적금액 중복 Exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class AccumulateAmountIsOverlap extends RuntimeException {
    public static final String MESSAGE = "동일한 기준누적금액에 해당하는 등급이 존재합니다. 기준누적금액 : ";

    /**
     * Instantiates a new Accumulate amount is overlap.
     *
     * @param accumulateAmount 회원등급 승급 기준누적금액 (Long)
     */
    public AccumulateAmountIsOverlap(Long accumulateAmount) {
        super(MESSAGE + accumulateAmount);
    }
}
