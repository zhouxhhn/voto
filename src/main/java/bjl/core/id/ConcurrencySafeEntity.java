package bjl.core.id;


import bjl.core.exception.ConcurrencyException;

import java.util.Date;

/**
 * Created by pengyi on 2016/3/2.
 */
public class ConcurrencySafeEntity extends Entity {

    private Integer version;

    private Date createDate;    //数据创建时间

    protected ConcurrencySafeEntity() {
        super();
    }

    public Integer getVersion() {
        return version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void fainWhenConcurrencyViolation(Integer version) {
        if (!version.equals(this.getVersion())) {
            throw new ConcurrencyException("记录在提交之前已发生改变[id=" + this.getId() + "],请重新提交.");
        }
    }
}
