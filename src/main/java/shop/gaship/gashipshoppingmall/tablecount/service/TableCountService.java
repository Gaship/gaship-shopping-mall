package shop.gaship.gashipshoppingmall.tablecount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.tablecount.exception.TableCountNotFoundException;
import shop.gaship.gashipshoppingmall.tablecount.entity.TableCount;
import shop.gaship.gashipshoppingmall.tablecount.repository.TableCountRepository;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TableCountService {
    private final TableCountRepository tableCountRepository;

    public TableCount findByName(String name) {
        return tableCountRepository.findById(name).orElseThrow(TableCountNotFoundException::new);
    }
}
