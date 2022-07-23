package shop.gaship.gashipshoppingmall.tag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 태그 repository custom
 *
 * @author 최정우
 * @since 1.0
 */
@NoRepositoryBean
public interface TagRepositoryCustom {
    /**
     * 태그를 페이징하는 메서드
     *
     * @param pageable 조회하려는 태그 페이지 정보
     * @return Page<TagResponseDto>
     */
    Page<Tag> getAllTags(Pageable pageable);
}
