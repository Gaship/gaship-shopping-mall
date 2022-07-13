package shop.gaship.gashipshoppingmall.statuscode.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.entity
 * fileName       : StatusCode
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@Entity
@Table(name = "status_codes")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class StatusCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_code_no")
    private Integer statusCodeNo;

    @Column(name = "status_code_name")
    private String statusCodeName;

    @Column(name = "is_used")
    private Boolean isUsed;

    private Integer priority;

    @Column(name = "group_code_name")
    private String groupCodeName;

    private String explanation;

    @Builder
    public StatusCode(String statusCodeName, Integer priority, String groupCodeName, String explanation) {
        this.statusCodeName = statusCodeName;
        this.isUsed = true;
        this.priority = priority;
        this.groupCodeName = groupCodeName;
        this.explanation = explanation;
    }

    @Builder(builderMethodName = "allBuilder")
    public StatusCode(Integer statusCodeNo, String statusCodeName, Boolean isUsed, Integer priority, String groupCodeName, String explanation) {
        this.statusCodeNo = statusCodeNo;
        this.statusCodeName = statusCodeName;
        this.isUsed = isUsed;
        this.priority = priority;
        this.groupCodeName = groupCodeName;
        this.explanation = explanation;
    }
}
