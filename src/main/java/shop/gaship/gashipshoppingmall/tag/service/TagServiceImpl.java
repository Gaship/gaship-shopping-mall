package shop.gaship.gashipshoppingmall.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.service
 * fileName       : TagServiceImpl
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public TagResponseDto register(TagRequestDto tagRequestDto) {
        Tag tag = dtoToEntity(tagRequestDto);
        Tag save = tagRepository.save(tag);
        return entityToDto(save);
    }

    @Transactional
    @Override
    public TagResponseDto modify(TagRequestDto tagRequestDto) {
        Tag tag = tagRepository.findById(tagRequestDto.getTagNo()).orElseThrow(RuntimeException::new);
        tag.modifyEntity(tagRequestDto);
        return entityToDto(tagRepository.save(tag));
    }
    @Transactional
    @Override
    public void delete(Integer tagNo) {
        tagRepository.deleteById(tagNo);
    }

    @Override
    public TagResponseDto get(Integer tagNo) {
        Tag tag = tagRepository.findById(tagNo).orElseThrow(RuntimeException::new);
        return entityToDto(tag);
    }

    @Override
    public List<TagResponseDto> getList(Pageable pageable) {
        Page<Tag> page = tagRepository.findAll(pageable);
        return page.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
