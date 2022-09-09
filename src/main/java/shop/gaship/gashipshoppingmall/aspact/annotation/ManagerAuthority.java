package shop.gaship.gashipshoppingmall.aspact.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 직원(수리설치기사) 권한 이상만이 접근 할 수 있는 어노테이션입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerAuthority {
}
