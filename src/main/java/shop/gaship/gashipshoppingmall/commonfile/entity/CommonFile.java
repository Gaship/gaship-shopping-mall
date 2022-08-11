package shop.gaship.gashipshoppingmall.commonfile.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일 엔티티입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "common_files")
public class CommonFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Integer no;

    @NotNull
    private String path;

    @NotNull
    private String originalName;

    @NotNull
    private String extension;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime registerDatetime = LocalDateTime.now();

    @Column(name = "owner_no")
    private Integer ownerNo;

    @NotNull
    private String service;

    /**
     * 공통파일 생성자입니다.
     *
     * @param path         파일경로
     * @param originalName 파일이름
     * @param extension    파일확장자
     */
    @Builder
    public CommonFile(String path, String originalName, String extension) {
        this.path = path;
        this.originalName = originalName;
        this.extension = extension;
    }

    /**
     * 공통파일의 주인 번호와 서비스를 수정하는 메서드입니다.
     *
     * @param ownerNo 공통파일 주인번호
     * @param service 공통파일 주인의 서비스
     */
    public void updateCommonFile(Integer ownerNo, String service) {
        this.ownerNo = ownerNo;
        this.service = service;
    }
}
