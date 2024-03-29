package shop.gaship.gashipshoppingmall.commonfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.custom.CommonFileRepositoryCustom;

/**
 * 파일 레퍼지토리 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CommonFileRepository extends JpaRepository<CommonFile, Integer>,
        CommonFileRepositoryCustom {
}
