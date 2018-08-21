package bjl.application.bank.representation;

import java.util.Date;

/**
 * Created by zhangjin on 2017/10/26.
 */
public class ApiBankDtlRepresentation {

    private Date createDate;
    private int money;
    private String type;
    private Integer status;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ApiBankDtlRepresentation(Date createDate, int money, String type, Integer status) {
        this.createDate = createDate;
        this.money = money;
        this.type = type;
        this.status = status;
    }
}
