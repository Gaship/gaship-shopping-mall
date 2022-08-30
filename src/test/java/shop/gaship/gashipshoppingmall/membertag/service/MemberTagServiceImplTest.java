package shop.gaship.gashipshoppingmall.membertag.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberBaseDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dummy.MemberTagDummy;
import shop.gaship.gashipshoppingmall.membertag.exception.IllegalTagSelectionException;
import shop.gaship.gashipshoppingmall.membertag.repository.MemberTagRepository;
import shop.gaship.gashipshoppingmall.membertag.service.impl.MemberTagServiceImpl;
import shop.gaship.gashipshoppingmall.tag.dummy.TagDummy;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author 최정우
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import({MemberTagServiceImpl.class})
class MemberTagServiceImplTest {
    ObjectMapper objectMapper;

    @Autowired
    MemberTagService memberTagService;

    @MockBean
    MemberTagRepository memberTagRepository;

    @MockBean
    TagRepository tagRepository;

    @MockBean
    MemberRepository memberRepository;

    private final List<Integer> tagIds = new ArrayList<>(List.of(1,3,5,6,8));
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("회원이 미리 설정해놓은 태그를 전부 삭제 후 다시 설정하기 원하는 태그를 등록한다.(Success)")
    @Test
    void deleteAllAndAddAllMemberTags() {
        Member member = MemberBaseDummy.member1();
        List<Tag> tagList = TagDummy.TagDummyListPersist();
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        doNothing().when(memberTagRepository).deleteAllByMember_MemberNo(any());
        when(tagRepository.findAllById(any())).thenReturn(tagList);
        when(memberTagRepository.findAllByMember_MemberNo(any())).thenReturn(null);
        when(memberTagRepository.saveAll(any())).thenReturn(null);

        memberTagService.deleteAllAndAddAllMemberTags(MemberTagDummy.memberTagRequestDtoDummy(tagIds),1);

        verify(memberRepository, times(1)).findById(any());
        verify(memberTagRepository, times(1)).deleteAllByMember_MemberNo(any());
        verify(tagRepository, times(1)).findAllById(any());
        verify(memberTagRepository, times(1)).findAllByMember_MemberNo(any());
        verify(memberTagRepository, times(1)).saveAll(any());
    }

    @DisplayName("태그 등록을 하려 memberRepository 를 조회하는데 해당 아이디를 가진 멤버를 조회할 수 없어서 MemberNotFoundException 오류가 발생")
    @Test
    void deleteAllAndAddAllMemberTagsMemberNotFoundExceptionFail() {
        MemberTagRequestDto memberTagRequestDto = MemberTagDummy.memberTagRequestDtoDummy(tagIds);
        List<Tag> tagList = TagDummy.TagDummyListPersist();
        when(memberRepository.findById(any())).thenReturn(Optional.empty());
        doNothing().when(memberTagRepository).deleteAllByMember_MemberNo(any());
        when(tagRepository.findAllById(any())).thenReturn(tagList);
        when(memberTagRepository.findAllByMember_MemberNo(any())).thenReturn(null);
        when(memberTagRepository.saveAll(any())).thenReturn(null);

        assertThatThrownBy(() -> memberTagService.deleteAllAndAddAllMemberTags(memberTagRequestDto,1))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("해당 멤버를 찾을 수 없습니다");

        verify(memberRepository, times(1)).findById(any());
        verify(memberTagRepository, never()).deleteAllByMember_MemberNo(any());
        verify(tagRepository, never()).findAllById(any());
        verify(memberTagRepository, never()).findAllByMember_MemberNo(any());
        verify(memberTagRepository, never()).saveAll(any());
    }

    @DisplayName("태그 등록을 하려 회원이 요청한 tagId로 Tag DB 를 뒤졌는데 가져온 총 태그 갯수가 5개가 아닌 경우 IllegalTagSelectionException 오류가 발생")
    @Test
    void deleteAllAndAddAllMemberTagsIllegalTagSelectionExceptionFail() {
        MemberTagRequestDto memberTagRequestDto = MemberTagDummy.memberTagRequestDtoDummy(tagIds);
        Member member = MemberBaseDummy.member1();
        List<Tag> tagList = TagDummy.TagListDummyPersist();
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        doNothing().when(memberTagRepository).deleteAllByMember_MemberNo(any());
        when(tagRepository.findAllById(any())).thenReturn(tagList);
        when(memberTagRepository.findAllByMember_MemberNo(any())).thenReturn(null);
        when(memberTagRepository.saveAll(any())).thenReturn(null);

        assertThatThrownBy(() -> memberTagService.deleteAllAndAddAllMemberTags(memberTagRequestDto,1))
                .isInstanceOf(IllegalTagSelectionException.class)
                .hasMessage("태그는 다섯개까지 선택할 수 있습니다");

        verify(memberRepository, times(1)).findById(any());
        verify(memberTagRepository, times(1)).deleteAllByMember_MemberNo(any());
        verify(tagRepository, times(1)).findAllById(any());
        verify(memberTagRepository, never()).findAllByMember_MemberNo(any());
        verify(memberTagRepository, never()).saveAll(any());
    }

    @DisplayName("회원이 미리 설정한 태그 5개를 가져온다.(Success)")
    @Test
    void findMemberTags() {
        when(memberTagRepository.findAllByMember_MemberNo(any())).thenReturn(MemberTagDummy.memberTagListDummy());
        when(memberRepository.existsByMemberNo(any())).thenReturn(true);

        memberTagService.findMemberTags(1);

        verify(memberRepository, times(1)).existsByMemberNo(any());
        verify(memberTagRepository, times(1)).findAllByMember_MemberNo(any());
    }

    @DisplayName("회원이 미리 설정한 태그 5개를 가져온다.(회원 조회 실패(Fail))")
    @Test
    void findMemberTagsTest2() {
        when(memberTagRepository.findAllByMember_MemberNo(any())).thenReturn(MemberTagDummy.memberTagListDummy());
        when(memberRepository.existsByMemberNo(any())).thenReturn(false);

        assertThatThrownBy(()->memberTagService.findMemberTags(1))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("해당 멤버를 찾을 수 없습니다");

        verify(memberRepository, times(1)).existsByMemberNo(any());
        verify(memberTagRepository, never()).findAllByMember_MemberNo(any());
    }

    @DisplayName("회원이 미리 설정한 태그 5개를 가져온다.(찾은 멤버태그 리스트 사이즈가 5가 아님(Fail))")
    @Test
    void findMemberTagsTest3() {
        when(memberTagRepository.findAllByMember_MemberNo(any())).thenReturn(MemberTagDummy.memberTagList100SizeDummy());
        when(memberRepository.existsByMemberNo(any())).thenReturn(true);

        assertThatThrownBy(()->memberTagService.findMemberTags(1))
                .isInstanceOf(IllegalTagSelectionException.class)
                .hasMessage("태그는 다섯개까지 선택할 수 있습니다");

        verify(memberRepository, times(1)).existsByMemberNo(any());
        verify(memberTagRepository, times(1)).findAllByMember_MemberNo(any());
    }
}
