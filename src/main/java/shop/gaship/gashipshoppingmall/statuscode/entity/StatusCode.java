package shop.gaship.gashipshoppingmall.statuscode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 상태코드 entity class.
 *
 * @author : 김세미
 * @since 1.0
 */
@Entity
@Table(name = "status_codes")
@NoArgsConstructor
@Getter
public class StatusCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_code_no")
    private Integer statusCodeNo;

    @Column(name = "status_code_name", nullable = false)
    private String statusCodeName;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @NotNull
    private Integer priority;

    @Column(name = "group_code_name", nullable = false)
    private String groupCodeName;

    private String explanation;

    /**
     * Instantiates a new Status code.
     *
     * @param statusCodeName the status code name
     * @param priority       the priority
     * @param groupCodeName  the group code name
     * @param explanation    the explanation
     */
    @Builder
    public StatusCode(String statusCodeName, Integer priority,
                      String groupCodeName, String explanation) {
        this.statusCodeName = statusCodeName;
        this.isUsed = true;
        this.priority = priority;
        this.groupCodeName = groupCodeName;
        this.explanation = explanation;
    }

    public void modifyRenewalPeriod(String period) {
        this.statusCodeName = period;
    }
}
