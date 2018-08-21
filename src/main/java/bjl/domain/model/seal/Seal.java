package bjl.domain.model.seal;

import bjl.core.enums.SealType;
import bjl.core.id.ConcurrencySafeEntity;

import java.util.Date;

/**
 * Author pengyi
 * Date 16-11-24.
 */
public class Seal extends ConcurrencySafeEntity {

    private SealType type;
    private String sealNo;
    private String phoneNo; //手机号
    private String name;   //昵称

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SealType getType() {
        return type;
    }

    private void setType(SealType type) {
        this.type = type;
    }

    public String getSealNo() {
        return sealNo;
    }

    private void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public Seal() {
        setCreateDate(new Date());
    }

    public Seal(SealType type, String sealNo, String phoneNo, String name) {
        this.type = type;
        this.sealNo = sealNo;
        this.phoneNo = phoneNo;
        this.name = name;
        setCreateDate(new Date());
    }

    public Seal(SealType type, String sealNo) {

        this.type = type;
        this.sealNo = sealNo;
    }
}
