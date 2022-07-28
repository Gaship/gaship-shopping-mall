package shop.gaship.gashipshoppingmall.elastic.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;


/**
 * elastic 에있는 product 와 연동하기 위한 클래스입니다.
 *
 * @author : 유호철
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "#{@environment.getProperty('elasticsearch.index')}",
    writeTypeHint = WriteTypeHint.FALSE)
public class ElasticProduct {
    @Id
    @Column(name = "id")
    Integer id;

    @Column
    @MultiField(
        mainField = @Field(type = FieldType.Text, store = true),
        otherFields = {
            @InnerField(suffix = "nori", type = FieldType.Text,
                store = true, analyzer = "nori_discard")
        }
    )
    String name;

    @Column
    String code;
}
