package shop.gaship.gashipshoppingmall.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainLowerCategoryException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainProductException;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.service.impl.CategoryServiceImpl;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(CategoryServiceImpl.class)
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    private Category upperCategory;
    private Category category;

    @BeforeEach
    void setUp() {
        upperCategory = CategoryDummy.upperDummy();
        category = CategoryDummy.dummy();
    }

    @Test
    @DisplayName("???????????? ??????")
    void addRootCategory() {
        CategoryCreateRequestDto createRequest = new CategoryCreateRequestDto(
                "????????????",
                null
        );
        ReflectionTestUtils.setField(upperCategory, "no", 1);

        when(categoryRepository.save(any(Category.class))).thenReturn(upperCategory);

        categoryService.addRootCategory(createRequest);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("?????? ???????????? ?????? ??????")
    void addLowerCategorySuccess() {
        CategoryCreateRequestDto createRequest = new CategoryCreateRequestDto(
                "????????????",
                1
        );

        when(categoryRepository.findById(createRequest.getUpperCategoryNo())).thenReturn(Optional.of(category));

        categoryService.addLowerCategory(createRequest);

        verify(categoryRepository).findById(createRequest.getUpperCategoryNo());
    }

    @Test
    @DisplayName("?????? ???????????? ?????? ?????? - ?????? ???????????? ?????? ??????")
    void addLowerCategoryFail() {
        CategoryCreateRequestDto createRequest = new CategoryCreateRequestDto(
                "????????????",
                9999
        );

        when(categoryRepository.findById(createRequest.getUpperCategoryNo())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.addLowerCategory(createRequest))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(createRequest.getUpperCategoryNo());
    }

    @Test
    @DisplayName("???????????? ?????? ??????")
    void modifyCategorySuccess() {
        CategoryModifyRequestDto modifyRequest = new CategoryModifyRequestDto(
                1,
                "?????? ????????????"
        );

        Integer categoryNo = modifyRequest.getNo();
        ReflectionTestUtils.setField(upperCategory, "no", categoryNo);

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.of(upperCategory));

        categoryService.modifyCategory(modifyRequest);

        assertThat(upperCategory.getName()).isEqualTo(modifyRequest.getName());

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).save(upperCategory);
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ?????? ???????????? ?????? ??????")
    void modifyCategoryFail() {
        CategoryModifyRequestDto modifyRequest = new CategoryModifyRequestDto(
                9999,
                "?????? ????????????"
        );
        Integer categoryNo = modifyRequest.getNo();

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.modifyCategory(modifyRequest))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(categoryNo);
    }

    @Test
    @DisplayName("???????????? ?????? ??????")
    void findCategory() {
        Integer categoryNo = 1;
        CategoryResponseDto categoryResponseDto = CategoryDummy.upperDtoDummy();

        when(categoryRepository.findCategoryById(categoryNo)).thenReturn(Optional.of(categoryResponseDto));

        assertThat(categoryService.findCategory(categoryNo)).isEqualTo(categoryResponseDto);

        verify(categoryRepository).findCategoryById(categoryNo);
    }

    @Test
    @DisplayName("???????????? ?????? ??????")
    void findCategories() {
        CategoryResponseDto categoryResponseDto = CategoryDummy.upperDtoDummy();

        when(categoryRepository.findAllCategories()).thenReturn(List.of(categoryResponseDto));

        List<CategoryResponseDto> categories = categoryService.findCategories();

        assertThat(categories.get(0)).isEqualTo(categoryResponseDto);

        verify(categoryRepository).findAllCategories();
    }

    @Test
    @DisplayName("?????? ???????????? ??????")
    void findLowerCategories() {
        Integer upperCategoryNo = 1;
        CategoryResponseDto categoryResponseDto = CategoryDummy.dtoDummy();
        ReflectionTestUtils.setField(upperCategory, "no", upperCategoryNo);

        when(categoryRepository.findById(upperCategoryNo)).thenReturn(Optional.of(upperCategory));
        when(categoryRepository.findAllLowerCategories(upperCategoryNo)).thenReturn(List.of(categoryResponseDto));

        List<CategoryResponseDto> categories = categoryService.findLowerCategories(upperCategoryNo);

        assertThat(categories.get(0)).isEqualTo(categoryResponseDto);

        verify(categoryRepository).findById(upperCategoryNo);
        verify(categoryRepository).findAllLowerCategories(upperCategoryNo);
    }

    @Test
    @DisplayName("???????????? ?????? ??????")
    void removeCategorySuccess() {
        ReflectionTestUtils.setField(category, "no", 2);

        when(categoryRepository.findById(category.getNo())).thenReturn(Optional.of(category));
        when(categoryRepository.findAllLowerCategories(category.getNo())).thenReturn(Collections.emptyList());
        when(productRepository.findAllByCategoryNo(category.getNo())).thenReturn(Collections.emptyList());

        categoryService.removeCategory(category.getNo());

        verify(categoryRepository).findById(category.getNo());
        verify(categoryRepository).findAllLowerCategories(category.getNo());
        verify(productRepository).findAllByCategoryNo(category.getNo());
        verify(categoryRepository).deleteById(category.getNo());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ?????? ???????????? ?????? ??????")
    void removeCategoryFail_notFoundCategory() {
        Integer categoryNo = 9999;

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.removeCategory(categoryNo))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(categoryNo);
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ?????? ???????????? ??????")
    void removeCategoryFail_remainLowerCategory() {
        Integer categoryNo = 1;
        CategoryResponseDto lowerCategory = CategoryDummy.dtoDummy();
        ReflectionTestUtils.setField(upperCategory, "no", categoryNo);

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.of(upperCategory));
        when(categoryRepository.findAllLowerCategories(categoryNo)).thenReturn(List.of(lowerCategory));

        assertThatThrownBy(() -> categoryService.removeCategory(categoryNo))
                .isInstanceOf(CategoryRemainLowerCategoryException.class);

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).findAllLowerCategories(categoryNo);
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ?????? ??????????????? ?????? ?????? ??????")
    void removeCategoryFail_remainProduct() {
        Integer categoryNo = 2;
        Product product = ProductDummy.dummy();
        ReflectionTestUtils.setField(category, "no", categoryNo);

        when(categoryRepository.findById(category.getNo())).thenReturn(Optional.of(category));
        when(categoryRepository.findAllLowerCategories(categoryNo)).thenReturn(Collections.emptyList());
        when(productRepository.findAllByCategoryNo(categoryNo)).thenReturn(List.of(product));

        assertThatThrownBy(() -> categoryService.removeCategory(category.getNo()))
                .isInstanceOf(CategoryRemainProductException.class);

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).findAllLowerCategories(categoryNo);
        verify(productRepository).findAllByCategoryNo(category.getNo());
    }
}