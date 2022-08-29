package shop.gaship.gashipshoppingmall.product.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 상품을 가져올 정보들이 담겨있습니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@RequiredArgsConstructor
public class ProductAllInfoResponseDto {
    private final Integer productNo;
    private final String productName;
    private final String productCode;
    private final String categoryName;
    private final Long amount;
    private final LocalDateTime dateTime;
    private final String manufacturer;
    private final String country;
    private final String seller;
    private final String importer;
    private final Long installationCost;
    private final String quality;
    private final String color;
    private final Integer quantity;
    private final String explanation;
    private final Integer level;
    private final String deliveryType;
    private final String upperName;
    private final List<String> tags = new ArrayList<>();
    private final List<Integer> fileNos = new ArrayList<>();
}
