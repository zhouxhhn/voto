package bjl.application.bank.representation;


/**
 * Created by zhangjin on 2017/9/7.
 */
public class ApiBankRepresentation {

    private String banktype;            //银行名称
    private String cardnum;       //银行卡号
    private String cardtype;        //银行卡类型 （01 借记卡,02 信用卡）

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }
}
