package shop.gaship.gashipshoppingmall.tag.dto.response;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 목록 조회 반환 타입으로 사용되는 page 정보와 data 를 담은 Data Transfer Object.
 * </p>
 * D = DTO, E = Entity
 *
 * @author : 최정우
 * @since 1.0
 */
@Getter
public class PageResponseDto<D, E> {
    //DTO리스트
    private final List<D> dtoList;

    //총 페이지 번호
    private final int totalPage;

    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start;

    private int end;

    //이전, 다음
    private boolean prev;

    private boolean next;

    //페이지 번호  목록
    private List<Integer> pageList;

    public PageResponseDto(Page<E> result, Function<E, D> fn) {

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = Math.min(totalPage, tempEnd);

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
}
