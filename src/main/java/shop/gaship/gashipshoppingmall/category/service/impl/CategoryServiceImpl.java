package shop.gaship.gashipshoppingmall.category.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.category.dto.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainLowerCategoryException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainProductException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.category.service.CategoryService;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.service.impl
 * fileName       : CategoryServiceImpl
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
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * methodName : createCategory
     * author : 김보민
     * description : 카테고리 생성
     *
     * @param request category create request
     */
    @Transactional
    @Override
    public void createCategory(CategoryCreateRequestDto request) {
        Category upperCategory = null;

        if (Objects.nonNull(request.getUpperCategoryNo())) {
            upperCategory = categoryRepository.findById(request.getUpperCategoryNo())
                    .orElseThrow(CategoryNotFoundException::new);
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
     * @param categoryNo category no
     * @param request category modify request
     */
    @Transactional
    @Override
    public void modifyCategory(Integer categoryNo, CategoryModifyRequestDto request) {
        Category category = categoryRepository.findById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.save(category.updateCategory(request.getName()));
    }

    /**
     * methodName : getCategory
     * author : 김보민
     * description : 카테고리 단건 조회
     *
     * @param categoryNo category no
     */
    @Override
    public CategoryDto getCategory(Integer categoryNo) {
        return categoryRepository.findCategoryById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);
    }

    /**
     * methodName : getCategories
     * author : 김보민
     * description : 카테고리 다건 조회
     *
     */
    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAllCategories();
    }

    /**
     * methodName : removeCategory
     * author : 김보민
     * description : 카테고리 삭제
     *
     * @param categoryNo category no
     */
    @Transactional
    @Override
    public void removeCategory(Integer categoryNo) {
        Category category = categoryRepository.findById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);
        List<CategoryDto> lowerCategories = categoryRepository.findLowerCategories(categoryNo);

        if (!lowerCategories.isEmpty()) {
            throw new CategoryRemainLowerCategoryException();
        }

        List<Product> products = productRepository.findAllByCategoryNo(category.getNo());

        if (!products.isEmpty()) {
            throw new CategoryRemainProductException();
        }

        categoryRepository.deleteById(categoryNo);
    }
}
