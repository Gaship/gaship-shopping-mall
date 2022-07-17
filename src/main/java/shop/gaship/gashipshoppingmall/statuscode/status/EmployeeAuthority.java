package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : EmployeeAuthority
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum EmployeeAuthority {
    ADMIN("관리자"), REPAIR_INSTALLER("수리설치기사");

    public static final String GROUP = "직원권한";

    private final String value;

    EmployeeAuthority(String value) {
        this.value = value;
    }
}
