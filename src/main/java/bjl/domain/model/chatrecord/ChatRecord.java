package bjl.domain.model.chatrecord;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.user.User;

import java.util.Date;

/**
 * 聊天记录
 * Created by zhangjin on 2017/8/3.
 */
public class ChatRecord extends ConcurrencySafeEntity {

    private Integer type;   //类型。1.用户 2.客服
    private String  deviceno;   //用户
    private User  custom;   //客服
    private Integer serial;  //序号
    private String  content;//内容

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceno() {
        return deviceno;
    }

    public void setDeviceno(String deviceno) {
        this.deviceno = deviceno;
    }

    public User getCustom() {
        return custom;
    }

    public void setCustom(User custom) {
        this.custom = custom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }
}
