package shop.gaship.gashipshoppingmall.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @Column(name = "recommend_member_no")
    private Integer recommendMemberNo;

    @Column(name = "member_status_no", nullable = false)
    private Integer statusNo;

    @Column(name = "member_grade_no", nullable = false)
    private Integer gradeNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "accumulate_purchase_amount", nullable = false)
    private Long totalPurchaseAmount;

    @Column(name = "next_renewal_grade_date", nullable = false)
    private LocalDate nextRenewalGradeDate;

    @Column(name = "register_datetime", nullable = false)
    private LocalDateTime registerDateTime;
}
