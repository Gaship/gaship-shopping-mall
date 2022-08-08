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
    /**
     * 파일경로를 찾는 메서드입니다.
     *
     * @param ownerNo 파일경로를 찾을 파일 주인 번호
     * @param service 파일 주인의 서비스
     * @return 파일경로 목록
     */
    List<String> findPaths(Integer ownerNo, String service);
}
