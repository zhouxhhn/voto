package bjl.core.upload;

import java.util.Arrays;
import java.util.List;

/**
 * 文件上传配置
 * Created by pengyi on 2016/4/11.
 */
public class FileUploadConfig {

    private String path;    //上传工作路径

    private long maxSize;   //最大上传大小

    private List<String> type;  //上传类型

    private String folder;       // 保存文件的目录(domainName/folder/)

    private String domainName;      //上传工作地址

    private String QRCode;      //存放二维码目录

    private String chatImg;  //存放聊天图片

    private String betImg;   //下注图片

    private String lottery; //开奖图片

    private String validateCode;//验证码

    private String installation; //安装包

    public String getInstallation() {
        return installation;
    }

    public void setInstallation(String installation) {
        this.installation = installation;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getLottery() {
        return lottery;
    }

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    public String getBetImg() {
        return betImg;
    }

    public void setBetImg(String betImg) {
        this.betImg = betImg;
    }

    private String resourcePackage; //资源包路径

    public String getChatImg() {
        return chatImg;
    }

    public void setChatImg(String chatImg) {
        this.chatImg = chatImg;
    }

    public String getResourcePackage() {
        return resourcePackage;
    }

    public void setResourcePackage(String resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(String type) {
        String[] strings = type.split(",");
        this.type = Arrays.asList(strings);
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        domainName = domainName.toLowerCase();
        if (!domainName.startsWith("http://")) {
            domainName = "http://" + domainName;
        }

        if (!domainName.endsWith("/")) {
            domainName += "/";
        }
        this.domainName = domainName;
    }
}
