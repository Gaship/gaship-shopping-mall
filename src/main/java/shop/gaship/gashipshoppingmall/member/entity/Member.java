package shop.gaship.gashipshoppingmall.member.entity;

import com.google.common.base.Objects;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

@Entity(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @ManyToOne
    @JoinColumn(name = "recommend_member_no")
    private Member recommendMember;

    @ManyToOne
    @JoinColumn(name = "member_status_no", nullable = false)
    private StatusCode status;

    @ManyToOne
    @JoinColumn(name = "member_grade_no", nullable = false)
    private MemberGrade grade;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equal(memberNo, member.memberNo) &&
            Objects.equal(recommendMember, member.recommendMember) &&
            Objects.equal(status, member.status) &&
            Objects.equal(grade, member.grade) &&
            Objects.equal(email, member.email) &&
            Objects.equal(password, member.password) &&
            Objects.equal(phoneNumber, member.phoneNumber) &&
            Objects.equal(name, member.name) &&
            Objects.equal(birthDate, member.birthDate) &&
            Objects.equal(nickName, member.nickName) &&
            Objects.equal(gender, member.gender) &&
            Objects.equal(totalPurchaseAmount, member.totalPurchaseAmount) &&
            Objects.equal(nextRenewalGradeDate, member.nextRenewalGradeDate) &&
            Objects.equal(registerDateTime, member.registerDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberNo, recommendMember, status, grade, email, password,
            phoneNumber, name, birthDate, nickName, gender, totalPurchaseAmount,
            nextRenewalGradeDate,
            registerDateTime);
    }
}