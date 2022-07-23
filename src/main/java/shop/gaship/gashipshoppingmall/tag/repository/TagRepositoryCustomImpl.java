package shop.gaship.gashipshoppingmall.tag.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.QTag;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 태그 custom repository interface 구현체
 *
 * @author 최정우
 * @since 1.0
 */
public class TagRepositoryCustomImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {
    public TagRepositoryCustomImpl() {
        super(Tag.class);
    }

    /**
     * 태그를 페이징하는 메서드
     *
     * @param pageable 조회하려는 태그 페이지 정보
     * @return  Page<TagResponseDto>
     */
    @Override
    public Page<TagResponseDto> getAllTags(Pageable pageable) {
        QTag tag = QTag.tag;
        QueryResults<TagResponseDto> queryResults = from(tag)
                .orderBy(tag.title.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .select(Projections.bean(TagResponseDto.class,
                        tag.tagNo,
                        tag.title,
                        tag.registerDatetime,
                        tag.modifiedDatetime))
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(),pageable, queryResults.getTotal());
    }
}
