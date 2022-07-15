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
 * The type Employee controller.
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    /**
     * methodName : postEmployee
     * author : 유호철
     * description : Employee post 요청
     *
     * @param dto CreateEmployeeDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postEmployee(@Valid @RequestBody CreateEmployeeRequestDto dto) {
        employeeService.createEmployee(dto);
    }

    /**
     * methodName : putEmployee
     * author : 유호철
     * description : Employee put 요청
     *
     * @param dto ModifyEmployeeDto
     */

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void putEmployee(@Valid @RequestBody ModifyEmployeeRequestDto dto) {

        employeeService.modifyEmployee(dto);
    }

    /**
     * methodName : getEmployee
     * author : 유호철
     * description : Employee get 단건 요청
     *
     * @param employeeNo no
     * @return GetEmployee
     */

    @GetMapping("/{employeeNo}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeInfoResponseDto getEmployee(@PathVariable("employeeNo") Integer employeeNo) {

        return employeeService.getEmployee(employeeNo);
    }

    /**
     * methodName : getEmployees
     * author : 유호철
     * description : Employee get 다건 요청
     *
     * @return List<GetEmployee>
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeInfoResponseDto> getEmployees() {

        return employeeService.getAllEmployees();
    }

}
