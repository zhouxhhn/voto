package bjl.application.robot.command;

/**
 * Created by zhangjin on 2018/4/26
 */
public class CreateRobotChatCommand {

    private Integer roomtype;
    private String username;
    private Integer texttype;
    private Integer betType;  //下注类型
    private Integer score; //下注分数
    private String name; //昵称
    private String head;

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

    public Integer getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(Integer roomtype) {
        this.roomtype = roomtype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTexttype() {
        return texttype;
    }

    public void setTexttype(Integer texttype) {
        this.texttype = texttype;
    }

    public Integer getBetType() {
        return betType;
    }

    public void setBetType(Integer betType) {
        this.betType = betType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
