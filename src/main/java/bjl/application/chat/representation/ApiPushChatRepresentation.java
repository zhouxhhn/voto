package bjl.application.chat.representation;

/**
 * Created by zhangjin on 2017/12/27.
 */
public class ApiPushChatRepresentation extends ApiChatRepresentation{

    private Integer roomtype; //1菲律宾2越南3澳门

    public Integer getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(Integer roomtype) {
        this.roomtype = roomtype;
    }

}
