package bjl.core.chat;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.robot.command.CreateRobotChatCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.core.util.ServiceUtil;
import bjl.domain.model.account.Account;
import bjl.domain.model.robot.Robot;
import bjl.domain.model.user.User;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.GameStatus;
import bjl.websocket.command.WSMessage;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2017/12/27
 */
public class ChatProcess {

    /**
     * 消息处理
     */
    public static JSONObject messageProcess(CreateChatCommand chatCommand){

        //是否禁言
        Account account = ServiceUtil.serviceUtil.getAccountAppService().searchByUsername(chatCommand.getUserid());
        if(account != null){
            if(account.getGag() != null && account.getGag() == 1){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cbid", chatCommand.getCbid());
                jsonObject.put("code", 1);
                jsonObject.put("errmsg", "该账号已被禁言");
                return jsonObject;
            }
        }

        if (chatCommand.getTexttype() == 1 || chatCommand.getTexttype() == 3 || chatCommand.getTexttype() == 4 || chatCommand.getTexttype() == 5) {
            //文字或图片消息
            ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);
            return null;

        } else if (chatCommand.getTexttype() == 2) {
            JSONObject jsonObject = new JSONObject();
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(chatCommand.getRoomtype());
            if (gameStatus == null || gameStatus.getStatus() != 1) {
                jsonObject.put("cbid", chatCommand.getCbid());
                jsonObject.put("code", 1);
                jsonObject.put("errmsg", "游戏未开始，或已停止投注");
                return jsonObject;
            }
            //下注消息
            if (!CoreStringUtils.isEmpty(chatCommand.getUserid()) && !CoreStringUtils.isEmpty(chatCommand.getText()) && chatCommand.getRoomtype() != null) {
                //下注同步处理
                synchronized (chatCommand.getUserid()) {

                    //先查看玩家是否下过注
                    GlobalVariable.getBetMap().computeIfAbsent(chatCommand.getRoomtype(), k -> new HashMap<>());
                    WSMessage wsMessage = GlobalVariable.getBetMap().get(chatCommand.getRoomtype()).get(chatCommand.getUserid());
                    String[] strings = chatCommand.getText().split(":");
                    //玩家当前积分
                    BigDecimal currentScore;

                    if (wsMessage == null) {  //玩家没有下过注
                        jsonObject.put("errmsg","");
                        //先扣除玩家下注金额
                        Object[] objects = ServiceUtil.serviceUtil.getGameBetAppService().gameBet(chatCommand);
                        jsonObject.put("cbid", chatCommand.getCbid());
                        jsonObject.put("code", 1);

                        if (1 == (Integer) objects[0]) {
                            jsonObject.put("errmsg", "积分类型错误");
                            return jsonObject;
                        } else if(2 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "用户不存在");
                            return jsonObject;
                        } else if(3 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "积分不足");
                            return jsonObject;
                        } else if(4 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "单次下注不能最小于限红");
                            return jsonObject;
                        } else if(5 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "下注之和超过最大限红");
                            return jsonObject;
                        }
                        WSMessage message = new WSMessage(chatCommand.getUserid(), (User) objects[1], strings[0], strings[1]);
                        //将玩家下注情况暂存在内存中
                        GlobalVariable.getBetMap().get(chatCommand.getRoomtype()).put(chatCommand.getUserid(), message);
                        //更新页面玩家下注情况
                        updateMessage(message, chatCommand.getRoomtype(), 1, new BigDecimal[]{message.getScore()[0], message.getScore()[1], message.getScore()[2],
                                message.getScore()[3], message.getScore()[4]});
                        //广播信息
                        ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);
                        currentScore = ((User) objects[1]).getScore();

                    } else {
                        //玩家已下注过，更新玩家下注信息
                        BigDecimal[] bigDecimals = wsMessage.getScore();
                        BigDecimal addScore = BigDecimal.valueOf(Integer.valueOf(strings[1]));
                        //计算玩家下注变更
                        BigDecimal[] betBigDecimal = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                                BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
                        //变更金额
                        BigDecimal changeScore = BigDecimal.valueOf(0);
                        jsonObject.put("errmsg","");
                        switch (strings[0]) {
                            //同指令，覆盖之前的下注。当前下注金额-之前下注金额=变更金额
                            case "1":
                                betBigDecimal[0] = addScore.subtract(bigDecimals[0]);
                                changeScore = betBigDecimal[0];
                                if(bigDecimals[1].compareTo(BigDecimal.valueOf(0))>0){  //庄闲对压，以最后一次为准
                                    changeScore = changeScore.subtract(bigDecimals[1]);
                                    jsonObject.put("errmsg","庄闲不能对压，对压后以最后一次为准");
                                    betBigDecimal[1] = BigDecimal.valueOf(-1).multiply(bigDecimals[1]);
                                }
                                break;
                            case "2":
                                betBigDecimal[4] = addScore.subtract(bigDecimals[4]);
                                changeScore = betBigDecimal[4];
                                break;
                            case "3":
                                betBigDecimal[1] = addScore.subtract(bigDecimals[1]);
                                changeScore = betBigDecimal[1];
                                if(bigDecimals[0].compareTo(BigDecimal.valueOf(0))>0){  //庄闲对压，以最后一次为准
                                    changeScore = changeScore.subtract(bigDecimals[0]);
                                    jsonObject.put("errmsg","庄闲不能对压，对压后以最后一次为准");
                                    betBigDecimal[0] = BigDecimal.valueOf(-1).multiply(bigDecimals[0]);
                                }
                                break;
                            case "4":
                                betBigDecimal[2] = addScore.subtract(bigDecimals[2]);
                                changeScore = betBigDecimal[2];
                                break;
                            case "5":
                                betBigDecimal[3] = addScore.subtract(bigDecimals[3]);
                                changeScore = betBigDecimal[3];
                                break;
                            case "6":
                                betBigDecimal[2] = addScore.subtract(bigDecimals[2]);
                                betBigDecimal[3] = addScore.subtract(bigDecimals[3]);
                                changeScore = betBigDecimal[2].add(betBigDecimal[3]);
                                break;
                            case "7":
                                betBigDecimal[2] = addScore.subtract(bigDecimals[2]);
                                betBigDecimal[3] = addScore.subtract(bigDecimals[3]);
                                betBigDecimal[4] = addScore.subtract(bigDecimals[4]);
                                changeScore = betBigDecimal[2].add(betBigDecimal[3]).add(betBigDecimal[4]);
                        }
                        //计算玩家总资金变更
                        Object[] objects = ServiceUtil.serviceUtil.getGameBetAppService().repeatBet(chatCommand,changeScore);
                        jsonObject.put("cbid", chatCommand.getCbid());
                        jsonObject.put("code", 1);
                        if (1 == (Integer) objects[0]) {
                            jsonObject.put("errmsg", "积分类型错误");
                            return jsonObject;
                        } else if(2 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "用户不存在");
                            return jsonObject;
                        } else if(3 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "积分不足");
                            return jsonObject;
                        } else if(4 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "单次下注不能最小于限红");
                            return jsonObject;
                        } else if(5 == (Integer) objects[0]){
                            jsonObject.put("errmsg", "下注之和超过最大限红");
                            return jsonObject;
                        }
                        switch (strings[0]) {
                            //更新玩家下注信息.
                            case "1":
                                bigDecimals[0] = addScore;
                                if(betBigDecimal[1].compareTo(BigDecimal.valueOf(0)) < 0){
                                    bigDecimals[1] = BigDecimal.valueOf(0);
                                }
                                break;
                            case "2":
                                bigDecimals[4] = addScore;
                                break;
                            case "3":
                                bigDecimals[1] = addScore;
                                if(betBigDecimal[0].compareTo(BigDecimal.valueOf(0)) < 0){
                                    bigDecimals[0] = BigDecimal.valueOf(0);
                                }
                                break;
                            case "4":
                                bigDecimals[2] = addScore;
                                break;
                            case "5":
                                bigDecimals[3] = addScore;
                                break;
                            case "6":
                                bigDecimals[2] = addScore;
                                bigDecimals[3] = addScore;
                                break;
                            case "7":
                                bigDecimals[2] = addScore;
                                bigDecimals[3] = addScore;
                                bigDecimals[4] = addScore;
                        }
                        bigDecimals[6] = (BigDecimal) objects[1];
                        bigDecimals[7] = (BigDecimal) objects[2];
                        //更新页面玩家下注情况
                        updateMessage(wsMessage, chatCommand.getRoomtype(), 0, betBigDecimal);
                        //广播信息
                        ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);
                        currentScore = (BigDecimal) objects[1];
                    }
                    jsonObject.put("cbid", chatCommand.getCbid());
                    jsonObject.put("code", 0);
                    jsonObject.put("gold", currentScore.setScale(0,BigDecimal.ROUND_HALF_UP));

                    return jsonObject;
                }
            } else {
                jsonObject.put("cbid", chatCommand.getCbid());
                jsonObject.put("code", 1);
                jsonObject.put("errmsg", "无法识别的信息");
                return jsonObject;
            }
        }
        else if (chatCommand.getTexttype() == 6){
            //撤销下注
            JSONObject jsonObject = new JSONObject();
            GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(chatCommand.getRoomtype());
            if ( gameStatus.getStatus() != 1) {
                jsonObject.put("cbid", chatCommand.getCbid());
                jsonObject.put("code", 1);
                jsonObject.put("errmsg", "当前无法撤销下注");
                return jsonObject;
            }
            //先查看玩家是否下过注
            GlobalVariable.getBetMap().computeIfAbsent(chatCommand.getRoomtype(), k -> new HashMap<>());
            WSMessage wsMessage = GlobalVariable.getBetMap().get(chatCommand.getRoomtype()).get(chatCommand.getUserid());
            if(wsMessage != null){
                //统计玩家已下注的金额
                BigDecimal[] betBigDecimal = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),
                        BigDecimal.valueOf(0),BigDecimal.valueOf(0)};

                BigDecimal score = BigDecimal.valueOf(0);
                for(int i=0;i<5;i++){
                    score = score.add(wsMessage.getScore()[i]);
                    betBigDecimal[i] = BigDecimal.valueOf(-1).multiply(wsMessage.getScore()[i]);
                    wsMessage.getScore()[i] = BigDecimal.valueOf(0);
                }
                //退还下注金额
                User user = ServiceUtil.serviceUtil.getGameBetAppService().revokeBet(chatCommand.getUserid(),score);
                if(user == null){
                    jsonObject.put("cbid", chatCommand.getCbid());
                    jsonObject.put("code", 1);
                    jsonObject.put("errmsg", "撤销失败");
                    return jsonObject;
                }
                wsMessage.getScore()[6] = user.getScore();
                wsMessage.getScore()[7] = user.getPrimeScore();

                //更新页面玩家下注情况
                updateMessage(wsMessage, chatCommand.getRoomtype(), 0, betBigDecimal);
                //广播信息
                ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);
                //撤销成功,返回玩家当前积分
                jsonObject.put("cbid", chatCommand.getCbid());
                jsonObject.put("code", 0);
                jsonObject.put("gold",user.getScore());
                jsonObject.put("errmsg", "撤销成功");
                return jsonObject;
            }else {
                //广播信息
                ServiceUtil.serviceUtil.getChatAppService().create(chatCommand);
            }
        }
        return null;
    }

    /**
     * 机器人模拟玩家下注
     */
    public static boolean robotBet(Integer roomType, Robot robot){

        try {
            GlobalVariable.getBetMap().computeIfAbsent(roomType, k -> new HashMap<>());

            WSMessage wsMessage = GlobalVariable.getBetMap().get(roomType).get(robot.getId());

            if(wsMessage == null){

                //之前没有下过注
                //随机下庄闲还是下三宝
                CreateRobotChatCommand command = new CreateRobotChatCommand();
                String head = ServiceUtil.serviceUtil.getFileUploadConfig().getDomainName()+ServiceUtil.serviceUtil.getFileUploadConfig().getFolder()+robot.getHead()+".png";
                command.setHead(head);
                command.setName(robot.getName());
                command.setRoomtype(roomType);
                command.setUsername(robot.getId());
                command.setTexttype(2);

                Random random = new Random();
                int range = random.nextInt(100)+1;
                if(range <= robot.getBankPlayRatio().intValue()){ //命中下注庄闲
                    //先随机庄闲的下注金额
                    int betMoney = random.nextInt(robot.getBankPlayMax()-robot.getBankPlayMin())+robot.getBankPlayMin()+1;
                    //求余
                    int remainder = betMoney%5;
                    //保证下注分数是5的倍数
                    betMoney = remainder != 0 ? betMoney+(5-remainder) : betMoney;

                    if((robot.getScore().intValue() - betMoney) <0){
                        //机器人资金不足
                        System.out.println(robot.getName()+"第1次下注庄闲，金额不足");
                        return false;
                    }
                    command.setScore(betMoney);

                    //在庄闲中随机下一个
                    if(random.nextInt(2) == 0){
                        //下闲
                        command.setBetType(1);
                    }else {
                        //下庄
                        command.setBetType(3);
                    }
                    //扣除后余额
                    Integer restScore = ServiceUtil.serviceUtil.getRobotAppService().bet(robot.getId(),betMoney);
                    if(restScore == null){
                        //下注失败
                        return false;
                    }
                    robot.setScore(BigDecimal.valueOf(restScore));
                    WSMessage message = new WSMessage(robot,command.getBetType(),betMoney);
                    //将玩家下注情况暂存在内存中
                    GlobalVariable.getBetMap().get(roomType).put(robot.getId(), message);
                    //更新页面玩家下注情况
                    updateMessage(message, roomType, 1, new BigDecimal[]{message.getScore()[0], message.getScore()[1], message.getScore()[2],
                            message.getScore()[3], message.getScore()[4]});
                    //广播信息
                    ServiceUtil.serviceUtil.getChatAppService().create(command);
                    System.out.println(robot.getName()+"第1次下注庄闲，金额"+betMoney);

                    return true;

                }else if(robot.getBankPlayRatio().intValue() <range && range <= robot.getBankPlayRatio().intValue()+robot.getTriratnaRatio().intValue()){ //命中下注三宝
                    //先随机三宝的下注金额
                    int betMoney = random.nextInt(robot.getTriratnaMax()-robot.getTriratnaMin())+robot.getTriratnaMin()+1;
                    //求余
                    int remainder = betMoney%5;
                    //保证下注分数是5的倍数
                    betMoney = remainder != 0 ? betMoney+(5-remainder) : betMoney;
                    command.setScore(betMoney);

                    //在庄对，闲对，和，二宝，三宝中随机下一个
                    Integer type = random.nextInt(5);
                    switch (type){
                        case 0:   //庄对
                            command.setBetType(5);
                            break;
                        case 1:   //闲对
                            command.setBetType(4);
                            break;
                        case 2:     //和
                            command.setBetType(2);
                            break;
                        case 3:    //二宝
                            command.setBetType(6);
                            command.setScore(betMoney*2);
                            break;
                        case 4:     //三宝
                            command.setBetType(7);
                            command.setScore(betMoney*3);
                    }
                    if((robot.getScore().intValue() - betMoney) <0){
                        System.out.println(robot.getName()+"第1次下注三宝，金额不足");
                        //机器人资金不足
                        return false;
                    }
                    //扣除后余额
                    Integer restScore = ServiceUtil.serviceUtil.getRobotAppService().bet(robot.getId(),betMoney);
                    if(restScore == null){
                        //下注失败
                        return false;
                    }
                    robot.setScore(BigDecimal.valueOf(restScore));
                    WSMessage message = new WSMessage(robot,command.getBetType(),betMoney);
                    //将玩家下注情况暂存在内存中
                    GlobalVariable.getBetMap().get(roomType).put(robot.getId(), message);
                    //更新页面玩家下注情况
                    updateMessage(message, roomType, 1, new BigDecimal[]{message.getScore()[0], message.getScore()[1], message.getScore()[2],
                            message.getScore()[3], message.getScore()[4]});

                    System.out.println(robot.getName()+"第1次下注三宝，金额"+betMoney);
                    //广播信息
                    ServiceUtil.serviceUtil.getChatAppService().create(command);
                    return true;
                }else {
                    System.out.println("第一次几率:"+range+",不下注");
                    return true;
                }
            }else {
                //之前下过注
                System.out.println(wsMessage.getName()+"重复下注");
                //玩家已下注过，更新玩家下注信息
                BigDecimal[] bigDecimals = wsMessage.getScore();
                Random random = new Random();
                int range = random.nextInt(100)+1;
                int betMoney,betType = 0;
                if(range <= robot.getBankPlayRatio().intValue()) { //命中下注庄闲
                    //随机庄闲的下注金额
                    betMoney = random.nextInt(robot.getBankPlayMax()-robot.getBankPlayMin())+robot.getBankPlayMin()+1;
                    //求余
                    int remainder = betMoney%5;
                    //保证下注分数是5的倍数
                    betMoney = remainder != 0 ? betMoney+(5-remainder) : betMoney;
                    //在庄闲中随机下一个
                    if(random.nextInt(2) == 0){
                        //下闲
                        betType = 1;
                    }else {
                        //下庄
                        betType = 3;
                    }
                    System.out.println(robot.getName()+"重复下注庄闲，金额"+betMoney);
                } else if(robot.getBankPlayRatio().intValue() <range && range <= robot.getBankPlayRatio().intValue()+robot.getTriratnaRatio().intValue()){
                    //随机三宝的下注金额
                    betMoney = random.nextInt(robot.getTriratnaMax()-robot.getTriratnaMin())+robot.getTriratnaMin()+1;
                    //求余
                    int remainder = betMoney%5;
                    //保证下注分数是5的倍数
                    betMoney = remainder != 0 ? betMoney+(5-remainder) : betMoney;
                    //在庄对，闲对，和，二宝，三宝中随机下一个
                    Integer type = random.nextInt(5);
                    switch (type){
                        case 0:   //庄对
                            betType = 5;
                            break;
                        case 1:   //闲对
                            betType = 4;
                            break;
                        case 2:     //和
                            betType = 2;
                            break;
                        case 3:    //二宝
                            betType = 6;
                            break;
                        case 4:     //三宝
                            betType = 7;
                    }
                    System.out.println(robot.getName()+"重复下注三宝，金额"+betMoney);
                } else {
                    System.out.println("重复几率:"+range+",不下注");
                    return true;
                }
                BigDecimal addScore = BigDecimal.valueOf(betMoney);
                //计算玩家下注变更
                BigDecimal[] betBigDecimal = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                        BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
                //变更金额
                BigDecimal changeScore = BigDecimal.valueOf(0);

                switch (betType) {
                    //同指令，覆盖之前的下注。当前下注金额-之前下注金额=变更金额
                    case 1:
                        betBigDecimal[0] = addScore.subtract(bigDecimals[0]);
                        changeScore = betBigDecimal[0];
                        if(bigDecimals[1].compareTo(BigDecimal.valueOf(0))>0){  //庄闲对压，以最后一次为准
                            changeScore = changeScore.subtract(bigDecimals[1]);
                            betBigDecimal[1] = BigDecimal.valueOf(-1).multiply(bigDecimals[1]);
                        }
                        break;
                    case 2:
                        betBigDecimal[4] = addScore.subtract(bigDecimals[4]);
                        changeScore = betBigDecimal[4];
                        break;
                    case 3:
                        betBigDecimal[1] = addScore.subtract(bigDecimals[1]);
                        changeScore = betBigDecimal[1];
                        if(bigDecimals[0].compareTo(BigDecimal.valueOf(0))>0){  //庄闲对压，以最后一次为准
                            changeScore = changeScore.subtract(bigDecimals[0]);
                            betBigDecimal[0] = BigDecimal.valueOf(-1).multiply(bigDecimals[0]);
                        }
                        break;
                    case 4:
                        betBigDecimal[2] = addScore.subtract(bigDecimals[2]);
                        changeScore = betBigDecimal[2];
                        break;
                    case 5:
                        betBigDecimal[3] = addScore.subtract(bigDecimals[3]);
                        changeScore = betBigDecimal[3];
                        break;
                    case 6:
                        betBigDecimal[2] = addScore.subtract(bigDecimals[2]);
                        betBigDecimal[3] = addScore.subtract(bigDecimals[3]);
                        changeScore = betBigDecimal[2].add(betBigDecimal[3]);
                        break;
                    case 7:
                        betBigDecimal[2] = addScore.subtract(bigDecimals[2]);
                        betBigDecimal[3] = addScore.subtract(bigDecimals[3]);
                        betBigDecimal[4] = addScore.subtract(bigDecimals[4]);
                        changeScore = betBigDecimal[2].add(betBigDecimal[3]).add(betBigDecimal[4]);
                }
                //计算玩家总资金变更
                Integer restScore = ServiceUtil.serviceUtil.getRobotAppService().bet(wsMessage.getUsername(),changeScore.intValue());
                if (restScore == null){
                    //资金不足
                    System.out.println(robot.getName()+"重复下注三宝，金额不足");
                    return false;
                }

                switch (betType) {
                    //更新玩家下注信息.
                    case 1:
                        bigDecimals[0] = addScore;
                        if(betBigDecimal[1].compareTo(BigDecimal.valueOf(0)) < 0){
                            bigDecimals[1] = BigDecimal.valueOf(0);
                        }
                        break;
                    case 2:
                        bigDecimals[4] = addScore;
                        break;
                    case 3:
                        bigDecimals[1] = addScore;
                        if(betBigDecimal[0].compareTo(BigDecimal.valueOf(0)) < 0){
                            bigDecimals[0] = BigDecimal.valueOf(0);
                        }
                        break;
                    case 4:
                        bigDecimals[2] = addScore;
                        break;
                    case 5:
                        bigDecimals[3] = addScore;
                        break;
                    case 6:
                        bigDecimals[2] = addScore;
                        bigDecimals[3] = addScore;
                        break;
                    case 7:
                        bigDecimals[2] = addScore;
                        bigDecimals[3] = addScore;
                        bigDecimals[4] = addScore;
                }
                bigDecimals[6] = BigDecimal.valueOf(restScore);
                //更新机器人剩余积分
                GlobalVariable.getRobotMap().get(roomType).get(wsMessage.getUsername()).setScore(bigDecimals[6]);

                CreateRobotChatCommand command = new CreateRobotChatCommand();

                String head = ServiceUtil.serviceUtil.getFileUploadConfig().getDomainName()+ServiceUtil.serviceUtil.getFileUploadConfig().getFolder()+robot.getHead()+".png";
                command.setHead(head);
                command.setName(robot.getName());
                command.setRoomtype(roomType);
                command.setUsername(robot.getId());
                command.setTexttype(2);
                command.setBetType(betType);
                command.setScore(betMoney);

                //更新页面玩家下注情况
                updateMessage(wsMessage, roomType, 0, betBigDecimal);
                //广播信息
                ServiceUtil.serviceUtil.getChatAppService().create(command);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更新玩家下注信息
     */
    public static void updateMessage(WSMessage wsMessage,Integer roomType,Integer exist,BigDecimal[] betBigDecimal){
        //取出客户端连接
        Session session;
        if(roomType == 1){
            //菲律宾厅
            session = GlobalVariable.getWsSession().get("1");
        }else if(roomType == 2){
            //越南厅
            session = GlobalVariable.getWsSession().get("2");
        }else {
            //澳门厅
            session = GlobalVariable.getWsSession().get("3");
        }

        //更新合计
        BigDecimal[] bigDecimals = updateTotal(roomType,exist,wsMessage,betBigDecimal);
        wsMessage.setTotal(bigDecimals);

        if(session == null){
            System.out.println(CoreDateUtils.formatDateTime(new Date())+"客户端类型【"+roomType+"】连接为空或已断开，无法更新页面玩家下注情况:"+JSONObject.toJSONString(wsMessage));
        }else {
            //更新页面玩家下注情况
            try {
                session.getBasicRemote().sendText(JSONObject.toJSONString(wsMessage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 更新合计
     * @return
     */
    private static BigDecimal[] updateTotal(Integer roomType,Integer exist,WSMessage wsMessage,BigDecimal[] betBigDecimal){
        BigDecimal[] bet = wsMessage.getScore();
        BigDecimal[] bigDecimals = GlobalVariable.getTotalMap().get(roomType.toString());
        if(bigDecimals == null){
            //没有合计
            bigDecimals = new BigDecimal[14];
            bigDecimals[0] = BigDecimal.valueOf(1);
            System.arraycopy(bet, 0, bigDecimals, 1, 8);
            //占比后
            //台面分计算
            if(wsMessage.getVirtual() == 2){
                //不是虚拟则计算台面分
                //闲
                bigDecimals[9] = bet[0].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())).setScale(0,BigDecimal.ROUND_HALF_UP);
                wsMessage.setPlayMesa(bet[0].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())).setScale(0,BigDecimal.ROUND_HALF_UP));
                //庄
                bigDecimals[10] = bet[1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())).setScale(0,BigDecimal.ROUND_HALF_UP);
                wsMessage.setBankMesa(bet[1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())).setScale(0,BigDecimal.ROUND_HALF_UP));

                bigDecimals[11] = bet[2].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())).setScale(0,BigDecimal.ROUND_HALF_UP);

                bigDecimals[12] = bet[3].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())).setScale(0,BigDecimal.ROUND_HALF_UP);

                bigDecimals[13] = bet[4].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())).setScale(0,BigDecimal.ROUND_HALF_UP);

            }else {
                //是虚拟或机器人则不计算台面分
                bigDecimals[9] = BigDecimal.valueOf(0);
                bigDecimals[10] = BigDecimal.valueOf(0);
                bigDecimals[11] = BigDecimal.valueOf(0);
                bigDecimals[12] = BigDecimal.valueOf(0);
                bigDecimals[13] = BigDecimal.valueOf(0);
            }


            GlobalVariable.getTotalMap().put(roomType.toString(),bigDecimals);
        }else {
            //有合计
            if(exist == 0){
                //玩家之前已存在,
                //先直接将玩家下注情况合并到总计
                for(int i = 0;i<5;i++){
                    bigDecimals[i+1] = bigDecimals[i+1].add(betBigDecimal[i]);
                }
                //遍历map中的值
                BigDecimal totalS = BigDecimal.valueOf(0);
                BigDecimal totalP = BigDecimal.valueOf(0);
                BigDecimal bankMesa = BigDecimal.valueOf(0);
                BigDecimal playMesa = BigDecimal.valueOf(0);
                BigDecimal bankPirMesa = BigDecimal.valueOf(0);
                BigDecimal playPirMesa = BigDecimal.valueOf(0);
                BigDecimal drawMesa = BigDecimal.valueOf(0);

                for (WSMessage value : GlobalVariable.getBetMap().get(roomType).values()) {
                    if(value.getVirtual() == 2){
                        totalS = totalS.add(value.getScore()[6]);
                        totalP = totalP.add(value.getScore()[7]);
                        bankMesa = bankMesa.add(value.getScore()[0].multiply(BigDecimal.valueOf(1).subtract(value.getRatio())));
                        playMesa = playMesa.add(value.getScore()[1].multiply(BigDecimal.valueOf(1).subtract(value.getRatio())));
                        playPirMesa = playPirMesa.add(value.getScore()[2].multiply(BigDecimal.valueOf(1).subtract(value.gettRatio())));
                        bankPirMesa = bankPirMesa.add(value.getScore()[3].multiply(BigDecimal.valueOf(1).subtract(value.gettRatio())));
                        drawMesa = drawMesa.add(value.getScore()[4].multiply(BigDecimal.valueOf(1).subtract(value.gettRatio())));
                    }else {
                        totalS = totalS.add(value.getScore()[6]);
                        totalP = totalP.add(value.getScore()[7]);
                    }

                }
                bigDecimals[7] = totalS;
                bigDecimals[8] = totalP;
                bigDecimals[9] = bankMesa.setScale(0,BigDecimal.ROUND_HALF_UP);
                bigDecimals[10] = playMesa.setScale(0,BigDecimal.ROUND_HALF_UP);
                bigDecimals[11] = playPirMesa.setScale(0,BigDecimal.ROUND_HALF_UP);
                bigDecimals[12] = bankPirMesa.setScale(0,BigDecimal.ROUND_HALF_UP);
                bigDecimals[13] = drawMesa.setScale(0,BigDecimal.ROUND_HALF_UP);

            }else {
                //玩家之前不存在
                bigDecimals[0] = bigDecimals[0].add(BigDecimal.valueOf(1)); //人数+1
                //直接将玩家下注情况分合并到总计
                for(int i = 0;i<8;i++){
                    bigDecimals[i+1] = bigDecimals[i+1].add(bet[i]);
                }
                if(wsMessage.getVirtual() == 2){
                    bigDecimals[9] = (bigDecimals[9].add(wsMessage.getScore()[0].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())))).setScale(0,BigDecimal.ROUND_HALF_UP);
                    bigDecimals[10] = (bigDecimals[10].add(wsMessage.getScore()[1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())))).setScale(0,BigDecimal.ROUND_HALF_UP);
                    bigDecimals[11] = (bigDecimals[11].add(wsMessage.getScore()[2].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())))).setScale(0,BigDecimal.ROUND_HALF_UP);
                    bigDecimals[12] = (bigDecimals[12].add(wsMessage.getScore()[3].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())))).setScale(0,BigDecimal.ROUND_HALF_UP);
                    bigDecimals[13] = (bigDecimals[13].add(wsMessage.getScore()[4].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())))).setScale(0,BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return bigDecimals;
    }

    /**
     * 获取下注表格数据
     * @param jsonObject
     * @return
     */
    public static JSONObject getTableData(JSONObject jsonObject){

        Map<String,Object> map = new HashMap<>();

        String index = jsonObject.getString("index");
        if (!CoreStringUtils.isEmpty(index)){
            String[] strings = index.split("_");
            //是否是当天的时间
            if(CoreDateUtils.formatDate(new Date()).equals(strings[0])){
                Map<String, WSMessage> messageMap = GlobalVariable.getBetMap().get(Integer.valueOf(strings[1]));
                BigDecimal[] t = GlobalVariable.getTotalMap().get(strings[1]);
                GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[1]));
                //是否在内存中,时间、鞋局都相同
                if(messageMap != null  && strings[2].equals(gameStatus.getxNum().toString()) && strings[3].equals(gameStatus.getjNum().toString())){
                    List<Object[]> list = new ArrayList<>();
                    if("1".equals(strings[4])){
                        //投注表
                        BigDecimal[] total = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)
                                ,BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
                        for(WSMessage wsMessage : messageMap.values()){

                            if (wsMessage.getHide() == 1){
                                //合计
                                total[0] = total[0].add(BigDecimal.valueOf(1));
                                total[1] = total[1].add(wsMessage.getScore()[0]);
                                total[2] = total[2].add(wsMessage.getScore()[1]);
                                total[3] = total[3].add(wsMessage.getScore()[2]);
                                total[4] = total[4].add(wsMessage.getScore()[3]);
                                total[5] = total[5].add(wsMessage.getScore()[4]);

                                //添加玩家数据
                                Object[] b = new Object[6];

                                b[0] = wsMessage.getName();

                                System.arraycopy(wsMessage.getScore(), 0, b, 1, b.length-1);

                                list.add(b);
                                map.put("player",list);
                            }
                        }
                        map.put("total",total);
                    }else {
                        //分数表
                        BigDecimal[] total = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)};
                        for(WSMessage wsMessage : messageMap.values()){
                            if(wsMessage.getHide() == 1){

                                //合计
                                total[0] = total[0].add(BigDecimal.valueOf(1));
                                total[1] = total[1].add(wsMessage.getScore()[5]);
                                total[2] = total[2].add(wsMessage.getScore()[6]);
                                total[3] = total[3].add(wsMessage.getScore()[7]);

                                //添加玩家数据
                                Object[] b = new Object[4];

                                b[0] = wsMessage.getName();

                                System.arraycopy(wsMessage.getScore(), 5, b, 1, b.length-1);

                                list.add(b);
                                map.put("player",list);
                            }
                        }
                        map.put("total",total);
                    }
                } else {
                    //不在内存中，则到数据库中查找
                    Map<String,Object> maps = ServiceUtil.serviceUtil.getGameDetailedAppService().apiList(strings);
                    map.put("player",maps.get("list"));
                    map.put("total",maps.get("total"));
                }
            }else {
                //不是当天时间，则到数据库中查找
                Map<String,Object> maps = ServiceUtil.serviceUtil.getGameDetailedAppService().apiList(strings);
                map.put("player",maps.get("list"));
                map.put("total",maps.get("total"));
            }
        }

        jsonObject.put("code",0);
        jsonObject.put("errmsg","获取成功");
        jsonObject.put("data",map);
        return jsonObject;
    }
}
