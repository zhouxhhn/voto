package bjl.core.enums;

/**
 * Created by pengyi on 16-9-5.
 */
public enum PhoneType {

    ANDROID("安卓", 1),
    IOS("ios", 2);

    PhoneType(String name, int value) {
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
