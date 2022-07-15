package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.exception
 * fileName       : AccumulateAmountIsOverlap
 * author         : Semi Kim
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14        Semi Kim       최초 생성
 */
public class AccumulateAmountIsOverlap extends RuntimeException {
    private static final String MESSAGE = "동일한 기준누적금액에 해당하는 등급이 존재합니다. 기준누적금액 : ";

    public AccumulateAmountIsOverlap(Long accumulateAmount) {
        super(MESSAGE + accumulateAmount);
    }
}
