package shop.gaship.gashipshoppingmall.membergrade.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import javax.persistence.*;

@Entity
@Table(name = "member_grades")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_no")
    private Integer memberGradeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renewal_period_no")
    private StatusCode renewalPeriodStatusCode;

    private String name;

    @Column(name = "accumulate_amount")
    private Long accumulateAmount;
}