package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListPageResponseDto<D,E> {
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

    public AddressListPageResponseDto(Page<E> result, Function<E,D> fn ){

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }


    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        end = Math.min(totalPage, tempEnd);

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

    @Builder
    public AddressListPageResponseDto(List<D> dtoList, int totalPage, int page, int size, int start, int end, boolean prev, boolean next, List<Integer> pageList) {
        this.dtoList = dtoList;
        this.totalPage = totalPage;
        this.page = page;
        this.size = size;
        this.start = start;
        this.end = end;
        this.prev = prev;
        this.next = next;
        this.pageList = pageList;
    }
}
