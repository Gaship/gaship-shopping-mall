package shop.gaship.gashipshoppingmall.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
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
    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;

    @NotNull
    private String name;

    private LocalDate birthDate;

    @Column(unique = true)
    @NotNull
    private String nickname;

    private String gender;

    @NotNull
    private Long accumulatePurchaseAmount;

    @NotNull
    private LocalDate nextRenewalGradeDate;

    @NotNull
    private Boolean isSocial;

    /**
     * 멤버의 정보를 변경하는 메서드입니다.
     *
     * @param memberModifyRequestDto 변경할 멤버의 정보가 담긴 객체입니다.
     */
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


    /**
     * 관리자가 회원정보를 변경하기 위한 메서드입니다.
     *
     * @param nickname   변경할 닉네임입니다.
     * @param statusCode 변경할 상태정보입니다.
     */
    public void modifyMemberByAdmin(String nickname, StatusCode statusCode) {
        this.nickname = nickname;
        this.memberStatusCodes = statusCode;
    }
}
