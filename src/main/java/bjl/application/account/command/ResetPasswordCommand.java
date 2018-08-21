package bjl.application.account.command;


import bjl.application.shared.command.SharedCommand;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by pengyi on 2016/3/30 0030.
 */
public class ResetPasswordCommand extends SharedCommand {

    @NotBlank(message = "{user.password.NotBlank.messages}")
    private String password;

    @NotBlank(message = "{user.confirmPassword.NotBlank.messages}")
    private String confirmPassword; //确认密码

    private String newPassword;

    private String verificationCode;    //验证码

    private String userName;        //账号

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
