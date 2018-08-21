package bjl.core.enums;

/**
 * Created by pengyi on 16-7-5.
 */
public enum GameType {

    NIUNIU_GET_BANKER("牛牛上庄", 1),
    FIXED_BANKER("固定庄家", 2),
    FREE_GET_BANKER("自由抢庄", 3),
    OPEN_GET_BANKER("明牌抢庄", 4),
    COMPARE("通比", 5),
    GOLD("金币场", 6);

    GameType(String name, int value) {
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
