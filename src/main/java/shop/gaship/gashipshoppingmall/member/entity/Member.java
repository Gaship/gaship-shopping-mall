package shop.gaship.gashipshoppingmall.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
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
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Integer memberNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_member_no")
    private Member recommendMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_status_no")
    private StatusCode memberStatusCodes;

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

    @Column(unique=true)
    private String nickname;

    private String gender;

    @Column(name = "accumulate_purchase_amount")
    private Long accumulatePurchaseAmount;

    @Column(name = "next_renewal_grade_date")
    private LocalDate nextRenewalGradeDate;

    @Column(name = "register_datetime")
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime")
    private LocalDateTime modifyDatetime;

    @Column(name = "is_black_member")
    private Boolean isBlackMember;

    public void modifyMember(MemberModifyRequestDto memberModifyRequestDto) {
        this.email = memberModifyRequestDto.getEmail();
        this.password = memberModifyRequestDto.getPassword();
        this.phoneNumber = memberModifyRequestDto.getPhoneNumber();
        this.name = memberModifyRequestDto.getName();
        this.nickname = memberModifyRequestDto.getNickname();
        this.gender = memberModifyRequestDto.getGender();
    }
}
