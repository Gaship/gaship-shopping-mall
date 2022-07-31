package shop.gaship.gashipshoppingmall.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.PageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.service.TagService;

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
     * {@inheritDoc}
     *
     * @param request 등록에 필요한 정보를 담는 dto 입니다.
     * @throws DuplicatedTagTitleException 등록하려는 태그명이 이미 존재할 경우 나오는 예외입니다.
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
     * {@inheritDoc}
     *
     * @param request 수정에 필요한 정보를 담는 dto 입니다.
     * @throws DuplicatedTagTitleException 수정하려는 태그명이 이미 존재할 경우 나오는 예외입니다.
     * @throws TagNotFoundException        수정하려는 태그 조회 실패 시 나오는 예외입니다.
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
     * {@inheritDoc}
     *
     * @param tagNo 단건 조회하려는 태그의 식별번호입니다.
     * @return TagResponseDto
     * @throws TagNotFoundException 수정하려는 태그 조회 실패 시 나오는 예외입니다.
     */
    @Override
    public TagResponseDto findTag(Integer tagNo) {
        Tag tag = tagRepository.findById(tagNo).orElseThrow(TagNotFoundException::new);
        return entityToDto(tag);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 다건 조회하려는 태그 페이지의 page, size 가 담겨져있습니다.
     * @return TagResponseDto
     */
    @Override
    public PageResponseDto<TagResponseDto, Tag> findTags(Pageable pageable) {
        Page<Tag> page = tagRepository.getAllTags(pageable);
        return new PageResponseDto<>(page, this::entityToDto);
    }
}
