package bjl.core.api;

/**
 * Tcp消息头
 * Created by zhangjin on 2017/10/27.
 */
public class MessageID {

    //接受消息号

    public static final short _LOGIN = 8001;            //登录
    public static final short _REGISTER = 8002;         //注册
    public static final short _USERINFO = 8003;     //个人信息
    public static final short _MODIFYNAME = 8004; //修改昵称
    public static final short _MODIFYPASSWORD = 8005; //修改密码
    public static final short _TRANSFERSCORE = 8006; //转账分数
    public static final short _TRANSFERRECORD = 8007; //获取转账记录
    public static final short _BANKINFO = 8008; //获取银行信息
    public static final short _BANDBANK = 8009; //绑定银行卡
    public static final short _GETSAFEBOX = 8010; //打开保险箱
    public static final short _SAVESCORE = 8011; //保险箱存积分
    public static final short _TAKESCORE = 8012; //保险箱取积分
    public static final short _RECHARGEDTL = 8013; //充值明细
    public static final short _WITHDRAW = 8014; //提现申请
    public static final short _GETPHONEBET = 8015; //电话投注数据请求
    public static final short _APPLYPHONEBET = 8016; //请求电话投注
    public static final short _CHATINFO = 8017; //聊天信息
    public static final short _RADIOINFO = 8018; //广播聊天信息
    public static final short _GETCHATRECORD = 8019; //获取聊天记录
    public static final short _GETTABLEDATA= 8020; //获取表格数据
    public static final short _NOTICE = 8021; //系统公告
    public static final short _CHECKQRCODE  = 8022; //扫码检测
    public static final short _MODIFYTWOPWD   = 8023; //修改二级密码
    public static final short _RECHARFECTL   = 8024; //支付控制
    public static final short _PUSHGOLD = 8025; //推送玩家积分
    public static final short _FINDPASSWORD = 8026; //找回密码
    public static final short _FINDTWOPWD = 8027; //找回二级密码
    public static final short _PLAYERLIST = 8028; //玩家列表
    public static final short _ROOMCHECK = 8029; //房间检查
    public static final short _ROOMTIREN = 8030; //房间踢人
    public static final short _GETSPREAD = 8031; //获取推广配置
    public static final short _USERSPREAD = 8032; //获取玩家收益信息
    public static final short _RECEIVE = 8033; //领取玩家收益
    public static final short _TOTALSUM = 8034; //历史推广人数
    public static final short _TODAYSUM= 8035; //今日推广人数
    public static final short _YESTERDAYPROFIT = 8036; //昨日收益
    public static final short _TODAYPROFIT= 8037; //今日收益
    public static final short _WEEKPROFIT = 8038; //本周收益
    public static final short _LASTWEEKPROFIT= 8039; //上周收益
    public static final short _MONTHPROFIT = 8040; //本月收益
    public static final short _SUBDETAILED = 8041; //下级详情
    public static final short _GETGAMEURL = 8042; //获取游戏链接
    public static final short _VALIDATECODE= 8043; //获取图片验证码连接
    public static final short _ISBINDTSECURITY = 8044; //是否绑定密保
    public static final short _BINDTSECURITY = 8045; //绑定密保
    public static final short _GETACTIVITY = 8046; //获取活动列表
    public static final short _GETCAROUSEL = 8047; //获取轮播图
    public static final short _GETGUIDE = 8048; //获取新手指南
    public static final short _GETDETAILED = 8049; //获取个人流水
    public static final short _GETARTICLE = 8050; //获取文章详情

    //返回消息号

    public static final short LOGIN_ = 9001;
    public static final short REGISTER_ = 9002;
    public static final short USERINFO_ = 9003;
    public static final short MODIFYNAME_ = 9004; //修改昵称
    public static final short MODIFYPASSWORD_ = 9005; //修改密码
    public static final short TRANSFERSCORE_ = 9006; //转账
    public static final short TRANSFERRECORD_ = 9007; //获取转账记录
    public static final short BANKINFO_ = 9008; //获取银行信息
    public static final short BANDBANK_ = 9009; //绑定银行卡
    public static final short GETSAFEBOX_ = 9010; //打开保险箱
    public static final short SAVESCORE_ = 9011; //保险箱存积分
    public static final short TAKESCORE_ = 9012; //保险箱取积分
    public static final short RECHARGEDTL_ = 9013; //充值明细
    public static final short WITHDRAW_ = 9014; //提现申请
    public static final short GETPHONEBET_ = 9015; //电话投注数据请求
    public static final short APPLYPHONEBET_ = 9016; //请求电话投注
    public static final short CHATINFO_ = 9017; //聊天信息
    public static final short RADIOINFO_ = 9018; //广播聊天信息
    public static final short GETCHATRECORD_ = 9019; //获取聊天记录
    public static final short GETTABLEDATA_ = 9020; //获取表格数据
    public static final short NOTICE_  = 9021; //系统公告
    public static final short CHECKQRCODE_  = 9022; //扫码检测
    public static final short MODIFYTWOPWD_   = 9023; //修改二级密码
    public static final short RECHARFECTL_   = 8024; //支付控制
    public static final short PUSHGOLD_ = 9025; //推送玩家积分
    public static final short FINDPASSWORD_ = 9026; //找回密码
    public static final short FINDTWOPWD_ = 9027; //找回二级密码
    public static final short PLAYERLIST_ = 9028; //玩家列表
    public static final short ROOMCHECK_ = 9029; //房间检查
    public static final short ROOMTIREN_ = 9030; //房间检查
    public static final short GETSPREAD_ = 9031; //获取推广配置
    public static final short USERSPREAD_ = 9032; //获取玩家收益信息
    public static final short RECEIVE_ = 9033; //领取玩家收益
    public static final short TOTALSUM_ = 9034; //历史推广人数
    public static final short TODAYSUM_ = 9035; //今日推广人数
    public static final short YESTERDAYPROFIT_ = 9036; //昨日收益
    public static final short TODAYPROFIT_= 9037; //今日收益
    public static final short WEEKPROFIT_ = 9038; //本周收益
    public static final short LASTWEEKPROFIT_ = 9039; //上周收益
    public static final short MONTHPROFIT_ = 9040; //本月收益
    public static final short SUBDETAILED_ = 9041; //下级详情
    public static final short GETGAMEURL_ = 9042; //获取游戏链接
    public static final short VALIDATECODE_ = 9043; //获取图片验证码连接
    public static final short ISBINDTSECURITY_ = 9044; //是否绑定密保
    public static final short BINDTSECURITY_ = 9045; //绑定密保
    public static final short GETACTIVITY_ = 9046; //获取活动列表
    public static final short GETCAROUSEL_ = 9047; //获取轮播图
    public static final short GETGUIDE_ = 9048; //获取新手指南
    public static final short GETDETAILED_ = 9049; //获取个人明细

}
