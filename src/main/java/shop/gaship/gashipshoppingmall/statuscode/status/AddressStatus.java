package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : AddressStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum AddressStatus {
    USE("사용"), DELETE("삭제");

    public static final String GROUP = "배송지상태";

    private final String value;

    AddressStatus(String value) {
        this.value = value;
    }
}
