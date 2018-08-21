package bjl.core.enums;

/**
 * Created by pengyi on 2016/3/2.
 */
public enum LoggerType {

    APP_LOGGER("APP日志", 0),
    OPERATION("操作日志", 1);

    LoggerType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

}
