package bjl.mode;

/**
 * Author pengyi
 * Date 17-5-25.
 */
public class MessageID {
    public static final int ERROR_CLIRNT = 10000;//错误
    public static final int HEART_BEAT_SERVER = 10001;//心跳
    public static final int HEART_BEAT_CLIENT = 10002;//心跳
    public static final int LOGIN_SERVER = 10003;//登录
    public static final int LOGIN_CLIENT = 10004;//登录
    public static final int GET_ROOM_SERVER = 10005;//开房
    public static final int GET_ROOM_CLIENT = 10006;//开房
    public static final int REPEAT_LOGIN = 10007;//重复登录
    public static final int NOTICE_SERVER = 10008;//公告
    public static final int NOTICE_CLIENT = 10009;//公告
    public static final int ROOMS_SERVER = 10010;//房间列表
    public static final int ROOMS_CLIENT = 10011;//房间列表
    public static final int RECHARGE_SERVER = 10012;//充值列表
    public static final int RECHARGE_CLIENT = 10013;//充值列表
    public static final int INVITE_SERVER = 10014;//绑定邀请码
    public static final int INVITE_CLIENT = 10015;//绑定邀请码
    public static final int SYSTEM_SERVER = 10016;//系统信息
    public static final int SYSTEM_CLIENT = 10017;//系统信息
    public static final int GAME_RECORD_SERVER = 10018;//游戏记录
    public static final int GAME_RECORD_CLIENT = 10019;//游戏记录
    public static final int RECEIVE_GOLD_SERVER = 10020;//领取金币
    public static final int RECEIVE_GOLD_CLIENT = 10021;//领取金币
    public static final int RECEIVE_BENEFIT_SERVER = 10022;//领取救济金
    public static final int RECEIVE_BENEFIT_CLIENT = 10023;//领取救济金
    public static final int USER_INFO_SERVER = 10024;//个人信息
    public static final int USER_INFO_CLIENT = 10025;//个人信息

    public static final int INTO_ROOM_SERVER = 20001;//进入房间
    public static final int INTO_ROOM_CLIENT = 20002;//进入房间
    public static final int ADD_ROOM_CLIENT = 20003;//有人进入
    public static final int SEAT_ROOM_SERVER = 20004;//坐下
    public static final int START_SERVER = 20005;//开始
    public static final int START_CLIENT = 20006;//开始
    public static final int GRAB_SERVER = 20007;//抢庄
    public static final int GRAB_CLIENT = 20008;//抢庄
    public static final int PLAY_SERVER = 20009;//下注
    public static final int PLAY_CLIENT = 20010;//下注
    public static final int SET_BANKER_CLIENT = 20011;//确定庄家
    public static final int DEAL_CARD_CLIENT = 20012;//发牌
    public static final int OPEN_CARD_SERVER = 20013;//开牌
    public static final int OPEN_CARD_CLIENT = 20014;//开牌
    public static final int RESULT_CLIENT = 20015;//比分结果
    public static final int READY_SERVER = 20016;//准备
    public static final int READY_CLIENT = 20017;//准备
    public static final int EXIT_SERVER = 20018;//退出
    public static final int EXIT_CLIENT = 20019;//退出
    public static final int DELETE_SERVER = 20020;//请求解散
    public static final int DELETE_CLIENT = 20021;//请求解散
    public static final int DELETE_CONFIRM_SERVER = 20022;//请求解散
    public static final int DELETE_CONFIRM_CLIENT = 20023;//请求解散
    public static final int DELETED_CLIENT = 20024;//解散成功
    public static final int IMG_TEXT_SERVER = 20025;//表情文字
    public static final int IMG_TEXT_CLIENT = 20026;//表情文字
    public static final int INTERACTION_SERVER = 20027;//互动
    public static final int INTERACTION_CLIENT = 20028;//互动
    public static final int VOICE_SERVER = 20029;//语音
    public static final int VOICE_CLIENT = 20030;//语音
    public static final int COMPLETE_SERVER = 20031;//完成
    public static final int COMPLETE_CLIENT = 20032;//完成
    public static final int OVER_CLIENT = 20033;//游戏结束
}