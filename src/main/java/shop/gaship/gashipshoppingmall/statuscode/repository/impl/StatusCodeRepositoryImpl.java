package shop.gaship.gashipshoppingmall.statuscode.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepositoryCustom;

/**
 * 상태코드 Custom Repository 구현체.
 *
 * @author : 김세미
 * @see shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepositoryCustom
 * @since 1.0
 */
public class StatusCodeRepositoryImpl extends QuerydslRepositorySupport
        implements StatusCodeRepositoryCustom {
    public StatusCodeRepositoryImpl() {
        super(StatusCode.class);
    }

    @Override
    public List<StatusCodeResponseDto> getStatusCodesByGroup(String groupCodeName) {
        QStatusCode statusCode = QStatusCode.statusCode;

        return from(statusCode)
                .where(statusCode.groupCodeName.eq(groupCodeName))
                .orderBy(statusCode.priority.asc())
                .select(Projections.bean(StatusCodeResponseDto.class,
                        statusCode.statusCodeName,
                        statusCode.priority))
                .fetch();
    }
}
