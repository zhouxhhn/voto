package bjl.core.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengyi on 2016/4/15.
 */
public enum ApiReturnCode {

    // 通用错误码
    ERROR_UNKNOWN("未知错误", 10000),
    NO_FOUND("数据不存在", 10001),
    SUCCESS("处理成功", 10002),
    FAILURE("处理失败", 10003),
    ERROR_DATA("数据异常", 10004),

    //api 错误码
    AUTHENTICATION_FAILURE("鉴权失败", 20000),
    ILLEGAL_ARGUMENT("不合法参数", 20001),
    ERROR_PHONE_EXISTS("该手机号已被注册",20002),
    ERROR_PHONE_ERROR("手机号不正确",20003),
    ERROR_CODE_ERROR("验证码错误",20004),
    ERROR_ACCOUNT_ERROR("账号或密码错误",20005),
    ERROR_ACCOUNT_NOT_EXISTS("该账号不存在",20006),
    ERROR_BANK_EXISTS("银行卡信息已存在", 20007),
    ERROR_WITHDRAWAL_FAIL("申请提现失败", 20008),
    ERROR_NOT_AGENT("代理不存在",20009),
    ERROR_IP_LOCKED("该IP已被封",20010),

    ERROR_DATA_NOT_FOUND("相关数据没有找到", 30001),
    ERROR_ACCOUNT_LOCKED("账户被禁用", 30002),
    ERROR_NO_LOGIN("没有登录", 30003),
    ERROR_MONEY_NOT_ENOUGH("余额不足", 30004),
    ERROR_NOT_ALLOW("不允许的操作", 30005),
    ERROR_ACCOUNT_EXCEPTION("账号异常", 30006),
    ERROR_MAINTENANCE("服务器维护中", 30007),
    ERROR_INVITECODE_USED("该邀请码已被使用", 30008),
    ERROR_INVITECODE_BOUND("已绑定过邀请码", 30009),
    ERROR_COUNT_NOT_ENOUGH("领取次数不足", 30010),
    ERROR_ROOM_NOT_EXISTS("房间不存在", 40008),
    ERROR_FULL("人数已满", 40009),
    ERROR_DELETE_FREQUENT("解散太频繁", 40010),
    ERROR_USER_LITTLE("人数太少", 40011),
    ERROR_ALREADY_STARTED("游戏已经开始", 40012);





    ApiReturnCode(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private static Map<Integer, ApiReturnCode> CACHE = new HashMap<Integer, ApiReturnCode>(ApiReturnCode.values().length * 2);

    static {
        for (ApiReturnCode apiReturnCode : ApiReturnCode.values()) {
            CACHE.put(apiReturnCode.getValue(), apiReturnCode);
        }
    }

    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public static ApiReturnCode valueOf(int value) {
        return CACHE.get(value);
    }
}
