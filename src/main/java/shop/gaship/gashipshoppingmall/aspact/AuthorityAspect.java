package shop.gaship.gashipshoppingmall.aspact;

import java.util.Objects;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.gaship.gashipshoppingmall.aspact.exception.InvalidAuthorityException;
import shop.gaship.gashipshoppingmall.member.entity.MembersRole;

/**
 * 권한에 관한 관심사 종단을 체크하는 AOP 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Aspect
@Component
@Slf4j
public class AuthorityAspect {

    /**
     * 멤버 이상의 권한을 가진 멤버만 접근 가능합니다.
     *
     * @param pjp 실제 실행될 메서드 객체입니다.
     * @return 메서드의 실행결과를 반환합니다.
     */
    @Around("@annotation(shop.gaship.gashipshoppingmall.aspact.annotation.MemberAuthority)")
    public Object inspectMemberAuthority(ProceedingJoinPoint pjp) {
        return compareAuthority(pjp, this::checkMemberAuthority);
    }

    /**
     * 매니저(수리설치기사) 이상의 권한을 가진 멤버만 접근 가능합니다.
     *
     * @param pjp 실제 실행될 메서드 객체입니다.
     * @return 메서드의 실행결과를 반환합니다.
     */

    @Around("@annotation(shop.gaship.gashipshoppingmall.aspact.annotation.ManagerAuthority)")
    public Object inspectManagerAuthority(ProceedingJoinPoint pjp) {
        return compareAuthority(pjp, this::checkManagerAuthority);
    }

    /**
     * 관리자 권한을 가진 멤버만 접근 가능합니다.
     *
     * @param pjp 실제 실행될 메서드 객체입니다.
     * @return 메서드의 실행결과를 반환합니다.
     */
    @Around("@annotation(shop.gaship.gashipshoppingmall.aspact.annotation.AdminAuthority)")
    public Object inspectAdminAuthority(ProceedingJoinPoint pjp) {
        return compareAuthority(pjp, this::checkAdminAuthority);
    }

    /**
     * 멤버 권한을 가진 멤버만 접근 가능합니다.
     *
     * @param pjp 실제 실행될 메서드 객체입니다.
     * @return 메서드의 실행결과를 반환합니다.
     */
    @Around("@annotation(shop.gaship.gashipshoppingmall.aspact.annotation.MemberOnlyAuthority)")
    public Object inspectMemberOnlyAuthority(ProceedingJoinPoint pjp) {
        return compareAuthority(pjp, this::checkMemberOnlyAuthority);
    }

    /**
     * 매니저(수리설치기사) 권한을 가진 멤버만 접근 가능합니다.
     *
     * @param pjp 실제 실행될 메서드 객체입니다.
     * @return 메서드의 실행결과를 반환합니다.
     */
    @Around("@annotation(shop.gaship.gashipshoppingmall.aspact.annotation.ManagerOnlyAuthority)")
    public Object inspectManagerOnlyAuthority(ProceedingJoinPoint pjp) {
        return compareAuthority(pjp, this::checkManagerOnlyAuthority);
    }

    /**
     * 권한을 비교하여 권한에 맞는 메서드를 실행합니다.
     *
     * @param pjp 실제 실행될 메서드 객체입니다.
     * @return 메서드의 실행결과를 반환합니다.
     */
    private Object compareAuthority(ProceedingJoinPoint pjp, Predicate<String> qualifyAuthority) {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String authority = request.getHeader("X-AUTH-ROLE");
        log.debug("-------------------------------------------------");
        log.debug("토큰 : {} ",authority);
        log.debug("-------------------------------------------------");
        if (isExecutable(qualifyAuthority, request, authority)) {
            try {
                return pjp.proceed(pjp.getArgs());
            } catch (Throwable e) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new InvalidAuthorityException("접근권한이 없습니다."));
    }

    private boolean isExecutable(Predicate<String> qualifyAuthority, HttpServletRequest request,
                               String authority) {
        return Objects.equals(request.getRequestURI(), "swagger-ui")
            || Objects.nonNull(authority) && qualifyAuthority.test(authority);
    }

    /**
     * 멤버 이상의 권한을 가진 멤버인지 비교하는 메서드입니다.
     *
     * @param authority 접속자의 권한입니다.
     * @return 메서드 접근 허용 결과를 반환합니다.
     */
    private boolean checkMemberAuthority(String authority) {
        return authority.equals(MembersRole.ROLE_USER.getRole())
            || authority.equals(MembersRole.ROLE_MANAGER.getRole())
            || authority.equals(MembersRole.ROLE_ADMIN.getRole());
    }

    /**
     * 매니저(수리설치기사) 이상의 권한을 가진 멤버인지 비교하는 메서드입니다.
     *
     * @param authority 접속자의 권한입니다.
     * @return 메서드 접근 허용 결과를 반환합니다.
     */
    private boolean checkManagerAuthority(String authority) {
        return authority.equals(MembersRole.ROLE_MANAGER.getRole()) || authority.equals(MembersRole.ROLE_ADMIN.getRole());
    }

    /**
     * 관리자 권한을 가진 멤버인지 비교하는 메서드입니다.
     *
     * @param authority 접속자의 권한입니다.
     * @return 메서드 접근 허용 결과를 반환합니다.
     */
    private boolean checkAdminAuthority(String authority) {
        return authority.equals(MembersRole.ROLE_ADMIN.getRole());
    }

    /**
     * 멤버 권한을 가진 멤버인지 비교하는 메서드입니다.
     *
     * @param authority 접속자의 권한입니다.
     * @return 메서드 접근 허용 결과를 반환합니다.
     */
    private boolean checkMemberOnlyAuthority(String authority) {
        return authority.equals(MembersRole.ROLE_USER.getRole());
    }

    /**
     * 매니저(수리설치기사) 권한을 가진 멤버인지 비교하는 메서드입니다.
     *
     * @param authority 접속자의 권한입니다.
     * @return 메서드 접근 허용 결과를 반환합니다.
     */
    private boolean checkManagerOnlyAuthority(String authority) {
        return authority.equals(MembersRole.ROLE_MANAGER.getRole());
    }
}
