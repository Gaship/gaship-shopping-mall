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
import java.util.Objects;

/**
 *
 * 카테고리 컨트롤러
 *
 * @author : 김보민
 * @since 1.0
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     *
     * 카테고리 post 요청 매핑
     * 요청 바디를 확인하여 최상위, 하위 카테고리 생성을 구분합니다.
     *
     * @param createRequest 카테고리 생성 요청 바디
     * @return responseEntity 응답 바디는 없습니다.
     * @author 김보민
     */
    @PostMapping
    public ResponseEntity<Void> categoryAdd(@Valid @RequestBody CategoryCreateRequestDto createRequest) {
        if (Objects.isNull(createRequest.getUpperCategoryNo())) {
            categoryService.addRootCategory(createRequest);
        } else {
            categoryService.addLowerCategory(createRequest);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     *
     * 카테고리 put 요청 매핑
     *
     * @param modifyRequest 카테고리 수정 요청 바디
     * @return responseEntity 응답 바디는 없습니다.
     * @author 김보민
     */
    @PutMapping
    public ResponseEntity<Void> categoryModify(@Valid @RequestBody CategoryModifyRequestDto modifyRequest) {
        categoryService.modifyCategory(modifyRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


    /**
     *
     * 카테고리 단건 get 요청 매핑
     *
     * @param categoryNo 조회할 카테고리 번호
     * @return responseEntity 조회한 단건의 카테고리를 포함하고 있습니다.
     * @author 김보민
     */
    @GetMapping("/{categoryNo}")
    public ResponseEntity<CategoryResponseDto> categoryDetails(@PathVariable("categoryNo") Integer categoryNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findCategory(categoryNo));
    }


    /**
     *
     * 카테고리 전체 get 요청 매핑
     *
     * @return responseEntity 조회한 전체 카테고리 목록을 포함하고 있습니다.
     * @author 김보민
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> categoryList() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findCategories());
    }


    /**
     *
     * 하위 카테고리 get 요청 매핑
     *
     * @param categoryNo 하위 카테고리를 조회할 부모 카테고리 번호
     * @return response entity 조회한 하위 카테고리 목록을 포함하고 있습니다.
     * @author 김보민
     */
    @GetMapping("/{categoryNo}/lower")
    public ResponseEntity<List<CategoryResponseDto>> lowerCategoryList(@PathVariable Integer categoryNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findLowerCategories(categoryNo));
    }

    /**
     *
     * 카테고리 delete 매핑
     *
     * @param categoryNo 삭제할 카테고리 번호
     * @return response entity 응답 바디는 없습니다.
     * @author 김보민
     */
    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<Void> categoryRemove(@PathVariable("categoryNo") Integer categoryNo) {
        categoryService.removeCategory(categoryNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     *
     * 예외 처리
     *
     * @param ex 처리할 예외
     * @return responseEntity 에러 객체를 포함하고 있습니다.
     * @author 김보민
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
