package bjl.domain.model.bank;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2017/9/8.
 */

public class BankDtl {

    private Date createDate;
    private BigDecimal money;
    private String type;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BankDtl() {
    }

    public BankDtl(Date createDate, BigDecimal money, String type, Integer status) {
        this.createDate = createDate;
        this.money = money;
        this.type = type;
        this.status = status;
    }
}
