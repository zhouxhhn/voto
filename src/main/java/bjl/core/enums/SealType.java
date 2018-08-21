package bjl.core.enums;

/**
 * Author pengyi
 * Date 2016/11/24.
 */
public enum SealType {

    ALL("全部", 0, Boolean.TRUE),
    IP("IP", 1, Boolean.FALSE),
    DEVICE("设备", 2, Boolean.FALSE);

    SealType(String name, int value, Boolean onlyQuery) {
        this.name = name;
        this.value = value;
        this.onlyQuery = onlyQuery;
    }

    private String name;

    private int value;

    private Boolean onlyQuery;                  // 仅用于页面查询和业务逻辑无关

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean isOnlyQuery() {
        return onlyQuery;
    }

}
