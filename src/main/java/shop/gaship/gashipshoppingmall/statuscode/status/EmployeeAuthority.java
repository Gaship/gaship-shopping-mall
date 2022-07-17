package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 직원에 사용되는 직원 권한 값
 *
 * @author : 김세미
 * @since 1.0
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
