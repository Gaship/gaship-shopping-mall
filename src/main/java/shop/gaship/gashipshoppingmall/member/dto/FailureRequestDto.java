package shop.gaship.gashipshoppingmall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dto <br/>
 * fileName       : FailureRequestDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
@Getter
@AllArgsConstructor
public class FailureRequestDto {
    private String requestStatus;
    private String message;
}
