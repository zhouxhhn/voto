package bjl.websocket;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.gamedetailed.IGameDetailedAppService;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.core.api.MessageID;
import bjl.core.chat.ChatProcess;
import bjl.core.chat.CountDown;
import bjl.core.message.PushMessage;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreImgUtils;
import bjl.core.util.CoreStringUtils;
import bjl.core.util.ServiceUtil;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.robot.Robot;
import bjl.domain.model.user.User;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;
import bjl.websocket.command.WSMessage;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.math.BigDecimal;
import java.util.*;

/**
 * 处理 webSocket事件
 * Created by zhangjin on 2018/1/2.
 */
public class EventProcess {


    /**
     * webSocket消息
     * @param message
     * @return
     */
    public JSONObject eventMessage(String message){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",1);
        try {
            if(!CoreStringUtils.isEmpty(message)){
                String[] strings = message.split(",");
                if(strings.length > 1 ){
                    GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
                    if("0".equals(strings[1])){
                        //启动游戏

                        if(gameStatus == null){
                            //获取当天最大鞋局
                            GameDetailed gameDetailed = ServiceUtil.serviceUtil.getGameDetailedAppService().getMax(Integer.valueOf(strings[0]));
                            if(gameDetailed != null){
                                gameStatus = new GameStatus(gameDetailed.getBoots(),gameDetailed.getGames(),0);
                            }else {
                                gameStatus = new GameStatus(1,1,0);
                            }
                            GlobalVariable.getGameStatusMap().put(Integer.valueOf(strings[0]),gameStatus);

                            //加载机器人
                            List<Robot> robotList = ServiceUtil.serviceUtil.getRobotAppService().list(Integer.valueOf(strings[0]));
                            Map<String,Robot> robotMap = new HashMap<>();
                            for(Robot robot : robotList){
                                robotMap.put(robot.getId(),robot);
                            }
                            //如果该房间有机器人，则加载到内存中
                            if(robotMap.size() > 0){
                                GlobalVariable.getRobotMap().put(Integer.valueOf(strings[0]),robotMap);
                            }

                            jsonObject.put("text","已启动游戏");
                            return jsonObject;
                        }else {
                            if(gameStatus.getStatus() == 3 || gameStatus.getStatus() == 4){
                                //关闭游戏
                                gameStatus.setStatus(-1);
                                //清除暂存数据
                                GlobalVariable.getBetMap().remove(Integer.valueOf(strings[0]));
                                GlobalVariable.getTotalMap().remove(strings[0]);
                                GlobalVariable.getRobotMap().remove(Integer.valueOf(strings[0]));
                                jsonObject.put("text","已停止游戏");
                                //关闭房间，则踢出玩家
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("roomtype",Integer.valueOf(strings[0]));
                                PushMessage.ordinaryPush(jsonObject1,MessageID.ROOMTIREN_);
                            }else if (gameStatus.getStatus() == -1){

                                //加载机器人
                                List<Robot> robotList = ServiceUtil.serviceUtil.getRobotAppService().list(Integer.valueOf(strings[0]));
                                Map<String,Robot> robotMap = new HashMap<>();
                                for(Robot robot : robotList){
                                    robotMap.put(robot.getId(),robot);
                                }
                                //如果该房间有机器人，则加载到内存中
                                if(robotMap.size() > 0){
                                    GlobalVariable.getRobotMap().put(Integer.valueOf(strings[0]),robotMap);
                                }

                                gameStatus.setStatus(0);
                                jsonObject.put("text","已启动游戏");
                            } else if (gameStatus.getStatus() == 0){
                                //清除暂存数据
                                GlobalVariable.getBetMap().remove(Integer.valueOf(strings[0]));
                                GlobalVariable.getTotalMap().remove(strings[0]);
                                GlobalVariable.getRobotMap().remove(Integer.valueOf(strings[0]));
                                gameStatus.setStatus(-1);
                                jsonObject.put("text","已停止游戏");
                                //关闭房间，则踢出玩家
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("roomtype",Integer.valueOf(strings[0]));
                                PushMessage.ordinaryPush(jsonObject1,MessageID.ROOMTIREN_);
                            }else {
                                jsonObject.put("text","游戏未开奖,无法停止游戏");
                            }
                            return jsonObject;
                        }

                    } else if("1".equals(strings[1])){
                        //投注表截图
                        if(gameStatus == null || gameStatus.getStatus() == -1 || gameStatus.getStatus() == 0){
                            jsonObject.put("text","游戏未开始或未开始投注，无法发送投注截图");
                        }else {
                            String result = betScreen(Integer.valueOf(strings[0]));
                            if(result != null){
                                CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",4,result);
                                //推送消息
                                ChatProcess.messageProcess(command);
                                command.setTexttype(5);
                                command.setText("5");
                                //推送消息
                                ChatProcess.messageProcess(command);
                                jsonObject.put("text","推送投注表截图成功");
                            }else {
                                jsonObject.put("text","当前状态无法推送投注截图");
                            }
                        }

                        return jsonObject;

                    }else if("2".equals(strings[1])){
                        //分数表截图
                        if(gameStatus == null || gameStatus.getStatus() == -1 || gameStatus.getStatus() == 0){
                            jsonObject.put("text","游戏未开始，无法发送分数截图");
                        } else if(gameStatus.getStatus() == 1 || gameStatus.getStatus() == 2){
                            jsonObject.put("text","游戏未开奖，无法发送分数截图");
                        } else {
                            String result = scoreScreen(Integer.valueOf(strings[0]));
                            if(result != null){
                                CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",4,result);
                                //推送消息
                                ChatProcess.messageProcess(command);
                                jsonObject.put("text","推送分数表截图成功");
                                gameStatus.setStatus(4);
                            }else {
                                jsonObject.put("text","当前状态无法推送分数截图");
                            }
                        }
                        return jsonObject;

                    } else if("3".equals(strings[1])){
                        //开奖
                        String result = lottery(strings);
                        if(result != null){
                            CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",5,"6");
                            //推送消息
                            ChatProcess.messageProcess(command);
                            //推送玩家当前信息
                            Map<String, WSMessage> messageMap = GlobalVariable.getBetMap().get(Integer.valueOf(strings[0]));
                            List<Map<String,Object>> list = new ArrayList<>();
                            if(messageMap != null){
                                for(WSMessage wsMessage : messageMap.values()){
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("userid",wsMessage.getUsername());
                                    map.put("gold",wsMessage.getScore()[6]);
                                    list.add(map);
                                }
                            }
                            //推送
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("data",list);
                            PushMessage.ordinaryPush(jsonObject1, MessageID.PUSHGOLD_);
                            jsonObject.put("success",0);
                            return jsonObject;
                        }else {
                            jsonObject.put("success",5);
                        }
                        return jsonObject;
                    }else if("4".equals(strings[1])){
                        //下一鞋
                        if(gameStatus == null || gameStatus.getStatus() != 4){
                            jsonObject.put("text","游戏开奖，并发送分数截图后才可进入下一靴");
                        }else {
                            //保存投注表截图
                            ServiceUtil.serviceUtil.getBetTableAppService().create(Integer.valueOf(strings[0]),gameStatus.getxNum(),gameStatus.getjNum(),GlobalVariable.getBetMap().get(Integer.valueOf(strings[0])));
                            //保存分数表截图
                            ServiceUtil.serviceUtil.getScoreTableAppService().create(Integer.valueOf(strings[0]),gameStatus.getxNum(),gameStatus.getjNum(),GlobalVariable.getBetMap().get(Integer.valueOf(strings[0])));

                            gameStatus.setStatus(0);
                            gameStatus.setxNum(gameStatus.getxNum()+1); //鞋数+1
                            gameStatus.setjNum(1); //局数归1
                            //清除暂存数据
                            GlobalVariable.getBetMap().remove(Integer.valueOf(strings[0]));
                            GlobalVariable.getTotalMap().remove(strings[0]);
                            //机器人补分
                            addScore(GlobalVariable.getRobotMap().get(Integer.valueOf(strings[0])));

                            jsonObject.put("type",2);
                            jsonObject.put("text","已开始"+gameStatus.getxNum()+"靴"+gameStatus.getjNum()+"局游戏");
                        }
                        return jsonObject;
                    }
                    else if("5".equals(strings[1])){
                        //下一局
                        if(gameStatus == null || gameStatus.getStatus() != 4){
                            jsonObject.put("text","游戏开奖，并发送分数截图后才可进入下一局");
                        }else {
                            //保存投注表截图
                            ServiceUtil.serviceUtil.getBetTableAppService().create(Integer.valueOf(strings[0]),gameStatus.getxNum(),gameStatus.getjNum(),GlobalVariable.getBetMap().get(Integer.valueOf(strings[0])));
                            //保存分数表截图
                            ServiceUtil.serviceUtil.getScoreTableAppService().create(Integer.valueOf(strings[0]),gameStatus.getxNum(),gameStatus.getjNum(),GlobalVariable.getBetMap().get(Integer.valueOf(strings[0])));

                            gameStatus.setStatus(0);
                            gameStatus.setjNum(gameStatus.getjNum()+1); //局数+1
                            //清除暂存数据
                            GlobalVariable.getBetMap().remove(Integer.valueOf(strings[0]));
                            GlobalVariable.getTotalMap().remove(strings[0]);
                            //机器人补分
                            addScore(GlobalVariable.getRobotMap().get(Integer.valueOf(strings[0])));

                            jsonObject.put("type",2);
                            jsonObject.put("text","已开始"+gameStatus.getxNum()+"靴"+gameStatus.getjNum()+"局游戏");
                        }
                        return jsonObject;
                    } else if ("6".equals(strings[1])){
                        //修改靴局
                        if(gameStatus != null){
                            if(gameStatus.getxNum() <= Integer.valueOf(strings[2]) && gameStatus.getjNum() <= Integer.valueOf(strings[3])){

                                gameStatus = new GameStatus(Integer.valueOf(strings[2]),Integer.valueOf(strings[3]),0);
                                GlobalVariable.getGameStatusMap().put(Integer.valueOf(strings[0]),gameStatus);
                                jsonObject.put("type",3);
                                jsonObject.put("suc",0);
                            }else {
                                jsonObject.put("type",3);
                                jsonObject.put("text","修改的靴局必须大于当前靴局");
                            }
                        } else {
                            GameDetailed gameDetailed = ServiceUtil.serviceUtil.getGameDetailedAppService().getMax(Integer.valueOf(strings[0]));
                            if(gameDetailed != null){
                                if(gameDetailed.getBoots() <= Integer.valueOf(strings[2]) && gameDetailed.getGames() <= Integer.valueOf(strings[3])){
                                    jsonObject.put("type",3);
                                    gameStatus = new GameStatus(Integer.valueOf(strings[2]),Integer.valueOf(strings[3]),0);
                                    GlobalVariable.getGameStatusMap().put(Integer.valueOf(strings[0]),gameStatus);
                                    jsonObject.put("suc",0);

                                }else {
                                    jsonObject.put("text","修改的靴局必须大于当前靴局");
                                    jsonObject.put("type",3);
                                }
                            } else {
                                jsonObject.put("type",3);
                                gameStatus = new GameStatus(Integer.valueOf(strings[2]),Integer.valueOf(strings[3]),0);
                                GlobalVariable.getGameStatusMap().put(Integer.valueOf(strings[0]),gameStatus);
                                jsonObject.put("suc",0);
                            }
                        }
                        return jsonObject;
                    } else if ("7".equals(strings[1])){
                        if(gameStatus.getStatus() != 1 && gameStatus.getStatus() != 2){
                            jsonObject.put("text","当前游戏状态无法修改玩家下注");
                            jsonObject.put("type",7);
                            return jsonObject;
                        }
                        //修改下注
                        //玩家已下注过，更新玩家下注信息
                        WSMessage wsMessage = GlobalVariable.getBetMap().get(Integer.valueOf(strings[0])).get(strings[2]);
                        if(wsMessage == null){
                            return null;
                        }
                        if(wsMessage.getVirtual() == 3){
                            jsonObject.put("text","该玩家是机器人无法修改其下注");
                            return jsonObject;
                        }
                        //判断是否庄闲对压
                        if(BigDecimal.valueOf(Integer.valueOf(strings[3])).compareTo(BigDecimal.valueOf(0))>0 && BigDecimal.valueOf(Integer.valueOf(strings[4])).compareTo(BigDecimal.valueOf(0))>0){
                            jsonObject.put("text","庄闲不能对压");
                            return jsonObject;
                        }
                        BigDecimal[] bigDecimals = wsMessage.getScore();
                        //计算玩家下注变更
                        BigDecimal[] betBigDecimal = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                                BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
                        //变更金额
                        BigDecimal changeScore = BigDecimal.valueOf(0);
                        try {
                            for(int i= 0;i<5;i++){
                                betBigDecimal[i] = BigDecimal.valueOf(Integer.valueOf(strings[i+3])).subtract(bigDecimals[i]);
                                changeScore = changeScore.add(betBigDecimal[i]);
                            }
                        }catch (NumberFormatException e){
                            System.out.println("修改玩家下注信息，积分修改异常");
                            jsonObject.put("type",7);
                            jsonObject.put("text", "积分异常，或不能为空");
                            return jsonObject;
                        }

                        //计算玩家总资金变更
                        CreateChatCommand chatCommand = new CreateChatCommand();
                        chatCommand.setUserid(wsMessage.getUsername());

                        Object[] objects = ServiceUtil.serviceUtil.getGameBetAppService().editBet(strings,wsMessage.getUsername(),changeScore);
                        jsonObject.put("type",7);
                        if (1 == (Integer) objects[0]) {
                            jsonObject.put("text", "积分类型错误");
                            return jsonObject;
                        } else if(2 == (Integer) objects[0]){
                            jsonObject.put("text", "用户不存在");
                            return jsonObject;
                        } else if(3 == (Integer) objects[0]){
                            jsonObject.put("text", "积分不足");
                            return jsonObject;
                        } else if(4 == (Integer) objects[0]){
                            jsonObject.put("text", "单次下注不能最小于限红");
                            return jsonObject;
                        } else if(5 == (Integer) objects[0]){
                            jsonObject.put("text", "下注之和超过最大限红");
                            return jsonObject;
                        }
                        for(int i= 0;i<5;i++){
                            bigDecimals[i] = BigDecimal.valueOf(Integer.valueOf(strings[i+3]));
                        }
                        bigDecimals[6] = (BigDecimal) objects[1];
                        bigDecimals[7] = (BigDecimal) objects[2];
                        //更新页面玩家下注情况
                        ChatProcess.updateMessage(wsMessage, Integer.valueOf(strings[0]), 0, betBigDecimal);
                        jsonObject.put("type",7);
                        jsonObject.put("text", "修改玩家下注成功");
                        return jsonObject;
                    } else if("8".equals(strings[1])){

                        if(gameStatus.getStatus() == 0){
                            //开始投注
                            CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",5,"1");
                            //推送消息
                            ChatProcess.messageProcess(command);
                            //创建并启动该房间的机器人线程
                            new Thread(new CountDown(strings[0])).start();

                            gameStatus.setStatus(1);
                            jsonObject.put("text", "已开始下注");
                            return jsonObject;
                        } else if(gameStatus.getStatus() == 1){
                            jsonObject.put("text", "已开始下注");
                            return jsonObject;
                        } else {
                            jsonObject.put("text", "当前游戏状态无法开启投注");
                            return jsonObject;
                        }
                    } else if("9".equals(strings[1])){

                        if(gameStatus.getStatus() == 1){
                            //停止投注
                            CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",5,"4");
                            //推送消息
                            ChatProcess.messageProcess(command);
                            gameStatus.setStatus(2);
                            jsonObject.put("text", "已停止下注");
                            return jsonObject;
                        } else if(gameStatus.getStatus() ==2){
                            jsonObject.put("text", "已停止下注");
                            return jsonObject;
                        } else {
                            jsonObject.put("text", "当前游戏状态无法停止投注");
                            return jsonObject;
                        }
                    } else if("10".equals(strings[1])){
                        //还有无
                        CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",5,"3");
                        //推送消息
                        ChatProcess.messageProcess(command);
                        jsonObject.put("text", "提示还有无成功");
                        return jsonObject;
                    }
                    else if("11".equals(strings[1])){  //重开本局

                        if(gameStatus.getStatus() !=3 && gameStatus.getStatus() !=4){
                            jsonObject.put("text","当前游戏状态无法重开");
                            return jsonObject;
                        }
                        //开奖
                        Integer result = reOpen(strings);

                        if(result == 0){ //重开成功
                            CreateChatCommand command = new CreateChatCommand(Integer.valueOf(strings[0]),"admin",5,"6");
                            //推送消息
                            ChatProcess.messageProcess(command);
                            //推送玩家当前信息
                            Map<String, WSMessage> messageMap = GlobalVariable.getBetMap().get(Integer.valueOf(strings[0]));
                            List<Map<String,Object>> list = new ArrayList<>();
                            if(messageMap != null){
                                for(WSMessage wsMessage : messageMap.values()){
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("userid",wsMessage.getUsername());
                                    map.put("gold",wsMessage.getScore()[6]);
                                    list.add(map);
                                }
                            }
                            //推送
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("data",list);
                            PushMessage.ordinaryPush(jsonObject1, MessageID.PUSHGOLD_);
                            jsonObject.put("success",8);
                            gameStatus.setStatus(3);
                            return jsonObject;
                        }else if(result == 1){
                            jsonObject.put("success",6);
                        } else{
                            jsonObject.put("success",7);
                        }
                        return jsonObject;
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 投注表截图事件
     */
    private String betScreen(Integer roomType){

        try {
            //游戏状态
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(roomType);

            Map<String,Object> map = new HashMap<>();
            ArrayList<Object[]> list = new ArrayList<>();
            BigDecimal[] total = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)
                    ,BigDecimal.valueOf(0), BigDecimal.valueOf(0)};

            Map<String,WSMessage> maps = GlobalVariable.getBetMap().get(roomType);

            if(maps != null && maps.size()>0){

                for(WSMessage wsMessage : maps.values()){

                    if(wsMessage.getHide() == 1){ //是否显示玩家
                        //总人数+1
                        total[0] = total[0].add(BigDecimal.valueOf(1));
                        //统计各项之和
                        Object[] b = new Object[6];
                        for (int i = 1;i<b.length;i++){
                            total[i] = total[i].add(wsMessage.getScore()[i-1]);
                        }
                        b[0] = wsMessage.getName();

                        System.arraycopy(wsMessage.getScore(), 0, b, 1, 5);

                        if(list.size()<10){
                            list.add(b);
                        }
                    }
                }
                //投注表存图
                return CoreImgUtils.tableImage(gameStatus,list,total,roomType,1);
            }else {
                //投注表存图
                return CoreImgUtils.tableImage(gameStatus,list,total,roomType,1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分数表截图事件
     */
    private String scoreScreen(Integer roomType){

        try {
            //游戏状态
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(roomType);

            Map<String,Object> map = new HashMap<>();
            ArrayList<Object[]> list = new ArrayList<>();
            BigDecimal[] total = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)};

            Map<String,WSMessage> maps = GlobalVariable.getBetMap().get(roomType);

            if(maps != null && maps.size()>0){


                for(WSMessage wsMessage : maps.values()){

                    if(wsMessage.getHide() == 1){ //是否显示玩家

                        //总人数+1
                        total[0] = total[0].add(BigDecimal.valueOf(1));
                        //统计各项之和
                        Object[] b = new Object[4];
                        for (int i = 1;i<b.length;i++){
                            total[i] = total[i].add(wsMessage.getScore()[i+4]);
                        }
                        b[0] = wsMessage.getName();

                        System.arraycopy(wsMessage.getScore(), 5, b, 1, 3);

                        if (list.size() < 10) {
                            list.add(b);
                        }
                    }
                }
                //分数表存图
                return CoreImgUtils.tableImage(gameStatus,list,total,roomType,2);
            }else {
                //分数表存图
                return CoreImgUtils.tableImage(gameStatus,list,total,roomType,2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 开奖事件
     * @param strings 参数
     * @return
     */
    private static synchronized String lottery(String[] strings){

        try {
            //游戏状态
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
            if(gameStatus == null || gameStatus.getStatus() != 2){
                return null;
            }
            Map<String, WSMessage> map = GlobalVariable.getBetMap().get(Integer.valueOf(strings[0]));
            BigDecimal[] total = GlobalVariable.getTotalMap().get(strings[0]);

            if (map != null) {
                List<CreateGameDetailedCommand> list = new ArrayList<>();
                for (WSMessage wsMessage : map.values()) {
                    CreateGameDetailedCommand command = new CreateGameDetailedCommand(wsMessage, strings, gameStatus);

                    //如果庄赢
                    if (1 == command.getLottery()[0]) {
                        //计算庄闲盈亏
                        command.setBankPlayProfit(command.getBanker().multiply(BigDecimal.valueOf(1.95)).add(command.getBankPlayProfit()));
                    } else {
                        //计算庄闲洗码
                        command.setBankPlayLose(command.getBankPlayLose().add(command.getBanker()));
                    }

                    //如果闲赢
                    if (1 == command.getLottery()[1]) {
                        //计算庄闲盈亏
                        command.setBankPlayProfit(command.getPlayer().multiply(BigDecimal.valueOf(2)).add(command.getBankPlayProfit()));
                    } else {
                        //计算庄闲洗码
                        command.setBankPlayLose(command.getBankPlayLose().add(command.getPlayer()));
                    }

                    //如果是庄对
                    if (1 == command.getLottery()[2]) {
                        //计算庄对
                        command.setTriratnaProfit(command.getBankPair().multiply(BigDecimal.valueOf(12)).add(command.getTriratnaProfit()));
                    } else {
                        //计算三宝洗码
                        command.setTriratnaLose(command.getTriratnaLose().add(command.getBankPair()));
                    }

                    //如果是闲对
                    if (1 == command.getLottery()[3]) {
                        //计算闲对
                        command.setTriratnaProfit(command.getPlayerPair().multiply(BigDecimal.valueOf(12)).add(command.getTriratnaProfit()));
                    } else {
                        //计算三宝洗码
                        command.setTriratnaLose(command.getTriratnaLose().add(command.getPlayerPair()));
                    }

                    //如果是和
                    if (1 == command.getLottery()[4]) {
                        //如何是和 则庄闲洗码清0
                        command.setBankPlayLose(BigDecimal.valueOf(0));
                        //退还庄闲本金
                        command.setBankPlayProfit(command.getPlayer().add(command.getBanker()));
                        //计算和
                        command.setTriratnaProfit(command.getDraw().multiply(BigDecimal.valueOf(9)).add(command.getTriratnaProfit()));
                    } else {
                        //计算三宝洗码
                        command.setTriratnaLose(command.getTriratnaLose().add(command.getDraw()));
                    }
                    //四舍五入取整
                    command.setBankPlayProfit(command.getBankPlayProfit().setScale(0,BigDecimal.ROUND_HALF_UP));
                    //赢钱总和
                    command.setWinTotal(command.getBankPlayProfit().add(command.getTriratnaProfit()));
                    //记录本次赢钱总和
                    wsMessage.setThisProfit(command.getWinTotal());
                    //计算盈利
                    //庄闲盈利   庄闲赢钱之和-庄闲本金
                    command.setBankPlayProfit(command.getBankPlayProfit().subtract(command.getBanker()).subtract(command.getPlayer()));
                    //三宝盈利  三宝赢钱之和-三宝本金
                    command.setTriratnaProfit(command.getTriratnaProfit().subtract(command.getBankPair()).subtract(command.getPlayerPair()).subtract(command.getDraw()));
                    //记录玩家本局游戏明细
                    command.setInitScore(wsMessage.getScore()[7]);
                    command.setVirtual(wsMessage.getVirtual());
                    if(wsMessage.getVirtual() != 3){
                        User user = ServiceUtil.serviceUtil.getGameDetailedAppService().save(command);
                        wsMessage.getScore()[6] = user.getScore();
                    }else {
                        //机器人
                        if(command.getWinTotal().compareTo(BigDecimal.valueOf(0)) > 0){
                            Robot robot = ServiceUtil.serviceUtil.getRobotAppService().win(command.getWinTotal(),wsMessage.getUsername());
                            if(robot == null){
                                wsMessage.getScore()[6] = wsMessage.getScore()[6].add(command.getWinTotal());
                            }else {
                                wsMessage.getScore()[6] = robot.getScore();
                                //更新内存中的机器人积分
                                GlobalVariable.getRobotMap().get(Integer.valueOf(strings[0])).get(robot.getId()).setScore(robot.getScore());
                            }
                        }
                    }
                    //更新页面信息
                    wsMessage.getScore()[5] = command.getBankPlayProfit().add(command.getTriratnaProfit());
                    total[6] = total[6].add(command.getTriratnaProfit()).add(command.getBankPlayProfit());
                    total[7] = total[7].add(command.getTriratnaProfit()).add(command.getBankPlayProfit());
                    wsMessage.setTotal(total);

                    //虚拟玩家不统计财务汇总
                    if (wsMessage.getVirtual() == 2){
                        list.add(command);
                    }

                }
                System.out.println(CoreDateUtils.formatDateTime(new Date()) +":"+strings[0]+"号厅,"+gameStatus.getxNum()+"靴"+gameStatus.getjNum()+"局,开奖成功,数据长度:"+list.size());

                if(list.size() > 0){
                    try {
                        //创建财务汇总
                        ServiceUtil.serviceUtil.getFinancialSummaryAppService().create(list);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("创建财务汇总失败");
                    }
                }
                //更新游戏状态
                gameStatus.setStatus(3);
            } else {
                System.out.println(CoreDateUtils.formatDateTime(new Date()) +":"+strings[0]+"号厅,"+gameStatus.getxNum()+"靴"+gameStatus.getjNum()+"局无人游戏,开奖无效");
                //更新游戏状态
                gameStatus.setStatus(3);
            }
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重开本局
     * @param strings
     * @return
     */
    private synchronized Integer reOpen(String[] strings){

        try {
            //游戏状态 未进入下一局前可重开 即状态3,4可以重开
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
            if(gameStatus == null || (gameStatus.getStatus() != 3 && gameStatus.getStatus() != 4)){
                return 1;
            }
            Map<String, WSMessage> map = GlobalVariable.getBetMap().get(Integer.valueOf(strings[0]));
            BigDecimal[] total = GlobalVariable.getTotalMap().get(strings[0]);

            //本局游戏记录
            IGameDetailedAppService gameDetailedAppService = ServiceUtil.serviceUtil.getGameDetailedAppService();
            List<CreateGameDetailedCommand> list1 = new ArrayList<>();
            if( map != null && map.size()>0){
                for(WSMessage wsMessage : map.values()){

                    //记录存在
                    if(wsMessage != null){

                        CreateGameDetailedCommand command = new CreateGameDetailedCommand(wsMessage,strings,gameStatus);

                        //如果庄赢
                        if (1 == command.getLottery()[0]) {
                            //计算庄闲盈亏
                            command.setBankPlayProfit(command.getBanker().multiply(BigDecimal.valueOf(1.95)).add(command.getBankPlayProfit()));
                        } else {
                            //计算庄闲洗码
                            command.setBankPlayLose(command.getBankPlayLose().add(command.getBanker()));
                        }

                        //如果闲赢
                        if (1 == command.getLottery()[1]) {
                            //计算庄闲盈亏
                            command.setBankPlayProfit(command.getPlayer().multiply(BigDecimal.valueOf(2)).add(command.getBankPlayProfit()));
                        } else {
                            //计算庄闲洗码
                            command.setBankPlayLose(command.getBankPlayLose().add(command.getPlayer()));
                        }

                        //如果是庄对
                        if (1 == command.getLottery()[2]) {
                            //计算庄对
                            command.setTriratnaProfit(command.getBankPair().multiply(BigDecimal.valueOf(12)).add(command.getTriratnaProfit()));
                        } else {
                            //计算三宝洗码
                            command.setTriratnaLose(command.getTriratnaLose().add(command.getBankPair()));
                        }

                        //如果是闲对
                        if (1 == command.getLottery()[3]) {
                            //计算闲对
                            command.setTriratnaProfit(command.getPlayerPair().multiply(BigDecimal.valueOf(12)).add(command.getTriratnaProfit()));
                        } else {
                            //计算三宝洗码
                            command.setTriratnaLose(command.getTriratnaLose().add(command.getPlayerPair()));
                        }

                        //如果是和
                        if (1 == command.getLottery()[4]) {
                            //如何是和 则庄闲洗码清0
                            command.setBankPlayLose(BigDecimal.valueOf(0));
                            //退还庄闲本金
                            command.setBankPlayProfit(command.getPlayer().add(command.getBanker()));
                            //计算和
                            command.setTriratnaProfit(command.getDraw().multiply(BigDecimal.valueOf(9)).add(command.getTriratnaProfit()));
                        } else {
                            //计算三宝洗码
                            command.setTriratnaLose(command.getTriratnaLose().add(command.getDraw()));
                        }

                        command.setWinTotal(command.getBankPlayProfit().add(command.getTriratnaProfit()));
                        //计算盈利
                        //庄闲盈利   庄闲赢钱之和-庄闲本金
                        command.setBankPlayProfit(command.getBankPlayProfit().subtract(command.getBanker()).subtract(command.getPlayer()));
                        //三宝盈利  三宝赢钱之和-三宝本金
                        command.setTriratnaProfit(command.getTriratnaProfit().subtract(command.getBankPair()).subtract(command.getPlayerPair()).subtract(command.getDraw()));
                        //赢钱总和,当前盈亏减去之前的盈亏，即积分变动的实际值
                        BigDecimal changeScore = command.getWinTotal().subtract(wsMessage.getThisProfit());
                        //更新玩家本局游戏明细
                        if(wsMessage.getVirtual() != 3){
                            User user = gameDetailedAppService.update(command,changeScore);
                            wsMessage.getScore()[6] = user.getScore();
                        }else {
                            //机器人
                            if(changeScore.compareTo(BigDecimal.valueOf(0)) != 0){
                                Robot robot = ServiceUtil.serviceUtil.getRobotAppService().reOpen(changeScore,wsMessage.getUsername());
                                if(robot == null){
                                    wsMessage.getScore()[6] = wsMessage.getScore()[6].add(command.getWinTotal());
                                }else {
                                    wsMessage.getScore()[6] = robot.getScore();
                                }
                            }
                        }
                        //更新页面信息
                        wsMessage.getScore()[5] = command.getBankPlayProfit().add(command.getTriratnaProfit());

                        total[6] = total[6].add(command.getTriratnaProfit()).add(command.getBankPlayProfit());
                        total[7] = total[7].add(command.getTriratnaProfit()).add(command.getBankPlayProfit());
                        wsMessage.setTotal(total);

                        //虚拟玩家不记录游戏数据
                        if (wsMessage.getVirtual() == 2){
                            list1.add(command);
                        }
                    }
                }
                if(list1.size() > 0){
                    try {
                        //更新财务汇总
                        ServiceUtil.serviceUtil.getFinancialSummaryAppService().update(list1);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("更新财务汇总失败");
                    }
                }
                return 0;
            }else {
                //无可更新数据
                System.out.println("无可操作数据:");
                return 2;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 2;
    }

    /**
     * 机器人补分
     * @param robotMap 机器人
     */
    private static void addScore(Map<String,Robot> robotMap){

        if(robotMap != null){
            System.out.println("机器人补分检测,数量："+robotMap.size());
            for(Robot robot : robotMap.values()){
                if(robot.getScore().compareTo(robot.getScoreMin()) <0){
                    //需要补分
                    System.out.print(robot.getName()+"原分数:"+robot.getScore());
                    robot.setScore(robot.getScore().add(robot.getAddScore()));
                    ServiceUtil.serviceUtil.getRobotAppService().updateById(robot.getId(),robot.getScore());
                    System.out.println(",补分后:"+robot.getScore());
                }else {
                    System.out.println(robot.getName()+"当前分数："+robot.getScore()+",下限分数："+robot.getScoreMin()+",无需补分");
                }
            }
        }
    }

}
