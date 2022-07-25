package shop.gaship.gashipshoppingmall.tag.service;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.tag.dto.TagPageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

/**
 * tagservice를 구현하는 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public void addTag(TagRequestDto request) {
        if (tagRepository.existsByTitle(request.getTitle())) {
            throw new DuplicatedTagTitleException();
        }
        Tag tag = dtoToEntity(request);
        tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void modifyTag(TagRequestDto request) {
        if (tagRepository.existsByTitle(request.getTitle())) {
            throw new DuplicatedTagTitleException();
        }
        Tag tag = tagRepository.findById(request.getTagNo()).orElseThrow(TagNotFoundException::new);
        tag.modifyEntity(request);
        tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void removeTag(Integer tagNo) {
        tagRepository.deleteById(tagNo);
    }

    @Override
    public TagResponseDto findTag(Integer tagNo) {
        Tag tag = tagRepository.findById(tagNo).orElseThrow(TagNotFoundException::new);
        return entityToDto(tag);
    }

    @Override
    public TagPageResponseDto<TagResponseDto, Tag> findTags(Pageable pageable) {
        Page<Tag> page = tagRepository.findAll(pageable);
        Function<Tag, TagResponseDto> fn = (this::entityToDto);
        return new TagPageResponseDto<>(page, fn);
    }
}
