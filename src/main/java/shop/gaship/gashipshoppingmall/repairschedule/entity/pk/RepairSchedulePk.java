package shop.gaship.gashipshoppingmall.repairschedule.entity.pk;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 직원스케줄에대한 Pk 들이 있는 클래스입니다.
 *
 * @author : 유호철
 * @see Serializable
 * @since 1.0
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
