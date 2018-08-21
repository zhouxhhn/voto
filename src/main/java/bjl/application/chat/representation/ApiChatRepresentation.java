package bjl.application.chat.representation;

/**
 * Created by zhangjin on 2017/12/28.
 */
public class ApiChatRepresentation {

    private String userid;
    private String name;
    private String head;
    private Long index;  //消息索引
    private Integer texttype; //消息类型
    private String text;    //内容
    private Long time;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Integer getTexttype() {
        return texttype;
    }

    public void setTexttype(Integer texttype) {
        this.texttype = texttype;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
