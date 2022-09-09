package shop.gaship.gashipshoppingmall.tablecount.nameenum;

/**
 * @author : 최겸준
 * @since 1.0
 */
public enum TableName {
    CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME("고객문의답변완료개수"),
    CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME("고객문의전체개수");

    TableName(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
