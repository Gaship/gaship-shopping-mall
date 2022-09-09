package shop.gaship.gashipshoppingmall.aspact;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
@Aspect
@RequiredArgsConstructor
public class MemberValidAspect {
    private final ObjectMapper objectMapper;
    private static final String HEADER_ID = "X-AUTH-ID";
    private static final String ATTRIBUTE_ID = "memberNo";

    @Around("@annotation(shop.gaship.gashipshoppingmall.aspact.annotation.MemberValid)")
    public Object memberCheck(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        if (Objects.equals(request.getHeader(HEADER_ID), getMemberNo(request))) {
            return pjp.proceed();
        }

        throw new RuntimeException("jwt인증실패");
    }

    private String getMemberNo(HttpServletRequest request) {
        return (String) objectMapper.convertValue(
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE), Map.class)
                .get(ATTRIBUTE_ID);
    }
}
