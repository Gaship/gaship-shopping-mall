package shop.gaship.gashipshoppingmall.member.entity;

import com.google.common.base.Objects;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * 회원의 엔티티 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Entity
@Table(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_member_no")
    private Member recommendMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_no", nullable = false)
    private StatusCode memberStatusCodes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_grade_no", nullable = false)
    private MemberGrade memberGrades;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTag> memberTags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_authority_no", nullable = false)
    private StatusCode userAuthorityNo;

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

    private Boolean isSocial;

    public void modifyMember(MemberModifyRequestDto memberModifyRequestDto) {
        this.memberGrades = memberModifyRequestDto.getMemberGrade();
        this.memberStatusCodes = memberModifyRequestDto.getStatusCode();
        this.email = memberModifyRequestDto.getEmail();
        this.password = memberModifyRequestDto.getPassword();
        this.phoneNumber = memberModifyRequestDto.getPhoneNumber();
        this.name = memberModifyRequestDto.getName();
        this.nickname = memberModifyRequestDto.getNickname();
        this.gender = memberModifyRequestDto.getGender();
    }

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
            Objects.equal(memberStatusCodes, member.memberStatusCodes) &&
            Objects.equal(memberGrades, member.memberGrades) &&
            Objects.equal(email, member.email) &&
            Objects.equal(password, member.password) &&
            Objects.equal(phoneNumber, member.phoneNumber) &&
            Objects.equal(name, member.name) &&
            Objects.equal(birthDate, member.birthDate) &&
            Objects.equal(nickname, member.nickname) &&
            Objects.equal(gender, member.gender) &&
            Objects.equal(accumulatePurchaseAmount,
                member.accumulatePurchaseAmount) &&
            Objects.equal(nextRenewalGradeDate, member.nextRenewalGradeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberNo, recommendMember, memberStatusCodes, memberGrades, email,
            password, phoneNumber, name, birthDate, nickname, gender, accumulatePurchaseAmount,
            nextRenewalGradeDate);
    }
}
