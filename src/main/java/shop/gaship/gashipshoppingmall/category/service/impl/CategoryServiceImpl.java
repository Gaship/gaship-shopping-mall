package shop.gaship.gashipshoppingmall.category.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
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

    @Transactional
    @Override
    public void addCategory(CategoryCreateRequestDto request) {
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

    @Transactional
    @Override
    public void modifyCategory(Integer categoryNo, CategoryModifyRequestDto request) {
        Category category = categoryRepository.findById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.save(category.updateCategory(request.getName()));
    }

    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return categoryRepository.findCategoryById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public List<CategoryResponseDto> findCategories() {
        return categoryRepository.findAllCategories();
    }

    @Transactional
    @Override
    public void removeCategory(Integer categoryNo) {
        Category category = categoryRepository.findById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);
        List<CategoryResponseDto> lowerCategories = categoryRepository
                .findLowerCategories(categoryNo);

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
