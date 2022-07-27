package shop.gaship.gashipshoppingmall.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * 회원의 엔티티 클래스입니다.
 *
 * @author 김민수
 * @author 조재철
 * @author 최겸준
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

    private boolean isSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "members_role_set",
        joinColumns = @JoinColumn(name = "member_no")
    )
    @Builder.Default
    private Set<MembersRole> roleSet = new HashSet<>();

    private String encodedEmailForSearch;

    public void modifyMember(MemberModifyRequestDto memberModifyRequestDto) {
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
        return isSocial == member.isSocial && Objects.equals(memberNo, member.memberNo)
            && Objects.equals(recommendMember, member.recommendMember)
            && Objects.equals(memberStatusCodes, member.memberStatusCodes)
            && Objects.equals(memberGrades, member.memberGrades) && Objects.equals(
            memberTags, member.memberTags) && Objects.equals(email, member.email)
            && Objects.equals(password, member.password) && Objects.equals(
            phoneNumber, member.phoneNumber) && Objects.equals(name, member.name)
            && Objects.equals(birthDate, member.birthDate) && Objects.equals(
            nickname, member.nickname) && Objects.equals(gender, member.gender)
            && Objects.equals(accumulatePurchaseAmount, member.accumulatePurchaseAmount)
            && Objects.equals(nextRenewalGradeDate, member.nextRenewalGradeDate)
            && Objects.equals(roleSet, member.roleSet) && Objects.equals(
            encodedEmailForSearch, member.encodedEmailForSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberNo, recommendMember, memberStatusCodes, memberGrades, memberTags,
            email, password, phoneNumber, name, birthDate, nickname, gender,
            accumulatePurchaseAmount,
            nextRenewalGradeDate, isSocial, roleSet, encodedEmailForSearch);
    }
}
