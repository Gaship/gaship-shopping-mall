package shop.gaship.gashipshoppingmall.repairSechedule.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.repairSechedule.entity.pk.RepairSchedulePk;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.entity fileName       :
 * RepairSchedule author         : HoChul date           : 2022/07/09 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/09        HoChul
 * 최초 생성
 */
@Entity
@Table(name = "repair_schedules")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairSchedule {
    @EmbeddedId
    public RepairSchedulePk pk;

    @Column(name = "labor")
    private Integer labor;

    @MapsId("addressNo")
    @JoinColumn(name = "address_local_no")
    @ManyToOne(fetch = FetchType.LAZY)
    private DayLabor dayLabor;


}
