package shop.gaship.gashipshoppingmall.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.tag.dto.PageResponse;
import shop.gaship.gashipshoppingmall.tag.dto.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

/**
 * 태그의 service interface 의 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    /**
     * 태그 등록을 하기 위한 메서드
     *
     * @param request 등록에 필요한 정보를 담는 dto 입니다.
     * @Exception DuplicatedTagTitleException 등록하려는 태그명이 이미 존재할 경우 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void addTag(TagAddRequestDto request) {
        if (tagRepository.existsByTitle(request.getTitle())) {
            throw new DuplicatedTagTitleException();
        }

        tagRepository.save(dtoToEntity(request));
    }

    /**
     * 태그 수정을 하기 위한 메서드
     *
     * @param request 수정에 필요한 정보를 담는 dto 입니다.
     * @Exception DuplicatedTagTitleException 수정하려는 태그명이 이미 존재할 경우 나오는 예외입니다.
     * @Exception TagNotFoundException 수정하려는 태그 조회 실패 시 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void modifyTag(TagModifyRequestDto request) {
        if (tagRepository.existsByTitle(request.getTitle())) {
            throw new DuplicatedTagTitleException();
        }
        Tag tag = tagRepository.findById(request.getTagNo()).orElseThrow(TagNotFoundException::new);
        tag.modifyEntity(request);
        tagRepository.save(tag);
    }

    /**
     * 태그 단건 조회를 하기 위한 메서드
     *
     * @param tagNo 단건 조회하려는 태그의 식별번호입니다.
     * @return TagResponseDto
     * @Exception TagNotFoundException 수정하려는 태그 조회 실패 시 나오는 예외입니다.
     */
    @Override
    public TagResponseDto findTag(Integer tagNo) {
        Tag tag = tagRepository.findById(tagNo).orElseThrow(TagNotFoundException::new);
        return entityToDto(tag);
    }

    /**
     * 태그 다건 조회를 하기 위한 메서드
     *
     * @param pageable 다건 조회하려는 태그 페이지의 page, size 가 담겨져있습니다.
     * @return TagResponseDto
     */
    @Override
    public PageResponse<TagResponseDto> findTags(Pageable pageable) {
        return new PageResponse(tagRepository.getAllTags(pageable));
    }
}
