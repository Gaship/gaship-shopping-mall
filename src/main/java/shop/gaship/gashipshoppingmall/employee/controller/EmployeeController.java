package shop.gaship.gashipshoppingmall.employee.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;

/**
 * 직원에대한 요청을 처리하기위한 컨트롤러 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    /**
     * post 요청시 직원을 생성하는 메서드입니다.
     *
     * @param dto 직원을 생성하기위한 기본정보들이 포함되어있습니다.
     * @author 유호철
     */
    @PostMapping
    public ResponseEntity<Void> addEmployee(@Valid @RequestBody CreateEmployeeRequestDto dto) {
        employeeService.addEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
            .build();
    }

    /**
     * put 요청이 왔을때 직원에대한 정보를 수정하기위한 메서드입니다.
     *
     * @param dto 직원을 수정하기위한 기본정보들이 포함되어있습니다.
     * @author 유호철
     */
    @PutMapping
    public ResponseEntity<Void> modifyEmployee(@Valid @RequestBody ModifyEmployeeRequestDto dto) {

        employeeService.modifyEmployee(dto);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }

    /**
     * get 요청 단건조회시 직원의 정보를 보여주기위한 메서드입니다.
     *
     * @param employeeNo 조회하기위한 직원번호입니다.
     * @return GetEmployee info response dto
     * @author 유호철
     */
    @GetMapping("/{employeeNo}")
    public ResponseEntity<EmployeeInfoResponseDto> employeeDetails(
        @PathVariable("employeeNo") Integer employeeNo) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(employeeService.findEmployee(employeeNo));
    }

    /**
     * get 요청시 전체 직원에대한 정보들이 반환되는 메서드입니다.
     *
     * @return List<GetEmployee> 모든 직원에대한 정보들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<List<EmployeeInfoResponseDto>> employeeList() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(employeeService.findEmployees());
    }

}
