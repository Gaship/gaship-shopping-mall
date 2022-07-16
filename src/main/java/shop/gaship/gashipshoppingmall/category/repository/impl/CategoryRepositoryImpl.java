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
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 * @see shop.gaship.gashipshoppingmall.category.repository.custom.CategoryRepositoryCustom
 * @author : 김보민
 * @since 1.0
 */
public class CategoryRepositoryImpl
        extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    /**
     * 카테고리 단건 조회 메서드입니다.
     *
     * @param categoryNo 조회할 카테괼 번호
     * @return optional 카테고리 정보를 담은 optional
     * @author 김보민
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
                        category.upperCategory.no.as("upperCategoryNo"),
                        category.upperCategory.name.as("upperCategoryName")))
                .fetchOne()
        );
    }

    /**
     * 카테고리 전체 조회 메서드입니다.
     *
     * @return list 카테고리 목록 정보를 담은 list
     * @author 김보민
     */
    @Override
    public List<CategoryResponseDto> findAllCategories() {
        QCategory category = QCategory.category;

        return from(category)
                .leftJoin(category.upperCategory)
                .select(Projections.bean(CategoryResponseDto.class,
                        category.no,
                        category.name,
                        category.level,
                        category.upperCategory.no.as("upperCategoryNo"),
                        category.upperCategory.name.as("upperCategoryName")))
                .fetch();
    }

    /**
     * 하위 카테고리 조회 메서드입니다.
     *
     * @param categoryNo 하위 카테고리를 조회할 상위 카테고리 번호
     * @return list 하위 카테고리 목록 정보를 담은 list
     * @author 김보민
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
                        category.upperCategory.no.as("upperCategoryNo"),
                        category.upperCategory.name.as("upperCategoryName")))
                .fetch();
    }
}
