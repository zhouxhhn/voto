package bjl.application.withdraw.command;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2017/9/5.
 */
public class CreateWithdrawCommand {

    private String username;    //用户名
    private BigDecimal money;   //提现金额
    private Integer type;       //提现类型 1.支付宝 2.微信
    private String acc;         //到账账号

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }
}
