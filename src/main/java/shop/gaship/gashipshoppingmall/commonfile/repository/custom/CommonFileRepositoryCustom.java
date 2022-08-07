package shop.gaship.gashipshoppingmall.commonfile.repository.custom;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 공통파일 커스텀 레퍼지토리 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@NoRepositoryBean
public interface CommonFileRepositoryCustom {
    List<String> findPaths(Integer ownerNo, String service);
}
