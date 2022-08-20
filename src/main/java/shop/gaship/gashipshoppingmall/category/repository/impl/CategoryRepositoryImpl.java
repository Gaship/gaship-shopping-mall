package shop.gaship.gashipshoppingmall.category.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.entity.QCategory;
import shop.gaship.gashipshoppingmall.category.repository.custom.CategoryRepositoryCustom;


/**
 * 카테고리 레퍼지토리 구현체입니다.
 *
 * @author : 김보민
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 * @see shop.gaship.gashipshoppingmall.category.repository.custom.CategoryRepositoryCustom
 * @since 1.0
 */
public class CategoryRepositoryImpl
    extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    private static final String UPPER_CATEGORY_NO = "upperCategoryNo";
    private static final String UPPER_CATEGORY_NAME = "upperCategoryName";

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CategoryResponseDto> findCategoryById(Integer categoryNo) {
        QCategory category = QCategory.category;

        return Optional.ofNullable(from(category)
            .where(category.no.eq(categoryNo))
            .leftJoin(category.upperCategory)
            .select(Projections.bean(CategoryResponseDto.class,
                category.no,
                category.name,
                category.level,
                category.upperCategory.no.as(UPPER_CATEGORY_NO),
                category.upperCategory.name.as(UPPER_CATEGORY_NAME)))
            .fetchOne()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> findAllLowerCategories(Integer categoryNo) {
        QCategory category = QCategory.category;

        return from(category)
            .where(category.upperCategory.no.eq(categoryNo))
            .select(Projections.bean(CategoryResponseDto.class,
                category.no,
                category.name,
                category.level,
                category.upperCategory.no.as(UPPER_CATEGORY_NO),
                category.upperCategory.name.as(UPPER_CATEGORY_NAME)))
            .fetch();
    }
}
