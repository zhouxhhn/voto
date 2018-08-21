package bjl.application.chat.command;

/**
 * Created by zhangjin on 2017/12/28.
 */
public class ListChatCommand {

    private Integer roomtype; //1菲律宾2越南3澳门
    private Long index; //信息索引
    private Integer gettype; ////1是向上取2是向下取
    private Integer count;  //取多少条
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

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Integer getGettype() {
        return gettype;
    }

    public void setGettype(Integer gettype) {
        this.gettype = gettype;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
