package bjl.application.publicRecharge.representation;

/**
 * Created by zhangjin on 2018/6/21
 */
public class ApiPublicRechargeRepresentation {

    private String title; //标题
    private String content; //内容
    private String image;//图片
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
