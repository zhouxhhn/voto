package bjl.domain.model.chat;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;

/**
 * Created by zhangjin on 2017/12/27.
 */
public class Chat extends ConcurrencySafeEntity {

    private Account user;  //用户
    private String username;//用户数字ID
    private Integer roomType; //1菲律宾2越南3澳门
    private Long index;  //消息索引
    private Integer textType; //消息类型
    private String text;    //内容
    private String name; //昵称
    private String head; //头像

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
