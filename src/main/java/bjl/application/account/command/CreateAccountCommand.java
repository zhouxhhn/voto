package bjl.application.account.command;

import bjl.core.enums.EnableStatus;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30 0030.
 */
public class CreateAccountCommand {

    protected String userName;        //用户名
    protected List<String> roles;            //用户角色
    protected EnableStatus status;     //状态
    protected String password;        //密码
    protected String confirmPassword; //确认密码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
