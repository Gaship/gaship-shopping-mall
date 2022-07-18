package shop.gaship.gashipshoppingmall.repairSchedule.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.pk.RepairSchedulePk;

import javax.persistence.*;

/**
 * 데이터베이스에있는 직원스케줄을 사용하기위한 클래스입니다.
 *
 *
 * @author : 유호철
 * @since 1.0
 */
@Entity
@Table(name = "repair_schedules")
@Getter
@NoArgsConstructor
public class RepairSchedule {
    @EmbeddedId
    public RepairSchedulePk pk;

    @Column(name = "labor")
    private Integer labor;

    @MapsId("addressNo")
    @JoinColumn(name = "address_local_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private DayLabor dayLabor;

    @Builder
    public RepairSchedule(RepairSchedulePk pk, Integer labor, DayLabor dayLabor) {
        this.pk = pk;
        this.labor = labor;
        this.dayLabor = dayLabor;
    }

    public RepairSchedule(RepairSchedulePk pk, Integer labor) {
        this.pk = pk;
        this.labor = labor;
    }

    /**
     * 지역별물량이 변경될경우 사용되는 메서드입니다.
     *
     *
     * @param dayLabor 지역별 물량
     * @author 유호철
     */
    public void fixDayLabor(DayLabor dayLabor) {
        this.dayLabor = dayLabor;
    }

    /**
     * 스케줄의 건수가 변경될때 사용되는 메서드입니다.
     *
     * @param labor 변경될 건수
     * @author 유호철
     */
    public void fixLabor(Integer labor) {
        this.labor = labor;
    }
}
