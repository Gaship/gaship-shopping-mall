package shop.gaship.gashipshoppingmall.membergrade.entity;

import javax.persistence.*;
import lombok.*;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.entity
 * fileName       : MemberGrade
 * author         : semi
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        semi       최초 생성
 */
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member_grades")
public class MemberGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renewal_period_no")
    private StatusCode renewalPeriodStatusCode;

    @Setter
    private String name;

    @Setter
    @Column(name = "accumulate_amount")
    private Long accumulateAmount;

    /**
     * Instantiates a new Member grade.
     *
     * @param renewalPeriod    the renewal period
     * @param name             the name
     * @param accumulateAmount the accumulateAmount
     */
    @Builder
    public MemberGrade(StatusCode renewalPeriod, String name, Long accumulateAmount) {
        this.renewalPeriodStatusCode = renewalPeriod;
        this.name = name;
        this.accumulateAmount = accumulateAmount;
    }
}
