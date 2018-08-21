package bjl.domain.model.recharge;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * Created by zhangjin on 2017/9/29.
 */
public class RechargeCtl extends ConcurrencySafeEntity {

    private Integer weChat;
    private Integer aliPay;
    private Integer qqPay;
    private Integer bank;
    private Integer aliCode;
    private Integer weCode;
    private Integer qqCode;
    private Integer weChatPass;
    private Integer aliPayPass;
    private Integer bankPass;
    private Integer QQPayPass;

    public Integer getBankPass() {
        return bankPass;
    }

    public void setBankPass(Integer bankPass) {
        this.bankPass = bankPass;
    }

    public Integer getQQPayPass() {
        return QQPayPass;
    }

    public void setQQPayPass(Integer QQPayPass) {
        this.QQPayPass = QQPayPass;
    }

    public Integer getQqCode() {
        return qqCode;
    }

    public void setQqCode(Integer qqCode) {
        this.qqCode = qqCode;
    }

    public Integer getWeChatPass() {
        return weChatPass;
    }

    public void setWeChatPass(Integer weChatPass) {
        this.weChatPass = weChatPass;
    }

    public Integer getAliPayPass() {
        return aliPayPass;
    }

    public void setAliPayPass(Integer aliPayPass) {
        this.aliPayPass = aliPayPass;
    }

    public Integer getWeChat() {
        return weChat;
    }

    public void setWeChat(Integer weChat) {
        this.weChat = weChat;
    }

    public Integer getAliPay() {
        return aliPay;
    }

    public void setAliPay(Integer aliPay) {
        this.aliPay = aliPay;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public Integer getAliCode() {
        return aliCode;
    }

    public void setAliCode(Integer aliCode) {
        this.aliCode = aliCode;
    }

    public Integer getWeCode() {
        return weCode;
    }

    public void setWeCode(Integer weCode) {
        this.weCode = weCode;
    }

    public Integer getQqPay() {
        return qqPay;
    }

    public void setQqPay(Integer qqPay) {
        this.qqPay = qqPay;
    }
}
