package bjl.core.chat;

import bjl.domain.model.robot.Robot;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;

import java.util.*;

/**
 * 机器人下注线程
 * Created by zhangjin on 2018/1/10.
 */
public class CountDown implements Runnable{

    private Integer roomType;

    public CountDown(String roomType) {
        this.roomType = Integer.valueOf(roomType);
    }

    @Override
    public void run() {

        try {
            //先获取该房间的机器人
            Map<String,Robot> robotMap = GlobalVariable.getRobotMap().get(roomType);
            if(robotMap == null || robotMap.size() < 1){
                //没有机器人，则无操作
                return;
            }
            //5秒后开始随机下注
            Thread.sleep(5000);
            List<Bet> list = new ArrayList<>();
            Random random = new Random();
            //给机器人随机时间内下注
            for(Map.Entry<String,Robot> entry : robotMap.entrySet()){
                Bet t = new Bet(entry.getValue(),System.currentTimeMillis()+random.nextInt(20)*500);
                list.add(t);
            }

            //游戏状态是否仍是下注状态
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(roomType);
            if(gameStatus.getStatus() == 1){
                for(int i=1;i<=30;i++){
                    //是否在下注期间
                    if(gameStatus.getStatus() != 1){
                        break;
                    }
                    Iterator<Bet> iterator = list.iterator();

                    if(!iterator.hasNext()){
                        //没有元素则退出外循环
                        break;
                    }
                    while (iterator.hasNext()){
                        Bet bet = iterator.next();
                        //是否达到机器人下注时间,且还有下注次数
                        if(System.currentTimeMillis()>bet.getTime() && bet.getCount() >0){
                            //机器人下注
                            boolean success = ChatProcess.robotBet(roomType,bet.robot);
                            if(success){
                                //下注成功，次数减1
                                bet.setCount(bet.getCount()-1);
                                if(bet.getCount() < 1){
                                    iterator.remove();
                                    continue;
                                }
                            }
                            //重新随机下次下注时间
                            bet.setTime(System.currentTimeMillis()+random.nextInt(20)*500+1000*5);
                        }
                    }

                    try {
                        //一秒钟轮询一次
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 机器人下注内部类
     */
    private class Bet{

        private Robot robot;
        private Long time;
        private Integer count;

        private Bet(Robot robot, Long time) {
            this.robot = robot;
            this.time = time;
            this.count = robot.getFrequency();
        }

        public Robot getRobot() {
            return robot;
        }

        public void setRobot(Robot robot) {
            this.robot = robot;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
