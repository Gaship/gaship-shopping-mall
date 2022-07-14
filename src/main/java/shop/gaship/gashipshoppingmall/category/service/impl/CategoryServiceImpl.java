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

    /**
     * methodName : addCategory
     * author : 김보민
     * description : 카테고리 생성
     *
     * @param createRequest category create request
     */
    @Transactional
    @Override
    public void addCategory(CategoryCreateRequestDto createRequest) {
        Category upperCategory = null;

        if (Objects.nonNull(createRequest.getUpperCategoryNo())) {
            upperCategory = categoryRepository.findById(createRequest.getUpperCategoryNo())
                    .orElseThrow(CategoryNotFoundException::new);
        }

        Category category = Category.builder()
                .name(createRequest.getName())
                .level(createRequest.getLevel())
                .build();

        category.updateUpperCategory(upperCategory);

        categoryRepository.save(category);
    }

    /**
     * methodName : modifyCategory
     * author : 김보민
     * description : 카테고리 수정
     *
     * @param modifyRequest category modify request
     */
    @Transactional
    @Override
    public void modifyCategory(CategoryModifyRequestDto modifyRequest) {
        Category category = categoryRepository.findById(modifyRequest.getNo())
                .orElseThrow(CategoryNotFoundException::new);

        category.updateCategoryName(modifyRequest.getName());

        categoryRepository.save(category);
    }

    /**
     * methodName : findCategory
     * author : 김보민
     * description : 카테고리 단건 조회
     *
     * @param categoryNo category no
     */
    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return categoryRepository.findCategoryById(categoryNo)
                .orElseThrow(CategoryNotFoundException::new);
    }

    /**
     * methodName : findCategories
     * author : 김보민
     * description : 카테고리 다건 조회
     *
     */
    @Override
    public List<CategoryResponseDto> findCategories() {
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
        List<CategoryResponseDto> lowerCategories = categoryRepository
                .findLowerCategories(categoryNo);

        //해당 카테고리의 하위 카테고리가 존재할 시 삭제 실패
        if (!lowerCategories.isEmpty()) {
            throw new CategoryRemainLowerCategoryException();
        }

        List<Product> products = productRepository.findAllByCategoryNo(category.getNo());

        //해당 카테고리에 속한 상품이 존재할 시 삭제 실패
        if (!products.isEmpty()) {
            throw new CategoryRemainProductException();
        }

        categoryRepository.deleteById(categoryNo);
    }
}
