package shop.gaship.gashipshoppingmall.category.service.impl;

import java.util.List;
import java.util.stream.Collectors;
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
 * 카테고리 서비스 구현체입니다.
 *
 * @author : 김보민
 * @see shop.gaship.gashipshoppingmall.category.service.CategoryService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void addRootCategory(CategoryCreateRequestDto createRequest) {
        Category category = new Category(
            createRequest.getName(),
            1
        );

        categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException 카테고리를 못찾음
     */
    @Transactional
    @Override
    public void addLowerCategory(CategoryCreateRequestDto createRequest) {
        Category upperCategory = categoryRepository.findById(createRequest.getUpperCategoryNo())
            .orElseThrow(CategoryNotFoundException::new);

        Category category = new Category(
            createRequest.getName(),
            upperCategory.getLevel() + 1
        );

        upperCategory.insertLowerCategory(category);
    }

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException 카테고리를 못찾음
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
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException 카테고리를 못찾음
     */
    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return categoryRepository.findCategoryById(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> findCategories() {
        return categoryRepository.findByUpperCategoryNoIsNull().stream()
                .map(CategoryResponseDto::dtoToEntity)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException            카테고리를 못찾음
     * @throws CategoryRemainLowerCategoryException 하위 카테고리가 존재할 시 삭제 불가
     * @throws CategoryRemainProductException       해당 카테고리에 속한 상품이 있을 경우 삭제 불가
     */
    @Transactional
    @Override
    public void removeCategory(Integer categoryNo) {
        Category category = categoryRepository.findById(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);
        List<CategoryResponseDto> lowerCategories = categoryRepository
            .findAllLowerCategories(categoryNo);

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

    /**
     * {@inheritDoc}
     *
     * @throws CategoryNotFoundException 카테고리를 못찾음
     */
    @Override
    public List<CategoryResponseDto> findLowerCategories(Integer categoryNo) {
        Category category = categoryRepository.findById(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);

        return categoryRepository.findAllLowerCategories(category.getNo());
    }
}
