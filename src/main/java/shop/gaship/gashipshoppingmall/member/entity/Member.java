package shop.gaship.gashipshoppingmall.member.entity;

import com.google.common.base.Objects;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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


/**
 * 회원의 엔티티 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Entity(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_member_no")
    private Member recommendMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_no", nullable = false)
    private StatusCode status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_grade_no", nullable = false)
    private MemberGrade grade;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String name;

    private LocalDate birthDate;

    @Column(unique = true)
    private String nickname;

    private String gender;

    private Long accumulatePurchaseAmount;

    private LocalDate nextRenewalGradeDate;

    private LocalDateTime registerDatetime;

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
            Objects.equal(nickname, member.nickname) &&
            Objects.equal(gender, member.gender) &&
            Objects.equal(accumulatePurchaseAmount, member.accumulatePurchaseAmount) &&
            Objects.equal(nextRenewalGradeDate, member.nextRenewalGradeDate) &&
            Objects.equal(registerDatetime, member.registerDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberNo, recommendMember, status, grade, email, password,
            phoneNumber, name, birthDate, nickname, gender, accumulatePurchaseAmount,
            nextRenewalGradeDate,
            registerDatetime);
    }
}
