package shop.gaship.gashipshoppingmall.tag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 태그관련 커스텀 쿼리를 위한 인터페이스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@NoRepositoryBean
public interface TagRepositoryCustom {
    /**
     * 태그를 페이징하는 메서드입니다.
     *
     * @param pageable 조회하려는 태그 페이지 정보
     * @return TagResponseDto의 페이징 객체가 반환됩니다.
     */
    Page<Tag> getAllTags(Pageable pageable);
}
