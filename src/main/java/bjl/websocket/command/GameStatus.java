package bjl.websocket.command;

/**
 * 游戏状态
 * Created by zhangjin on 2018/1/2.
 */
public class GameStatus {

    private Integer xNum;   //鞋数
    private Integer jNum;   //局数
    private Integer status; //游戏状态 -1 游戏停止中 0 已启动游戏 1 游戏下注 2停止下注 3游戏开奖

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getxNum() {
        return xNum;
    }

    public void setxNum(Integer xNum) {
        this.xNum = xNum;
    }

    public Integer getjNum() {
        return jNum;
    }

    public void setjNum(Integer jNum) {
        this.jNum = jNum;
    }

    public GameStatus() {
    }

    public GameStatus(Integer xNum, Integer jNum, Integer status) {
        this.xNum = xNum;
        this.jNum = jNum;
        this.status = status;
    }
}
