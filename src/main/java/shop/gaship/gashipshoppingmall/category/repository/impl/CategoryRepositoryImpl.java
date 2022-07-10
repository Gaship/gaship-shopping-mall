package shop.gaship.gashipshoppingmall.category.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.entity.QCategory;
import shop.gaship.gashipshoppingmall.category.repository.custom.CategoryRepositoryCustom;

import java.util.List;
import java.util.Optional;

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
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Integer categoryNo) {
        QCategory category = QCategory.category;

        JPQLQuery query = from(category);
        query.leftJoin(category.upperCategory);
        query.select(Projections.bean(CategoryDto.class,
                category.no,
                category.name,
                category.level,
                category.upperCategory.no.as("upperCategoryNo"),
                category.upperCategory.name.as("upperCategoryName")
        ));
        query.where(category.no.eq(categoryNo));

        return Optional.of((CategoryDto) query.fetchOne());
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return null;
    }
}
