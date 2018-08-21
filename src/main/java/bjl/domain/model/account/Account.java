package bjl.domain.model.account;


import bjl.core.enums.EnableStatus;
import bjl.core.enums.Sex;
import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.role.Role;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
public class Account extends ConcurrencySafeEntity {

    private String userName;        //数字ID
    private String password;        //密码
    private String bankPwd;         //银行密码
    private String salt;            //密码加密盐
    private String lastLoginIP;     //最后登录ip
    private Date lastLoginDate;     //最后登录时间
    private String lastLoginPlatform;//最后登录平台
    private String area;             //地区
    private List<Role> roles;        //用户角色
    private EnableStatus status;     //状态
    private String head;             //头像
    private String token;            //登录账号
    private String name;             //昵称
    private Sex sex;                 //性别
    private Boolean vip;             //是否vip
    private String phoneNo;          //手机号
    private Integer gag;             //禁言  0.不禁言 1.禁言
    private BigDecimal R;           //玩家R值
    private BigDecimal ratio;       //上级占比
    private Integer question;        //问题索引
    private String answer;           //问题答案
    private Account parent; //推广上级
    private Account referee; //推荐人
    private String playName;        //注册的体育游戏名称，如果存在，则已注册
    private String mail;//邮箱

    public Account getReferee() {
        return referee;
    }

    public void setReferee(Account referee) {
        this.referee = referee;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    /*  您母亲的姓名是？ 1

        您父亲的姓名是？ 2

        您配偶的姓名是？3

        您的出生地是？4

        您高中班主任的名字是？5

        您初中班主任的名字是？6

        您小学班主任的名字是？7

        您的小学校名是？8

        您的学号（或工号）是？9

        您父亲的生日是？10

        您母亲的生日是？11

        您配偶的生日是？12
    * */

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public BigDecimal getR() {
        return R;
    }

    public void setR(BigDecimal r) {
        R = r;
    }

    public Integer getGag() {
        return gag;
    }

    public void setGag(Integer gag) {
        this.gag = gag;
    }

    public String getBankPwd() {
        return bankPwd;
    }

    public void setBankPwd(String bankPwd) {
        this.bankPwd = bankPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void changeUserName(String userName) {
        this.userName = userName;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeSalt(String salt) {
        this.salt = salt;
    }

    public void changeLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public void changeLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void changeLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
    }

    public void changeRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void changeStatus(EnableStatus status) {
        this.status = status;
    }

    public void changeHead(String head) {
        this.head = head;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setSalt(String salt) {
        this.salt = salt;
    }

    private void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    private void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    private void setLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
    }

    private void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    private void setStatus(EnableStatus status) {
        this.status = status;
    }

    private void setHead(String head) {
        this.head = head;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public String getLastLoginPlatform() {
        return lastLoginPlatform;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public String getHead() {
        return head;
    }

    public Account() {
        super();
    }

    public Account(String token, String head, String userName, String password,String bankPwd, String salt, String lastLoginIP, Date lastLoginDate, String lastLoginPlatform,
                   List<Role> roles, EnableStatus status,Sex sex,String name) {
        if("".equals(token)){
            this.token = userName;
        }else {
            this.token = token;
        }
        this.sex = sex;
        this.head = head;
        this.userName = userName;
        this.password = password;
        this.bankPwd = bankPwd;
        this.salt = salt;
        this.lastLoginIP = lastLoginIP;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginPlatform = lastLoginPlatform;
        this.roles = roles;
        this.status = status;
        this.head = head;
        this.name = name;
        this.setCreateDate(new Date());
        this.gag = 0;
    }
}
