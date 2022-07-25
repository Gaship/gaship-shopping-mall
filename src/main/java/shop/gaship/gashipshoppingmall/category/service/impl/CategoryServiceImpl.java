package shop.gaship.gashipshoppingmall.category.service.impl;

import java.util.List;
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
     * root 카테고리 추가 메서드입니다.
     *
     * @param createRequest 카테고리 생성 요청
     * @author 김보민
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
     * 하위 카테고리 추가 메서드입니다.
     *
     * @param createRequest 카테고리 생성 요청
     * @throws CategoryNotFoundException 카테고리를 못찾음
     * @author 김보민
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
     * 카테고리 수정 메서드입니다.
     *
     * @param modifyRequest 카테고리 수정 요청
     * @throws CategoryNotFoundException 카테고리를 못찾음
     * @author 김보민
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
     * 카테고리 단건 조회 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호
     * @return CategoryResponseDto 카테고리 데이터
     * @throws CategoryNotFoundException 카테고리를 못찾음
     * @author 김보민
     */
    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return categoryRepository.findCategoryById(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);
    }

    /**
     * 카테고리 전체 조회 메서드입니다.
     *
     * @return list 카테고리 목록 데이터
     * @author 김보민
     */
    @Override
    public List<CategoryResponseDto> findCategories() {
        return categoryRepository.findAllCategories();
    }

    /**
     * 하위 카테고리 조회 메서드입니다.
     *
     * @param categoryNo 하위카테고리를 조회할 상위 카테고리 번호
     * @throws CategoryNotFoundException            카테고리를 못찾음
     * @throws CategoryRemainLowerCategoryException 하위 카테고리가 존재할 시 삭제 불가
     * @throws CategoryRemainProductException       해당 카테고리에 속한 상품이 있을 경우 삭제 불가
     * @author 김보민
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
     * 하위 카테고리 조회 메서드입니다.
     *
     * @param categoryNo 하위 카테고리를 조회할 상위 카테고리
     * @return list 하위 카테고리 목록
     * @throws CategoryNotFoundException 카테고리를 못찾음
     * @author 김보민
     */
    @Override
    public List<CategoryResponseDto> findLowerCategories(Integer categoryNo) {
        Category category = categoryRepository.findById(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);

        return categoryRepository.findAllLowerCategories(category.getNo());
    }
}
