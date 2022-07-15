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
 * packageName    : shop.gaship.gashipshoppingmall.category.repository.impl
 * fileName       : CategoryRepositoryImpl
 * author         : 김보민
 * date           : 2022-07-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
public class CategoryRepositoryImpl
        extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    /**
     * methodName : findCategoryById
     * author : 김보민
     * description : 카테고리 단건 조회
     *
     * @param categoryNo category no
     * @return optional
     */
    @Override
    public Optional<CategoryResponseDto> findCategoryById(Integer categoryNo) {
        QCategory category = QCategory.category;

        return Optional.of(from(category)
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
     * methodName : findAllCategories
     * author : 김보민
     * description : 카테고리 다건 조회
     *
     * @return list
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
     * methodName : findAllLowerCategories
     * author : 김보민
     * description : 하위 카테고리 조회
     *
     * @param categoryNo category no
     * @return list
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
