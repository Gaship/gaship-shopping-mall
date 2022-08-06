package shop.gaship.gashipshoppingmall.tag.repository.impl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
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
     *
     * @param pageable {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        QTag tag = QTag.tag;

        List<Tag> list = from(tag).orderBy(tag.title.asc()).limit(pageable.getPageSize())
            .offset(pageable.getOffset()).fetch();

        return PageableExecutionUtils.getPage(list, pageable, () -> from(tag).fetch().size());
    }
}
