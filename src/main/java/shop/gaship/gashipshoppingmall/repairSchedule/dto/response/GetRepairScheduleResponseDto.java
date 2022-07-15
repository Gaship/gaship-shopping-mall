package shop.gaship.gashipshoppingmall.repairSchedule.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 스케줄에대한 정보를 반환받기위한 클래스입니다.
 *
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
