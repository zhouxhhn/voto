package bjl.core.message;

import bjl.application.bank.command.CreateBankCommand;
import bjl.application.chat.command.CreateChatCommand;
import bjl.application.chat.command.ListChatCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.userManager.command.RegisterUserCommand;
import bjl.core.api.MessageID;
import bjl.core.chat.ChatProcess;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.ServiceUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;


/**
 * TCP 消息处理
 * Created by zhangjin on 2017/10/27.
 */
public class MessageCoding {


    /**
     * 消息分流
     */
    public static JSONObject messageShunt(short messageId, byte[] bytes){

        try {
            switch (messageId){

                case MessageID._LOGIN: //登录
                    RegisterUserCommand registerUserCommand = JSONObject.parseObject(bytes,RegisterUserCommand.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 登录接口："+JSONObject.toJSONString(registerUserCommand));
                    JSONObject jsonObject = ServiceUtil.serviceUtil.getUserManagerAppService().login(registerUserCommand);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._REGISTER: //注册
                    registerUserCommand = JSONObject.parseObject(bytes,RegisterUserCommand.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 注册接口："+JSONObject.toJSONString(registerUserCommand));
                    jsonObject = ServiceUtil.serviceUtil.getUserManagerAppService().register(registerUserCommand);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._USERINFO: //个人信息
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
//                    System.out.println("个人信息接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getUserManagerAppService().info(jsonObject);
//                    System.out.println("返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._MODIFYNAME: //修改昵称
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 修改昵称接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().modifyName(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._MODIFYPASSWORD: //修改密码
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 修改密码接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().modifyPassword(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._FINDPASSWORD: //找回密码
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 找回密码接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().findPassword(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._FINDTWOPWD: //找回银行密码
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 找回二级密码接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().findTwoPwd(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._TRANSFERSCORE: //转账
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 转账接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getTransferAppService().transfer(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._TRANSFERRECORD: //转账记录
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 转账记录接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getTransferAppService().list(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._BANKINFO: //获取银行信息
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 银行信息接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getBankAppService().info(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._BANDBANK: //绑定银行卡
                    CreateBankCommand bankCommand = JSONObject.parseObject(bytes,CreateBankCommand.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 绑定银行卡接口："+JSONObject.toJSONString(bankCommand));
                    jsonObject = ServiceUtil.serviceUtil.getBankAppService().create(bankCommand);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETSAFEBOX: //打开保险箱
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 打开保险箱接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSafeBoxAppService().get(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._SAVESCORE: //向保险箱存积分
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 保险箱存积分接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSafeBoxAppService().access(jsonObject,1);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._TAKESCORE: //从保险箱取积分
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 保险箱取积分接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSafeBoxAppService().access(jsonObject,2);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._RECHARGEDTL: //充值明细
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 保充值明细接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getRechargeAppService().list(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._WITHDRAW: //提现申请
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 提现申请接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getWithdrawAppService().applyWithdraw(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETPHONEBET: //请求电话投注数据
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 请求电话投注数据接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getPhoneBetAppService().getBetData(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._APPLYPHONEBET: //请求电话
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 请求电话投注接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getPhoneBetAppService().applyPhoneBet(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._CHATINFO: //聊天信息
                    CreateChatCommand chatCommand = JSONObject.parseObject(bytes,CreateChatCommand.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 聊天信息接口："+JSONObject.toJSONString(chatCommand));
                    jsonObject = ChatProcess.messageProcess(chatCommand);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETCHATRECORD: //获取聊天记录
                    ListChatCommand listChatCommand = JSONObject.parseObject(bytes,ListChatCommand.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 聊天信息接口："+JSONObject.toJSONString(listChatCommand));
                    jsonObject = ServiceUtil.serviceUtil.getChatAppService().getChatList(listChatCommand);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;
                case MessageID._GETTABLEDATA: //获取表格数据
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取表格数据接口："+jsonObject.toJSONString());
                    jsonObject = ChatProcess.getTableData(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._NOTICE: //系统公告
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 系统公告接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getNoticeAppService().list(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._CHECKQRCODE: //扫码检测
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 扫码检查接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().checkCode(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._MODIFYTWOPWD: //修改保险箱密码
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 修改保险箱密码接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().modifyBankPwd(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._RECHARFECTL: //支付控制
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 支付控制接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getRechargeCtlAppService().get(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._PLAYERLIST: //玩家列表
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 玩家列表接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().playerList(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._ROOMCHECK: //房间检查
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 房间检查接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getChatAppService().roomCheck(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETSPREAD: //获取推广配置
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取推广配置接口："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().getSpread(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._USERSPREAD: //获取玩家收益信息
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取玩家收益信息："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().getByUsername(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._RECEIVE: //领取玩家收益
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 领取玩家收益："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().receive(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._TOTALSUM: //历史推广人数
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 历史推广人数："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().spreadSum(2,jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._TODAYSUM: //今日推广人数
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 今日推广人数："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().spreadSum(1,jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._YESTERDAYPROFIT: //昨日推广收益
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 昨日推广收益："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().yesterdayProfit(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                return jsonObject;

                case MessageID._TODAYPROFIT: //今日推广收益
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 今日推广收益："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().todayProfit(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._WEEKPROFIT: //本周推广收益
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 本周推广收益："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().weekProfit(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._LASTWEEKPROFIT: //上周推广收益
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 上周推广收益："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().lastWeekProfit(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._MONTHPROFIT: //本月推广收益
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 本月推广收益："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().monthProfit(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._SUBDETAILED: //下级详情
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 下级详情："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getSpreadProfitAppService().profitDetailed(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETGAMEURL: //获取游戏链接
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取游戏链接："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().getGameUrl(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._VALIDATECODE: //获取图片验证码
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取图片验证码："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getFileUploadService().validateCode(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._ISBINDTSECURITY: //是否绑定密保
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 是否绑定密保："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().isBindSecurity(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._BINDTSECURITY: //绑定密保
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 绑定密保："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getAccountAppService().bindSecurity(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETACTIVITY: //获取活动列表
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取活动列表："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getActivityAppService().list(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETCAROUSEL: //获取轮播图
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取轮播图："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getCarouselAppService().list(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;

                case MessageID._GETGUIDE: //获取新手指南
                    jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取新手指南："+jsonObject.toJSONString());
                    jsonObject = ServiceUtil.serviceUtil.getGuideAppService().list(jsonObject);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;
                case MessageID._GETDETAILED: //获取个人流水
                    ListGameDetailedCommand listGameDetailedCommand = JSONObject.parseObject(bytes, ListGameDetailedCommand.class);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 获取个人流水："+JSONObject.toJSONString(listGameDetailedCommand));
                    jsonObject = ServiceUtil.serviceUtil.getScoreDetailedAppService().list(listGameDetailedCommand);
//                    jsonObject = ServiceUtil.serviceUtil.getGameDetailedAppService().list(listGameDetailedCommand);
                    System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
                    return jsonObject;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(bytes,JSONObject.class);
        jsonObject.put("code", 1);
        jsonObject.put("errmsg","发生异常");
        System.out.println(CoreDateUtils.formatDateTime(new Date())+" 返回内容:"+jsonObject);
        return jsonObject;
    }
}
