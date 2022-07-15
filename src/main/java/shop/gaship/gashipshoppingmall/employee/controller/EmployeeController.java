package shop.gaship.gashipshoppingmall.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void postEmployee(@Valid @RequestBody CreateEmployeeRequestDto dto) {
        employeeService.createEmployee(dto);
    }

    /**
     * put 요청이 왔을때 직원에대한 정보를 수정하기위한 메서드입니다.
     *
     * @param dto 직원을 수정하기위한 기본정보들이 포함되어있습니다.
     * @author 유호철
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void putEmployee(@Valid @RequestBody ModifyEmployeeRequestDto dto) {

        employeeService.modifyEmployee(dto);
    }

    /**
     * get 요청 단건조회시 직원의 정보를 보여주기위한 메서드입니다.
     *
     * @param employeeNo 조회하기위한 직원번호입니다.
     * @return GetEmployee info response dto
     * @author 유호철
     */
    @GetMapping("/{employeeNo}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeInfoResponseDto getEmployee(@PathVariable("employeeNo") Integer employeeNo) {

        return employeeService.getEmployee(employeeNo);
    }

    /**
     * get 요청시 전체 직원에대한 정보들이 반환되는 메서드입니다.
     *
     * @return List<GetEmployee> 모든 직원에대한 정보들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeInfoResponseDto> getEmployees() {

        return employeeService.getAllEmployees();
    }

}
