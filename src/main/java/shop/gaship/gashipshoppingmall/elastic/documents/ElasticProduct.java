package shop.gaship.gashipshoppingmall.elastic.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

import javax.persistence.Column;
import javax.persistence.Id;


/**
 * elastic 에있는 product 와 연동하기 위한 클래스입니다.
 *
 * @author : 유호철, 김보민
 * @since 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "gaship-product", writeTypeHint = WriteTypeHint.FALSE)
public class ElasticProduct {
    @Id
    @Column(name = "productNo")
    Integer id;

    @Column
    String name;

    @Column
    String code;
}
