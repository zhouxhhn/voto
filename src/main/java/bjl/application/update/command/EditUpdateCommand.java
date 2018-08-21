package bjl.application.update.command;

/**
 * Created by zhangjin on 2017/12/23.
 */
public class EditUpdateCommand {

    private String androidVersion;  //安卓版本号
    private String iosVersion;  //ios版本号
    private String htmlVersion;//资源版本号
    private String androidUrl;  //安卓包地址
    private String iosUrl;  //IOS包地址
    private String htmlUrl; //资源链接
    private String modifier;

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getHtmlVersion() {
        return htmlVersion;
    }

    public void setHtmlVersion(String htmlVersion) {
        this.htmlVersion = htmlVersion;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
