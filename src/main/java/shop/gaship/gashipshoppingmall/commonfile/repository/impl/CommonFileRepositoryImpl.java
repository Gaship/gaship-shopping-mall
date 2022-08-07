package shop.gaship.gashipshoppingmall.commonfile.repository.impl;

import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.entity.QCommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.custom.CommonFileRepositoryCustom;

/**
 * 공통파일 레퍼지토리 구현체
 *
 * @author : 김보민
 * @since 1.0
 */
public class CommonFileRepositoryImpl extends QuerydslRepositorySupport implements
        CommonFileRepositoryCustom {
    public CommonFileRepositoryImpl() {
        super(CommonFile.class);
    }

    @Override
    public List<String> findPaths(Integer ownerNo, String service) {
        QCommonFile commonFile = QCommonFile.commonFile;
        return from(commonFile)
                .select(commonFile.path)
                .where(commonFile.service.eq(service))
                .fetch();
    }
}
