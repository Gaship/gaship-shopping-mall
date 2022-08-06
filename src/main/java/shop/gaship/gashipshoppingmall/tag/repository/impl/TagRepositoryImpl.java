package shop.gaship.gashipshoppingmall.tag.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.QTag;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepositoryCustom;

/**
 * 태그 custom repository interface 구현체.
 *
 * @author 최정우
 * @since 1.0
 */

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {
    public TagRepositoryImpl() {
        super(Tag.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TagResponseDto> getTags(Pageable pageable) {
        QTag tag = QTag.tag;

        List<TagResponseDto> tags = from(tag)
                .orderBy(tag.title.asc())
                .limit(Math.min(pageable.getPageSize(), 50))
                .offset(pageable.getOffset())
                .select(Projections.fields(TagResponseDto.class,
                        tag.tagNo,
                        tag.title,
                        tag.registerDatetime,
                        tag.modifyDatetime))
                .fetch();

        return PageableExecutionUtils.getPage(tags,
                pageable,
                () -> from(tag)
                        .fetch()
                        .size());
    }
}
