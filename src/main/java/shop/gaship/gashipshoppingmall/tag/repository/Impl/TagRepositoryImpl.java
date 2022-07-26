package shop.gaship.gashipshoppingmall.tag.repository.Impl;

import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.tag.entity.QTag;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepositoryCustom;

import java.util.List;

/**
 * 태그 custom repository interface 구현체
 *
 * @author 최정우
 * @since 1.0
 */

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {
    public TagRepositoryImpl() {
        super(Tag.class);
    }

    /**
     * 태그를 페이징하는 메서드
     *
     * @param pageable 조회하려는 태그 페이지 정보
     * @return Page<TagResponseDto>
     */
    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        QTag tag = QTag.tag;

        List<Tag> list = from(tag)
                .orderBy(tag.title.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return PageableExecutionUtils.getPage(list,
                pageable,
                ()->from(tag)
                        .fetch()
                        .size());
    }
}
