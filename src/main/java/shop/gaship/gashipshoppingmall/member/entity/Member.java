package shop.gaship.gashipshoppingmall.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Integer memberNo;

    @Column(name = "recommend_member_no")
    private Integer recommendMemberNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_no")
    private StatusCode MemberStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_grade_no")
    private MemberGrade memberGrades;

    private String email;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String nickname;

    private String gender;

    @Column(name = "accumulate_purchase_amount")
    private Long accumulatePurchaseAmount;

    @Column(name = "next_renewal_grade_date")
    private LocalDate nextRenewalGradeDate;

    @Column(name = "register_datetime")
    private LocalDateTime registerDatetime;
}
