package bjl.application.notice.reprensentation;

/**
 * Created by zhangjin on 2018/1/17.
 */
public class ApiNoticeRepresentation {

    private String title;           //公告标题
    private String content;         //公告内容
    private String image;           //图片链接
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
