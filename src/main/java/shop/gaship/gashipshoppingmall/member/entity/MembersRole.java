package shop.gaship.gashipshoppingmall.member.entity;

/**
 * 계정 권한을 나타내기 위한 열거형 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
public enum MembersRole {
    ROLE_USER("ROLE_USER"), ROLE_MANAGER("ROLE_MANAGER"), ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    MembersRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
