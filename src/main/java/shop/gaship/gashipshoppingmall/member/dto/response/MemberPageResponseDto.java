package shop.gaship.gashipshoppingmall.member.dto.response;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 멤버 리스트 요청을 한 후 반환값에 담기는 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberPageResponseDto<D, E> {
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

    /**
     * 페이지 네이션 결과를 통해서 멤버 목록 페이지네이션 Dto를 생성하는 생성자입니다.
     *
     * @param result 페이지네이션 결과입니다.
     * @param fn Member 엔티티에서 Dto 타입으로 만들기 위한 함수형 파라미터입니다.
     */
    public MemberPageResponseDto(Page<E> result, Function<E, D> fn) {

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
