package bjl.application.account.representation;


import java.util.Date;

/**
 * Created by pengyi on 2016/3/30 0030.
 */
public class ApiAccountRepresentation {

    private String id;
    private Date createDate;

    private String userName;        //用户名
    private String lastLoginIP;     //最后登录ip
    private Date lastLoginDate;     //最后登录时间
    private String head;        //头像

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
