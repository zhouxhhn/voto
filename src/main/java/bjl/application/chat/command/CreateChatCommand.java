package bjl.application.chat.command;

/**
 * Created by zhangjin on 2017/12/27.
 */
public class CreateChatCommand {

    private Integer roomtype;
    private String userid;
    private Integer texttype;
    private String text;
    private String cbid;

    public String getCbid() {
        return cbid;
    }

    public void setCbid(String cbid) {
        this.cbid = cbid;
    }

    public Integer getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(Integer roomtype) {
        this.roomtype = roomtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public CreateChatCommand() {
    }

    public CreateChatCommand(Integer roomtype, String userid, Integer texttype, String text) {
        this.roomtype = roomtype;
        this.userid = userid;
        this.texttype = texttype;
        this.text = text;
    }
}
