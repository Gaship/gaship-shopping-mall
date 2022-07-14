package shop.gaship.gashipshoppingmall.repairSchedule.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.pk.RepairSchedulePk;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.entity fileName       :
 * RepairSchedule author         : HoChul date           : 2022/07/09 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/09        HoChul
 * 최초 생성
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

    public RepairSchedule(RepairSchedulePk pk,Integer labor) {
        this.pk = pk;
        this.labor = labor;
    }

    /**
     * methodName : fixDayLabor
     * author : 유호철
     * description : DayLabor 변경시 기입
     *
     * @param dayLabor DayLabor
     */
    public void fixDayLabor(DayLabor dayLabor) {
        this.dayLabor = dayLabor;
    }

    public void fixLabor(Integer labor) {
        this.labor = labor;
    }
}
