package shop.gaship.gashipshoppingmall.util;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * 목록 조회 반환 타입으로 사용되는 page 정보와 data 를 담은 Data Transfer Object.
 *
 * @param <T> the type parameter
 * @author : 최정우, 김세미
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class PageResponse<T> {

    private List<T> content;

    private int totalPages;

    private int number;

    private boolean previous;

    private boolean next;

    /**
     * Instantiates a new Page response dto.
     *
     * @param result the result
     */
    public PageResponse(Page<T> result) {

        this.content = result.getContent();

        this.totalPages = result.getTotalPages();

        this.number = result.getNumber();

        this.previous = result.hasPrevious();

        this.next = result.hasNext();
    }

    public void decodeContent(List<T> decodedMembers) {
        this.content = decodedMembers;
    }
}
