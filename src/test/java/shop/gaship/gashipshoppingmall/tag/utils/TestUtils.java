package shop.gaship.gashipshoppingmall.tag.utils;

import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.utils
 * fileName       : TestUtils
 * author         : choijungwoo
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        choijungwoo       최초 생성
 */
public class TestUtils {
      public static TagRequestDto CreateTestTagRequestDto(String title){
         TagRequestDto tagRequestDto = new TagRequestDto();
         tagRequestDto.setTitle(title);

         return tagRequestDto;
   }

    public static TagResponseDto CreateTestTagResponseDto(String title) {
        return new TagResponseDto(1,title,LocalDateTime.now(),LocalDateTime.now());
    }
}
