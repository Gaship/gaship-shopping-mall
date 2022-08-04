package shop.gaship.gashipshoppingmall.repairschedule.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 스케줄에대한 정보를 반환받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class GetRepairScheduleResponseDto {
    private String localName;

    private LocalDate localDate;

    private Integer labor;
}
