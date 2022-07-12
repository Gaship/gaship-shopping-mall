package shop.gaship.gashipshoppingmall.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.category.dto.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.service.CategoryService;

import javax.validation.Valid;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.controller
 * fileName       : CategoryController
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * methodName : createCategory
     * author : 김보민
     * description : 카테고리 post 요청 매핑
     *
     * @param request category create request
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryCreateRequestDto request) {
        categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * methodName : modifyCategory
     * author : 김보민
     * description : 카테고리 put 요청 매핑
     *
     * @param categoryNo category no
     * @param request category modify request
     * @return response entity
     */
    @PutMapping("/{categoryNo}")
    public ResponseEntity<Void> modifyCategory(@PathVariable("categoryNo") Integer categoryNo,
                                               @Valid @RequestBody CategoryModifyRequestDto request) {
        categoryService.modifyCategory(categoryNo, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


    /**
     * methodName : getCategory
     * author : 김보민
     * description : 카테고리 단건 get 요청 매핑
     *
     * @param categoryNo category no
     * @return response entity
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryNo") Integer categoryNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getCategory(categoryNo));
    }


    /**
     * methodName : getCategories
     * author : 김보민
     * description : 카테고리 전체 get 요청 매핑
     *
     * @return response entity
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getCategories());
    }

    /**
     * methodName : removeCategory
     * author : 김보민
     * description : 카테고리 delete 요청 매핑
     *
     * @param categoryNo category no
     * @return response entity
     */
    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<Void> removeCategory(@PathVariable("categoryNo") Integer categoryNo) {
        categoryService.removeCategory(categoryNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
