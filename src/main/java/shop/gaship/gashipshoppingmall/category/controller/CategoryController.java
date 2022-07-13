package shop.gaship.gashipshoppingmall.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.service.CategoryService;
import shop.gaship.gashipshoppingmall.message.Response;
import javax.validation.Valid;
import java.util.Collections;
import java.util.LinkedHashMap;
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
    private final Response responseBody = new Response();

    /**
     * methodName : categoryAdd
     * author : 김보민
     * description : 카테고리 post 요청 매핑
     *
     * @param request category create request
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<?> categoryAdd(@Valid @RequestBody CategoryCreateRequestDto request) {
        categoryService.addCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody.success(Collections.emptyList(), null, HttpStatus.CREATED));
    }

    /**
     * methodName : categoryModify
     * author : 김보민
     * description : 카테고리 put 요청 매핑
     *
     * @param categoryNo category no
     * @param request category modify request
     * @return response entity
     */
    @PutMapping("/{categoryNo}")
    public ResponseEntity<?> categoryModify(@PathVariable("categoryNo") Integer categoryNo,
                                               @Valid @RequestBody CategoryModifyRequestDto request) {
        categoryService.modifyCategory(categoryNo, request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody.success());
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
    public ResponseEntity<?> categoryDetails(@PathVariable("categoryNo") Integer categoryNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody.success(categoryService.findCategory(categoryNo)));
    }


    /**
     * methodName : categoryList
     * author : 김보민
     * description : 카테고리 전체 get 요청 매핑
     *
     * @return response entity
     */
    @GetMapping
    public ResponseEntity<?> categoryList() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody.success(categoryService.findCategories()));
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
    public ResponseEntity<?> categoryRemove(@PathVariable("categoryNo") Integer categoryNo) {
        categoryService.removeCategory(categoryNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody.success());
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
    public ResponseEntity<?> handleException(Exception ex) {
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        errors.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody.invalidFields(List.of(errors)));
    }
}
