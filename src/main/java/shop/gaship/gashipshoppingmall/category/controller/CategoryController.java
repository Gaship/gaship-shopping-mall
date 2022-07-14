package shop.gaship.gashipshoppingmall.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.service.CategoryService;
import shop.gaship.gashipshoppingmall.message.ErrorResponse;

import javax.validation.Valid;
import java.util.List;

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
     * methodName : categoryAdd
     * author : 김보민
     * description : 카테고리 post 요청 매핑
     *
     * @param createRequest category create request
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<Void> categoryAdd(@Valid @RequestBody CategoryCreateRequestDto createRequest) {
        categoryService.addCategory(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * methodName : categoryModify
     * author : 김보민
     * description : 카테고리 put 요청 매핑
     *
     * @param modifyRequest category modify request
     * @return response entity
     */
    @PutMapping
    public ResponseEntity<Void> categoryModify(@Valid @RequestBody CategoryModifyRequestDto modifyRequest) {
        categoryService.modifyCategory(modifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


    /**
     * methodName : categoryDetails
     * author : 김보민
     * description : 카테고리 단건 get 요청 매핑
     *
     * @param categoryNo category no
     * @return response entity
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CategoryResponseDto> categoryDetails(@PathVariable("categoryNo") Integer categoryNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findCategory(categoryNo));
    }


    /**
     * methodName : categoryList
     * author : 김보민
     * description : 카테고리 전체 get 요청 매핑
     *
     * @return response entity
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> categoryList() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findCategories());
    }

    /**
     * methodName : categoryRemove
     * author : 김보민
     * description : 카테고리 delete 요청 매핑
     *
     * @param categoryNo category no
     * @return response entity
     */
    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<Void> categoryRemove(@PathVariable("categoryNo") Integer categoryNo) {
        categoryService.removeCategory(categoryNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * methodName : handleException
     * author : 김보민
     * description : 예외 처리
     *
     * @param ex exception
     * @return response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
