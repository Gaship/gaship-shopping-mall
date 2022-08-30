package shop.gaship.gashipshoppingmall.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * 회원의 엔티티 클래스입니다.
 *
 * @author 김민수
 * @author 조재철
 * @author 최겸준
 * @author 김세미
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MemberTag> memberTags = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<GradeHistory> gradeHistories = new ArrayList<>();

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

    private boolean isSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "members_role_set",
        joinColumns = @JoinColumn(name = "member_no")
    )
    @Builder.Default
    private List<MembersRole> roleSet = new ArrayList<>();

    private String encodedEmailForSearch;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Inquiry> inquiries = new ArrayList<>();

    /**
     * 멤버의 정보를 변경하는 메서드입니다.
     *
     * @param memberModifyRequestDto 변경할 멤버의 정보가 담긴 객체입니다.
     */
    public void modifyMember(MemberModifyRequestDto memberModifyRequestDto) {
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

    /**
     * 회원의 비밀번호 변경.
     *
     * @param hashedPassword 암호화된 비밀번호입니다.
     */
    public void modifyMemberPassword(String hashedPassword) {
        this.password = hashedPassword;
    }


    /**
     * 회원의 비밀번호 변경.
     *
     * @param encodedEmail 암호화된 이메일입니다.
     */
    public void modifyMemberEmail(String encodedEmail) {
        this.email = encodedEmail;
    }


    /**
     * 회원의 등급 정보 수정.
     *
     * @param memberGrade 갱신할 회원등급 (MemberGrade)
     * @param nextRenewalGradeDate 다음 등급 갱신 일자 (LocalDate)
     * @author 김세미
     */
    public void modifyGrade(MemberGrade memberGrade, LocalDate nextRenewalGradeDate) {
        this.memberGrades = memberGrade;
        this.nextRenewalGradeDate = nextRenewalGradeDate;
        this.accumulatePurchaseAmount = 0L;
    }

    public void decodeMemberProtectionData(String decodedEmail, String decodedPhoneNumber,
                                           String decodedName){
        this.email = decodedEmail;
        this.phoneNumber = decodedPhoneNumber;
        this.name = decodedName;
    }
}
