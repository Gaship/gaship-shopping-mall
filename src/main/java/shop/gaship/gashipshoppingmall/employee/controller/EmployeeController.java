package shop.gaship.gashipshoppingmall.employee.controller;

import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.aspact.anntation.AdminAuthority;
import shop.gaship.gashipshoppingmall.aspact.anntation.ManagerAuthority;
import shop.gaship.gashipshoppingmall.aspact.anntation.ManagerOnlyAuthority;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.InstallOrderRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.InstallOrderPageableDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.InstallOrderResponseDto;
import shop.gaship.gashipshoppingmall.employee.exception.NotMatchRequestData;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 직원에대한 요청을 처리하기위한 컨트롤러 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    /**
     * post 요청시 직원을 생성하는 메서드입니다.
     *
     * @param dto 직원을 생성하기위한 기본정보들이 포함되어있습니다.
     * @author 유호철
     */
    @AdminAuthority
    @PostMapping
    public ResponseEntity<Void> addEmployee(@Valid @RequestBody CreateEmployeeRequestDto dto) {
        employeeService.addEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * put 요청이 왔을때 직원에대한 정보를 수정하기위한 메서드입니다.
     *
     * @param dto 직원을 수정하기위한 기본정보들이 포함되어있습니다.
     * @author 유호철
     */
    @ManagerAuthority
    @PutMapping("/{employeeNo}")
    public ResponseEntity<Void> modifyEmployee(@PathVariable("employeeNo") Integer employeeNo,
                                               @Valid @RequestBody ModifyEmployeeRequestDto dto) {

        employeeService.modifyEmployee(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * get 요청 단건조회시 직원의 정보를 보여주기위한 메서드입니다.
     *
     * @param employeeNo 조회하기위한 직원번호입니다.
     * @return GetEmployee info response dto
     * @author 유호철
     */
    @ManagerAuthority
    @GetMapping("/{employeeNo}")
    public ResponseEntity<EmployeeInfoResponseDto> employeeDetails(
        @PathVariable("employeeNo") Integer employeeNo) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(employeeService.findEmployee(employeeNo));
    }

    /**
     * get 요청시 전체 직원에대한 정보들이 반환되는 메서드입니다.
     *
     * @return 모든 직원에대한 정보들이 반환됩니다.
     * @author 유호철
     */
    @AdminAuthority
    @GetMapping
    public ResponseEntity<PageResponse<EmployeeInfoResponseDto>> employeeList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(employeeService.findEmployees(pageable));
    }

    /**
     * get 요청시 직원 로그인을 요청하는 직원의 상세 정보가 반환되는 메서드입니다.
     *
     * @param email 로그인 할 직원의 이메일입니다.
     * @return 로그인에 필요한 직원의 상세정보가 담긴 객체가 반환됩니다.
     */
    @GetMapping(value = "/user-detail", params = "email")
    public ResponseEntity<SignInUserDetailsDto> employeeSignInRequest(@RequestParam String email) {
        return ResponseEntity.ok(employeeService.findSignInEmployeeFromEmail(email));
    }

    /**
     * 직원의 가입 위치 정보를 기반으로 해당 지역(광역시, 도)의 설치주문,
     * 배송준비중상태인 주문들을 조회 할 수 있는 메서드입니다.
     *
     * @param employeeNo 직원의 고유 번호입니다.
     * @param pageable 페이징 객체입니다.
     * @return 지역의 상태는 배송준비중이자 설치주문들을 조회합니다. 주문일자를 기준, 오름차순으로 정렬합니다.
     */
    @ManagerOnlyAuthority
    @GetMapping("/{employeeNo}/orders")
    public ResponseEntity<InstallOrderPageableDto> employeeInstallOrdersFind(
        @PathVariable Integer employeeNo, Pageable pageable) {
        Page<InstallOrderResponseDto> installOrders =
            employeeService.findInstallOrdersFromEmployeeLocation(pageable, employeeNo);

        return ResponseEntity.ok(
            new InstallOrderPageableDto(
                installOrders.getContent(),
                installOrders.getTotalPages(),
                installOrders.getSize(),
                installOrders.hasPrevious(),
                installOrders.hasNext()));
    }

    /**
     * 직원이 시공 주문에 대해 수락을 하는 메서드입니다.
     *
     * @param employeeNo 직원의 고유번호입니다.
     * @param orderNo 주문 고유번호입니다.
     * @param installOrderRequestDto 설치할 주문과 설치 배송을 수락하는 직원의 정보가 담긴 객체입니다.
     * @return 200 상태이고 body는 비어있는 응답객체를 반환합니다.
     * @throws NotMatchRequestData path variable과 body 객체에 담긴 정보가 다를 경우 해당 예외를 던집니다.
     */
    @ManagerOnlyAuthority
    @PostMapping("/{employeeNo}/orders/{orderNo}")
    public ResponseEntity<Void> acceptInstallOrder(
        @PathVariable Integer employeeNo, @PathVariable Integer orderNo,
        @Valid @RequestBody InstallOrderRequestDto installOrderRequestDto) {
        checkSameRequestAndPathVariable(employeeNo, orderNo, installOrderRequestDto);

        employeeService.acceptInstallOrder(employeeNo, orderNo);

        return ResponseEntity.ok().build();
    }


    /**
     * 설치 및 배송이 끝난 뒤 직원의 설치 및 배송완료 요청을 받는 메서드입니다.
     *
     * @param employeeNo 직원의 고유번호입니다.
     * @param orderNo 주문 고유번호입니다.
     * @param installOrderRequestDto 요청한 설치 주문 정보입니다.
     * @return 요청이 완료되면 200 상태와 body는 비어있는 응답객체를 반환합니다.
     */
    @ManagerAuthority
    @PutMapping("/{employeeNo}/orders/{orderNo}")
    public ResponseEntity<Void> deliveryComplete(
        @PathVariable Integer employeeNo, @PathVariable Integer orderNo,
        @Valid @RequestBody InstallOrderRequestDto installOrderRequestDto) {
        checkSameRequestAndPathVariable(employeeNo, orderNo, installOrderRequestDto);

        employeeService.completeDelivery(employeeNo, orderNo);

        return ResponseEntity.ok().build();
    }


    /**
     * 요청한 정보와 url path variable과 같은지 비교합니다.
     *
     * @param employeeNo 직원의 고유번호입니다.
     * @param orderNo 주문 고유번호입니다.
     * @param installOrderRequestDto 요청한 설치 주문 정보입니다.
     */
    private void checkSameRequestAndPathVariable(Integer employeeNo, Integer orderNo,
                                                 InstallOrderRequestDto installOrderRequestDto) {
        if (!Objects.equals(employeeNo, installOrderRequestDto.getEmployeeNo())
            || !Objects.equals(orderNo, installOrderRequestDto.getOrderNo())) {
            String message = "직원번호 또는 주문 번호가 요청과 일치하지않습니다.";
            throw new NotMatchRequestData(message);
        }
    }
}
