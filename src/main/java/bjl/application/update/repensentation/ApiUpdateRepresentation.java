package bjl.application.update.repensentation;

/**
 * Created by zhangjin on 2017/12/23.
 */
public class ApiUpdateRepresentation {


    private String android_version;  //安卓版本号
    private String ios_version;  //ios版本号
    private String html_version;//资源版本号
    private String android_download_url ;  //安卓包地址
    private String ios_download_url;  //IOS包地址
    private String html_download_url; //资源链接

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getIos_version() {
        return ios_version;
    }

    public void setIos_version(String ios_version) {
        this.ios_version = ios_version;
    }

    public String getHtml_version() {
        return html_version;
    }

    public void setHtml_version(String html_version) {
        this.html_version = html_version;
    }

    public String getAndroid_download_url() {
        return android_download_url;
    }

    public void setAndroid_download_url(String android_download_url) {
        this.android_download_url = android_download_url;
    }

    public String getIos_download_url() {
        return ios_download_url;
    }

    public void setIos_download_url(String ios_download_url) {
        this.ios_download_url = ios_download_url;
    }

    public String getHtml_download_url() {
        return html_download_url;
    }

    public void setHtml_download_url(String html_download_url) {
        this.html_download_url = html_download_url;
    }
}
