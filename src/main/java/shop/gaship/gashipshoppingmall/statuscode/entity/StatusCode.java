package shop.gaship.gashipshoppingmall.statuscode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "status_codes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StatusCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_code_no")
    private Integer statusCodeNo;

    @Column(name = "status_code_name")
    private String statusCodeName;

    @Column(name = "is_used")
    private Boolean isUsed;

    private Integer priority;

    @Column(name = "group_code_name")
    private String groupCodeName;

    private String explanation;
}
