package shop.gaship.gashipshoppingmall.commonfile.entity;

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
@Table(name = "files")
public class File {
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
    @Column(name = "owner_no")
    private Integer ownerNo;

    @NotNull
    private String service;

    @Builder
    public File(String path, String originalName, String extension) {
        this.path = path;
        this.originalName = originalName;
        this.extension = extension;
    }

    public void updateFile(Integer ownerNo, String service) {
        this.ownerNo = ownerNo;
        this.service = service;
    }
}
