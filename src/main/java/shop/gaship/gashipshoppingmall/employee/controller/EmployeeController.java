package shop.gaship.gashipshoppingmall.employee.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.dto.GetEmployee;
import shop.gaship.gashipshoppingmall.employee.dto.ModifyEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;

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
    public void postEmployee(@Valid @RequestBody CreateEmployeeDto dto){
        employeeService.createEmployee(dto);
    }

    /**
     * methodName : putEmployee
     * author : 유호철
     * description : Employee put 요청
     *
     * @param employeeNo no
     * @param dto ModifyEmployeeDto
     */

    @PutMapping("/{employeeNo}")
    @ResponseStatus(HttpStatus.OK)
    public void putEmployee(@PathVariable("employeeNo") Integer employeeNo,
                            @Valid @RequestBody ModifyEmployeeDto dto){

        employeeService.modifyEmployee(employeeNo,dto);
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
    public GetEmployee getEmployee(@PathVariable("employeeNo") Integer employeeNo){

        return employeeService.getEmployee(employeeNo);
    }

    /**
     * methodName : getEmployees
     * author : 유호철
     * description : Employee get 다건 요청
     *
     *
     * @return List<GetEmployee>
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GetEmployee> getEmployees(){

        return employeeService.getAllEmployees();
    }

}
