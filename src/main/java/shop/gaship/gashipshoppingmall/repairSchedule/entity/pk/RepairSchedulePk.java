package shop.gaship.gashipshoppingmall.repairSchedule.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.entity.pk
 * fileName       : RepairSchedulePk
 * author         : HoChul
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/09        HoChul     최초 생성
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RepairSchedulePk implements Serializable {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "address_local_no")
    private Integer addressNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepairSchedulePk that = (RepairSchedulePk) o;
        return Objects.equals(date, that.date) && Objects.equals(addressNo,
                that.addressNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, addressNo);
    }
}
