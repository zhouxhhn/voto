package bjl.domain.model.ip;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * 注册IP存储
 * Created by zhangjin on 2017/11/1.
 */
public class Ip extends ConcurrencySafeEntity {

    private String parentId;  //代理ID
    private String ip;  //下级IP

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
