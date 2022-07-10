package shop.gaship.gashipshoppingmall.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.category.request.CategoryCreateRequest;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.category.request.CategoryModifyRequest;
import shop.gaship.gashipshoppingmall.category.service.CategoryService;

import java.util.Objects;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.service.impl
 * fileName       : DefaultCategoryService
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * methodName : createCategory
     * author : 김보민
     * description : 카테고리 생성
     *
     * @param request category create request
     */
    @Transactional
    @Override
    public void createCategory(CategoryCreateRequest request) {
        Category upperCategory = null;

        if (Objects.nonNull(request.getUpperCategoryNo())) {
            upperCategory = categoryRepository.findById(request.getUpperCategoryNo()).orElseThrow(CategoryNotFoundException::new);
        }

        Category category = Category.builder()
                .name(request.getName())
                .level(request.getLevel())
                .upperCategory(upperCategory)
                .build();

        categoryRepository.save(category);
    }

    /**
     * methodName : modifyCategory
     * author : 김보민
     * description : 카테고리 수정
     *
     * @param request category modify request
     */
    @Transactional
    @Override
    public void modifyCategory(Integer categoryNo, CategoryModifyRequest request) {
        Category category = categoryRepository.findById(categoryNo).orElseThrow(CategoryNotFoundException::new);

        categoryRepository.save(category.updateCategory(request.getName()));
    }
}
