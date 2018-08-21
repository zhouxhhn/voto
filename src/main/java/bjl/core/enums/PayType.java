package bjl.core.enums;

/**
 * Created by pengyi on 16-7-9.
 */
public enum PayType {

    ALL("全部", 0, Boolean.TRUE),
    WECHAT("微信支付", 1, Boolean.FALSE),
    ALIPAY("支付宝支付", 2, Boolean.FALSE),
    QQPAY("QQ钱包", 3, Boolean.FALSE),
    BANK("网银支付", 4, Boolean.FALSE),
    QQPAYCODE("QQ钱包二维码", 5, Boolean.FALSE),
    WECHATCODE("微信二维码",6,Boolean.FALSE),
    ALIPAYCODE("支付宝二维码", 7, Boolean.FALSE);

    private PayType(String name, int value, Boolean onlyQuery) {
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
